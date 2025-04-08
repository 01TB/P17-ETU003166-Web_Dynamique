<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Employé</title>
    
</head>
<body>

    <h2>Formulaire d'Ajout de previsions</h2>

    <form action="prevision" method="post">
        <label for="libelle">libelle :</label>
        <input type="text" id="libelle" name="libelle"  required>

        <br>

        <label for="montant">montant :</label>
        <input type="number" id="montant" name="montant" min="0" required>

        <br>

        <label for="date">date :</label>
        <input type="date" id="date" name="date"  required>

        <button type="submit">Enregistrer</button>
    </form>
    <a href="etat">Retour</a>
</body>
</html>
