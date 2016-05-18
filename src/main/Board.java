package main;

import java.awt.*;

public class Board extends CardContainer {
	
	protected boolean initiating;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, (int)getDimension().getWidth(), (int)getDimension().getHeight());
	}
	
	protected Dimension getDimension() {
		return new Dimension((int)(Card.WIDTH * maxCardsX + border * (maxCardsX + 1)), (int)(Card.HEIGHT * maxCardsY + border * (maxCardsY + 1)));
	}

	public void setInitiation(boolean state) {
		initiating = state;
	}
}
