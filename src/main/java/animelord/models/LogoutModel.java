package animelord.models;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        HttpSession session =
                request.getSession(false);

        /*
            DESTROY EXISTING SESSION
        */
        if (session != null) {

            session.invalidate();

        }

        /*
            CREATE NEW SESSION
            FOR FLASH MESSAGE
        */
        HttpSession newSession =
                request.getSession(true);

        newSession.setAttribute(
                "success",
                "You have been logged out successfully."
        );

        /*
            REDIRECT TO HOME PAGE
        */
        response.sendRedirect(
                request.getContextPath()
                + "/"
        );
    }
}