<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <title>

        Browse Anime | AnimeLord

    </title>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/anime.css">

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

        <%@ include file="/WEB-INF/views/fragments/user/animeDirectory.jspf" %>

    </main>

    <!-- Footer -->

    <%@ include file="/WEB-INF/views/fragments/common/footer.jspf" %>

    <!-- Scripts -->
    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/search.js"></script>
</body>

</html>