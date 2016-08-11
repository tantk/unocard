/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference;

import java.io.IOException;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

/**
 *
 * @author tantk
 */
@ApplicationScoped
public class ClientSessionHandler {

    private final Set<Session> sessions = new HashSet<>();
    private final Set<Client> clients = new HashSet<>();
    private int clientId = 0;

    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public void addSession(Session session) {
        sessions.add(session);
        for (Client client : clients) {
            JsonObject addMessage = createAddMessage(client);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void addClient(Client client) {
        client.setId(clientId);
        clients.add(client);
        clientId++;
        JsonObject addMessage = createAddMessage(client);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeClient(int id) {
        Client client = getClientById(id);
        if (client != null) {
            clients.remove(client);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleClient(int id) {
        JsonProvider provider = JsonProvider.provider();
        Client client = getClientById(id);
        if (client != null) {
            if ("On".equals(client.getStatus())) {
                client.setStatus("Off");
            } else {
                client.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", client.getId())
                    .add("status", client.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }

    private Client getClientById(int id) {
        for (Client device : clients) {
            if (device.getId() == id) {
                return device;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Client client) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", client.getId())
                .add("name", client.getName())
                .add("type", client.getType())
                .add("status", client.getStatus())
                .add("description", client.getDescription())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(ClientSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
