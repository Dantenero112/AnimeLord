<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <title>

        ${anime.title} | AnimeLord

    </title>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/animeDetails.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/responsive.css">

</head>

<body>

    <!-- Drawer -->

    <%@ include file="/WEB-INF/views/fragments/user/userDrawer.jspf" %>

    <!-- Navbar -->

    <%@ include file="/WEB-INF/views/fragments/user/navbar.jspf" %>

    <!-- Main Content -->

    <main class="main-container">

        <!-- Anime Hero -->

        <%@ include file="/WEB-INF/views/fragments/user/animeInfo.jspf" %>

        <!-- Episodes Section -->

        <%@ include file="/WEB-INF/views/fragments/user/episodeDirectory.jspf" %>

        <!-- Related Anime -->

        <%@ include file="/WEB-INF/views/fragments/user/relatedAnime.jspf" %>

        <!-- Comments Section (Future) -->

        <%--
            <%@ include file="/WEB-INF/views/fragments/user/comments.jspf" %>
        --%>

    </main>

    <!-- Footer -->

    <%@ include file="/WEB-INF/views/fragments/common/footer.jspf" %>

    <!-- Scripts -->

    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>

    <script src="${pageContext.request.contextPath}/js/animeDetails.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/search.js"></script>

</body>

</html>