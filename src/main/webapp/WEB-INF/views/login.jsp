<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <title>Login - AnimeLord</title>

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
                Sign in to continue watching your favorite anime.
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
            action="${pageContext.request.contextPath}/doLogin"
            method="post">

            <div class="mb-3">

                <label class="form-label">

                    Username

                </label>

                <input
                    type="text"
                    name="username"
                    class="form-control"
                    placeholder="Enter username"
                    required>

            </div>

            <div class="mb-4">

                <label class="form-label">

                    Password

                </label>

                <input
                    type="password"
                    name="password"
                    class="form-control"
                    placeholder="Enter password"
                    required>

            </div>

            <button
                type="submit"
                class="btn auth-btn">

                Login

            </button>

        </form>

        <div class="auth-links">

            <p>

                New to AnimeLord?

                <a href="${pageContext.request.contextPath}/register">

                    Create an Account

                </a>

            </p>

        </div>

    </div>

</div>

</body>

</html>