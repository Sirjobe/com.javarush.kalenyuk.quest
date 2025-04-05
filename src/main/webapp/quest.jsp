<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Delta Green Quest</title>
</head>
<body>
<h1>Delta Green Quest</h1>
<p>Игрок: ${user.name}</p>
<p>Количество сыгранных игр: ${user.count}</p>

<!-- Отладочный вывод -->
<p>Debug: node.options is empty: ${empty node.options}</p>
<p>Debug: node.question: ${node.question}</p>

<h2>${node.question}</h2>

<c:choose>
    <c:when test="${empty node.options}">
        <p>Игра окончена!</p>
        <a href="quest">Начать заново</a>
    </c:when>
    <c:otherwise>
        <form action="quest" method="post">
            <c:forEach items="${node.options}" var="option" varStatus="loop">
                <div>
                    <input type="radio" id="choice${loop.index}" name="choice" value="${loop.count}" required>
                    <label for="choice${loop.index}">${option}</label>
                </div>
            </c:forEach>
            <button type="submit">Сделать выбор</button>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>