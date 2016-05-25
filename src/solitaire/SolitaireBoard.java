package solitaire;

import main.Board;
import main.Card;
import main.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SolitaireBoard extends Board {

	private boolean m_finishing = false;

	public SolitaireBoard() {
		border = 10;
		maxCardsX = 7;
		maxCardsY = 3.5f;
		cards = null;
		stacks = new ArrayList[(int)maxCardsX];
		addMouseListener(this);
		setLayout(null);
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = new ArrayList<Card>();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		setHighlightedCards();
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				int x = border + i * (Card.WIDTH + border);
				int y = (int)(border + j * (border * 1.75f));
                if (j == stacks[i].size() - 1 && !initiating) {
                    stacks[i].get(j).setFaceUp(true);
				}
				if (stacks[i].get(j).isHighlighted()) {
                    y -= border / 2;
                }
				stacks[i].get(j).setBounds(x, y, Card.WIDTH, Card.HEIGHT);
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
		return new Dimension((int)(Card.WIDTH * maxCardsX + border * (maxCardsX + 1)), (int)(Card.HEIGHT * maxCardsY + border * (maxCardsY + 1)));
	}

    public boolean areCompatible(Card current, Card incoming) {
		if (current.getValue() == incoming.getValue() + 1) {
			if (current.getSuitColor() != incoming.getSuitColor()) {
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
        Card tmp = getSelectedCard();
        fireActionPerformed(new ActionEvent(SolitaireBoard.this, 0, null));
        Card c = getSelectedCard();
		if (c != null) {
            if (c.getSuit() == tmp.getSuit() && c.getValue() == tmp.getValue()) {
                fireActionPerformed(new ActionEvent(SolitaireBoard.this, 0, Game.BOARD_TO_BOARD));
            }
        }
	}

    public boolean isTopCard(Card card) {
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].size(); j++) {
                Card c = stacks[i].get(j);
                if (c.getSuit() == card.getSuit() && c.getValue() == card.getValue()) {
                    if (j == stacks[i].size() - 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean canFinish() {
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].size(); j++) {
                if (!stacks[i].get(j).isFaceUp()) {
                    return false;
                }
            }
        }
        return !m_finishing;
    }

	public void setFinishing(boolean finishing) {
		m_finishing = finishing;
	}

	public boolean isFinishing() { return m_finishing; }

	public void setSelectedCard(int stack, int index) {
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				stacks[i].get(j).setHighlighted(false);
			}
		}
		if (stack >= 0 && stack < stacks.length) {
			if (index >= 0 && index < stacks[stack].size()) {
				stacks[stack].get(index).setHighlighted(true);
			}
		}
	}

	@Override
	public Point getNextCardLocation(int i) {
		return new Point(border + i * (Card.WIDTH + border), (int)(border + getNumCardsOnStack(i) * (border * 1.75f)));

	}
}
