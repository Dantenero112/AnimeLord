package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.GenreDAO;

import animelord.entities.Anime;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

public class UpdateAnimeModel
        implements Model {

    private static final String COVER_DIRECTORY =
            Paths.get(
                    "D:",
                    "AnimeLordStorage",
                    "anime",
                    "covers"
            ).toString();

    private static final String BANNER_DIRECTORY =
            Paths.get(
                    "D:",
                    "AnimeLordStorage",
                    "anime",
                    "banners"
            ).toString();

    private static final String STORAGE_ROOT =
            Paths.get(
                    "D:",
                    "AnimeLordStorage"
            ).toString();

    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
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

            String title =
                    request.getParameter(
                            "title"
                    );

            String synopsis =
                    request.getParameter(
                            "synopsis"
                    );

            String status =
                    request.getParameter(
                            "status"
                    );

            int releaseYear =
                    Integer.parseInt(
                            request.getParameter(
                                    "releaseYear"
                            )
                    );

            String[] selectedGenres =
                    request.getParameterValues(
                            "genreIds"
                    );

            AnimeDAO animeDAO =
                    new AnimeDAO();

            GenreDAO genreDAO =
                    new GenreDAO();

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
                VALIDATION
            */
            if(title == null
                    || title.isBlank()
                    || synopsis == null
                    || synopsis.isBlank()){

                request.setAttribute(
                        "errorMessage",
                        "Title and Synopsis are required."
                );

                request.setAttribute(
                        "anime",
                        anime
                );

                request.setAttribute(
                        "genreList",
                        genreDAO.getAllGenres()
                );

                request.setAttribute(
                        "view",
                        "editAnime"
                );

                request.getRequestDispatcher(
                        "/WEB-INF/views/admin.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            /*
                OLD FILES
            */
            String oldCover =
                    anime.getCoverImage();

            String oldBanner =
                    anime.getBannerImage();

            /*
                IMAGE PARTS
            */
            Part coverPart =
                    request.getPart(
                            "coverImage"
                    );

            Part bannerPart =
                    request.getPart(
                            "bannerImage"
                    );

            /*
                COVER UPDATE
            */
            if(coverPart != null
                    && coverPart.getSize() > 0){

                String contentType =
                        coverPart.getContentType();

                if(!ALLOWED_IMAGE_TYPES.contains(
                        contentType
                )){

                    throw new Exception(
                            "Invalid cover image."
                    );

                }

                String fileName =
                        UUID.randomUUID()
                        + "_"
                        + Paths.get(
                                coverPart.getSubmittedFileName()
                        )
                        .getFileName()
                        .toString();

                String fullPath =
                        Paths.get(
                                COVER_DIRECTORY,
                                fileName
                        ).toString();

                coverPart.write(
                        fullPath
                );

                anime.setCoverImage(
                        "/anime/covers/"
                        + fileName
                );

                /*
                    DELETE OLD COVER
                */
                if(oldCover != null
                        && !oldCover.isBlank()){

                    File oldFile =
                            Paths.get(
                                    STORAGE_ROOT,
                                    oldCover.replaceFirst(
                                            "^/",
                                            ""
                                    )
                            )
                            .toFile();

                    if(oldFile.exists()){

                        oldFile.delete();

                    }

                }

            }

            /*
                BANNER UPDATE
            */
            if(bannerPart != null
                    && bannerPart.getSize() > 0){

                String contentType =
                        bannerPart.getContentType();

                if(!ALLOWED_IMAGE_TYPES.contains(
                        contentType
                )){

                    throw new Exception(
                            "Invalid banner image."
                    );

                }

                String fileName =
                        UUID.randomUUID()
                        + "_"
                        + Paths.get(
                                bannerPart.getSubmittedFileName()
                        )
                        .getFileName()
                        .toString();

                String fullPath =
                        Paths.get(
                                BANNER_DIRECTORY,
                                fileName
                        ).toString();

                bannerPart.write(
                        fullPath
                );

                anime.setBannerImage(
                        "/anime/banners/"
                        + fileName
                );

                /*
                    DELETE OLD BANNER
                */
                if(oldBanner != null
                        && !oldBanner.isBlank()){

                    File oldFile =
                            Paths.get(
                                    STORAGE_ROOT,
                                    oldBanner.replaceFirst(
                                            "^/",
                                            ""
                                    )
                            )
                            .toFile();

                    if(oldFile.exists()){

                        oldFile.delete();

                    }

                }

            }

            /*
                UPDATE FIELDS
            */
            anime.setTitle(
                    title
            );

            anime.setSynopsis(
                    synopsis
            );

            anime.setReleaseYear(
                    releaseYear
            );

            anime.setStatus(
                    status
            );

            /*
                UPDATE ANIME
            */
            boolean success =
                    animeDAO.updateAnime(
                            anime
                    );

            if(!success){

                throw new Exception(
                        "Failed to update anime."
                );

            }

            /*
                UPDATE GENRES
            */
            genreDAO.deleteAnimeGenres(
                    animeId
            );

            if(selectedGenres != null
                    && selectedGenres.length > 0){

                int[] genreIds =
                        new int[
                                selectedGenres.length
                        ];

                for(int i = 0;
                        i < selectedGenres.length;
                        i++){

                    genreIds[i] =
                            Integer.parseInt(
                                    selectedGenres[i]
                            );

                }

                genreDAO.addAnimeGenres(
                        animeId,
                        genreIds
                );

            }

            request.getSession()
                    .setAttribute(
                            "successMessage",
                            "Anime updated successfully."
                    );

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
                            "Failed to update anime."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageAnime"
            );

        }

    }

}