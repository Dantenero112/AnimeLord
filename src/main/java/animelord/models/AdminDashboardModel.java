package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;
import animelord.dao.GenreDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminDashboardModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        AnimeDAO animeDAO =
                new AnimeDAO();

        EpisodeDAO episodeDAO =
                new EpisodeDAO();
        
        GenreDAO genreDAO = new GenreDAO();
        /*
            DASHBOARD STATISTICS
        */
        request.setAttribute(
                "totalAnime",
                animeDAO.getAnimeCount()
        );

        request.setAttribute(
                "totalEpisodes",
                episodeDAO.getEpisodeCount()
        );

        /*
            RECENT ANIME
        */
        request.setAttribute(
                "recentAnime",
                animeDAO.getRecentAnime(5)
        );

        /*
            RECENT EPISODES
        */
        request.setAttribute(
                "recentEpisodes",
                episodeDAO.getRecentlyAddedEpisodes(5)
        );

        /*
            CURRENT ADMIN VIEW
        */
        String view =
                request.getParameter(
                        "view"
                );

        if(view == null
                || view.isBlank()) {

            view = "dashboard";

        }

        request.setAttribute(
                "view",
                view
        );
        
        /*
            ADD ANIME PAGE
        */
        if("addAnime".equals(view)){

            request.setAttribute(
                    "genreList",
                    genreDAO.getAllGenres()
            );

        }
        
        // Manage Anime
        if("manageAnime".equals(view)){

            String search =
                    request.getParameter(
                            "search"
                    );

        if(search != null
                && !search.isBlank()){

            request.setAttribute(
                    "animeList",
                    animeDAO.searchAnime(
                            search
                    )
            );

        }
        else{

            request.setAttribute(
                    "animeList",
                    animeDAO.getAllAnime()
            );

        }
    }
        /*
            EDIT ANIME
        */
        if("editAnime".equals(view)){

            try{

                int animeId =
                        Integer.parseInt(
                                request.getParameter(
                                        "id"
                                )
                        );

                request.setAttribute(
                        "anime",
                        animeDAO.getAnimeById(
                                animeId
                        )
                );

                request.setAttribute(
                        "genreList",
                        genreDAO.getAllGenres()
                );

            }
            catch(Exception e){

                view = "manageAnime";

                request.setAttribute(
                        "view",
                        view
                );

            }

        }
        
        //Upload Episode
        if("uploadEpisode".equals(view)){

            request.setAttribute(
                    "animeList",
                    animeDAO.getAllAnime()
            );

        }
        //Manage Episode
        if("manageEpisodes".equals(view)){

        request.setAttribute(
                "animeList",
                animeDAO.getAllAnime()
        );

        String search =
                request.getParameter(
                        "search"
                );

        String animeIdParam =
                request.getParameter(
                        "animeId"
                );

        /*
            SEARCH
        */
        if(search != null
                && !search.isBlank()){

            request.setAttribute(
                    "episodeList",
                    episodeDAO.searchEpisodes(
                            search
                    )
            );

        }

        /*
            FILTER BY ANIME
        */
        else if(animeIdParam != null
                && !animeIdParam.isBlank()){

            int animeId =
                    Integer.parseInt(
                            animeIdParam
                    );

            request.setAttribute(
                    "episodeList",
                    episodeDAO.getEpisodesByAnime(
                            animeId
                    )
            );

        }

        /*
            SHOW ALL
        */
        else{

            request.setAttribute(
                    "episodeList",
                    episodeDAO.getAllEpisodes()
            );

        }
    }
        //Edit Episode
        if("editEpisode".equals(view)){
        try{

            int episodeId =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            request.setAttribute(
                    "episode",
                    episodeDAO.getEpisodeById(
                            episodeId
                    )
            );

        }
        catch(Exception e){

            view = "manageEpisodes";

            request.setAttribute(
                    "view",
                    view
            );
        }

    }
                /*
            FORWARD TO ADMIN PAGE
        */
        RequestDispatcher rd =
                request.getRequestDispatcher(
                        "/WEB-INF/views/admin.jsp"
                );

        rd.forward(
                request,
                response
        );
    }
}