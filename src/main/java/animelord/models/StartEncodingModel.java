package animelord.models;

import animelord.services.FFmpegEncodingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StartEncodingModel
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

            FFmpegEncodingService service =
                    new FFmpegEncodingService();

            service.processEpisode(
                    episodeId
            );

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        response.sendRedirect(
                request.getContextPath()
                + "/encodingQueue"
        );
    }
}