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

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author tan
 */
@ApplicationScoped

public class SessionHandler {
//global room is assumed to be 000000
    private final List<Session> allSessions = new LinkedList<>();
    private final HashMap<String, LinkedList<Session>> sessionRoom = new HashMap<>();

    public void add(Session s, String gameID) {
        allSessions.add(s);
        if (this.sessionRoom.containsKey(gameID)) {
            this.sessionRoom.get(gameID).add(s);

        } else {
            LinkedList<Session> newRoom = new LinkedList<>();
            newRoom.add(s);
            this.sessionRoom.put(gameID, newRoom);
        }
    }

    public void broadcastAll(String message) {
        allSessions.stream()
                .forEach(s -> {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException ex) {
                    }
                });
    }
//my netbeans have problem using lambda expression to loop through linkedlist
    public void broadcastRoom(String message, String gameID) {
        for (Session s : this.sessionRoom.get(gameID)) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException ex) {
            }

        }
    }
    //we have assummed session global to be 000000 in string
    
}
