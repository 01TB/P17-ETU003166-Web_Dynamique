<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Prevision" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Employés</title>
</head>
<body>

    <a href="prevision"> Ajout de prévision</a>
    <a href="depense"> Ajoute d'une dépense</a>

    <h2>Etat</h2>
    <form action="etat" method="get">
        <label for="dateDebut">Date de début :</label>
        <input type="date" id="dateDebut" name="dateDebut" required>

        <br>

        <label for="dateFin">dateFin :</label>
        <input type="date" id="dateFin" name="dateFin" required>

        <button type="submit">Voir</button>
    </form>

    <table>
        <tr>
            <th>Libelle</th>
            <th>Depense</th>
            <th>Reste</th>
            <th>Date</th>
        </tr>
        <%
            List<Prevision> previsions = (List<Prevision>) request.getAttribute("previsions");
            for (Prevision p : previsions) {
                %>
                    <tr>
                        <td><%= p.getLibelle() %></td>
                        <td><%= p.getAllDepense() %></td>
                        <td><%= p.getReste() %></td>
                        <td><%= p.getDate() %></td>
                    </tr>
                <%
            }
        %>
    </table>

</body>
</html>
