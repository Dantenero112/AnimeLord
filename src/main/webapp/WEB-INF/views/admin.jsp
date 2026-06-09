<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html>

<head>

    <%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

    <title>Admin Panel - AnimeLord</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/components.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/admin.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/responsive.css">

</head>

<body>

    <%@ include file="/WEB-INF/views/fragments/admin/components/adminNavbar.jspf" %>

    <%@ include file="/WEB-INF/views/fragments/admin/components/adminDrawer.jspf" %>

    <div class="container-fluid">

        <div class="content">

            <c:choose>

                <c:when test="${view eq 'addAnime'}">

                    <%@ include file="/WEB-INF/views/fragments/admin/components/addAnimeForm.jspf" %>

                </c:when>

                <c:when test="${view eq 'manageAnime'}">

                    <%@ include file="/WEB-INF/views/fragments/admin/components/manageAnime.jspf" %>

                </c:when>
                
                <c:when test="${view eq 'editAnime'}">

                    <%@ include file="/WEB-INF/views/fragments/admin/components/editAnime.jspf" %>

                </c:when>

                <c:when test="${view eq 'uploadEpisode'}">

                    <h3 class="mb-4">

                        📺 Upload Episode

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/uploadEpisode.jspf" %>

                </c:when>

                <c:when test="${view eq 'manageEpisodes'}">

                    <h3 class="mb-4">

                        🎞 Manage Episodes

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/manageEpisodes.jspf" %>

                </c:when>

                <c:when test="${view eq 'encodingQueue'}">

                    <h3 class="mb-4">

                        ⚙️ Encoding Queue

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/encodingQueue.jspf" %>

                </c:when>

                <c:when test="${view eq 'feedbackReports'}">

                    <h3 class="mb-4">

                        📨 Feedback Reports

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/feedbackReports.jspf" %>

                </c:when>

                <c:when test="${view eq 'brokenReports'}">

                    <h3 class="mb-4">

                        🚨 Broken Episode Reports

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/brokenReports.jspf" %>

                </c:when>

                <c:when test="${view eq 'users'}">

                    <h3 class="mb-4">

                        👥 Manage Users

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/manageUsers.jspf" %>

                </c:when>

                <c:when test="${view eq 'statistics'}">

                    <h3 class="mb-4">

                        📈 Watch Statistics

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/statistics.jspf" %>

                </c:when>

                <c:when test="${view eq 'announcements'}">

                    <h3 class="mb-4">

                        📢 Announcements

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/announcements.jspf" %>

                </c:when>

                <c:when test="${view eq 'settings'}">

                    <h3 class="mb-4">

                        🛠 Site Settings

                    </h3>

                    <%@ include file="/WEB-INF/views/fragments/admin/components/settings.jspf" %>

                </c:when>

                <c:otherwise>

                    <h3 class="mb-4">

                        ⚙️ Admin Dashboard

                    </h3>

                   <%@ include file="/WEB-INF/views/fragments/admin/components/dashboard.jspf" %>

                </c:otherwise>

            </c:choose>

        </div>

    </div>
    <%@ include file="/WEB-INF/views/fragments/admin/components/adminFooter.jspf" %>
    <%@ include file="/WEB-INF/views/fragments/common/scripts.jspf" %>

    <script src="${pageContext.request.contextPath}/js/admin.js"></script>

</body>

</html>