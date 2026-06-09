package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;

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
        // Manage Anime
        if("manageAnime".equals(view)){

        request.setAttribute(
            "animeList",
            animeDAO.getAllAnime()
            );
        }
        //Edit Anime
        if("editAnime".equals(view)){
            try{
                int animeId =
                        Integer.parseInt(
                                request.getParameter("id")
                        );

                request.setAttribute(
                        "anime",
                        animeDAO.getAnimeById(
                                animeId
                        )
                );

            }
            catch(Exception e){

                view = "manageAnime";

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