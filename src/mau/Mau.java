package mau;

import main.Card;
import main.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mau extends Game implements ActionListener {

	public static final String DRAW_TWO_CARDS = "Draw two cards";
	public static final String SKIP_NEXT_PLAYER = "Skip next player";
	public static final String SWITCH_COLOR = "Switch color";

	private MauDeck m_deck;
	private MauPlayer m_player;

	private ArrayList<MauOpponent> opponents;

	public Mau() {
		super("MauMau");
	}

	@Override
	protected void resetGame() {
		resetOpponents();

		resetDeck();
		getDeck().shuffle();

		resetPlayer();
		
		for (int i = 0; i < 5; i++) {
			getPlayer().addCard(m_deck.dealCard());
		}
		for (int i= 0; i < opponents.size(); i++) {
			for (int j = 0; j < 5; j++) opponents.get(i).addCard(getDeck().dealCard());
		}
		getDeck().playCard(getDeck().dealCard());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case DECK_TO_PLAYER:
				m_player.addCard(m_deck.dealCard());
				break;
			case PLAYER_TO_BOARD:
				Card pCard = m_player.getSelectedCard();
				Card bCard = m_deck.getFaceUpCard();
				if (pCard.getValue() == bCard.getValue() || pCard.getSuit() == bCard.getSuit()) {
					m_deck.playCard(m_player.removeSelectedCard());
				}
				break;
			case SKIP_NEXT_PLAYER:

				break;
			case DRAW_TWO_CARDS:
				m_player.addCard(m_deck.dealCard());
				m_player.addCard(m_deck.dealCard());
				break;
			case SWITCH_COLOR:

				break;
			default:
				super.actionPerformed(e);
		}
	}

	@Override
	public MauPlayer getPlayer() {
		return m_player;
	}

	@Override
	public MauBoard getBoard() {
		return null;
	}

	@Override
	public MauDeck getDeck() {
		return m_deck;
	}

	private void resetOpponents() {
		if (opponents != null) {
			for (int i = 0; i < opponents.size(); i++) {
				window.remove(opponents.get(i));
			}
		} else {
			opponents = new ArrayList<MauOpponent>();
		}
		boolean correctNumber = false;
		int numOfOpponents = 2;
		/*
		while (!correctNumber) {
			String s = "";
			s = (String) JOptionPane.showInputDialog("Number of opponents (1-5):");
			if(!s.isEmpty()) {
				for(int i = 0; i < s.length(); i++) {
					if (i == 0 && s.charAt(i) == '-') {
						if (s.length() != 1) {
							break;
						}
					}
					if (Character.digit(s.charAt(i), 10) > 0 && Character.digit(s.charAt(i), 10) < 6) {
						correctNumber = true;
					}
				}
			}
			numOfOpponents = new Integer(s);
		}
		*/
		for (int i = 0; i < numOfOpponents; i++) {
			MauOpponent opponent = new MauOpponent();
			gbc = new GridBagConstraints();
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			opponents.add(opponent);
			window.add(opponent, gbc);
		}
	}

	@Override
	public void resetPlayer() {
		if (m_player != null) {
			window.remove(m_player);
		}
		m_player = new MauPlayer();
		m_player.addActionListener(this);
		m_player.setActionCommand(PLAYER_TO_BOARD);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = opponents.size();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		window.add(m_player, gbc);
	}

	@Override
	public void resetDeck() {
		if (m_deck != null) {
			window.remove(m_deck);
		}
		m_deck = new MauDeck();
		m_deck.addActionListener(this);
		m_deck.setActionCommand(DECK_TO_PLAYER);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = opponents.size();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		window.add(m_deck, gbc);
	}

	@Override
	public void resetBoard() {}
}
