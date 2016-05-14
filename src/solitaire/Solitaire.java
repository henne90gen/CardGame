package solitaire;

import main.Card;
import main.Game;
import main.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Solitaire extends Game implements ActionListener {

	private SolitaireBoard m_board;
	private SolitairePlayer m_player;
    private SolitaireDeck m_deck;
	
	public Solitaire() {
		super("Solitaire");
    }

	@Override
	protected void resetGame() {
		m_deck = new SolitaireDeck();
		m_deck.addActionListener(this);
        m_deck.setActionCommand(DECK_TO_BOARD);
        m_deck.shuffle();
		gbc = new GridBagConstraints();
        gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		window.add(m_deck, gbc);

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

		m_board = new SolitaireBoard();
        m_board.addActionListener(this);
        m_board.setActionCommand(BOARD_TO_PLAYER);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		window.add(m_board, gbc);

		window.pack();

		m_board.setInitiation(true);
		for (int i = 0; i < m_board.getMaxCardsX(); i++) {
			for (int j = 0; j < i + 1; j++) {
				m_board.addCard(m_deck.dealCard(), i);
			}
		}
		m_board.setInitiation(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
            case BOARD_TO_PLAYER:
            {
                Card c = m_board.getSelectedCard();
                if (c == null) { break; }
                if (c.getValue() == 0 || m_player.getCard(c.getSuit().ordinal()).getValue() + 1 == c.getValue()) {
                    m_player.addCard(m_board.removeSelectedCard(), c.getSuit().ordinal());
                }
                break;
            }
			case DECK_TO_BOARD:         // deck to player and then deck to board
            {
                Card c = m_deck.getFaceUpCard();
                if (c == null) { return; }
                if (c.getValue() == 0 || m_player.getCard(c.getSuit().ordinal()).getValue() + 1 == c.getValue()) {
                    m_player.addCard(m_deck.removeFaceUpCard(), c.getSuit().ordinal());
                    return;
                }
                for (int i = 0; i < m_board.getMaxCardsX(); i++) {
                    if (m_board.getTopCard(i) != null) {
                        if (m_board.areCompatible(m_board.getTopCard(i), c)) {
                            m_board.addCard(m_deck.removeFaceUpCard(), i);
                            break;
                        }
                    }
                }
                break;
            }
			case Window.NEW_GAME:
				window.remove(m_deck);
				window.remove(m_player);
				window.remove(m_board);
				resetGame();
				break;
		}
	}
}
