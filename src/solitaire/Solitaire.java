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
        m_deck.setActionCommand(PLAY_TO_BOARD);
        m_deck.shuffle();
		gbc = new GridBagConstraints();
        gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		window.add(m_deck, gbc);

		m_player = new SolitairePlayer();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		m_player.addActionListener(this);
		m_player.setActionCommand(PLAY_TO_PLAYER);
		window.add(m_player, gbc);

		m_board = new SolitaireBoard();
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
			case PLAY_TO_PLAYER:
                /*Card bCard = m_board.getSelectedCard();
                if (bCard == null) { break; }
				Card pCard = m_player.getSelectedCard();
				if (pCard == null) { break; }
				if (bCard.getSuit() == pCard.getSuit() && bCard.getValue() - 1 == pCard.getValue()) {
					m_player.addCard(m_board.removeSelectedCard(), pCard.getSuit().ordinal());
				}*/
				break;
			case PLAY_TO_BOARD:
                for (int i = 0; i < 7; i++) {
                    Card d = m_deck.getFaceUpCard();
                    if (d.getValue() == 0 || m_player.getCard(d.getSuit().ordinal()).getValue() + 1 == d.getValue()) {
                        m_player.addCard(m_deck.removeFaceUpCard(), d.getSuit().ordinal());
                        break;
                    } else if (m_board.areCompatible(m_board.getTopCard(i), d)) {
                        m_board.addCard(m_deck.removeFaceUpCard(), i);
                        break;
                    }
                }
				break;
			case Window.NEW_GAME:
				window.remove(m_deck);
				window.remove(m_player);
				window.remove(m_board);
				resetGame();
				break;
		}
	}
}
