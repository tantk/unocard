/* 
 * Copyright 2016 tan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//join game

var PlayerID;
var gameID;
$(function ()
{
    $("#driver").click(function (event) {
        $.getJSON('uno/player/create/' + $('#playerName').val(), function (jd) {
            $('#loadlist').val(jd + " has been created");
            PlayerID = jd;
            alert("player created");
        });

    });
    $(".gameTemplate").on("singletap", "li", function () {
        gid = $(this).find("h3").text();
        console.log(gid);

    }
    );
    var gameTemplate = Handlebars.compile($("#gameTemplate").html());
    var waitingRoomTemplate = Handlebars.compile($("#waitingRoomTemplate").html());
    var gameRoomTemplate = Handlebars.compile($("#gameRoomTemplate").html());
    $("#loadlist").on("singletap", function ()
    {
        $("#view-games").empty();
        var getAllGames = $.getJSON("uno/game/viewall");
        getAllGames.done(function (result)
        {
            $("#view-games").append(gameTemplate({game: result}));
        });



    });
    $("#view-games").on('singletap', 'li', function () {


        gameID = $(this).find("h3").text();

        alert(gameID);
        var data = {};
        data["gameID"] = gameID;
        data["playerID"] = PlayerID;
        $.ajax({
            type: "Post",
            url: "uno/game/joingame",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function () {

            },
            error: function () {

            }
        });

        $("#wait_game_refreshBtn").trigger("singletap");
        $.UIGoToArticle("#wait_game");

    });

    $("#wait_game_refreshBtn").on("singletap", function getGame()
    {
        $.getJSON("uno/game/view/" + gameID).done(function (result) {
            var players = waitingRoomTemplate({players: result});
            $("#view-players").empty();
            $("#view-players").append(players);
            var keys = [];
            for (var k in result)
                keys.push(k);
            $('#wait_game_player_count').html(keys.length);

        });
        var Intervals = setInterval(function () {
            myTimer();
        }, 5000);
        function myTimer() {
            (function updateGameStatus() {
                $.getJSON('uno/game/view/' + gameID + '/status', function (status) {

                    var gameStatus = status;
                    alert(gameStatus);
                    if (gameStatus === "started") {
                        clearInterval(Intervals);
                        $.UIGoToArticle("#wait_game");
                    }
                }).then(function () {           // on completion, restart
                    setTimeout(updateGameStatus, 5000);  // function refers to itself
                });
            });
        }
        ;
    });
    $("#wait_game_startBtn").on("singletap", function startGame()
    {
        var data = {};
        data["gameID"] = gameID;
        data["playerID"] = PlayerID;
        $.ajax({
            type: "Post",
            url: "uno/game/startgame",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function () {
                $.UIGoToArticle("#gameRoom");
            },
            error: function () {

                alert('cant start');

            }
        });
    });
    
  $("#gameRoom_refreshBtn").on("singletap", function startGame()
    {
        var data = {};
        data["gameID"] = gameID;
        data["playerID"] = PlayerID;
        $.ajax({
            type: "Post",
            url: "uno/game/getplayerhand",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function () {
                alert('got cards');
            },
            error: function () {

                alert('cant start');

            }
        }).done(function (result)
        {
            $("#view-cards").append(gameRoomTemplate({card: result}));
        });
    });
    


});



//websocket
$(function () {
    var connection = null;
    var displaychat = function (msg) {
        $("#chatarea").prepend(
                $("<div>").text(msg));

    };
    $("#connectBtn").on("click", function () {
        connection = new WebSocket("ws://localhost:8080/Unocard/waitingroom");
        connection.onopen = function () {

            displayChat("Websocket is connected");
        };

        connection.onclose = function ()
        {
            displayChat("Websocket is closed");
        };
        connection.onmessage = function ()
        {
            displayChat(msg.data);
        };

    });
    $("#sendBtn").on("click", function () {
        connection.send($("#message").val());

        $("#message").val("");
    }
    );
});


