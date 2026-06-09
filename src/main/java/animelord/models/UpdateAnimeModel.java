package animelord.models;

import animelord.dao.AnimeDAO;
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
            "D:/AnimeLordStorage/anime/covers";

    private static final String BANNER_DIRECTORY =
            "D:/AnimeLordStorage/anime/banners";

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

            AnimeDAO animeDAO =
                    new AnimeDAO();

            Anime anime =
                    animeDAO.getAnimeById(
                            animeId
                    );

            if(anime == null){

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageAnime"
                );

                return;
            }

            /*
                BASIC VALIDATION
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

                request.getRequestDispatcher(
                        "/WEB-INF/views/admin.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

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
                COVER UPDATE
            */

            if(coverPart != null
                    && coverPart.getSize() > 0){

                String contentType =
                        coverPart.getContentType();

                if(!ALLOWED_IMAGE_TYPES.contains(
                        contentType)){

                    throw new Exception(
                            "Invalid cover image type."
                    );
                }

                String fileName =
                        UUID.randomUUID()
                        + "_"
                        + Paths.get(
                                coverPart.getSubmittedFileName()
                        ).getFileName();

                String fullPath =
                        COVER_DIRECTORY
                        + File.separator
                        + fileName;

                coverPart.write(
                        fullPath
                );

                anime.setCoverImage(
                        "/anime/covers/"
                        + fileName
                );
            }

            /*
                BANNER UPDATE
            */

            if(bannerPart != null
                    && bannerPart.getSize() > 0){

                String contentType =
                        bannerPart.getContentType();

                if(!ALLOWED_IMAGE_TYPES.contains(
                        contentType)){

                    throw new Exception(
                            "Invalid banner image type."
                    );
                }

                String fileName =
                        UUID.randomUUID()
                        + "_"
                        + Paths.get(
                                bannerPart.getSubmittedFileName()
                        ).getFileName();

                String fullPath =
                        BANNER_DIRECTORY
                        + File.separator
                        + fileName;

                bannerPart.write(
                        fullPath
                );

                anime.setBannerImage(
                        "/anime/banners/"
                        + fileName
                );
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
                SAVE UPDATE
            */

            boolean success =
                    animeDAO.updateAnime(
                            anime
                    );

            if(success){

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageAnime"
                );

            }
            else{

                throw new Exception(
                        "Failed to update anime."
                );
            }

        }
        catch(Exception e){

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageAnime"
            );
        }
    }
}