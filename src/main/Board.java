package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Board extends CardContainer {
	
	protected boolean initiating;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, (int)getDimension().getWidth(), (int)getDimension().getHeight());
	}
	
	protected Dimension getDimension() {
		return new Dimension(Card.WIDTH * maxCardsX + border * (maxCardsX + 1), Card.HEIGHT * maxCardsY + border * (maxCardsY + 1));
	}

	public void setInitiation(boolean state) {
		initiating = state;
	}
}
