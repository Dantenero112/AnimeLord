<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <title>Admin Panel - AnimeLord</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/admin.css">

</head>

<body>

    <%@ include file="/WEB-INF/views/fragments/admin/adminNavbar.jspf" %>

    <%@ include file="/WEB-INF/views/fragments/admin/adminDrawer.jspf" %>

    <div class="container-fluid">

        <div class="content">

            <h3 class="mb-4">
                ⚙️ Admin Dashboard
            </h3>

            <%@ include file="/WEB-INF/views/fragments/admin/uploadSection.jspf" %>

            <%@ include file="/WEB-INF/views/fragments/admin/encodingQueue.jspf" %>

            <%@ include file="/WEB-INF/views/fragments/admin/feedbackReports.jspf" %>

            <%@ include file="/WEB-INF/views/fragments/admin/brokenReports.jspf" %>

        </div>

    </div>
 
    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>

    <script src="${pageContext.request.contextPath}/js/admin.js"></script>

</body>

</html>