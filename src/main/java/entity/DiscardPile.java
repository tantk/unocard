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
public class DiscardPile extends pileOfCards {
private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public DiscardPile() {
        this.cardList = new LinkedList();
    }

    public Card showTopCard() {
        return this.cardList.get(this.cardList.size() - 1);
    }
}
