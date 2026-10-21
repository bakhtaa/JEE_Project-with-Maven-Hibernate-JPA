<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="model.Categorie" %>

<%
    Categorie categorie =
        (Categorie) request.getAttribute("categorie");

    boolean modification = categorie != null;

    String nom = "";
    String id = "";

    if (modification) {
        nom = categorie.getNom() == null ? "" : categorie.getNom();
        id = String.valueOf(categorie.getId());
    }

    String action = modification ? "update" : "add";
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>
        <%= modification ? "Modifier une catégorie" : "Ajouter une catégorie" %>
    </title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f6fa;
        }

        h1 {
            color: #2c3e50;
        }

        .container {
            max-width: 500px;
            padding: 25px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .btn {
            display: inline-block;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-save {
            background-color: #198754;
            color: white;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
            margin-left: 5px;
        }
    </style>
</head>

<body>

    <div class="container">

        <h1>
            <%= modification ? "Modifier une catégorie" : "Ajouter une catégorie" %>
        </h1>

        <form method="post"
              action="<%= request.getContextPath() %>/categories">

            <input type="hidden"
                   name="action"
                   value="<%= action %>">

            <input type="hidden"
                   name="id"
                   value="<%= id %>">

            <label for="nom">Nom de la catégorie :</label>

            <input type="text"
                   id="nom"
                   name="nom"
                   value="<%= nom %>"
                   required
                   maxlength="100">

            <button type="submit" class="btn btn-save">
                <%= modification ? "Enregistrer les modifications" : "Ajouter" %>
            </button>

            <a class="btn btn-cancel"
               href="<%= request.getContextPath() %>/categories">
                Annuler
            </a>

        </form>

    </div>

</body>
</html>