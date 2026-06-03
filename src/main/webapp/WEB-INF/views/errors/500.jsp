<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
    <title>500 - AnimeLord</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet">

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

        max-width:700px;
        width:90%;
    }

    .brand{
        font-size:2rem;
        font-weight:600;
        margin-bottom:20px;
    }

    .brand span{
        color:#22d3ee;
    }

    .error-code{
        font-size:7rem;
        font-weight:bold;
        color:#ef4444;
        line-height:1;
        text-shadow:0 0 15px rgba(239,68,68,0.4);
    }

    .error-title{
        margin-top:15px;
        font-weight:600;
    }

    .error-description{
        color:#94a3b8;
        margin-top:15px;
        line-height:1.6;
    }

    .system-log{
        margin-top:25px;

        background:rgba(0,0,0,0.3);

        border-radius:10px;

        padding:15px;

        text-align:left;

        font-family:Consolas, monospace;

        color:#f87171;
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
        margin-top:15px;
        color:#64748b;
        font-style:italic;
    }
</style>

</head>

<body>

<div class="error-card">

    <div class="brand">
        Anime<span>Lord</span>
    </div>

    <div class="error-code">
        500
    </div>

    <h2 class="error-title">
        Internal Server Error
    </h2>

    <p class="error-description">
        Something went wrong while processing your request.
        Our system encountered an unexpected exception.
    </p>

    <div class="system-log">
        ERROR: Internal server malfunction detected.<br>
        STATUS: Service temporarily unavailable.<br>
        ACTION: Please try again later.
    </div>

    <p class="quote">
        "Even the strongest heroes face unexpected bugs."
    </p>

    <a href="<%=request.getContextPath()%>/"
       class="btn btn-home mt-4">
        Return Home
    </a>

</div>

</body>
</html>
