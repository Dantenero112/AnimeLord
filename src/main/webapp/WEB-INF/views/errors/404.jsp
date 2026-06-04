<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
    <title>404 - AnimeLord</title>

<%@ include file="/WEB-INF/views/fragments/common/head.jspf" %>

<style>
    body{
        min-height:100vh;
        display:flex;
        justify-content:center;
        align-items:center;

        background: radial-gradient(circle at top,
                    #0f172a,
                    #020617);

        color:#e2e8f0;
        font-family:'Segoe UI',sans-serif;
    }

    .error-card{
        text-align:center;

        background:rgba(255,255,255,0.05);
        backdrop-filter:blur(10px);

        padding:50px;
        border-radius:20px;

        border:1px solid rgba(255,255,255,0.08);

        max-width:650px;
        width:90%;
    }

    .error-code{
        font-size:7rem;
        font-weight:bold;
        color:#22d3ee;
        line-height:1;
    }

    .brand{
        font-size:2rem;
        font-weight:600;
        margin-bottom:20px;
    }

    .brand span{
        color:#22d3ee;
    }

    .btn-home{
        background:#22d3ee;
        color:#020617;
        border:none;
        padding:10px 24px;
        border-radius:8px;
        font-weight:600;
    }

    .btn-home:hover{
        background:#06b6d4;
        color:#020617;
    }

    .quote{
        color:#94a3b8;
        margin-top:15px;
    }
</style>

</head>

<body>

<div class="error-card">

    <div class="brand">
        Anime<span>Lord</span>
    </div>

    <div class="error-code">
        404
    </div>

    <h2 class="mt-3">
        Lost In Another Dimension
    </h2>

    <p class="mt-3 text-secondary">
        The page you are looking for doesn't exist,
        was moved, or has not been released yet.
    </p>

    <p class="quote">
        "Even the strongest protagonists take a wrong turn sometimes."
    </p>

    <a href="<%=request.getContextPath()%>/"
       class="btn btn-home mt-4">
        Return Home
    </a>

</div>

</body>
</html>
