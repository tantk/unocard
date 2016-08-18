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
