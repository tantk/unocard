/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.*;
import entity.*;

/**
 *
 * @author tan When to Use ArrayList and LinkedList :
 *
 * In real world applications , you will more frequently use ArrayList than
 * LinkedList. But in a very specific situations LinkedList can be preferred.
 *
 * 1. ArrayList is preferred when there are more get(int) or search operations
 * need to be performed as every search operation runtime is O(1).
 *
 * 2. If application requires more insert(int) , delete(int) operations then the
 * get(int) operations then LinkedList is preferred as they do not need to
 * maintain back and forth like arraylist to preserve continues indices.
 *
 */
public class Deck extends pileOfCards {

    public Deck() {
        
    }
    public void createDeck(){
        this.cardList = new LinkedList();
        //add card type 1 until card type drawtwo for each color 2 times
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 13; j++) {//planning to rename image file name to my format
                    Card card = new Card(cardID, Card.cardType.values()[j], Card.cardColor.values()[i], Card.cardColor.values()[i].toString() + "_" + Card.cardType.values()[j].toString() + ".png");
                    this.cardList.add(card);
                    numberOfCards++;
                    cardID++;

                }
            }
        }
        //add the zeros
        for (int i = 0; i < 4; i++) {
            Card card = new Card(cardID, Card.cardType.values()[0], Card.cardColor.values()[i], Card.cardColor.values()[i].toString() + "_" + Card.cardType.values()[0].toString() + ".png");
            this.cardList.add(card);
            numberOfCards++;
            cardID++;
        }
        //add wild cards
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                //planning to rename image file name to my format
                Card card = new Card(cardID, Card.cardType.values()[13 + j], Card.cardColor.values()[4], Card.cardColor.values()[4].toString() + "_" + Card.cardType.values()[13 + j] + ".png");
                this.cardList.add(card);
                numberOfCards++;
                cardID++;

            }
        }
    }

}
