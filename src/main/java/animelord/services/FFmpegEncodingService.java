package animelord.services;

import animelord.dao.EpisodeDAO;
import animelord.dao.EpisodeFileDAO;
import animelord.dao.UploadQueueDAO;

import animelord.entities.Episode;
import animelord.entities.EpisodeFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FFmpegEncodingService {

    private static final String FFMPEG_PATH =
            "ffmpeg";

    private static final String STREAM_ROOT =
            Paths.get(
                    "D:",
                    "AnimeLordStorage",
                    "stream"
            ).toString();

    public void processEpisode(
            int episodeId)
            throws Exception {

        UploadQueueDAO queueDAO =
                new UploadQueueDAO();

        EpisodeDAO episodeDAO =
                new EpisodeDAO();

        EpisodeFileDAO episodeFileDAO =
                new EpisodeFileDAO();

        try {

            /*
                PROCESSING
            */
            queueDAO.updateStatus(
                    episodeId,
                    "PROCESSING"
            );

            /*
                LOAD EPISODE
            */
            Episode episode =
                    episodeDAO.getEpisodeById(
                            episodeId
                    );

            if(episode == null){

                throw new Exception(
                        "Episode not found."
                );

            }

            /*
                VIDEO PATH
            */
            String videoPath =
                    episode.getUploadedVideoPath();

            File videoFile =
                    new File(
                            videoPath
                    );

            if(!videoFile.exists()){

                throw new Exception(
                        "Video file missing."
                );

            }

            /*
                STREAM DIRECTORY

                stream/<animeId>/episode_<episodeId>/
            */
            Path episodeDirectory =
                    Paths.get(
                            STREAM_ROOT,
                            String.valueOf(
                                    episode.getAnimeId()
                            ),
                            "episode_"
                            + episodeId
                    );

            File episodeDirFile =
                    episodeDirectory.toFile();

            if(!episodeDirFile.exists()){

                episodeDirFile.mkdirs();

            }

            /*
                THUMBNAIL
            */
            String thumbnailFile =
                    episodeDirectory
                    .resolve(
                            "thumbnail.jpg"
                    )
                    .toString();

            ProcessBuilder thumbnailProcess =
                        new ProcessBuilder(
                                FFMPEG_PATH,
                                "-y",
                                "-ss",
                                "00:00:10",
                                "-i",
                                videoPath,
                                "-frames:v",
                                "1",
                                "-update",
                                "1",
                                thumbnailFile
                        );

            thumbnailProcess
                    .inheritIO();

            Process process =
                    thumbnailProcess.start();

            int exitCode =
                    process.waitFor();

            if(exitCode != 0){

                throw new Exception(
                        "Thumbnail generation failed."
                );

            }
            /*
                480P DIRECTORY
            */
            Path hls480Directory =
                    episodeDirectory.resolve(
                            "480p"
                    );

            File hls480DirFile =
                    hls480Directory.toFile();

            if(!hls480DirFile.exists()){

                hls480DirFile.mkdirs();

            }

            /*
                PLAYLIST
            */
            String playlist480 =
                    hls480Directory
                            .resolve(
                                    "index.m3u8"
                            )
                            .toString();

            /*
                SEGMENTS
            */
            String segmentPattern =
                    hls480Directory
                            .resolve(
                                    "segment_%03d.ts"
                            )
                            .toString();

            /*
                FFMPEG HLS
            */
            ProcessBuilder hls480Process =
                    new ProcessBuilder(
                            FFMPEG_PATH,

                            "-y",

                            "-i",
                            videoPath,

                            "-vf",
                            "scale=-2:480",

                            "-c:v",
                            "libx264",

                            "-preset",
                            "veryfast",

                            "-crf",
                            "23",

                            "-c:a",
                            "aac",

                            "-b:a",
                            "128k",

                            "-hls_time",
                            "10",

                            "-hls_playlist_type",
                            "vod",

                            "-hls_segment_filename",
                            segmentPattern,

                            playlist480
                    );

            hls480Process.inheritIO();

            Process hlsProcess =
                    hls480Process.start();

            int hlsExitCode =
                    hlsProcess.waitFor();

            if(hlsExitCode != 0){

                throw new Exception(
                        "480p HLS generation failed."
                );

            }

            /*
                STORE DB RECORD

                HLS not generated yet,
                so master playlist remains empty.
            */
            EpisodeFile episodeFile =
                    new EpisodeFile();

            episodeFile.setEpisodeId(
                    episodeId
            );

            episodeFile.setMasterPlaylist(
                    "/stream/"
                    + episode.getAnimeId()
                    + "/episode_"
                    + episodeId
                    + "/480p/index.m3u8"
            );

            episodeFile.setThumbnailPath(
                    "/stream/"
                    + episode.getAnimeId()
                    + "/episode_"
                    + episodeId
                    + "/thumbnail.jpg"
            );

            if(episodeFileDAO.episodeFileExists(
                    episodeId
            )){

                episodeFileDAO.updateEpisodeFile(
                        episodeFile
                );

            }
            else{

                episodeFileDAO.addEpisodeFile(
                        episodeFile
                );

            }

            /*
                SUCCESS
            */
            queueDAO.updateStatus(
                    episodeId,
                    "COMPLETED"
            );

        }
        catch(Exception e){

            e.printStackTrace();

            queueDAO.updateStatus(
                    episodeId,
                    "FAILED"
            );

            throw e;
        }
    }
}