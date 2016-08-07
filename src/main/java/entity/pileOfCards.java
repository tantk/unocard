/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Collections;
import java.util.LinkedList;
import entity.*;
import javax.json.Json;
import javax.json.JsonArray;

/**
 * 
 * @author tan
 * i think deck, playerhand and discard pile is a stack of cards, therefore created superclass pileOfCards
 */
public class pileOfCards {

    protected int numberOfCards;
    //use linkedlist because we draw cards more often than get card details in uno.
    //heard from mr chuk that we can use stack as list contains too much function that we might not need
    
    protected LinkedList<Card> cardList;
    protected String imageBack = "back.png";
    protected int cardID;

    public pileOfCards() {
    }

    /**
     * @return the numberOfCards
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * @return the cardList
     */
    public LinkedList<Card> getCardList() {
        return cardList;
    }

    public Card removeCard() {

        Card drawnCard = this.cardList.get(this.cardList.size() - 1);
        this.cardList.remove(this.cardList.size() - 1);
        return drawnCard;
    }

    public void Shuffle() {
        Collections.shuffle(this.cardList);
    }

    public int remainingCards() {
        return this.cardList.size();
    }
    public Card drawCardFrom(pileOfCards pile)
    {
        Card drawnCard=pile.removeCard();
        this.cardList.add(drawnCard);
        return drawnCard;
    }
        public JsonArray cardsToJsonArray() {
        JsonArray a = Json.createArrayBuilder().build();
        for (Card c : this.cardList) {
            a = Json.createArrayBuilder().add(c.cardToJson()).build();
        }
        return a;

    }

}
