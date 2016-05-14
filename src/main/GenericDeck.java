package main;

import java.awt.*;

/**
 * Created by henne on 14.05.16.
 */
public class GenericDeck extends Deck {

    public GenericDeck(DeckType type) {
        super(type);
        maxCardsX = 1;
        maxCardsY = 1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isEmpty()) {
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).setFaceUp(false);
                cards.get(i).setLocation(border, border);
            }
        }
    }
}
