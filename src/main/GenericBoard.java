package main;

import java.awt.*;

public class GenericBoard extends Board {

	public GenericBoard() {
		border = 10;
		maxCardsX = 7;
		maxCardsY = 3;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < cards.size(); i++) {
			int x = (int) (border + (Card.WIDTH + border) * (i % maxCardsX));
			int y = border + (Card.HEIGHT + border) * (int)(i / maxCardsX);
			if (cards.get(i).isHighlighted()) { y -= border / 2; }
			cards.get(i).setFaceUp(true);
			cards.get(i).setLocation(x, y);
		}
	}
}
