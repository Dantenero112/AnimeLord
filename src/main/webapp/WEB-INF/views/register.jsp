<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <title>Register - AnimeLord</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/auth.css">

</head>

<body>

<div class="auth-page">

    <div class="auth-card">

        <div class="auth-header">

            <h1 class="auth-logo">
                Anime<span>Lord</span>
            </h1>

            <p class="auth-subtitle">
                Create your AnimeLord account and start your anime journey.
            </p>

        </div>

        <%
            String error =
                    (String) session.getAttribute(
                            "error"
                    );

            String success =
                    (String) session.getAttribute(
                            "success"
                    );
        %>

        <% if(error != null){ %>

            <div class="alert alert-danger">

                <%= error %>

            </div>

        <% } %>

        <% if(success != null){ %>

            <div class="alert alert-success">

                <%= success %>

            </div>

        <% } %>

        <%
            session.removeAttribute(
                    "error"
            );

            session.removeAttribute(
                    "success"
            );
        %>

        <form
            action="${pageContext.request.contextPath}/doRegister"
            method="post">

            <div class="mb-3">

                <label class="form-label">

                    Username

                </label>

                <input
                    type="text"
                    name="username"
                    class="form-control"
                    placeholder="Choose a username"
                    required>

            </div>

            <div class="mb-3">

                <label class="form-label">

                    Email Address

                </label>

                <input
                    type="email"
                    name="email"
                    class="form-control"
                    placeholder="Enter your email"
                    required>

            </div>

            <div class="mb-3">

                <label class="form-label">

                    Password

                </label>

                <input
                    type="password"
                    name="password"
                    class="form-control"
                    placeholder="Create a password"
                    required>

            </div>

            <div class="mb-4">

                <label class="form-label">

                    Confirm Password

                </label>

                <input
                    type="password"
                    name="confirmPassword"
                    class="form-control"
                    placeholder="Re-enter your password"
                    required>

            </div>

            <button
                type="submit"
                class="btn auth-btn">

                Create Account

            </button>

        </form>

        <div class="auth-links">

            <p>

                Already have an account?

                <a href="${pageContext.request.contextPath}/login">

                    Sign In

                </a>

            </p>

        </div>

    </div>

</div>

</body>

</html>