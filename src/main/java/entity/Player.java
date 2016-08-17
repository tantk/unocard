/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import general.utility;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author tan
 */
public class Player {

    public Player(String name) {

        this.id = UUID.randomUUID().toString().substring(0, 4);
        this.name = name;
        this.playerHand = new PlayerHand();
    }
    private String id;
    private String name;
    //use linkedlist because we draw cards more often than get card details in uno.
    private PlayerHand playerHand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerHand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(PlayerHand playerHand) {
        this.playerHand = playerHand;
    }

    public void drawCardFrom(pileOfCards piles) {
        this.playerHand.cardList.add(piles.removeCard());
    }

    public Card removeCardTo(int cardID, pileOfCards piles) {
        Card discarded = new Card();
        for (Card c : this.playerHand.cardList) {
            if (c.getId() == cardID) {
                discarded = c;
                
            }
        }
        this.playerHand.cardList.remove(discarded);
        piles.cardList.add(discarded);
        return discarded;
    }

    public void showPlayerHand() {
        System.out.println("Player ID:" + this.getId() + " Name=" + this.getName() + ":");
        System.out.println("Player Total Card Score: " + this.playerHand.getHandValue());
        System.out.println("Card in hand:");
        for (Card c : this.getPlayerHand().cardList) {
            System.out.println(c.toString());
        }
    }

    public void removeRandomCardTo(pileOfCards pile) {
        removeCardTo(utility.randInt(0, (this.playerHand.cardList.size() - 1)), pile);
    }

    public JsonObject playerToJson() {
        return Json.createObjectBuilder().add("id", this.id).add("name", this.name).add("hand", this.playerHand.cardsToJsonArray()).build();

    }
}
