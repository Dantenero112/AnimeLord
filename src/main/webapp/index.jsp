<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <title>AnimeLord</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index.css">

</head>

<body>

    <%@ include file="/WEB-INF/views/fragments/user/navbar.jspf" %>
    <%@ include file="/WEB-INF/views/fragments/user/userDrawer.jspf" %>

    <div class="container-fluid px-md-4 px-2 mt-4">

        <%@ include file="/WEB-INF/views/fragments/user/spotlight.jspf" %>

        <%@ include file="/WEB-INF/views/fragments/user/recentlyAdded.jspf" %>

    </div>

    <%@ include file="/WEB-INF/views/fragments/common/footer.jspf" %>

    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>

    <script src="${pageContext.request.contextPath}/js/index.js"></script>

</body>

</html>