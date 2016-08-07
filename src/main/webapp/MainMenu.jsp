<%-- 
    Document   : MainMenu
    Created on : Aug 7, 2016, 1:05:08 AM
    Author     : tantk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Game</title>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js" ></script>
    </head>
    <body>
        <h1>New Game</h1>
        <form method="POST" action ="index">
            <div>Game Name: <input type="text" id="gameName" name="gameName" value="Game 1"></div>
           <!-- <div> User Name: <input type="text" id="playerName" name="playerName" value="Player 1"></div> -->
            <button id="create">Create</button>
        </form>
    </body>
</html>
