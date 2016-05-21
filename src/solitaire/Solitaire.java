package solitaire;

import main.Card;
import main.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Solitaire extends Game implements ActionListener {

	public static final String ADD_MOVE = "Add move";
	public static final String PREFIX = "Solitaire";

	private SolitaireBoard m_board;
	private SolitairePlayer m_player;
    private SolitaireDeck m_deck;

	private Statistics m_stats;
	
	public Solitaire() {
		super(PREFIX);
    }

	@Override
	protected void resetGame() {
		if (m_stats != null) window.remove(m_stats.getTimerLabel());
		m_stats = new Statistics();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(m_stats.getTimerLabel(), gbc);

		resetDeck();
		getDeck().shuffle();

		resetPlayer();

		resetBoard();

		m_board.setInitiation(true);
		for (int i = 0; i < m_board.getMaxCardsX(); i++) {
			for (int j = 0; j < i + 1; j++) {
				m_board.addCard(m_deck.dealCard(), i);
			}
		}
		m_board.setInitiation(false);

		m_stats.startTimer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
            case BOARD_TO_PLAYER:
            {
                if (moveCardToPlayer(m_board.getSelectedCard())) m_stats.addMove();
                break;
            }
			case DECK_TO_BOARD:         // deck to player and then deck to board
            {
                Card c = m_deck.getFaceUpCard();
                if (c == null) { return; }
                if (c.getValue() == 0 || m_player.getCard(c.getSuit().ordinal()).getValue() + 1 == c.getValue()) {		// Moving card from deck to player
                    m_player.addCard(m_deck.removeFaceUpCard(), c.getSuit().ordinal());
					m_stats.addMove();
                    return;
                }
                for (int i = 0; i < m_board.getMaxCardsX(); i++) {
                    if (m_board.getTopCard(i) != null) {
                        if (m_board.areCompatible(m_board.getTopCard(i), c)) { 		// Moving card from deck to next possible position on board
                            m_board.addCard(m_deck.removeFaceUpCard(), i);
							m_stats.addMove();
                            break;
                        }
                    } else if (c.getValue() == 12) { 			// Moving King to empty place on board
						m_board.addCard(m_deck.removeFaceUpCard(), i);
						m_stats.addMove();
						break;
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
							m_stats.addMove();
							break;
						}
					}
				}
				break;
			case ADD_MOVE:
				m_stats.addMove();
				break;
			default:
				super.actionPerformed(e);
		}
		if (m_board.canFinish()) {
			autoFinishGame();
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
				return true;
			}
		}
		return false;
	}

	public void autoFinishGame() {
		while (!m_player.gameIsFinished()) {
			for (int i = 0; i < m_board.getMaxCardsX(); i++) {
				Card c = m_board.getTopCard(i);
				if (c != null) {
					m_board.setSelectedCard(c);
					if (moveCardToPlayer(c)) m_stats.addMove();
				}
			}
			if (m_deck.getFaceUpCard() != null) {
				actionPerformed(new ActionEvent(Solitaire.this, 0, DECK_TO_BOARD));
			} else {
				m_deck.showNextCard();
				m_stats.addMove();
			}
		}
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
		if (m_deck != null) {
			window.remove(m_deck);
		}
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
}
