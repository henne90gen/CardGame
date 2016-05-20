package mau;

import main.Card;
import main.Deck;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by henne on 17.05.16.
 */
public class MauDeck extends Deck {

    public MauDeck() {
        super(DeckType.German);
        maxCardsX = 2;
        maxCardsY = 1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isEmpty()) {
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).setFaceUp(false);
                cards.get(i).setLocation(border * 2 + Card.WIDTH, border);
            }
        }
        for (int i = 0; i < faceUpStack.size(); i++) {
            faceUpStack.get(i).setFaceUp(true);
            faceUpStack.get(i).setLocation(border, border);
        }
    }

    public boolean areCompatible(Card current, Card incoming) {
        return ((current.getSuit() == incoming.getSuit()) || (current.getValue() == incoming.getValue()) || (incoming.getValue() == 10));
    }

    @Override
    public Card dealCard() {
        Card c = super.dealCard();
        if (c == null) {
            cards = new ArrayList<Card>();
            for (int i = faceUpStack.size() - 2; i >= 0; i--) {
                addCard(faceUpStack.get(i));
                remove(faceUpStack.remove(i));
                refresh();
            }
            return super.dealCard();
        }
        return c;
    }

    public void playCard(Card card) {
        if (card != null) {
            for (Card c : faceUpStack) {
                remove(c);
            }
            faceUpStack.add(card);
            for (int i = faceUpStack.size() - 1; i >= 0; i--) {
                add(faceUpStack.get(i));
            }
            refresh();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getPoint().getX() > 2 * border + Card.WIDTH) {
            fireActionPerformed(new ActionEvent(MauDeck.this, 0, null));
        }
    }
}
