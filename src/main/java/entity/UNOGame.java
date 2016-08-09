/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.*;

import general.utility;
import javax.json.Json;
import javax.json.JsonArray;
import com.google.gson.*;
/**
 *
 * @author tan
 */
public class UNOGame {

    private String gameID;
    private List<Player> playerList;
    private GameStatus gameStatus;
    private Deck gameDeck;
    private DiscardPile discardPile;
    private String gameName;
    private int numberOfPlayer;
    
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public enum GameStatus {
        waiting,
        started,
        ended;
    }
//game is created when at least 1 player wants to play <- this idea to be implemened in another class in future

    public UNOGame() {
        this.gameID = UUID.randomUUID().toString().substring(0, 8);
        this.gameStatus = gameStatus.waiting;
        this.gameDeck = new Deck();
        this.discardPile = new DiscardPile();
        this.playerList= new ArrayList<Player>();
        this.numberOfPlayer=0;
    }

    public String getGameID() {
        return gameID;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public void playerDrawCardFromDeck(Player p) {
        p.drawCardFrom(this.gameDeck);
    }

    public Card discardCard(Player p, Card c) {
        this.discardPile.cardList.add(c);
        p.removeCardTo(c.getId(), this.discardPile);
        return c;
    }

    //assume draw first card from discardpile
    public void takeFromDiscardPile(pileOfCards DeckOrPlayerHand) {
        DeckOrPlayerHand.drawCardFrom(this.discardPile);
    }
//to implement remove player in future

    public void addPlayerToGame(Player p) {
        this.playerList.add(p);
        this.numberOfPlayer++;
    }

    public void setupGame() {
        String startingPlayer = "";
        this.gameDeck.createDeck();
        this.gameDeck.Shuffle();
//7 cards for ea players
        for (int i = 0; i < 7; i++) {
            for (Player c : this.playerList) {
                c.drawCardFrom(this.gameDeck);
            }
        }
        //form discardpile
        this.discardPile.cardList.add(this.gameDeck.removeCard());
        
        //System.out.println("Game ID:" + this.getGameID());
        //assume random player start first,get starting player:
        startingPlayer = this.playerList.get(utility.randInt(0, this.playerList.size() - 1)).getName();
        //System.out.println("The starting player is: " + startingPlayer);
    }

    public int countCardScoreOnBoard() {
        int cardScoreOnBoard = 0;
        for (Player p : this.getPlayerList()) {
            cardScoreOnBoard += p.getPlayerHand().getHandValue();
        }
        return cardScoreOnBoard;
    }

    public void displayGameSituation() {
        System.out.println("Cards in deck:" + this.getGameDeck().remainingCards());
        System.out.println("Discard: " + this.discardPile.showTopCard().toString());
        System.out.println("Total card score on board: " + countCardScoreOnBoard());
        System.out.println();
        for (Player p : this.getPlayerList()) {

            p.showPlayerHand();
            System.out.println();
        }
    }

    //change game status
    public void changeToStarted() {
        this.gameStatus = GameStatus.started;
    }

    public void changeToEnded() {
        this.gameStatus = GameStatus.ended;
    }

    public void changeToWaiting() {
        this.gameStatus = GameStatus.waiting;
    }

    public void playRandomCard(Player p) {
        p.removeRandomCardTo(this.discardPile);
    }

    public JsonArray playerToJsonArray() {
        JsonArray a = Json.createArrayBuilder().build();
        for (Player p : this.playerList) {
            a = Json.createArrayBuilder().add(p.playerToJson()).build();
        }
        return a;

    }
    public String unoGameToJson(){
    Gson gson = new Gson();
    String jsonInString = gson.toJson(this);
    return jsonInString;
    }
}
