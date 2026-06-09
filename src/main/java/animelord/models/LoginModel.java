package animelord.models;

import animelord.dao.UserDAO;
import animelord.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

public class LoginModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String username =
                request.getParameter(
                        "username"
                );

        String password =
                request.getParameter(
                        "password"
                );

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.getUserByUsername(
                        username
                );

        /*
            USER NOT FOUND
        */
        if(user == null){

            request.getSession()
                    .setAttribute(
                            "error",
                            "Invalid username or password."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            PASSWORD CHECK
        */
        boolean passwordMatches =
                BCrypt.checkpw(
                        password,
                        user.getPasswordHash()
                );

        if(!passwordMatches){

            request.getSession()
                    .setAttribute(
                            "error",
                            "Invalid username or password."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            EMAIL VERIFIED CHECK
        */
        if(!user.isEmailVerified()){

            request.getSession()
                    .setAttribute(
                            "error",
                            "Please verify your email before logging in."
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            SESSION FIXATION PROTECTION
        */
        HttpSession oldSession =
                request.getSession(false);

        if(oldSession != null){

            oldSession.invalidate();

        }

        HttpSession session =
                request.getSession(true);

        /*
            SESSION DATA
        */

        session.setAttribute(
                "user",
                user
        );

        session.setAttribute(
                "username",
                user.getUsername()
        );

        session.setAttribute(
                "userId",
                user.getUserId()
        );

        session.setAttribute(
                "role",
                user.getRole()
        );

        /*
            SUCCESS MESSAGE
        */

        session.setAttribute(
                "success",
                "Welcome back, "
                + user.getUsername()
                + "!"
        );

        /*
            REDIRECT HOME
        */

        response.sendRedirect(
                request.getContextPath()
                + "/"
        );
    }
}