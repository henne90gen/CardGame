package mau;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Deck;
import main.Deck.DeckType;
import main.Game;

public class Mau extends Game implements ActionListener {

	private MauPlayer mPlayer;
	private MauPlayer mAI;
	private MauBoard mBoard;
	
	public Mau() {
		super("MauMau");
	}

	@Override
	protected void resetGame() {
		deck = new Deck(DeckType.German);
		deck.addActionListener(this);
		deck.setActionCommand(DEAL_TO_BOARD);
		deck.shuffle();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		window.add(deck, gbc);

		mPlayer = new MauPlayer();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		mPlayer.addActionListener(this);
		mPlayer.setActionCommand(PLAY_TO_PLAYER);
		window.add(mPlayer, gbc);
		
		mAI = new MauPlayer();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(mAI, gbc);

		mBoard = new MauBoard();
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
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
