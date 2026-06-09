package animelord.models;

import animelord.dao.UserDAO;
import animelord.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyAccountModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        HttpSession session =
                request.getSession();

        String token =
                request.getParameter(
                        "token"
                );

        if (token == null
                || token.trim().isEmpty()) {

            session.setAttribute(
                    "error",
                    "Invalid verification link."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.getUserByVerificationToken(
                        token
                );

        /*
            Invalid token
        */
        if (user == null) {

            session.setAttribute(
                    "error",
                    "Verification link is invalid or has already been used."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            Already verified
        */
        if (user.isEmailVerified()) {

            session.setAttribute(
                    "success",
                    "Your account is already verified."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            Verify account
        */
        boolean verified =
                userDAO.verifyUser(
                        token
                );

        if (verified) {

            session.setAttribute(
                    "success",
                    "Email verified successfully. Please login."
            );

        }
        else {

            session.setAttribute(
                    "error",
                    "Unable to verify account. Please try again."
            );

        }

        response.sendRedirect(
                request.getContextPath()
                + "/login"
        );
    }
}