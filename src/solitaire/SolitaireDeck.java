package solitaire;

import main.Card;
import main.Deck;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * Created by henne on 14.05.16.
 */
public class SolitaireDeck extends Deck {

    public SolitaireDeck() {
        super(DeckType.French);
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

    public void showNextCard() {
        for (Card c : faceUpStack) {
            remove(c);
        }
        if (!isEmpty()) {
            faceUpStack.add(cards.remove(cards.size() - 1));
            for (int i = faceUpStack.size() - 1; i >= 0; i--) {
                add(faceUpStack.get(i));
            }
            revalidate();
            repaint();
        } else {
            for (int i = faceUpStack.size() - 1; i >= 0; i--) {
                cards.add(faceUpStack.remove(i));
            }
            for (Card c : cards) {
                add(c);
            }
            revalidate();
            repaint();
        }
    }

    public Card getFaceUpCard() {
        return (faceUpStack.size() == 0)?null:faceUpStack.get(faceUpStack.size() - 1);
    }

    public Card removeFaceUpCard() {
        remove(faceUpStack.get(faceUpStack.size() - 1));
        revalidate();
        repaint();
        return faceUpStack.remove(faceUpStack.size() - 1);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 2 * border + Card.WIDTH) {
            showNextCard();
        } else if (e.getX() < border + Card.WIDTH) {
            fireActionPerformed(new ActionEvent(SolitaireDeck.this, 0, null));
        }
    }
}
