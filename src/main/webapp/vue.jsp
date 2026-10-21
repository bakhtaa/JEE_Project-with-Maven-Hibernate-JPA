<%@ page import="java.util.List" %>
<%@ page import="model.Produit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Produits</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>

<body>
<%@include file="header.html" %>
<%
    String mc = (String) request.getAttribute("mc");
    if (mc == null) mc = "";
%>

<!-- Formulaire de recherche -->
<form class="d-flex my-3" method="get" action="search">
    <input name="mc"
           value="<%= mc %>"
           type="text"
           class="form-control me-2"
           placeholder="Rechercher un produit..."/>
    <button type="submit" class="btn btn-primary">
        <i class="fa fa-search"></i> Rechercher
    </button>
</form>
<div class="container mt-5">

    <h1 class="text-center mb-4">Liste des Produits</h1>

    <%
        // Recuperation de la liste depuis le scope request
        List<Produit> liste = (List<Produit>) request.getAttribute("listep");
    %>

    <table class="table table-bordered table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Nom du Produit</th>
            <th>Prix (DH)</th>
            <th>Quantite</th>
        </tr>
        </thead>
        <tbody>
        <% for (Produit p : liste) { %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNom() %></td>
            <td><%= String.format("%.2f", p.getPrix()) %></td>
            <td><%= p.getQuantite() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="alert alert-info mt-3">
        <strong>Total :</strong> <%= liste.size() %> produit(s)
    </div>
</div>
</body>
</html>