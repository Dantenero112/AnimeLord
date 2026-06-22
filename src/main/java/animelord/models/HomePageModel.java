package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;

import animelord.entities.Anime;
import animelord.entities.EpisodeCard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class HomePageModel
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
            TRENDING
        */
        List<Anime> trendingAnime =
                animeDAO.getTopAnime(5);

        /*
            RECOMMENDED
        */
        List<Anime> recommendedAnime =
                animeDAO.getTopAnime(6);

        /*
            RECENTLY ADDED EPISODES
        */
        List<EpisodeCard> recentEpisodes =
                episodeDAO.getRecentEpisodeCards(12);

        /*
            NEWLY RELEASED
        */
        List<Anime> newReleases =
                animeDAO.getNewlyReleasedAnime(6);

        /*
            UPCOMING
        */
        List<Anime> upcomingAnime =
                animeDAO.getUpcomingAnime(6);

        /*
            COMPLETED
        */
        List<Anime> completedAnime =
                animeDAO.getRecentlyCompletedAnime(6);

        /*
            NULL SAFETY
        */
        if (trendingAnime == null) {

            trendingAnime =
                    new ArrayList<>();

        }

        if (recommendedAnime == null) {

            recommendedAnime =
                    new ArrayList<>();

        }

        if (recentEpisodes == null) {

            recentEpisodes =
                    new ArrayList<>();

        }

        if (newReleases == null) {

            newReleases =
                    new ArrayList<>();

        }

        if (upcomingAnime == null) {

            upcomingAnime =
                    new ArrayList<>();

        }

        if (completedAnime == null) {

            completedAnime =
                    new ArrayList<>();

        }

        /*
            HOMEPAGE DATA
        */
        request.setAttribute(
                "trendingAnime",
                trendingAnime
        );

        request.setAttribute(
                "recommendedAnime",
                recommendedAnime
        );

        request.setAttribute(
                "recentEpisodes",
                recentEpisodes
        );

        request.setAttribute(
                "newReleases",
                newReleases
        );

        request.setAttribute(
                "upcomingAnime",
                upcomingAnime
        );

        request.setAttribute(
                "completedAnime",
                completedAnime
        );

        /*
            PLATFORM STATS
        */
        request.setAttribute(
                "animeCountFooter",
                animeDAO.getAnimeCount()
        );

        request.setAttribute(
                "episodeCountFooter",
                episodeDAO.getEpisodeCount()
        );

        /*
            FORWARD TO HOMEPAGE
        */
        request.getRequestDispatcher(
                "/index.jsp"
        ).forward(
                request,
                response
        );
    }
}