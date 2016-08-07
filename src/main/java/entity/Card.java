/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author tan
 */
public class Card {

    private cardType type;
    private cardColor color;
    private int score;
    private String image;
    private int id;

    public Card() {
    }

    @Override
    public String toString() {
        return "Card:" + " color=" + color + ", type=" + type + ", score=" + score + ", image=" + image;
    }

    public Card(int ID, cardType type, cardColor color, String image) {
        this.type = type;
        this.color = color;
        this.id = ID;
        this.image = image;

        if (type.getCardValue() < 10) {
            this.score = type.getCardValue();
        } else {
            switch (type.getCardValue()) {
                case 10:
                    this.score = 20;
                    break;
                case 11:
                    this.score = 20;
                    break;
                case 12:
                    this.score = 20;
                    break;
                case 13:
                    this.score = 50;
                    break;
                case 14:
                    this.score = 50;
                    break;
            }
        }

    }

    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public cardType getType() {
        return type;
    }

    /**
     * @return the color
     */
    public cardColor getColor() {
        return color;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    public enum cardType {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        SKIP(10),
        REVERSE(11),
        DRAWTWO(12),
        AnyColor(13),
        DRAWFOUR(14);

        private int cardValue;
//if numeric form is needed
        private cardType(int value) {
            this.cardValue = value;
        }

        public int getCardValue() {
            return cardValue;
        }
    }

    public enum cardColor {
        RED,
        ORANGE,
        GREEN,
        BLUE,
        WILDCARD;
    }
public JsonObject cardToJson()
{
    return Json.createObjectBuilder().add("id",this.getId()).add("type", this.getType().toString()).add("color",this.getColor().toString()).add("score", this.getScore()).add("image", this.getImage()).build();
            
}
}
