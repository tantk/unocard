/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author tantk
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class ClientWebSocketServer {

    @Inject
    private ClientSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
         Logger.getLogger(ClientWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Client client = new Client();
                client.setName(jsonMessage.getString("name"));
                client.setDescription(jsonMessage.getString("description"));
                client.setType(jsonMessage.getString("type"));
                client.setStatus("Off");
                sessionHandler.addClient(client);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeClient(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleClient(id);
            }
        }
    }
}
