package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.entities.Anime;
import animelord.dao.GenreDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

public class AddAnimeModel
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

            /*
                PRESERVE FORM VALUES
            */
            request.setAttribute(
                    "title",
                    title
            );

            request.setAttribute(
                    "synopsis",
                    synopsis
            );

            request.setAttribute(
                    "status",
                    status
            );

            request.setAttribute(
                    "releaseYear",
                    releaseYear
            );

            /*
                IMAGE FILES
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
                EMPTY FILE CHECK
            */
            if (coverPart == null
                    || coverPart.getSize() == 0
                    || bannerPart == null
                    || bannerPart.getSize() == 0) {

                showAddAnimePage(
                        request,
                        response,
                        "Cover image and banner image are required."
                );

                return;
            }

            /*
                CONTENT TYPE VALIDATION
            */
            String coverContentType =
                    coverPart.getContentType();

            String bannerContentType =
                    bannerPart.getContentType();

            if (!ALLOWED_IMAGE_TYPES.contains(
                    coverContentType)
                    || !ALLOWED_IMAGE_TYPES.contains(
                            bannerContentType)) {

                showAddAnimePage(
                        request,
                        response,
                        "Only JPG, PNG and WEBP images are allowed."
                );

                return;
            }

            /*
                TITLE / SYNOPSIS VALIDATION
            */
            if (title == null
                    || title.isBlank()
                    || synopsis == null
                    || synopsis.isBlank()) {

                showAddAnimePage(
                        request,
                        response,
                        "Title and synopsis are required."
                );

                return;
            }

            /*
                RELEASE YEAR VALIDATION
            */
            if (releaseYear < 1950
                    || releaseYear > 2100) {

                showAddAnimePage(
                        request,
                        response,
                        "Invalid release year."
                );

                return;
            }

            /*
                CREATE DIRECTORIES
            */
            File coverDir =
                    new File(
                            COVER_DIRECTORY
                    );

            if (!coverDir.exists()) {

                coverDir.mkdirs();

            }

            File bannerDir =
                    new File(
                            BANNER_DIRECTORY
                    );

            if (!bannerDir.exists()) {

                bannerDir.mkdirs();

            }

            /*
                ORIGINAL FILENAMES
            */
            String originalCoverName =
                    Paths.get(
                            coverPart.getSubmittedFileName()
                    )
                    .getFileName()
                    .toString();

            String originalBannerName =
                    Paths.get(
                            bannerPart.getSubmittedFileName()
                    )
                    .getFileName()
                    .toString();

            /*
                UNIQUE FILENAMES
            */
            String coverFileName =
                    UUID.randomUUID()
                    + "_"
                    + originalCoverName;

            String bannerFileName =
                    UUID.randomUUID()
                    + "_"
                    + originalBannerName;

            /*
                FULL PATHS
            */
            String coverFullPath =
                    Paths.get(
                            COVER_DIRECTORY,
                            coverFileName
                    ).toString();

            String bannerFullPath =
                    Paths.get(
                            BANNER_DIRECTORY,
                            bannerFileName
                    ).toString();

            /*
                SAVE FILES
            */
            coverPart.write(
                    coverFullPath
            );

            bannerPart.write(
                    bannerFullPath
            );

            /*
                CREATE ANIME
            */
            Anime anime =
                    new Anime();

            anime.setTitle(
                    title
            );

            anime.setSynopsis(
                    synopsis
            );

            anime.setCoverImage(
                    "/anime/covers/"
                    + coverFileName
            );

            anime.setBannerImage(
                    "/anime/banners/"
                    + bannerFileName
            );

            anime.setReleaseYear(
                    releaseYear
            );

            anime.setStatus(
                    status
            );

            /*
                SELECTED GENRES
            */
            String[] selectedGenres =
                    request.getParameterValues(
                            "genreIds"
                    );

            /*
                SAVE TO DATABASE
            */
            AnimeDAO animeDAO =
                    new AnimeDAO();

            int animeId =
                    animeDAO.addAnimeAndGetId(
                            anime
                    );

            if(animeId > 0){

                /*
                    SAVE GENRES
                */
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

                    GenreDAO genreDAO =
                            new GenreDAO();

                    genreDAO.addAnimeGenres(
                            animeId,
                            genreIds
                    );

                }

                request.getSession()
                        .setAttribute(
                                "successMessage",
                                "Anime added successfully."
                        );

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageAnime"
                );

            }
            else{

                /*
                    DELETE FILES
                */
                new File(
                        coverFullPath
                ).delete();

                new File(
                        bannerFullPath
                ).delete();

                showAddAnimePage(
                        request,
                        response,
                        "Failed to save anime."
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

            showAddAnimePage(
                    request,
                    response,
                    "An unexpected error occurred while adding anime."
            );

        }

    }

    /*
        RETURN TO ADMIN PANEL
    */
        private void showAddAnimePage(
            HttpServletRequest request,
            HttpServletResponse response,
            String errorMessage)
            throws Exception {

        request.setAttribute(
                "errorMessage",
                errorMessage
        );

        request.setAttribute(
                "view",
                "addAnime"
        );

        GenreDAO genreDAO =
                new GenreDAO();

        request.setAttribute(
                "genreList",
                genreDAO.getAllGenres()
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin.jsp"
        ).forward(
                request,
                response
        );

    }

}