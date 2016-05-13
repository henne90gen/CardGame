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
		int tmp = selectedCard;
		selectedCard = -1;
		remove(cards.get(tmp));
		revalidate();
		repaint();
		return cards.remove(tmp);
	}
	
	public Card getSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		return cards.get(selectedCard);
	}
	
	protected Dimension getDimension() {
		return new Dimension(Card.WIDTH * maxCardsX + border * (maxCardsX + 1), Card.HEIGHT + border * 2);
	}
}
