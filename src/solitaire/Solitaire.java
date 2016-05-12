package solitaire;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Card;
import main.Deck;
import main.Deck.DeckType;
import main.Game;
import main.Window;

public class Solitaire extends Game implements ActionListener {

	private SolitaireBoard sBoard;
	private SolitairePlayer sPlayer;
	
	public Solitaire() {
		super("Solitaire");
	}

	@Override
	protected void resetGame() {
		deck = new Deck(DeckType.French);
		deck.addActionListener(this);
		deck.setActionCommand(DEAL_TO_BOARD);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		window.add(deck, gbc);

		deck.shuffle();

		sPlayer = new SolitairePlayer();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		sPlayer.addActionListener(this);
		sPlayer.setActionCommand(PLAY_TO_PLAYER);
		window.add(sPlayer, gbc);

		sBoard = new SolitaireBoard();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		window.add(sBoard, gbc);

		window.pack();

		sBoard.setInitiation(true);
		for (int i = 0; i < sBoard.getMaxCardsX(); i++) {
			for (int j = 0; j < i + 1; j++) {
				sBoard.addCard(deck.dealCard(), i);
			}
		}
		sBoard.setInitiation(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PLAY_TO_PLAYER:
			Card bCard = sBoard.getSelectedCard();
			if (bCard == null) { break; }
			Card pCard = sPlayer.getSelectedCard();
			if (pCard == null) { break; }
			if (bCard.getSuit() == pCard.getSuit() && bCard.getValue() - 1 == pCard.getValue()) {
				sPlayer.addCard(sBoard.removeSelectedCard(), pCard.getSuit().ordinal());
			}
			break;
		case DEAL_TO_BOARD:
			for (int i = 0; i < 7; i++) {
				sBoard.addCard(deck.dealCard(), i);
			}
			break;
		case Window.NEW_GAME:
			window.remove(deck);
			window.remove(sPlayer);
			window.remove(sBoard);
			resetGame();
			break;
		}
	}
}
