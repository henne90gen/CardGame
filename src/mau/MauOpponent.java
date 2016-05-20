package mau;

import log.Log;
import main.Card;
import main.Player;

import java.awt.*;

import static mau.Mau.PREFIX;

/**
 * Created by henne on 17.05.16.
 */
public class MauOpponent extends Player {

    public MauOpponent() {
        border = 5;
        maxCardsX = 1;
        maxCardsY = 1;
        selectedCard = -1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setFaceUp(false);
            cards.get(i).setLocation(border, border);
        }
    }

    public Card getCard(int id) {
        if (id < cards.size()) return cards.get(id);
        else return null;
    }

    public Card removeCard(int id) {
        if (id < cards.size()) {
            Card c = cards.remove(id);
            remove(c);
            refresh();
            return c;
        } else return null;
    }

    public void addCard(Card card, int id) {
        super.addCard(card);
        Log.w(PREFIX, "Opponent " + id + " drew " + card.getValueAsString() + " of " + card.getSuit().toString());
    }
}
