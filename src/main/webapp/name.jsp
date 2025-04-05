<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delta Green Quest - Введите имя</title>
</head>
<body>
<h1>Добро пожаловать в Delta Green Quest!</h1>
<form action="quest" method="post">
    <label for="name">Введите ваше имя:</label>
    <input type="text" id="name" name="name" required>
    <button type="submit">Начать игру</button>
</form>
</body>
</html>