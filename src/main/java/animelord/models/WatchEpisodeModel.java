package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;
import animelord.dao.EpisodeFileDAO;

import animelord.entities.Anime;
import animelord.entities.Episode;
import animelord.entities.EpisodeFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class WatchEpisodeModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        /*
            EPISODE ID
        */
        String episodeIdParam =
                request.getParameter(
                        "episodeId"
                );

        if(episodeIdParam == null
                || episodeIdParam.isBlank()){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        int episodeId;

        try{

            episodeId =
                    Integer.parseInt(
                            episodeIdParam
                    );

        }
        catch(NumberFormatException e){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        /*
            DAO
        */
        EpisodeDAO episodeDAO =
                new EpisodeDAO();

        EpisodeFileDAO episodeFileDAO =
                new EpisodeFileDAO();

        AnimeDAO animeDAO =
                new AnimeDAO();

        /*
            CURRENT EPISODE
        */
        Episode episode =
                episodeDAO.getEpisodeById(
                        episodeId
                );

        if(episode == null){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        /*
            ANIME
        */
        Anime anime =
                animeDAO.getAnimeById(
                        episode.getAnimeId()
                );

        if(anime == null){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        /*
            STREAM FILE
        */
        EpisodeFile episodeFile =
                episodeFileDAO.getEpisodeFileByEpisodeId(
                        episodeId
                );

        /*
            PREVIOUS EPISODE
        */
        Episode previousEpisode =
                episodeDAO.getPreviousEpisode(
                        episode.getAnimeId(),
                        episode.getEpisodeNumber()
                );

        /*
            NEXT EPISODE
        */
        Episode nextEpisode =
                episodeDAO.getNextEpisode(
                        episode.getAnimeId(),
                        episode.getEpisodeNumber()
                );

        /*
            EPISODE LIST
        */
        List<Episode> episodeList =
                episodeDAO.getEpisodesByAnime(
                        episode.getAnimeId()
                );

        if(episodeList == null){

            episodeList =
                    new ArrayList<>();
        }

        /*
            STREAM PATH

            HLS MASTER PLAYLIST
        */
        String masterPlaylist =
                null;

        if(episodeFile != null){

            masterPlaylist =
                    episodeFile.getMasterPlaylist();
        }

        /*
            REQUEST ATTRIBUTES
        */
        request.setAttribute(
                "anime",
                anime
        );

        request.setAttribute(
                "episode",
                episode
        );

        request.setAttribute(
                "episodeFile",
                episodeFile
        );

        request.setAttribute(
                "masterPlaylist",
                masterPlaylist
        );

        request.setAttribute(
                "previousEpisode",
                previousEpisode
        );

        request.setAttribute(
                "nextEpisode",
                nextEpisode
        );

        request.setAttribute(
                "episodeList",
                episodeList
        );
        
        request.setAttribute(
                "animeCountFooter",
                animeDAO.getAnimeCount()
        );

        request.setAttribute(
                "episodeCountFooter",
                episodeDAO.getEpisodeCount()
        );
        
        /*
            FORWARD
        */
        request.getRequestDispatcher(
                "/WEB-INF/views/watch.jsp"
        ).forward(
                request,
                response
        );
    }
}