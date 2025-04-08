<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire de Connexion</title>
    
</head>
<body>

    <h2>Formulaire de Connexion</h2>

    <form action="login" method="post">
        
        <label for="Name">Name :</label>
        <input type="text" id="Name" name="name" value="admin" required>

        <br>

        <label for="password">password :</label>
        <input type="password" id="password" name="password" value="admin" required>

        <button type="submit">Enregistrer</button>
    </form>

</body>
</html>
