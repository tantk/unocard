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

/**
 *
 * @author tan
 */
@Dependent
@ServerEndpoint("/globalRoom")
public class GlobalRoomEndPoint {

    @Inject
    private GameManager gamesMgr;
    @Inject
    SessionHandler connectionHandler;

    @OnMessage
    public void onMessage(String msg) {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(msg.getBytes()));
        JsonObject json = reader.readObject();

        switch (json.getString("cmd")) {
          
            case "ToGlobalRoomEndPoint-createdGame":
                System.out.println("> created game");
                JsonObject msgGlobalRefresh = Json.createObjectBuilder()
                        .add("cmd", "ToGlobalClient-createdGame").build();
                connectionHandler.broadcastRoom(msgGlobalRefresh.toString(), "000000");
                System.out.println("broadcastglobal that game created");
                break;
         

            default:
                //RoomIDs.broadcast(RoomID, json);
                System.out.println("msg switch not matched!!!" + msg);
                break;
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected: ");
        System.out.println("onopen");
        connectionHandler.add(session,"000000");
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
