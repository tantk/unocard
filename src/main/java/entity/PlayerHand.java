/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.LinkedList;

/**
 *
 * @author tan
 */
public class PlayerHand extends pileOfCards {

    public PlayerHand() {
        this.cardList = new LinkedList();
    }

    public int getHandValue() {
        int value=0;
        for (Card c : this.cardList) {
            value+=c.getScore();
        }
        return value;
    }
    
}
