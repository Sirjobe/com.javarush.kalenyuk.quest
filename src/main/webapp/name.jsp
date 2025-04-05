<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delta Green Quest - Введите имя</title>
    <meta charset="UTF-8">
    <!-- Подключаем Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <style>
        /* Сбрасываем стандартные отступы и задаём базовые стили */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #1a1a1a; /* Тёмный фон */
            color: #e0e0e0; /* Светлый текст */
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            background-color: #2c2c2c; /* Тёмный контейнер */
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
            text-align: center;
            max-width: 500px;
            width: 100%;
        }

        h1 {
            font-family: 'Playfair Display', serif;
            font-size: 2.5em;
            color: #00cc00; /* Зелёный цвет для заголовка */
            margin-bottom: 20px;
        }

        label {
            font-size: 1.2em;
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            font-size: 1em;
            border: 2px solid #00cc00;
            border-radius: 5px;
            background-color: #3a3a3a;
            color: #e0e0e0;
            margin-bottom: 20px;
            outline: none;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            border-color: #00ff00;
        }

        button {
            background-color: #00cc00;
            color: #fff;
            padding: 10px 20px;
            font-size: 1.1em;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.1s;
        }

        button:hover {
            background-color: #00ff00;
            transform: scale(1.05);
        }

        button:active {
            transform: scale(0.95);
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Добро пожаловать в Delta Green Quest!</h1>
    <form action="quest" method="post" accept-charset="UTF-8">
        <label for="name">Введите ваше имя:</label>
        <input type="text" id="name" name="name" required>
        <button type="submit">Начать игру</button>
    </form>
</div>
</body>
</html>