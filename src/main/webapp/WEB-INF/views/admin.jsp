<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
    <title>Admin Panel - AnimeLord</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<body>

<!--TOP NAVBAR-->

<nav class="admin-navbar">
    <button id="menuBtn" class="menu-btn">
        ☰
    </button>

    <div class="navbar-brand-custom">
        Anime<span>Lord</span>
    </div>
</nav>

<!-- OVERLAY -->

<div id="overlay" class="overlay"></div>

<!--SIDEBAR DRAWER-->

<div id="sidebarDrawer" class="sidebar-drawer">

    <!-- Drawer Header -->
    <div class="drawer-header">

        <div class="brand">
            Anime<span>Lord</span>
        </div>

        <button id="closeBtn" class="close-btn">
            ✕
        </button>

    </div>

    <a href="${pageContext.request.contextPath}/admin"
       class="nav-link-custom active">
        Dashboard
    </a>

    <a href="#upload"
       class="nav-link-custom">
        Upload Episode
    </a>

    <a href="#encoding"
       class="nav-link-custom">
        Encoding Queue
    </a>

    <a href="#reports"
       class="nav-link-custom">
        Reports
    </a>

    <a href="${pageContext.request.contextPath}/logout"
       class="nav-link-custom">
        Logout
    </a>

</div>

<!-- MAIN CONTENT-->

<div class="container-fluid">
    <div class="content">

        <h3 class="mb-4">
            ⚙️ Admin Dashboard
        </h3>

        <!-- Upload Section -->

        <div id="upload" class="glass-card">

            <h5 class="mb-3">
                📤 Upload Episode
            </h5>

            <div class="row g-3">

                <div class="col-md-4">
                    <input type="text"
                           class="form-control"
                           placeholder="Anime Title">
                </div>

                <div class="col-md-2">
                    <input type="number"
                           class="form-control"
                           placeholder="Episode">
                </div>

                <div class="col-md-6">
                    <input type="file"
                           class="form-control">
                </div>

            </div>

            <button class="btn btn-neon mt-3">
                Upload
            </button>

        </div>

        <!-- Encoding Queue -->

        <div id="encoding" class="glass-card">

            <h5 class="mb-3">
                ⚡ Encoding Queue
            </h5>

            <table class="table table-dark table-hover">

                <thead>
                    <tr>
                        <th>Anime</th>
                        <th>Episode</th>
                        <th>Status</th>
                    </tr>
                </thead>

                <tbody>

                    <tr>
                        <td>Neon Blades</td>
                        <td>Ep 5</td>
                        <td>Processing</td>
                    </tr>

                    <tr>
                        <td>Void Chronicles</td>
                        <td>Ep 7</td>
                        <td>Queued</td>
                    </tr>

                </tbody>

            </table>

        </div>

        <!-- Feedback Reports -->

        <div id="reports" class="glass-card">

            <h5 class="mb-3">
                💬 Feedback Reports
            </h5>

            <ul class="mb-0">
                <li>UI glitch on homepage</li>
                <li>Video buffering issue</li>
            </ul>

        </div>

        <!-- Broken Episodes -->

        <div class="glass-card">

            <h5 class="mb-3">
                🚨 Broken Episode Reports
            </h5>

            <ul class="mb-0">
                <li>Episode 3 not loading</li>
                <li>Episode 7 audio missing</li>
            </ul>

        </div>

    </div>

</div>

<script src="${pageContext.request.contextPath}/js/admin.js"></script>

</body>
</html>
