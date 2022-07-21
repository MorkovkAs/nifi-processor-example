<%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<html xmlns="http://www.w3.org/1999/html">
<link rel="stylesheet" href="style/app.css" type="text/css"/>
<script type="text/javascript" src="../nifi/assets/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="js/name-select.js"></script>
<body>
<div id='root'/>
<div class='main-container'>
    <div id="servletFields">
        <div id="processId"><%= request.getParameter("id")%></div>
        <div id="clientId"><%= request.getParameter("clientId")%></div>
        <div id="revision"><%= request.getParameter("revision")%></div>
    </div>
    <div>
        <h2 class='header2'>Пример работы процессора</h2>
        <name-select
                process-id=""
                client-id=""
                revision="">
        </name-select>
        <div class='name-select' id="nameSelect"></div>
        <h4>Введите текст в поле ниже:</h4>
        <input type="text" id="nameInput" name="name" placeholder="Имя" oninput="function refreshName() {
            console.log('3');
            const val = document.getElementById('nameInput').value
            document.getElementById('result').innerHTML = 'Hello ' + val;
        }
        refreshName()">
        <p>
            <b>Результат:</b>
        <p id="result">Hello _____</p>
    </div>
    <div id='message'></div>
    <div class='saveButton'>
        <button class='save-button' id="saveContext">Выбрать</button>
    </div>
</div>
</body>
</html>