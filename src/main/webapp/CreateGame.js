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
var PlayerID;
var gameID;
var connection = null;
var selectedCard;
var playerInRoom;
var connectionGlobal;
$(function ()

{
    var getAllGames = $.getJSON("uno/game/viewall");
    getAllGames.done(function (result)
    {
        $("#list-games2").empty();
        //$("#list-games").append(listGameTemplate({game: result}));
        $("#list-games2").append(listGameTemplate({game: result}));
    });
    $("#connectGlobal").click(function (event) {
        var getAllGames = $.getJSON("uno/game/viewall");
        getAllGames.done(function (result)
        {
            $("#list-games2").empty();
            //$("#list-games").append(listGameTemplate({game: result}));
            $("#list-games2").append(listGameTemplate({game: result}));
        });

        connectionGlobal = new WebSocket('ws://localhost:8080/Unocard/globalRoom');

        connectionGlobal.onerror = function (event) {
            onError(event);
        };

        connectionGlobal.onopen = function (event) {
            onOpen(event);
        };

        connectionGlobal.onmessage = function (event) {
            onMessage(event);
        };
        function onMessage(event) {
            var msg = JSON.parse(event.data);
            console.log("receive:" + JSON.stringify(event.data));
            switch (msg.cmd)
            {
                case 'getPlayerInfo':
                    var getAllGames = $.getJSON("uno/game/viewall");
                    getAllGames.done(function (result)
                    {
                        $("#list-games2").empty();
                        //$("#list-games").append(listGameTemplate({game: result}));
                        $("#list-games2").append(listGameTemplate({game: result}));
                    });
                    break;
                case 'ToGlobalClient-createdGame':
                    alert("got here");
                    var getAllGames = $.getJSON("uno/game/viewall");
                    getAllGames.done(function (result)
                    {
                        $("#list-games2").empty();
                        //$("#list-games").append(listGameTemplate({game: result}));
                        $("#list-games2").append(listGameTemplate({game: result}));
                    });
                    break;



//            case 'ToGameRoomClient_Playerdrawn':
//
//                var data = {};
//                data["gameID"] = gameID;
//                data["playerID"] = PlayerID;
//                $.ajax({
//                    type: "Post",
//                    url: "uno/game/getplayerhand",
//                    contentType: "application/json",
//                    data: JSON.stringify(data),
//                    success: function () {
//                        console.log("got cards");
//                    },
//                    error: function () {
//
//                        alert('cant start');
//
//                    }
//                }).done(function (result)
//                {
//                    $("#view-cards").empty();
//                    $("#view-cards").prepend(gameRoomTemplate({card: result}));
//                });
//
//                break;

            }
        }
    });
    function onOpen(event) {

    }

    function onError(event) {
        alert(event.data);
    }
    var listGameTemplate = Handlebars.compile($("#listGameTemplate").html());
    var waitingRoomTemplate = Handlebars.compile($("#waitingRoomTemplate").html());
    $("#btnCreateGame").on("singletap", function createGame()
    {
        alert($('#gameName').val());
        $.ajax({
            type: "POST",
            data: (""),
            url: "uno/game/create/" + $('#gameName').val(),
            contentType: "text/plain",
            success: function () {
                alert("game created");
                var getAllGames = $.getJSON("uno/game/viewall");
                var data = {};
                data["cmd"] = "ToGlobalRoomEndPoint-createdGame";
                data["playerID"] = "guest";

                getAllGames.done(function (result)
                {
                    $("#list-games2").empty();
                    //$("#list-games").append(listGameTemplate({game: result}));
                    $("#list-games2").append(listGameTemplate({game: result}));
                });

            }, error: function () {
                alert("Error");
            }});
    });
    $("#list-games2").on('singletap', 'div', function () {


        gameID = $(this).find("h3").text();
        alert(gameID);
        $.getJSON('uno/game/view/' + gameID + '/status', function (status) {

            if (status === "waiting") {
                $.UIGoToArticle("#waitGame");
            } else
            {
                $.UIGoToArticle("#viewGame");
            }
        });
        $(function () {
            var RoomID = gameID;



            connection = new WebSocket('ws://localhost:8080/unocard/gameroom/' + RoomID);

            connection.onerror = function (event) {
                onError(event);
            };

            connection.onopen = function (event) {
                onOpen(event);
            };

            connection.onmessage = function (event) {
                onMessage(event);
            };

            function onMessage(event) {
                var msg = JSON.parse(event.data);
                console.log("receive:" + JSON.stringify(event.data));
                switch (msg.cmd)
                {
                    case 'getPlayerInfo':
                        var data = {};
                        data["cmd"] = msg.cmd + "-ack";
                        data["gameID"] = gameID;
                        data["playerID"] = PlayerID;
                        connection.send(JSON.stringify(data));
                        break;
                    case 'waitingRoom-RefreshPlayer':
                        console.log("someone joined");
                        $.getJSON("uno/game/view/" + gameID).done(function (result) {
                            var players = waitingRoomTemplate({players: result});
                            $("#view-players").empty();
                            $("#view-players").append(players);
                            var keys = [];
                            for (var k in result)
                                keys.push(k);
                            $('#wait_game_player_count').html(keys.length);
                            playerInRoom = keys.length;
                        });
                        break;
                    case 'waitingRoomClient-StartGame':
                        $.getJSON('uno/game/view/' + gameID + '/status', function (status) {

                            var gameStatus = status;

                            if (gameStatus === "started") {
                                $.UIGoToArticle("#gameRoom");
                                var data = {};
                                data["gameID"] = gameID;
                                data["playerID"] = PlayerID;
                                $.ajax({
                                    type: "Post",
                                    url: "uno/game/getplayerhand",
                                    contentType: "application/json",
                                    data: JSON.stringify(data),
                                    success: function () {
                                        console.log("got cards");
                                    },
                                    error: function () {
                                        alert('cant start: game has already started or player is not created properly');
                                    }
                                }).done(function (result)
                                {
                                    $("#view-cards").empty();
                                    $("#view-cards").prepend(gameRoomTemplate({card: result}));
                                });
                                $.getJSON('uno/game/view/' + gameID + '/showTopDiscard', function (showdiscard) {

                                    imgList = '<img src= "image/uno_deck/' + showdiscard.image + '">';

                                    $('#discardTopCard').empty();
                                    $('#discardTopCard').append(imgList);
                                    $("#discardTopCard").append("<img src=image/back.png>");

                                });
                            }
                        });
                        break;
                    case 'ToGameRoomClient-CardDiscarded':

                        var data = {};
                        data["gameID"] = gameID;
                        data["playerID"] = PlayerID;
                        $.ajax({
                            type: "Post",
                            url: "uno/game/getplayerhand",
                            contentType: "application/json",
                            data: JSON.stringify(data),
                            success: function () {
                                console.log("discard cards");
                            },
                            error: function () {

                                alert('cant start');

                            }
                        }).done(function (result)
                        {
                            $("#view-cards").empty();
                            $("#view-cards").prepend(gameRoomTemplate({card: result}));
                        });

                        $.getJSON("uno/game/view/" + gameID).done(function (result) {
                            var player = playerList({player: result});
                            $("#playerDiv").empty();
                            $("#playerDiv").append(player);

                        });
                        // 
                        $.getJSON('uno/game/view/' + gameID + '/showTopDiscard', function (showdiscard) {

                            imgList = '<img src= "image/uno_deck/' + showdiscard.image + '">';

                            $('#discardTopCard').empty();
                            $('#discardTopCard').append(imgList);
                            $("#discardTopCard").append("<img src=image/back.png>");

                        }
                        );
                        break;
                    case 'ToGameRoomClient_Playerdrawn':

                        var data = {};
                        data["gameID"] = gameID;
                        data["playerID"] = PlayerID;
                        $.ajax({
                            type: "Post",
                            url: "uno/game/getplayerhand",
                            contentType: "application/json",
                            data: JSON.stringify(data),
                            success: function () {
                                console.log("got cards");
                            },
                            error: function () {

                                alert('cant start');

                            }
                        }).done(function (result)
                        {
                            $("#view-cards").empty();
                            $("#view-cards").prepend(gameRoomTemplate({card: result}));
                        });

                        break;

                }
            }

            function onOpen(event) {

            }

            function onError(event) {
                alert(event.data);
            }


        });
    });
    $("#wait_game_startBtn").on("singletap", function startGame()
    {
        if (playerInRoom < 1) {
            alert("Cannot start game with no player");
        } else {
            var data = {};
            data["gameID"] = gameID;
            data["playerID"] = PlayerID;
            $.ajax({
                type: "Post",
                url: "uno/game/startgame",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function () {
                    $.UIGoToArticle("#viewGame");

                    $.getJSON("uno/game/view/" + gameID).done(function (result) {
                        var player = playerList({player: result});
                        $("#playerDiv").empty();
                        $("#playerDiv").append(player);

                    });
                    // 
                    $.getJSON('uno/game/view/' + gameID + '/showTopDiscard', function (showdiscard) {

                        imgList = '<img src= "image/uno_deck/' + showdiscard.image + '">';

                        $('#discardTopCard').empty();
                        $('#discardTopCard').append(imgList);
                        $("#discardTopCard").append("<img src=image/back.png>");

                    });
                },
                error: function () {

                    alert('cant start');

                }
            });
            var sendmsg = {};
            sendmsg["cmd"] = "WaitingRoomEndPoint-StartGame";
            sendmsg["gameID"] = gameID;
            sendmsg["playerID"] = "PlayerID";
            connectionGlobal.send(JSON.stringify(sendmsg));
        }
    });
});

