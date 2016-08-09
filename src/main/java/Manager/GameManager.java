/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import com.google.gson.Gson;
import entity.Player;
import entity.UNOGame;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tans
 */
//not sure about stateful/stateless
@Stateless
public class GameManager {

    private static Map<String, UNOGame> globalUnoGames = new HashMap<>();
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    public UNOGame createGame(String gameName) {
        UNOGame newGame = new UNOGame();
        newGame.setGameName(gameName);
        globalUnoGames.put(newGame.getGameID(), newGame);

        System.out.print("got here");
        return newGame;
    }

    public Map<String, UNOGame> getAllGames() {
        return globalUnoGames;
    }

    public String getAllGamesJSon() {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(globalUnoGames);
        return jsonInString;
    }

    public Player addPlayer(String gameID, String playerID) {
        Player p = Manager.PlayerManager.getGlobalPlayers().get(playerID);
        globalUnoGames.get(gameID).addPlayerToGame(p);
        return p;
    }

}
