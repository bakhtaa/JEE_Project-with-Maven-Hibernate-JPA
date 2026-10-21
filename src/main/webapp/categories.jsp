 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Categorie" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des catégories</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f6fa;
        }

        h1 {
            color: #2c3e50;
        }

        .btn {
            display: inline-block;
            padding: 8px 12px;
            margin: 3px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: white;
            font-size: 14px;
        }

        .btn-add {
            background-color: #198754;
            margin-bottom: 20px;
        }

        .btn-edit {
            background-color: #0d6efd;
        }

        .btn-delete {
            background-color: #dc3545;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .actions {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        form {
            margin: 0;
        }
    </style>
</head>

<body>
<%@include file="header.html" %>
    <h1>Gestion des catégories</h1>

    <a class="btn btn-add"
       href="<%= request.getContextPath() %>/categories?action=form">
        + Ajouter une catégorie
    </a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom de la catégorie</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
        <%
            List<Categorie> categories =
                (List<Categorie>) request.getAttribute("categories");

            if (categories != null && !categories.isEmpty()) {
                for (Categorie c : categories) {
        %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getNom() %></td>

                <td>
                    <div class="actions">

                        <a class="btn btn-edit"
                           href="<%= request.getContextPath() %>/categories?action=edit&id=<%= c.getId() %>">
                            Modifier
                        </a>

                        <form method="post"
                              action="<%= request.getContextPath() %>/categories"
                              onsubmit="return confirm('Voulez-vous vraiment supprimer cette catégorie ?');">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="id"
                                   value="<%= c.getId() %>">

                            <button type="submit"
                                    class="btn btn-delete">
                                Supprimer
                            </button>
                        </form>

                    </div>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="3">Aucune catégorie trouvée.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

</body>
</html>