package animelord.models;

import animelord.dao.EpisodeDAO;
import animelord.entities.Episode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateEpisodeModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {

            int episodeId =
                    Integer.parseInt(
                            request.getParameter(
                                    "episodeId"
                            )
                    );

            int episodeNumber =
                    Integer.parseInt(
                            request.getParameter(
                                    "episodeNumber"
                            )
                    );

            String episodeTitle =
                    request.getParameter(
                            "episodeTitle"
                    );

            String description =
                    request.getParameter(
                            "description"
                    );

            /*
                TITLE OPTIONAL
            */
            if(episodeTitle == null
                    || episodeTitle.isBlank()){

                episodeTitle =
                        "Episode "
                        + episodeNumber;

            }

            EpisodeDAO episodeDAO =
                    new EpisodeDAO();

            /*
                GET EXISTING EPISODE
            */
            Episode episode =
                    episodeDAO.getEpisodeById(
                            episodeId
                    );

            if(episode == null){

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageEpisodes"
                );

                return;
            }

            /*
                UPDATE VALUES
            */
            episode.setEpisodeNumber(
                    episodeNumber
            );

            episode.setEpisodeTitle(
                    episodeTitle
            );

            episode.setDescription(
                    description
            );

            /*
                SAVE
            */
            boolean updated =
                    episodeDAO.updateEpisode(
                            episode
                    );

            if(updated){

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=manageEpisodes"
                );

            }
            else{

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin?view=editEpisode&id="
                        + episodeId
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin?view=manageEpisodes"
            );

        }

    }

}