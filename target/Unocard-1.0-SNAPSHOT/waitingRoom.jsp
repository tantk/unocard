<%-- 
    Document   : waitingRoom
    Created on : Aug 7, 2016, 9:02:27 PM
    Author     : tantk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>New Game</h1>
        <form method="POST" action ="index">
            <div>Id: ${game.gameID}
            <div>Game Name: <input type="text" id="gameName" name="gameName" value=${game.gameName}></div>
           <!-- <div> User Name: <input type="text" id="playerName" name="playerName" value="Player 1"></div> -->
            <button id="start">Start</button>
        </form>
    </body>
</html>
