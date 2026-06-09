package animelord.filters;

import animelord.entities.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter extends HttpFilter
        implements Filter {

    @Override
    public void init(
            FilterConfig filterConfig)
            throws ServletException {

        super.init(filterConfig);

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException,
                   ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse res =
                (HttpServletResponse) response;

        HttpSession session =
                req.getSession(false);

        User user = null;

        if(session != null){

            user =
                (User) session.getAttribute(
                        "user"
                );

        }

        /*
            NOT LOGGED IN
        */
        if(user == null){

            HttpSession newSession =
                    req.getSession(true);

            newSession.setAttribute(
                    "error",
                    "Please login first."
            );

            res.sendRedirect(
                    req.getContextPath()
                    + "/login"
            );

            return;
        }

        /*
            LOGGED IN
        */
        chain.doFilter(
                request,
                response
        );
    }

    @Override
    public void destroy() {
    }
}