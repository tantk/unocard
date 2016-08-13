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

import static com.google.javascript.rhino.Token.name;
import java.io.IOException;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author tan
 */
@Dependent
@ServerEndpoint("/waitingroom")
public class GameRoom {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("got to session onopen");
        Map<String, Object> sessObject = session.getUserProperties();
        sessObject.put("name", session.getQueryString());
        System.out.println("\tquery string: " + session.getQueryString());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(session.getId());
        System.out.println(reason.getReasonPhrase());
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        System.out.println("got to onmessage, no of connection:" + session.getOpenSessions());
        for (Session s : session.getOpenSessions()) {
            try {
                s.getBasicRemote().sendText(": " + msg);
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }
    }
}
