package mau;

import main.Board;
import main.Card;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class MauBoard extends Board {

	public MauBoard() {
		border = 10;
		maxCardsX = 1;
		maxCardsY = 1;
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Card c : cards) {
			c.setFaceUp(true);
			c.setLocation(border, border);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		fireActionPerformed(new ActionEvent(MauBoard.this, 0, null));
	}

	public Card getTopCard() {
		return cards.get(cards.size() - 1);
	}

	@Override
	public void addCard(Card card) {
		super.addCard(card);
		switch (card.getValue()) {
			case 0:
				fireActionPerformed(new ActionEvent(MauBoard.this, 0, Mau.SKIP_NEXT_PLAYER));
				break;
			case 6:
				fireActionPerformed(new ActionEvent(MauBoard.this, 0, Mau.DRAW_TWO_CARDS));
				break;
			case 10:
				fireActionPerformed(new ActionEvent(MauBoard.this, 0, Mau.SWITCH_COLOR));
		}
	}
}
