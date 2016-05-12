package mau;

import java.awt.Dimension;

import main.Board;
import main.Card;

public class MauBoard extends Board {

	public MauBoard() {
		border = 10;
		maxCardsX = 1;
		maxCardsY = 1;
		addMouseListener(this);
	}
	
	@Override
	protected Dimension getDimension() {
		return new Dimension(Card.WIDTH + 2 * border, Card.HEIGHT + 2 * border);
	}
}
