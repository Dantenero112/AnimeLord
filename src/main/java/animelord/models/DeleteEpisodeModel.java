package animelord.models;

import animelord.dao.EpisodeDAO;
import animelord.entities.Episode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteEpisodeModel
        implements Model {

    private static final String STORAGE_ROOT =
            Paths.get(
                    "D:",
                    "AnimeLordStorage"
            ).toString();

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {

            int episodeId =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );

            EpisodeDAO episodeDAO =
                    new EpisodeDAO();

            Episode episode =
                    episodeDAO.getEpisodeById(
                            episodeId
                    );

            if(episode == null){

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Episode not found."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageEpisodes"
                );

                return;
            }

            /*
                DELETE ORIGINAL SOURCE VIDEO

                D:\AnimeLordStorage\\uploads\
            */
            String uploadedVideoPath =
                    episode.getUploadedVideoPath();

            if(uploadedVideoPath != null
                    && !uploadedVideoPath.isBlank()){

                File sourceVideo =
                        new File(
                                uploadedVideoPath
                        );

                if(sourceVideo.exists()){

                    sourceVideo.delete();

                }

            }

            /*
                DELETE STREAM DIRECTORY

                D:\AnimeLordStorage\stream\<animeId>\episode_<episodeId>\
            */
            Path streamDirectory =
                    Paths.get(
                            STORAGE_ROOT,
                            "stream",
                            String.valueOf(
                                    episode.getAnimeId()
                            ),
                            "episode_"
                            + episodeId
                    );

            deleteDirectory(
                    streamDirectory.toFile()
            );

            /*
                DELETE EPISODE

                ON DELETE CASCADE removes:

                episode_files
                upload_queue
                subtitles
                broken_episode_reports
            */
            boolean deleted =
                    episodeDAO.deleteEpisode(
                            episodeId
                    );

            if(deleted){

                request.getSession()
                        .setAttribute(
                                "successMessage",
                                "Episode deleted successfully."
                        );

            }
            else{

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Failed to delete episode."
                        );

            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageEpisodes"
            );

        }
        catch(Exception e){

            e.printStackTrace();

            request.getSession()
                    .setAttribute(
                            "errorMessage",
                            "Error deleting episode."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageEpisodes"
            );

        }

    }

    /*
        RECURSIVE DIRECTORY DELETE
    */
    private void deleteDirectory(
            File file) {

        if(file == null
                || !file.exists()){

            return;
        }

        if(file.isDirectory()){

            File[] children =
                    file.listFiles();

            if(children != null){

                for(File child
                        : children){

                    deleteDirectory(
                            child
                    );

                }

            }

        }

        file.delete();

    }

}