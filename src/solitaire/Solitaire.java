package solitaire;

import main.Card;
import main.Game;
import main.Statistics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Solitaire extends Game {

	public static final String ADD_MOVE = "Add move";
	public static final String PREFIX = "Solitaire";

	private SolitaireBoard m_board;
	private SolitairePlayer m_player;
    private SolitaireDeck m_deck;
	
	public Solitaire() {
		super(PREFIX);
    }

	@Override
	protected void resetGame() {
		super.resetGame();

		m_deck.shuffle();
		m_board.setInitiation(true);
		for (int i = 0; i < m_board.getMaxCardsX(); i++) {
			for (int j = 0; j < i + 1; j++) {
				m_board.addCard(m_deck.dealCard(), i);
			}
		}
		m_board.setInitiation(false);

		stats.startTimer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
            case BOARD_TO_PLAYER:
            {
                moveCardToPlayer(m_board.getSelectedCard());
                break;
            }
			case DECK_TO_BOARD:         // deck to player and then deck to board
            {
                Card c = m_deck.getFaceUpCard();
                if (c == null) { return; }
                if (c.getValue() == 0 || m_player.getCard(c.getSuit().ordinal()).getValue() + 1 == c.getValue()) {		// Moving card from deck to player
                    if (m_board.isFinishing()) {
						m_player.addCard(m_deck.removeFaceUpCard(), c.getSuit().ordinal());
					} else {
						window.animate(m_player, m_deck, c.getSuit().ordinal());
					}
					stats.addMove();
                    return;
                }
                for (int i = 0; i < m_board.getMaxCardsX(); i++) {		// Moving card from deck to next possible position on board
                    if (((m_board.getTopCard(i) != null && m_board.areCompatible(m_board.getTopCard(i), c)) || (c.getValue() == 12 && m_board.getTopCard(i) == null)) && !window.isAnimating()) {
						if (m_board.isFinishing()) {
							m_board.addCard(m_deck.removeFaceUpCard(), i);
						} else {
							window.animate(m_board, m_deck, i);
						}
						stats.addMove();
						break;
                    }
                }
                break;
            }
			case BOARD_TO_BOARD:
			{
				Card c = m_board.getSelectedCard();
				Card tmp = null;
				for (int i = 0; i < m_board.getMaxCardsX(); i++) {
					if (m_board.getNumCardsOnStack(i) != 0) {
						boolean isSameStack = false;
						for (int j = 0; j < m_board.getNumCardsOnStack(i); j++) {
							m_board.setSelectedCard(i, j);
							tmp = m_board.getSelectedCard();
							if (c.getSuit() == tmp.getSuit() && c.getValue() == tmp.getValue()) {
								isSameStack = true;
							}
						}
						m_board.setSelectedCard(i, m_board.getNumCardsOnStack(i) - 1);
						tmp = m_board.getSelectedCard();
						if (m_board.areCompatible(tmp, c) && !isSameStack) {
							m_board.setSelectedCard(c);
							if (m_board.isFinishing()) {
								m_board.addCard(m_board.removeSelectedCards(), i);
							} else {
								window.animate(m_board, c, i);
							}
							stats.addMove();
							return;
						}
					} else if (c.getValue() == 12) {
						m_board.setSelectedCard(c);
						if (m_board.isFinishing()) {
							m_board.addCard(m_board.removeSelectedCards(), i);
						} else {
							window.animate(m_board, c, i);
						}
						stats.addMove();
						return;
					}
				}
				break;
			}
			case PLAYER_TO_BOARD:
				Card c = m_player.getSelectedCard();
				for (int i = 0; i < m_board.getMaxCardsX(); i++) {
					if (m_board.getTopCard(i) != null) {
						if (m_board.areCompatible(m_board.getTopCard(i), c)) {
							m_board.addCard(m_player.removeSelectedCard(), i);
							stats.addMove();
							break;
						}
					}
				}
				break;
			default:
				super.actionPerformed(e);
		}
		if (m_board.canFinish()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					autoFinishGame();
				}
			}).start();
		}
	}

	public boolean moveCardToPlayer(Card c) {
		if (c != null && m_board.isTopCard(c)) {
			if (c.getValue() == 0 || m_player.getCard(c.getSuit().ordinal()).getValue() + 1 == c.getValue()) {
				ArrayList<Card> cl = m_board.removeSelectedCards();
				if (cl == null) {
					return false;
				}
				m_player.addCard(cl.get(cl.size() - 1), c.getSuit().ordinal());
				stats.addMove();
				return true;
			}
		}
		return false;
	}

	public synchronized void autoFinishGame() {
		int timeDelay = 50;
		m_board.setFinishing(true);
		while (!m_player.gameIsFinished()) {
			for (int i = 0; i < m_board.getMaxCardsX(); i++) {
				Card c = m_board.getTopCard(i);
				if (c != null) {
					m_board.setSelectedCard(c);
					moveCardToPlayer(c);
					try {
						Thread.sleep(timeDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (m_deck.getFaceUpCard() != null) {
				Card c = m_deck.getFaceUpCard();
				actionPerformed(new ActionEvent(Solitaire.this, 0, DECK_TO_BOARD));
				if (m_deck.getFaceUpCard() == null || (c.getSuit() == m_deck.getFaceUpCard().getSuit() && c.getValue() == m_deck.getFaceUpCard().getValue())) {
					m_deck.showNextCard();
					stats.addMove();
					try {
						Thread.sleep(timeDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				m_deck.showNextCard();
				stats.addMove();
				try {
					Thread.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		stats.stopTimer();
	}

	@Override
	public SolitairePlayer getPlayer() {
		return m_player;
	}

	@Override
	public SolitaireBoard getBoard() {
		return m_board;
	}

	@Override
	public SolitaireDeck getDeck() {
		return m_deck;
	}

	public void resetPlayer() {
		if (m_player != null) {
			window.remove(m_player);
		}
		m_player = new SolitairePlayer();
		m_player.addActionListener(this);
		m_player.setActionCommand(PLAYER_TO_BOARD);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(m_player, gbc);
	}

	public void resetBoard() {
		if (m_board != null) {
			window.remove(m_board);
		}
		m_board = new SolitaireBoard();
		m_board.addActionListener(this);
		m_board.setActionCommand(BOARD_TO_PLAYER);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		window.add(m_board, gbc);
	}

	public void resetDeck() {
		if (m_deck != null) window.remove(m_deck);
		m_deck = new SolitaireDeck();
		m_deck.addActionListener(this);
		m_deck.setActionCommand(DECK_TO_BOARD);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		window.add(m_deck, gbc);
	}

	@Override
	public void resetStatistics() {
		if (stats != null) window.remove(stats);
		stats = new Statistics(PREFIX);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(stats, gbc);
	}
}
