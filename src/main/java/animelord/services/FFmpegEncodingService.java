package animelord.services;

import animelord.dao.EpisodeDAO;
import animelord.dao.EpisodeFileDAO;
import animelord.dao.UploadQueueDAO;

import animelord.entities.Episode;
import animelord.entities.EpisodeFile;
import animelord.entities.UploadQueue;

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
    
    private void generateHLS(
            String videoPath,
            Path episodeDirectory,
            String qualityFolder,
            int height)
            throws Exception {

        /*
            QUALITY DIRECTORY

            Example:
            stream/4/episode_7/720p/
        */
        Path qualityDirectory =
                episodeDirectory.resolve(
                        qualityFolder
                );

        File qualityDirFile =
                qualityDirectory.toFile();

        if(!qualityDirFile.exists()){

            qualityDirFile.mkdirs();

        }

        /*
            PLAYLIST FILE

            index.m3u8
        */
        String playlistFile =
                qualityDirectory
                        .resolve(
                                "index.m3u8"
                        )
                        .toString();

        /*
            TS SEGMENTS

            segment_000.ts
            segment_001.ts
            ...
        */
        String segmentPattern =
                qualityDirectory
                        .resolve(
                                "segment_%03d.ts"
                        )
                        .toString();

        /*
            VIDEO BITRATE
        */
        String videoBitrate;

        switch(height){

            case 1080:

                videoBitrate = "5000k";

                break;

            case 720:

                videoBitrate = "2800k";

                break;

            default:

                videoBitrate = "1200k";

                break;
        }

        /*
            FFMPEG HLS ENCODING
        */
        ProcessBuilder processBuilder =
                new ProcessBuilder(

                        FFMPEG_PATH,

                        "-y",

                        "-i",
                        videoPath,

                        /*
                            SCALE VIDEO
                        */
                        "-vf",
                        "scale=-2:" + height,

                        /*
                            VIDEO CODEC
                        */
                        "-c:v",
                        "libx264",

                        "-preset",
                        "veryfast",

                        "-crf",
                        "23",

                        "-b:v",
                        videoBitrate,

                        /*
                            AUDIO
                        */
                        "-c:a",
                        "aac",

                        "-b:a",
                        "128k",

                        /*
                            HLS
                        */
                        "-hls_time",
                        "10",

                        "-hls_playlist_type",
                        "vod",
                        
                        "-hls_flags",
                        "independent_segments",
                        
                        "-hls_segment_filename",
                        segmentPattern,

                        playlistFile
                );

        processBuilder.inheritIO();

        Process process =
                processBuilder.start();

        int exitCode =
                process.waitFor();

        if(exitCode != 0){

            throw new Exception(
                    qualityFolder
                    + " HLS generation failed."
            );

        }

        /*
            VERIFY PLAYLIST EXISTS
        */
        File playlist =
                new File(
                        playlistFile
                );

        if(!playlist.exists()){

            throw new Exception(
                    qualityFolder
                    + " playlist not created."
            );

        }
    }
    
    private void generateMasterPlaylist(
            Path episodeDirectory,
            UploadQueue queue)
            throws Exception {

        /*
            MASTER PLAYLIST

            stream/<animeId>/episode_<episodeId>/master.m3u8
        */
        Path masterPlaylistPath =
                episodeDirectory.resolve(
                        "master.m3u8"
                );

        StringBuilder content =
                new StringBuilder();

        content.append(
                "#EXTM3U\n"
        );

        /*
            1080P
        */
        if(queue.isEncode1080p()){

            content.append(
                    "#EXT-X-STREAM-INF:"
                    + "BANDWIDTH=5000000,"
                    + "RESOLUTION=1920x1080\n"
            );

            content.append(
                    "1080p/index.m3u8\n"
            );
        }

        /*
            720P
        */
        if(queue.isEncode720p()){

            content.append(
                    "#EXT-X-STREAM-INF:"
                    + "BANDWIDTH=2800000,"
                    + "RESOLUTION=1280x720\n"
            );

            content.append(
                    "720p/index.m3u8\n"
            );
        }

        /*
            480P
        */
        if(queue.isEncode480p()){

            content.append(
                    "#EXT-X-STREAM-INF:"
                    + "BANDWIDTH=1200000,"
                    + "RESOLUTION=854x480\n"
            );

            content.append(
                    "480p/index.m3u8\n"
            );
        }

        java.nio.file.Files.writeString(
                masterPlaylistPath,
                content.toString()
        );

        /*
            VERIFY FILE CREATED
        */
        File masterFile =
                masterPlaylistPath.toFile();

        if(!masterFile.exists()){

            throw new Exception(
                    "Master playlist generation failed."
            );

        }
    }
 
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
            SET STATUS
        */
        queueDAO.updateStatus(
                episodeId,
                "PROCESSING"
        );

        /*
            LOAD QUEUE
        */
        UploadQueue queue =
                queueDAO.getQueueItemByEpisodeId(
                        episodeId
                );

        if(queue == null){

            throw new Exception(
                    "Queue item not found."
            );

        }

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
            SOURCE VIDEO
        */
        String videoPath =
                episode.getUploadedVideoPath();

        File sourceFile =
                new File(
                        videoPath
                );

        if(!sourceFile.exists()){

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
            GENERATE THUMBNAIL
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

        thumbnailProcess.inheritIO();

        Process thumbnail =
                thumbnailProcess.start();

        int thumbnailExitCode =
                thumbnail.waitFor();

        if(thumbnailExitCode != 0){

            throw new Exception(
                    "Thumbnail generation failed."
            );

        }

        /*
            1080P
        */
        if(queue.isEncode1080p()){

            generateHLS(
                    videoPath,
                    episodeDirectory,
                    "1080p",
                    1080
            );

        }

        /*
            720P
        */
        if(queue.isEncode720p()){

            generateHLS(
                    videoPath,
                    episodeDirectory,
                    "720p",
                    720
            );

        }

        /*
            480P
        */
        if(queue.isEncode480p()){

            generateHLS(
                    videoPath,
                    episodeDirectory,
                    "480p",
                    480
            );

        }

        /*
            MASTER PLAYLIST
        */
        generateMasterPlaylist(
                episodeDirectory,
                queue
        );

        /*
            STORE DB RECORD
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
                + "/master.m3u8"
        );

        episodeFile.setThumbnailPath(
                "/stream/"
                + episode.getAnimeId()
                + "/episode_"
                + episodeId
                + "/thumbnail.jpg"
        );

        if(
                episodeFileDAO.episodeFileExists(
                        episodeId
                )
        ){

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