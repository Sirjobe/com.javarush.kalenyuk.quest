<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Delta Green Quest</title>
    <meta charset="UTF-8">
    <!-- Подключаем Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #1a1a1a;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            background-color: #2c2c2c;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }

        h1 {
            font-family: 'Playfair Display', serif;
            font-size: 2.5em;
            color: #00cc00;
            margin-bottom: 20px;
        }

        h2 {
            font-family: 'Playfair Display', serif;
            font-size: 1.8em;
            color: #e0e0e0;
            margin-bottom: 20px;
        }

        p {
            font-size: 1.1em;
            margin-bottom: 10px;
        }

        .player-info {
            background-color: #3a3a3a;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .options {
            margin-top: 20px;
        }

        .option {
            display: flex;
            align-items: center;
            margin: 10px 0;
            font-size: 1.1em;
        }

        input[type="radio"] {
            margin-right: 10px;
            accent-color: #00cc00;
        }

        label {
            cursor: pointer;
            transition: color 0.3s;
        }

        label:hover {
            color: #00ff00;
        }

        button {
            background-color: #00cc00;
            color: #fff;
            padding: 10px 20px;
            font-size: 1.1em;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
            transition: background-color 0.3s, transform 0.1s;
        }

        button:hover {
            background-color: #00ff00;
            transform: scale(1.05);
        }

        button:active {
            transform: scale(0.95);
        }

        .victory {
            color: #00ff00;
            font-size: 1.5em;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .defeat {
            color: #ff3333;
            font-size: 1.5em;
            font-weight: bold;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Delta Green Quest</h1>
    <div class="player-info">
        <p>Игрок: ${user.name}</p>
        <p>Количество сыгранных игр: ${user.count}</p>
    </div>

    <h2>${node.question}</h2>

    <c:choose>
        <c:when test="${gameEnded}">
            <c:choose>
                <c:when test="${isVictory}">
                    <p class="victory">Поздравляю, вы победили!</p>
                </c:when>
                <c:otherwise>
                    <p class="defeat">Соболезную, вы проиграли!</p>
                </c:otherwise>
            </c:choose>
            <form action="quest" method="post">
                <input type="hidden" name="restart" value="true">
                <button type="submit">Начать заново</button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="quest" method="post">
                <div class="options">
                    <c:forEach items="${node.options}" var="option" varStatus="loop">
                        <div class="option">
                            <input type="radio" id="choice${loop.index}" name="choice" value="${loop.count}" required>
                            <label for="choice${loop.index}">${option}</label>
                        </div>
                    </c:forEach>
                </div>
                <button type="submit">Сделать выбор</button>
            </form>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>