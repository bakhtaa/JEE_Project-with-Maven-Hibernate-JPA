<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            margin: 40px;
        }

        .container {
            max-width: 400px;
            margin: auto;
            padding: 25px;
            background: white;
            border-radius: 8px;
        }

        label {
            display: block;
            margin-top: 15px;
        }

        input {
            box-sizing: border-box;
            width: 100%;
            padding: 10px;
            margin-top: 5px;
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 10px;
            background: #0d6efd;
            color: white;
            border: none;
            cursor: pointer;
        }

        .erreur {
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Connexion</h1>

    <%
        String erreur = (String) request.getAttribute("erreur");
        if (erreur != null) {
    %>
        <p class="erreur"><%= erreur %></p>
    <%
        }
    %>

    <form method="post"
          action="<%= request.getContextPath() %>/login">

        <label for="username">Nom d'utilisateur</label>
        <input type="text"
               id="username"
               name="username"
               required>

        <label for="password">Mot de passe</label>
        <input type="password"
               id="password"
               name="password"
               required>

        <button type="submit">Se connecter</button>
    </form>
</div>

</body>
</html>