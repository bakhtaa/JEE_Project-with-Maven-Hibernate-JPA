
<%@ page import="model.Produit" %>
<%@ page import="model.Categorie" %>
<%@ page import="java.util.List" %>

<%
    Produit p = (Produit) request.getAttribute("produit");

    String action = (p != null && p.getId() != 0)
            ? "Modifier" : "Ajouter";

    if (p == null) {
    	p = new Produit(0L, "", 0.0, 0);
    }

    List<Categorie> categories =
            (List<Categorie>) request.getAttribute("categories");

    Long categorieSelectionnee = null;
    if (p.getCategorie() != null) {
        categorieSelectionnee = p.getCategorie().getId();
    }
%>

<div class="card">
    <div class="card-header bg-primary text-white">
        <h4><%= action %> un produit</h4>
    </div>

    <div class="card-body">
        <form method="post" action="save">

            <input type="hidden" name="id"
                   value="<%= p.getId() %>" />

            <div class="mb-3">
                <label>Nom :</label>
                <input type="text" name="nom"
                       value="<%= p.getNom() %>"
                       class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Catégorie :</label>

                <select name="categorieId"
                        class="form-select" required>

                    <option value="categorie">-- Choisir une catégorie --</option>

                    <%
                        if (categories != null) {
                            for (Categorie c : categories) {
                    %>

                    <option value="<%= c.getId() %>"
                        <%= categorieSelectionnee != null
                            && categorieSelectionnee.equals(c.getId())
                            ? "selected" : "" %>>
                        <%= c.getNom() %>
                    </option>

                    <%
                            }
                        }
                    %>

                </select>
            </div>

            <div class="mb-3">
                <label>Prix (DH) :</label>
                <input type="number" name="prix"
                       value="<%= p.getPrix() %>"
                       class="form-control"
                       step="0.01" min="0" required />
            </div>

            <div class="mb-3">
                <label>Quantité :</label>
                <input type="number" name="quantite"
                       value="<%= p.getQuantite() %>"
                       class="form-control"
                       min="0" required />
            </div>

            <button type="submit" class="btn btn-success">
                <%= action %>
            </button>

        </form>
    </div>
</div>
