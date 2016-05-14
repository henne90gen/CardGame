package mau;

import main.Card;
import main.Deck;
import main.Deck.DeckType;
import main.Game;
import main.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mau extends Game implements ActionListener {

	public static final String DRAW_TWO_CARDS = "Draw two cards";
	public static final String SKIP_NEXT_PLAYER = "Skip next player";
	public static final String SWITCH_COLOR = "Switch color";

	private MauPlayer mPlayer;
	private MauPlayer mAI;
	private MauBoard mBoard;

	private ArrayList<MauPlayer> players = new ArrayList<MauPlayer>();

	public Mau() {
		super("MauMau");
	}

	@Override
	protected void resetGame() {
		deck = new Deck(DeckType.German);
		deck.addActionListener(this);
		deck.setActionCommand(PLAY_TO_PLAYER);
		deck.shuffle();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		window.add(deck, gbc);

		mPlayer = new MauPlayer();
        mPlayer.addActionListener(this);
        mPlayer.setActionCommand(PLAY_TO_BOARD);
        gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(mPlayer, gbc);
		
		/*mAI = new MauPlayer();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(mAI, gbc);*/

		mBoard = new MauBoard();
		mBoard.addActionListener(this);
		mBoard.setActionCommand(PLAY_TO_BOARD);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		window.add(mBoard, gbc);

		window.pack();
		
		for (int i = 0; i < 5; i++) {
			mPlayer.addCard(deck.dealCard());
		}
		mBoard.addCard(deck.dealCard());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case PLAY_TO_PLAYER:
				mPlayer.addCard(deck.dealCard());
				break;
			case PLAY_TO_BOARD:
				Card pCard = mPlayer.getSelectedCard();
				Card bCard = mBoard.getTopCard();
				if (pCard.getValue() == bCard.getValue() || pCard.getSuit() == bCard.getSuit()) {
					mBoard.addCard(mPlayer.removeSelectedCard());
				}
				break;
			case SKIP_NEXT_PLAYER:

				break;
			case DRAW_TWO_CARDS:
				mPlayer.addCard(deck.dealCard());
				mPlayer.addCard(deck.dealCard());
				break;
			case SWITCH_COLOR:

				break;
			case Window.NEW_GAME:
				window.remove(deck);
				window.remove(mPlayer);
				window.remove(mBoard);
				resetGame();
				break;
			case Window.EXIT_GAME:
				System.exit(0);
				break;
		}
	}
}
