package solitaire;

import main.Board;
import main.Card;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SolitaireBoard extends Board {

	private ArrayList<Card>[] stacks;
	
	public SolitaireBoard() {
		border = 10;
		maxCardsX = 7;
		maxCardsY = 3;
		cards = null;
		stacks = new ArrayList[maxCardsX];
		addMouseListener(this);
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = new ArrayList<Card>();
		}
	}
	
	public void addCard(Card card, int stack) {
		if (card != null) {
			card.setFaceUp(!initiating);
			
			for (Card c : stacks[stack]) {
				remove(c);
			}
			stacks[stack].add(card);
			for (int i = stacks[stack].size() - 1; i >= 0; i--) {
				add(stacks[stack].get(i));
			}
			revalidate();
			repaint();
		}
	}

	/*public Card removeSelectedCard() {
        if (selCard == null || selCard[1] != stacks[selCard[0]].size() - 1) {
			return null;
		}
		Card card = stacks[selCard[0]].remove(selCard[1]);
		selCard = null;
		remove(card);
		revalidate();
		repaint();
		return card;
	}

	public Card getSelectedCard() {
		if (selCard == null || selCard[1] != stacks[selCard[0]].size() - 1) {
			return null;
		}
		return stacks[selCard[0]].get(selCard[1]);
	}*/

	public Card getTopCard(int stack) {
		return stacks[stack].get(stacks[stack].size() - 1);
	}

	@Override
	public void paintComponent(Graphics g) {
		setHighlightedCards();
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				int x = border + i * (Card.WIDTH + border);
				int y = (int)(border + j * (border * 1.5f));
                if (j == stacks[i].size() - 1 && !initiating) {
                    stacks[i].get(j).setFaceUp(true);
				}
				if (stacks[i].get(j).isHighlighted()) {
                    y -= border / 2;
                }
				stacks[i].get(j).setLocation(x, y);
			}
		}
		super.paintComponent(g);
	}

	private void setHighlightedCards() {
        for (ArrayList<Card> stack : stacks) {
            for (Card c : stack) {
                c.setHighlighted(false);
            }
        }
        boolean shouldHighlight = false;
        int stack = -1;
		int id = 0;
		for (int s = 0; s < stacks.length; s++) {
			for (int i = 0; i < stacks[s].size(); i++) {
				if (stacks[s].get(i).isMouseHover() && stacks[s].get(i).isFaceUp()) {
					shouldHighlight = true;
					stack = s;
					id = i;
					break;
				}
			}
			if (shouldHighlight) break;
		}
		if (shouldHighlight) {
			if (id == stacks[stack].size() - 1) {
				stacks[stack].get(id).setHighlighted(true);
			} else {
				for (int i = stacks[stack].size() - 1; i > id; i--) {
					if (areCompatible(stacks[stack].get(i - 1), stacks[stack].get(i))) {
						stacks[stack].get(i).setHighlighted(true);
						stacks[stack].get(i - 1).setHighlighted(true);
					} else {
						return;
					}
				}
			}
		}
	}

	protected Dimension getDimension() {
		return new Dimension(Card.WIDTH * maxCardsX + border * (maxCardsX + 1), Card.HEIGHT * maxCardsY + border * (maxCardsY + 1));
	}

    public boolean areCompatible(Card current, Card previous) {
		if (current.getValue() == previous.getValue() + 1) {
			if (current.getSuitColor() != previous.getSuitColor()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
        /*if (selCard != null) {
            for (int i = selCard[1]; i < stacks[selCard[0]].size(); i++) {
				stacks[selCard[0]].get(i).setHighlighted(false);
			}
			revalidate();
			repaint();
			for (int i = 0; i < stacks.length; i++) {
				if (stacks[i].size() == 0) {
					if (e.getX() > border + (Card.WIDTH + border) * i && e.getX() < (border + Card.WIDTH) * (i + 1)) {
						int originalStackSize = stacks[selCard[0]].size();
						for (int k = selCard[1]; k <  originalStackSize; k++) {
							addCard(stacks[selCard[0]].remove(selCard[1]), i);
						}
						selCard = null;
						revalidate();
						repaint();
						return;
					}
				}
			}
		}
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				if (stacks[i].get(j).isHighlighted()) {
					if (selCard != null && areCompatible(stacks[i].get(j), stacks[selCard[0]].get(selCard[1]))) {
						int originalStackSize = stacks[selCard[0]].size();
						for (int k = selCard[1]; k <  originalStackSize; k++) {
							addCard(stacks[selCard[0]].remove(selCard[1]), i);
						}
						selCard = null;
						revalidate();
						repaint();
						return;
					} else {
						selCard = new int[2];
						selCard[0] = i;
						selCard[1] = j;
						revalidate();
						repaint();
						return;
					}
				}
			}
		}
		selCard = null;*/
	}
}
