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
package websocket;

import Manager.GameManager;
import entity.UNOGame;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Dependent
@ServerEndpoint("/gameroom/{RoomID}")
public class GameRoomEndPoint {

    @Inject
    private GameManager gamesMgr;
    @Inject
    SessionHandler connectionHandler;
    private HashMap<String, Object> GlobalSession = new HashMap<>();
    private String clientId;

    @OnMessage
    public void onMessage(String msg, @PathParam("RoomID") String roomID)
            throws IOException, InterruptedException {

        String gameID = roomID;
        UNOGame thisGame = gamesMgr.getGame(roomID);
        System.out.println(">>>> msg = " + msg);
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(msg.getBytes()));
        JsonObject json = reader.readObject();

        switch (json.getString("cmd")) {
            case "getPlayerInfo-ack":
                JsonObject msgPlayerInfoAck = Json.createObjectBuilder()
                        .add("cmd", "waitingRoom-RefreshPlayer").build();

                connectionHandler.broadcastRoom(msgPlayerInfoAck.toString(), gameID);
                System.out.println("reached here after broadcast");
                break;
            case "WaitingRoomEndPoint-StartGame":
                System.out.println("> gameId:" + gameID);
                JsonObject msgWaitinRmStart = Json.createObjectBuilder()
                        .add("cmd", "waitingRoomClient-StartGame").build();
                connectionHandler.broadcastRoom(msgWaitinRmStart.toString(), gameID);
                System.out.println("broadcastRoom to start game");
                break;
            case "ToGameRoomEndPoint-PlayerDiscard":
                System.out.println("> gameId:" + gameID);
                JsonObject msgPlayerDiscardCard = Json.createObjectBuilder()
                        .add("cmd", "ToGameRoomClient-CardDiscarded").build();
                connectionHandler.broadcastRoom(msgPlayerDiscardCard.toString(), gameID);
                System.out.println("broadcastRoom to discard");
                break;
            case "ToGameRoomEndPoint_Playerdrawn":
                System.out.println("> gameId: drawn " + gameID);
                JsonObject msgPlayerDrawnCard = Json.createObjectBuilder()
                        .add("cmd", "ToGameRoomClient_Playerdrawn").build();
                connectionHandler.broadcastRoom(msgPlayerDrawnCard.toString(), gameID);
                System.out.println("broadcastRoom to drawn");
                break;
                
               
//            case "gaming":
//                switch (json.getString("cmd")) {
//                    case "throw":
//                        Player player = thisGame.findPlayer(json.getString("player"));
//                        Card card = player.findCard(json.getString("card"));
//                        GameEngine.throwOneCard(player, card, thisGame.getDisPipe());
//                        //check if someone wins
//                        Player winner = GameEngine.checkWinner(thisGame);
//                        if (winner != null) {
//                            //some one wins
//                            GameEngine.markWinner(thisGame, winner);
//                            JsonObject msg_update = Json.createObjectBuilder()
//                                    .add("msg", "gaming")
//                                    .add("cmd", "endRound")
//                                    .add("winner", winner.geroomID())
//                                    .add("score", winner.getScore())
//                                    .build();
//                            RoomIDs.broadcast(roomID, msg_update);
//                            return;
//                        }
//
//                        //update Status
//                        JsonObject msg_update = Json.createObjectBuilder()
//                                .add("msg", "gaming")
//                                .add("cmd", "updateGame")
//                                .add("Cards", thisGame.card2Json())
//                                .build();
//                        RoomIDs.broadcast(roomID, msg_update);
//                        break;
//                    case "undo":
//                        thisGame.getDisPipe().returnOneCard();
//                        JsonObject msg_undo = Json.createObjectBuilder()
//                                .add("msg", "gaming")
//                                .add("cmd", "updateGame")
//                                .add("Cards", thisGame.card2Json())
//                                .build();
//                        RoomIDs.broadcast(roomID, msg_undo);
//                        break;
//                    case "draw":
//                        Player draw_player = thisGame.findPlayer(json.getString("player"));
//                        GameEngine.drawOneCard(draw_player,
//                                thisGame.getDrawPipe(), thisGame.getDisPipe());
//                        JsonObject msg_draw = Json.createObjectBuilder()
//                                .add("msg", "gaming")
//                                .add("cmd", "updateGame")
//                                .add("Cards", thisGame.card2Json())
//                                .build();
//                        RoomIDs.broadcast(roomID, msg_draw);
//                        break;
//                    case "restart":
//                        thisGame.clearTable();
//                        GameEngine.initGame(thisGame);
//                        JsonObject msg_begin = Json.createObjectBuilder()
//                                .add("msg", "gaming")
//                                .add("cmd", "initGame")
//                                .add("Cards", thisGame.card2Json())
//                                .build();
//                        System.out.println(msg_begin);
//                        RoomIDs.broadcast(roomID, msg_begin);
//                        break;
//                    default:
//                        System.out.println("cmd switch not matched!!!");
//                }
//
//                break;
            default:
                //RoomIDs.broadcast(RoomID, json);
                System.out.println("msg switch not matched!!!" + msg);
                break;
        }

    }

    @OnOpen
    public void onOpen(Session session, @PathParam("RoomID") String roomID) {
        System.out.println("Client connected: " + roomID);
        System.out.println("onopen");
        connectionHandler.add(session, roomID);
        System.out.println("added session");
        JsonObject msg = Json.createObjectBuilder()
                .add("cmd", "getPlayerInfo").build();
        try {
            session.getBasicRemote().sendText(msg.toString());
        } catch (IOException ex) {
        }

    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Connection closed " + session.getId());
        System.out.println("close reason: " + reason.getReasonPhrase());
    }
}
