/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import com.google.gson.Gson;
import entity.Player;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tan
 */
//not sure about stateful/stateless
@ApplicationScoped
public class PlayerManager {

    private Map<String, Player> globalPlayers = new HashMap<>();

    public  Map<String, Player> getGlobalPlayers() {
        return this.globalPlayers;
    }

   
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    public Player createPlayer(String name) {

        Player player = new Player(name);
        globalPlayers.put(player.getId(), player);
//        ArrayList<Player> plist = new ArrayList<>();
//        plist.add(player);
//        UNOGame gam = new UNOGame(plist);
//        em.persist(gameMgr);
       System.out.print("player created");
        return player;
    }

    public String getAllPlayersJSon() {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(globalPlayers);
        return jsonInString;
    }
}
