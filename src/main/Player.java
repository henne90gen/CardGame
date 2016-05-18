package main;

import java.awt.*;

public class Player extends CardContainer {
	
	protected int selectedCard;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, (int)getDimension().getWidth(), (int)getDimension().getHeight());
	}
	
	public Card removeSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		Card c = cards.remove(selectedCard);
		selectedCard = -1;
		remove(c);
		refresh();
		return c;
	}
	
	public Card getSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		return cards.get(selectedCard);
	}
	
	protected Dimension getDimension() {
		return new Dimension((int)(Card.WIDTH * maxCardsX + border * (maxCardsX + 1)), (int)(Card.HEIGHT + border * 2));
	}
}
