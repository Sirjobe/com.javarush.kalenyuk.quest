<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delta Green Quest - Введите имя</title>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #1a1a1a;
            color: #e0e0e0;
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-image: url('https://www.transparenttextures.com/patterns/dark-mosaic.png');
            background-blend-mode: overlay;
        }
        .container {
            text-align: center;
            background-color: rgba(42, 42, 42, 0.95);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 204, 0, 0.3);
            max-width: 600px;
            border: 1px solid #00cc00;
        }
        h1 {
            font-family: 'Playfair Display', serif;
            color: #00cc00;
            font-size: 2.5em;
            margin-bottom: 20px;
        }
        .prologue {
            font-size: 1.1em;
            line-height: 1.6;
            margin-bottom: 30px;
            color: #b0b0b0;
            text-align: justify;
        }
        .prologue strong {
            color: #00cc00;
        }
        label {
            font-size: 1.2em;
            color: #e0e0e0;
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"] {
            padding: 10px;
            font-size: 1.1em;
            width: 80%;
            max-width: 300px;
            border: 1px solid #00cc00;
            border-radius: 5px;
            background-color: #333;
            color: #e0e0e0;
            margin-bottom: 20px;
        }
        button {
            background-color: #00cc00;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1.1em;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #009900;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Delta Green Quest</h1>
    <div class="prologue">
        <p>Ты стоишь на пороге заброшенного склада, окружённого мраком ночи. Холодный ветер доносит до тебя запах сырости и чего-то... <strong>нечеловеческого</strong>. Где-то вдалеке раздаётся вой сирены, но ты знаешь, что помощь не придёт. Ты — агент <strong>Delta Green</strong>, тайной организации, которая борется с угрозами, о которых мир не должен знать.</p>
        <p>Твой напарник пропал три дня назад, и единственная зацепка привела тебя сюда. В твоём кармане — старый диктофон с последним сообщением: "<em>Они здесь... Они знают... Не доверяй никому...</em>" Ты сглатываешь ком в горле, проверяешь оружие и делаешь шаг вперёд. Но прежде чем ты начнёшь эту миссию, мы должны знать, кто ты.</p>
    </div>
    <form action="quest" method="post" accept-charset="UTF-8">
        <label for="name">Назови своё имя, агент:</label>
        <input type="text" id="name" name="name" required>
        <button type="submit">Вступить в игру</button>
    </form>
</div>
</body>
</html>