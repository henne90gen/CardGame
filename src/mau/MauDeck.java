package mau;

import main.Card;
import main.Deck;

import java.awt.*;

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
                cards.get(i).setLocation(2 * border + Card.WIDTH, border);
            }
            for (int i = 0; i < faceUpStack.size(); i++) {
                faceUpStack.get(i).setFaceUp(true);
                faceUpStack.get(i).setLocation(border, border);
            }
        }
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
}
