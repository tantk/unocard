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

$(function () {

    
        var playerList = Handlebars.compile($("#playerList").html());
    $("#findGame").on("click", function (){ 
        alert("player created");
    $.getJSON("uno/game/view/" + $('#gameID').val()).done(function (result) {
        var player = playerList({player: result});
               $("#view-players").empty();
                $("#view-players").append(player);
                var keys = [];
                for (var k in result)
                keys.push(k);
                $('#wait_game_player_count').html(keys.length);
                });
                $.getJSON('uno/game/view/' + $('#gameID').val() + '/showTopDiscard', function (showdiscard) {
                    imgList = '<img src= "image/uno_deck/' + this.image + '">';
                     $('#discardTopCard').append(imgList);
                     $("#discardTopCard").append("<img src=image/back.png>");

                    }
                );
        });
    });