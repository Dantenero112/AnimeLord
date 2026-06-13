package animelord.models;

import animelord.dao.AnimeDAO;
import animelord.entities.Anime;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

public class SearchAnimeAjaxModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String keyword =
                request.getParameter(
                        "keyword"
                );

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        if (keyword == null
                || keyword.isBlank()) {

            response.getWriter()
                    .write("[]");

            return;
        }

        AnimeDAO animeDAO =
                new AnimeDAO();

        List<Anime> animeList =
                animeDAO.searchAnime(
                        keyword.trim()
                );

        PrintWriter out =
                response.getWriter();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        int maxResults =
                Math.min(
                        animeList.size(),
                        5
                );

        for (int i = 0;
                i < maxResults;
                i++) {

            Anime anime =
                    animeList.get(i);

            json.append("{");

            json.append(
                    "\"animeId\":"
                    + anime.getAnimeId()
                    + ","
            );

            json.append(
                    "\"title\":\""
                    + escapeJson(
                            anime.getTitle()
                    )
                    + "\","
            );

            json.append(
                    "\"coverImage\":\""
                    + escapeJson(
                            anime.getCoverImage()
                    )
                    + "\""
            );

            json.append("}");

            if (i < maxResults - 1) {

                json.append(",");

            }
        }

        json.append("]");

        out.write(
                json.toString()
        );
    }

    /*
        ESCAPE JSON VALUES
    */
    private String escapeJson(
            String value) {

        if (value == null) {

            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}