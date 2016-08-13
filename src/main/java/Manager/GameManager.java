/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import com.google.gson.Gson;
import entity.Card;
import entity.Player;
import entity.UNOGame;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tans
 */
//not sure about stateful/stateless
@ApplicationScoped
public class GameManager {

    @Inject 
    private PlayerManager playerMgr;
    private Map<String, UNOGame> globalUnoGames = new HashMap<>();
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
        Player p = playerMgr.getGlobalPlayers().get(playerID);
        //if static Manager.PlayerManager.getGlobalPlayers().get(playerID);
        globalUnoGames.get(gameID).addPlayerToGame(p);
        return p;
    }
    public List getPlayerList(String gameID)
    {
        return globalUnoGames.get(gameID).getPlayerList();
    }
    public String getStatus(String gameID)
    {
    return globalUnoGames.get(gameID).getGameStatus().toString();
    }
    public void startGame(String gameID)
    {
    globalUnoGames.get(gameID).changeToStarted();
    globalUnoGames.get(gameID).setupGame();
    }
    public LinkedList getPlayerHandCardList(String gameID,String playerID)
            
    {
        return globalUnoGames.get(gameID).getPlayerHand(playerID).getCardList();
    }
    public Card showTopDiscard(String gameID)
            
    {
        return globalUnoGames.get(gameID).getDiscardPile().showTopCard();
    }
    
}
