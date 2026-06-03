<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
    <title>AnimeLord</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>

<body>

<!-- Navbar -->

<nav class="navbar navbar-expand-lg navbar-dark px-4">
    <a class="navbar-brand" href="#">Anime<span>Lord</span></a>


<div class="ms-auto">
    <input class="form-control" type="search" placeholder="Search...">
</div>


</nav>

<div class="container-fluid px-md-4 px-2 mt-4">


<!-- Spotlight -->
<div class="spotlight">
    <h4>🌌 Spotlight Picks</h4>
    <div class="scroll-container mt-3">

        <div class="anime-card" style="min-width:250px;">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 3</div>
            <div class="card-body">
                <small>Neon Blades</small>
            </div>
        </div>

        <div class="anime-card" style="min-width:250px;">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 7</div>
            <div class="card-body">
                <small>Void Chronicles</small>
            </div>
        </div>

    </div>
</div>

<!-- Grid Section -->
<h5 class="mb-3">📺 Recently Added</h5>

<div class="row g-4">

    <div class="col-md-3">
        <div class="anime-card">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 5</div>
            <div class="card-body">
                <small>Celestial War</small>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="anime-card">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 2</div>
            <div class="card-body">
                <small>Night Pulse</small>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="anime-card">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 9</div>
            <div class="card-body">
                <small>Iron Spirits</small>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="anime-card">
            <img src="https://via.placeholder.com/300x200">
            <div class="ep-badge">Ep 1</div>
            <div class="card-body">
                <small>Phantom Grid</small>
            </div>
        </div>
    </div>

</div>


</div>
<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>
