<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <title>

        ${anime.title}
        -
        Episode ${episode.episodeNumber}
        | AnimeLord

    </title>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/watch.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/responsive.css">

</head>

<body>

    <%-- Drawer --%>

    <%@ include file="/WEB-INF/views/fragments/user/userDrawer.jspf" %>

    <%-- Navbar --%>

    <%@ include file="/WEB-INF/views/fragments/user/navbar.jspf" %>

    <%-- Main Content --%>

    <main class="main-container">

        <%-- Video Player --%>

        <%@ include file="/WEB-INF/views/fragments/user/videoPlayer.jspf" %>

        <%-- Previous / Episode List / Next --%>

        <%@ include file="/WEB-INF/views/fragments/user/episodeNavigation.jspf" %>

        <%-- Anime + Episode Information --%>

        <%@ include file="/WEB-INF/views/fragments/user/watchInfo.jspf" %>

        <%-- Episode List Offcanvas --%>

        <%@ include file="/WEB-INF/views/fragments/user/episodeListOffcanvas.jspf" %>

        <%-- Comments (Future) --%>

        <%--
        <section class="comments-placeholder">

            <h3>

                Comments

            </h3>

            <p>

                Coming Soon...

            </p>

        </section>
        --%>

    </main>

    <%-- Footer --%>

    <%@ include file="/WEB-INF/views/fragments/common/footer.jspf" %>

    <%-- Scripts --%>

    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>
    <script src="https://cdn.jsdelivr.net/npm/hls.js@latest"></script>
    <script src="${pageContext.request.contextPath}/js/watch.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/search.js"></script>

</body>

</html>