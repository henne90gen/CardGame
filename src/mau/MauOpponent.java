package mau;

import main.Player;

import java.awt.*;

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
}
