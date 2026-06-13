package animelord.models;

import animelord.dao.EpisodeDAO;
import animelord.dao.UploadQueueDAO;

import animelord.entities.Episode;
import animelord.entities.UploadQueue;

import animelord.services.FFprobeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

public class UploadEpisodeModel
        implements Model {

    private static final String VIDEO_DIRECTORY =
            Paths.get(
                    "D:",
                    "AnimeLordStorage",
                    "uploads"
            ).toString();

    private static final Set<String> ALLOWED_VIDEO_TYPES =
            Set.of(
                    "video/mp4",
                    "video/x-matroska",
                    "video/quicktime"
            );

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {

            int animeId =
                    Integer.parseInt(
                            request.getParameter(
                                    "animeId"
                            )
                    );

            int episodeNumber =
                    Integer.parseInt(
                            request.getParameter(
                                    "episodeNumber"
                            )
                    );
            EpisodeDAO episodeDAO =
                    new EpisodeDAO();

            if(
                episodeDAO.episodeExists(
                        animeId,
                        episodeNumber
                )
            ){

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Episode "
                                + episodeNumber
                                + " already exists for this anime."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=uploadEpisode"
                );

                return;
            }

            String episodeTitle =
                    request.getParameter(
                            "episodeTitle"
                    );

            String description =
                    request.getParameter(
                            "description"
                    );

            /*
                EPISODE TITLE OPTIONAL
            */
            if (episodeTitle == null
                    || episodeTitle.isBlank()) {

                episodeTitle =
                        "Episode "
                        + episodeNumber;

            }

            Part videoPart =
                    request.getPart(
                            "videoFile"
                    );

            /*
                VIDEO REQUIRED
            */
            if (videoPart == null
                    || videoPart.getSize() == 0) {

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Please select a video file."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=uploadEpisode"
                );

                return;
            }

            /*
                VIDEO TYPE VALIDATION
            */
            String contentType =
                    videoPart.getContentType();

            if (!ALLOWED_VIDEO_TYPES.contains(contentType)) {

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Unsupported video format. Only MP4, MKV and MOV are allowed."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=uploadEpisode"
                );

                return;
            }

            /*
                CREATE DIRECTORY
            */
            File uploadDir =
                    new File(
                            VIDEO_DIRECTORY
                    );

            if (!uploadDir.exists()) {

                uploadDir.mkdirs();

            }

            /*
                ORIGINAL FILE NAME
            */
            String originalName =
                    Paths.get(
                            videoPart.getSubmittedFileName()
                    )
                    .getFileName()
                    .toString();

            /*
                UNIQUE FILE NAME
            */
            String uniqueName =
                    UUID.randomUUID()
                    + "_"
                    + originalName;

            /*
                FULL PATH
            */
            String fullPath =
                    Paths.get(
                            VIDEO_DIRECTORY,
                            uniqueName
                    ).toString();

            /*
                SAVE VIDEO
            */
            videoPart.write(
                    fullPath
            );

            /*
                CREATE EPISODE
            */
            Episode episode =
                    new Episode();

            episode.setAnimeId(
                    animeId
            );

            episode.setEpisodeNumber(
                    episodeNumber
            );

            episode.setEpisodeTitle(
                    episodeTitle
            );

            episode.setDescription(
                    description
            );

            /*
                DURATION UNKNOWN
                UNTIL FFMPEG/FFPROBE
            */
            FFprobeService ffprobe = new FFprobeService();

            int duration = ffprobe.getDurationSeconds(
                            fullPath
                    );
            int videoHeight =
                        ffprobe.getVideoHeight(
                                fullPath
                        );

            episode.setDurationSeconds(
                    duration
            );

            /*
                STORE ORIGINAL VIDEO PATH
            */
            episode.setUploadedVideoPath(
                    fullPath
            );


            int episodeId =
                    episodeDAO.addEpisodeAndGetId(
                            episode
                    );

            /*
                DB SAVE FAILED
            */
            if (episodeId <= 0) {

                new File(
                        fullPath
                ).delete();

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=uploadEpisode"
                );

                return;
            }

            /*
                ADD TO ENCODING QUEUE
            */
            UploadQueue queue =
                    new UploadQueue();

            queue.setEpisodeId(
                    episodeId
            );

            queue.setStatus(
                    "QUEUED"
            );

            /*
                ENCODING OPTIONS
            */
            String encodingMode =
                    request.getParameter(
                            "encodingMode"
                    );

            if("CUSTOM".equalsIgnoreCase(
                    encodingMode
            )){

                queue.setEncode1080p(
                        request.getParameter(
                                "encode1080p"
                        ) != null
                );

                queue.setEncode720p(
                        request.getParameter(
                                "encode720p"
                        ) != null
                );

                queue.setEncode480p(
                        request.getParameter(
                                "encode480p"
                        ) != null
                );

                /*
                    PREVENT EMPTY SELECTION
                */
                if(!queue.isEncode1080p()
                        && !queue.isEncode720p()
                        && !queue.isEncode480p()){

                    queue.setEncode1080p(
                            true
                    );

                    queue.setEncode720p(
                            true
                    );

                    queue.setEncode480p(
                            true
                    );
                }

            }
            else{

                /*
                    AUTO MODE
                    ENABLE ONLY VALID
                    RESOLUTIONS
                */

                if(videoHeight >= 1080){

                    queue.setEncode1080p(
                            true
                    );

                    queue.setEncode720p(
                            true
                    );

                    queue.setEncode480p(
                            true
                    );

                }
                else if(videoHeight >= 720){

                    queue.setEncode1080p(
                            false
                    );

                    queue.setEncode720p(
                            true
                    );

                    queue.setEncode480p(
                            true
                    );

                }
                else{

                    queue.setEncode1080p(
                            false
                    );

                    queue.setEncode720p(
                            false
                    );

                    queue.setEncode480p(
                            true
                    );

                }

            }

            UploadQueueDAO queueDAO =
                    new UploadQueueDAO();

            queueDAO.addToQueue(
                    queue
            );

            /*
                SUCCESS
            */
            request.getSession()
            .setAttribute(
                    "successMessage",
                    "Episode uploaded successfully and added to encoding queue."
            );
            
                response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageEpisodes"
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            request.getSession()
                    .setAttribute(
                            "errorMessage",
                            "Failed to upload episode. "
                            + e.getMessage()
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=uploadEpisode"
            );
        }
    }
}