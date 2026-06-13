package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;

import animelord.entities.Anime;
import animelord.entities.Episode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteAnimeModel
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

            int animeId =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );

            AnimeDAO animeDAO =
                    new AnimeDAO();

            EpisodeDAO episodeDAO =
                    new EpisodeDAO();

            Anime anime =
                    animeDAO.getAnimeById(
                            animeId
                    );

            if(anime == null){

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Anime not found."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageAnime"
                );

                return;
            }

            /*
                DELETE ORIGINAL SOURCE VIDEOS

                D:\AnimeLordStorage\\uploads\
            */
            List<Episode> episodes =
                    episodeDAO.getEpisodesByAnime(
                            animeId
                    );

            for(Episode episode
                    : episodes){

                String videoPath =
                        episode.getUploadedVideoPath();

                if(videoPath != null
                        && !videoPath.isBlank()){

                    File sourceVideo =
                            new File(
                                    videoPath
                            );

                    if(sourceVideo.exists()){

                        sourceVideo.delete();

                    }

                }

            }

            /*
                DELETE COVER IMAGE
            */
            if(anime.getCoverImage() != null
                    && !anime.getCoverImage()
                    .isBlank()){

                File coverFile =
                        Paths.get(
                                STORAGE_ROOT,
                                anime.getCoverImage()
                                        .replaceFirst(
                                                "^/",
                                                ""
                                        )
                        )
                        .toFile();

                if(coverFile.exists()){

                    coverFile.delete();

                }

            }

            /*
                DELETE BANNER IMAGE
            */
            if(anime.getBannerImage() != null
                    && !anime.getBannerImage()
                    .isBlank()){

                File bannerFile =
                        Paths.get(
                                STORAGE_ROOT,
                                anime.getBannerImage()
                                        .replaceFirst(
                                                "^/",
                                                ""
                                        )
                        )
                        .toFile();

                if(bannerFile.exists()){

                    bannerFile.delete();

                }

            }

            /*
                DELETE STREAM DIRECTORY

                D:\AnimeLordStorage\stream\<animeId>\
            */
            Path streamDirectory =
                    Paths.get(
                            STORAGE_ROOT,
                            "stream",
                            String.valueOf(
                                    animeId
                            )
                    );

            deleteDirectory(
                    streamDirectory.toFile()
            );

            /*
                DELETE ANIME

                CASCADE REMOVES:

                anime_genres
                episodes
                episode_files
                subtitles
                upload_queue
                broken_episode_reports
            */
            boolean deleted =
                    animeDAO.deleteAnime(
                            animeId
                    );

            if(deleted){

                request.getSession()
                        .setAttribute(
                                "successMessage",
                                "Anime deleted successfully."
                        );

            }
            else{

                request.getSession()
                        .setAttribute(
                                "errorMessage",
                                "Failed to delete anime."
                        );

            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageAnime"
            );

        }
        catch(Exception e){

            e.printStackTrace();

            request.getSession()
                    .setAttribute(
                            "errorMessage",
                            "Error deleting anime."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageAnime"
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