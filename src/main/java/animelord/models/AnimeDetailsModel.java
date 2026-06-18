package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;
import animelord.dao.GenreDAO;

import animelord.entities.Anime;
import animelord.entities.EpisodeCard;
import animelord.entities.EpisodeRange;
import animelord.entities.Genre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnimeDetailsModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        /*
            ANIME ID
        */
        String animeIdParam =
                request.getParameter(
                        "id"
                );

        if(animeIdParam == null
                || animeIdParam.isBlank()){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        int animeId;

        try{

            animeId =
                    Integer.parseInt(
                            animeIdParam
                    );

        }
        catch(NumberFormatException e){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        AnimeDAO animeDAO =
                new AnimeDAO();

        EpisodeDAO episodeDAO =
                new EpisodeDAO();

        GenreDAO genreDAO =
                new GenreDAO();

        /*
            ANIME
        */
        Anime anime =
                animeDAO.getAnimeById(
                        animeId
                );

        if(anime == null){

            response.sendRedirect(
                    request.getContextPath()
                    + "/anime"
            );

            return;
        }

        /*
            GENRES
        */
        List<Genre> genres =
                genreDAO.getGenresByAnime(
                        animeId
                );

        anime.setGenres(
                genres
        );

        /*
            SORT PARAMETER
        */
        String sort =
                request.getParameter(
                        "sort"
                );

        if(sort == null
                || sort.isBlank()){

            sort = "asc";
        }

        /*
            EPISODE CARDS
        */
        List<EpisodeCard> episodeCards =
                episodeDAO.getEpisodeCardsByAnime(
                        animeId
                );

        if(episodeCards == null){

            episodeCards =
                    new ArrayList<>();
        }

        /*
            SORT EPISODES
        */
        if(sort.equalsIgnoreCase(
                "desc"
        )){

            episodeCards.sort(
                    Comparator.comparingInt(
                            EpisodeCard::getEpisodeNumber
                    ).reversed()
            );

        }
        else{

            episodeCards.sort(
                    Comparator.comparingInt(
                            EpisodeCard::getEpisodeNumber
                    )
            );

            sort = "asc";
        }

        /*
            TOTAL EPISODES
        */
        int totalEpisodes =
                episodeCards.size();

        /*
            HIGHEST EPISODE NUMBER

            Example:

            1
            2
            7
            10

            Max = 10
        */
        int highestEpisodeNumber =
                episodeCards.stream()
                        .mapToInt(
                                EpisodeCard::getEpisodeNumber
                        )
                        .max()
                        .orElse(0);

        /*
            EPISODE RANGES

            1-100
            101-200
            201-253
        */
        List<EpisodeRange> episodeRanges =
                new ArrayList<>();

        if(highestEpisodeNumber > 0){

            for(
                    int start = 1;
                    start <= highestEpisodeNumber;
                    start += 100
            ){

                int end =
                        Math.min(
                                start + 99,
                                highestEpisodeNumber
                        );

                episodeRanges.add(
                        new EpisodeRange(
                                start,
                                end
                        )
                );
            }
        }

        /*
            DEFAULT SELECTED RANGE
        */
        String selectedRange = null;

        if(!episodeRanges.isEmpty()){

            selectedRange =
                    episodeRanges.get(0)
                            .getRangeLabel();
        }

        /*
            RELATED ANIME
        */
        List<Anime> relatedAnime =
                animeDAO.getRelatedAnime(
                        animeId,
                        12
                );

        if(relatedAnime == null){

            relatedAnime =
                    new ArrayList<>();
        }

        /*
            REQUEST ATTRIBUTES
        */

        request.setAttribute(
                "anime",
                anime
        );

        request.setAttribute(
                "genres",
                genres
        );

        request.setAttribute(
                "episodeCards",
                episodeCards
        );

        request.setAttribute(
                "episodeCount",
                totalEpisodes
        );

        request.setAttribute(
                "episodeRanges",
                episodeRanges
        );

        request.setAttribute(
                "selectedRange",
                selectedRange
        );

        request.setAttribute(
                "sort",
                sort
        );

        request.setAttribute(
                "relatedAnime",
                relatedAnime
        );

        /*
            FORWARD
        */
        request.getRequestDispatcher(
                "/WEB-INF/views/animeDetails.jsp"
        ).forward(
                request,
                response
        );
    }
}