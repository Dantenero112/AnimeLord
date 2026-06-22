package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.dao.EpisodeDAO;
import animelord.entities.Anime;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimeListModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        AnimeDAO animeDAO = new AnimeDAO();
        EpisodeDAO episodeDAO = new EpisodeDAO();
        /*
            LETTER FILTER   
        */
        String letter =
                request.getParameter(
                        "letter"
                );

        List<Anime> animeList;

        /*
            SHOW ALL ANIME
        */
        if(letter == null
                || letter.isBlank()){

            animeList =
                    animeDAO.getAllAnime();

            letter =
                    "ALL";
        }

        /*
            NUMBER FILTER
        */
        else if("#".equals(
                letter
        )){

            animeList =
                    animeDAO.getAnimeByLetter(
                            "#"
                    );
        }

        /*
            LETTER FILTER
        */
        else{

            letter =
                    letter.toUpperCase();

            animeList =
                    animeDAO.getAnimeByLetter(
                            letter
                    );
        }

        /*
            NULL SAFETY
        */
        if(animeList == null){

            animeList =
                    new ArrayList<>();
        }

        /*
            A-Z FILTER BAR
        */
        List<String> alphabet =
                Arrays.asList(
                        "A","B","C","D","E","F",
                        "G","H","I","J","K","L","M",
                        "N","O","P","Q","R","S","T",
                        "U","V","W","X","Y", "Z"
                );

        /*
            REQUEST ATTRIBUTES
        */

        request.setAttribute(
                "animeList",
                animeList
        );

        request.setAttribute(
                "selectedLetter",
                letter
        );

        request.setAttribute(
                "alphabet",
                alphabet
        );

        request.setAttribute(
                "animeCount",
                animeList.size()
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
                "/WEB-INF/views/anime.jsp"
        ).forward(
                request,
                response
        );
    }
}