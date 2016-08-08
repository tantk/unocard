/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import entity.Player;
import entity.UNOGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tan
 */
//not sure about stateful/stateless
@Stateless
public class PlayerManager {
private static Map<String,Player> globalPlayers= new HashMap<>();
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
        em.persist(globalPlayers);
        return player;
    }
}
