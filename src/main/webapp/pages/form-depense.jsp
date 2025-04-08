<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Prevision" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Depense</title>
</head>
<body>

    <h2>Formulaire d'Ajout de depense</h2>

    <form action="depense" method="post">
        <label for="prevision">prevision :</label>
        <select name="prevision" required>
            <%
                List<Prevision> previsions = (List<Prevision>) request.getAttribute("previsions");
                for (Prevision p : previsions) {
                    %>
                        <option value="<%=p.getId() %>"><%= p.getLibelle() %></option>
                    <%
                }
            %>
        </select>

        <br/>

        <label for="montant">montant :</label>
        <input type="number" id="montant" name="montant" min="0" required>

        <button type="submit">Enregistrer</button>
    </form>
    <a href="etat">Retour</a>

</body>
</html>
