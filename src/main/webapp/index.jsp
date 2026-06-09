<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <title>AnimeLord</title>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index.css">

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

        <%@ include file="/WEB-INF/views/fragments/user/trending.jspf" %>
        <%@ include file="/WEB-INF/views/fragments/user/recommended.jspf" %>
        <%@ include file="/WEB-INF/views/fragments/user/recentlyAdded.jspf" %>
        <%@ include file="/WEB-INF/views/fragments/user/newReleases.jspf" %>
        <%@ include file="/WEB-INF/views/fragments/user/upcoming.jspf" %>
        <%@ include file="/WEB-INF/views/fragments/user/completed.jspf" %>
        

    </main>

    <!-- Footer -->

    <%@ include file="/WEB-INF/views/fragments/common/footer.jspf" %>

    <!-- Scripts -->

    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>

    <script src="${pageContext.request.contextPath}/js/index.js"></script>

</body>

</html>