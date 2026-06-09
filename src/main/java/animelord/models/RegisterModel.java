package animelord.models;

import animelord.dao.UserDAO;
import animelord.entities.User;
import animelord.util.EmailUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public class RegisterModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        HttpSession session =
                request.getSession();

        String username =
                request.getParameter(
                        "username"
                );

        String email =
                request.getParameter(
                        "email"
                );

        String password =
                request.getParameter(
                        "password"
                );

        String confirmPassword =
                request.getParameter(
                        "confirmPassword"
                );

        UserDAO userDAO = new UserDAO();

        /*
            Basic Validation
        */

        if (username == null
                || username.trim().isEmpty()) {

            session.setAttribute(
                    "error",
                    "Username is required."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        if (email == null
                || email.trim().isEmpty()) {

            session.setAttribute(
                    "error",
                    "Email is required."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        if (!password.equals(
                confirmPassword
        )) {

            session.setAttribute(
                    "error",
                    "Passwords do not match."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        /*
            Username Exists
        */

        if (userDAO.usernameExists(
                username
        )) {

            session.setAttribute(
                    "error",
                    "Username already exists."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        /*
            Email Exists
        */

        if (userDAO.emailExists(
                email
        )) {

            session.setAttribute(
                    "error",
                    "Email is already registered."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        /*
            Generate Password Hash
        */

        String passwordHash =
                BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                );

        /*
            Generate Verification Token
        */

        String verificationToken =
                UUID.randomUUID()
                        .toString();

        /*
            Create User Object
        */

        User user =
                new User();

        user.setUsername(
                username
        );

        user.setEmail(
                email
        );

        user.setPasswordHash(
                passwordHash
        );

        user.setRole(
                "USER"
        );

        user.setEmailVerified(
                false
        );

        user.setVerificationToken(
                verificationToken
        );

        /*
            Save User
        */

        boolean saved =
                userDAO.addUser(
                        user
                );

        if (!saved) {

            session.setAttribute(
                    "error",
                    "Unable to create account."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/register"
            );

            return;
        }

        /*
            Build Verification URL
        */

        String verificationLink =
                request.getScheme()
                + "://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath()
                + "/verify?token="
                + verificationToken;

        try {

            EmailUtility.sendVerificationEmail(
                    email,
                    verificationLink
            );

            session.setAttribute(
                    "success",
                    "Verification email sent. Please check your inbox."
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            session.setAttribute(
                    "error",
                    "Account created but email could not be sent."
            );
        }

        response.sendRedirect(
                request.getContextPath()
                + "/login"
        );
    }
}