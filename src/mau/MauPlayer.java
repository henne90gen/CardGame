package mau;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Card;
import main.Player;

public class MauPlayer extends Player {
	
	public MauPlayer() {
		border = 10;
		maxCardsX = 5;
		maxCardsY = 1;
		selectedCard = -1;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < cards.size(); i++) {
			int x = border + (Card.WIDTH + border) * i;
			int y = (cards.get(i).isMouseHover() || cards.get(i).isHighlighted())?border/2:border;
			cards.get(i).setFaceUp(true);
			if (cards.size() > maxCardsX) {
				int w = (int)getDimension().getWidth();
				x = (int) (w - border - Card.WIDTH - (double)(cards.size() - i - 1) * ((double)(w - Card.WIDTH - 2 * border) / (double)(cards.size() - 1)));
				cards.get(i).setLocation(x, y);
			} else {
				cards.get(i).setLocation(x, y);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).isMouseHover()) {
				cards.get(i).setHighlighted(true);
				if (selectedCard >= 0) cards.get(selectedCard).setHighlighted(false);
				selectedCard = i;
				revalidate();
				repaint();
			}
		}
	}
}
