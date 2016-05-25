package main;

import java.awt.*;
import java.util.ArrayList;

public class Board extends CardContainer {

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

	public Card getTopCard(int stack) {
		return (stacks[stack].size() == 0)?null:stacks[stack].get(stacks[stack].size() - 1);
	}

	public Card getSelectedCard() {
		for (int i = 0; i < maxCardsX; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				Card c = stacks[i].get(j);
				if (c != null && c.isHighlighted()) {
					return c;
				}
			}
		}
		return null;
	}

	public ArrayList<Card> removeSelectedCards() {
		int stack = -1;
		int id = -1;
		for (int i = 0; i < maxCardsX; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				Card tmp = stacks[i].get(j);
				if (tmp.isHighlighted()) {
					stack = i;
					id = j;
					break;
				}
			}
			if (stack != -1 && id != -1) break;
		}
		if (stack == -1 || id == -1) return null;
		int stackSize = stacks[stack].size();
		ArrayList<Card> result = new ArrayList<Card>();
		for (int i = id; i < stackSize; i++) {
			Card c = stacks[stack].remove(id);
			remove(c);
			refresh();
			result.add(c);
		}
		return result;
	}

	public void setSelectedCard(Card card) {
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				stacks[i].get(j).setHighlighted(false);
			}
		}
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				Card c = stacks[i].get(j);
				if (c.getSuit() == card.getSuit() && c.getValue() == card.getValue()) {
					stacks[i].get(j).setHighlighted(true);
				}
			}
		}
	}
}
