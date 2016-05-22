package mau;

import log.Log;
import main.Board;
import main.Card;
import main.Game;
import main.Statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mau extends Game implements ActionListener {

	public static final String PREFIX = "MauMau";

	private MauDeck m_deck;
	private MauPlayer m_player;
	private ArrayList<MauOpponent> opponents;

	private boolean cardEffectsNextPlayer;
	private int cardsToDrawForNextPlayer;

	public Mau() {
		super(PREFIX);
	}

	@Override
	protected void resetGame() {
		resetOpponents();
		super.resetGame();

		cardEffectsNextPlayer = true;
		cardsToDrawForNextPlayer = 0;

		Log.setVerbosity(false);

		m_deck.shuffle();
		for (int i = 0; i < 5; i++) {
			m_player.addCard(m_deck.dealCard());
		}
		for (int i= 0; i < opponents.size(); i++) {
			for (int j = 0; j < 5; j++) opponents.get(i).addCard(m_deck.dealCard());
		}
		m_deck.playCard(m_deck.dealCard());

		Log.setVerbosity(true);
		stats.startTimer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case DECK_TO_PLAYER:
				Card tmp = m_deck.dealCard();
				m_player.addCard(tmp);
				stats.addMove();
				playOpponent(0);
				break;
			case PLAYER_TO_BOARD:
				Card pCard = m_player.getSelectedCard();
				Card dCard = m_deck.getFaceUpCard();

				if (cardEffectsNextPlayer && dCard.getValue() == 6) {
					boolean playedCard = false;
					for (int i = 0; i < m_player.getNumCards(); i++) {
						m_player.setSelectedCard(i);
						pCard = m_player.getSelectedCard();
						if (pCard.getValue() == 6) {
							Log.w(PREFIX, "Player played " + pCard.getValueAsString() + " of " + pCard.getSuit().toString());
							m_deck.playCard(m_player.removeSelectedCard());
							stats.addMove();
							playedCard = true;
							cardsToDrawForNextPlayer += 2;
							cardEffectsNextPlayer = true;
							Log.w(PREFIX, "Player increased the number of cards to draw to " + cardsToDrawForNextPlayer);
							if (m_player.getNumCards() > 0) playOpponent(0);
							break;
						}
					}
					if (!playedCard) {
						for (int i = 0; i < cardsToDrawForNextPlayer; i++) {
							m_player.addCard(m_deck.dealCard());
						}
						cardsToDrawForNextPlayer = 0;
						cardEffectsNextPlayer = false;
						if (m_player.getNumCards() > 0) playOpponent(0);
					}
				} else if (m_deck.areCompatible(dCard, pCard)) {
					Log.w(PREFIX, "Player played " + pCard.getValueAsString() + " of " + pCard.getSuit().toString());
					m_deck.playCard(m_player.removeSelectedCard());
					stats.addMove();
					cardEffectsNextPlayer = true;
					if (pCard.getValue() == 6) {
						cardsToDrawForNextPlayer += 2;
						Log.w(PREFIX, "Player increased the number of cards to draw to " + cardsToDrawForNextPlayer);
					}
					if (m_player.getNumCards() > 0) playOpponent(0);
				}
				break;
			default:
				super.actionPerformed(e);
		}
		if (m_player.getNumCards() == 0) {
			// TODO insert victory
			Log.w(PREFIX, "Player won!");
		}
	}

	private void playOpponent(int id) {
		Log.w(PREFIX, "Opponent " + id + "'s turn");
		MauOpponent op = opponents.get(id);
		Card dc = m_deck.getFaceUpCard();
		boolean canPlay = true;
		if (cardEffectsNextPlayer) {
			cardEffectsNextPlayer = false;
			switch (dc.getValue()) {
				case 0:
					canPlay = false;
					Log.w(PREFIX, "Opponent " + id + " is skipped");
					break;
				case 6:
					boolean playedSeven = false;
					for (int i = 0; i < op.getNumCards(); i++) {
						if (op.getCard(i).getValue() == 6) {
							Log.w(PREFIX, "Opponent " + id + " played " + op.getCard(i).getValueAsString() + " of " + op.getCard(i).getSuit().toString());
							m_deck.playCard(op.removeCard(i));
							cardEffectsNextPlayer = true;
							cardsToDrawForNextPlayer += 2;
							Log.w(PREFIX, "Opponent " + id + " increased the number of cards to draw to " + cardsToDrawForNextPlayer);
							playedSeven = true;
						}
					}
					if (!playedSeven) {
						for (int i = 0; i < cardsToDrawForNextPlayer; i++) {
							op.addCard(m_deck.dealCard(), id);
						}
						cardsToDrawForNextPlayer = 0;
						cardEffectsNextPlayer = false;
					}
					canPlay = false;
					break;
			}
		}

		if (canPlay) {
			boolean playedCard = false;
			if (op.getNumCards() > 0) {
				for (int i = 0; i < op.getNumCards(); i++) {
					Card tmp = op.getCard(i);
					if (m_deck.areCompatible(dc, tmp)) {
						Log.w(PREFIX, "Opponent " + id + " played " + tmp.getValueAsString() + " of " + tmp.getSuit().toString());
						m_deck.playCard(op.removeCard(i));
						cardEffectsNextPlayer = true;
						if (tmp.getValue() == 6) {
							cardsToDrawForNextPlayer += 2;
							Log.w(PREFIX, "Opponent " + id + " increased the number of cards to draw to " + cardsToDrawForNextPlayer);
						} else if (id == opponents.size() - 1 && tmp.getValue() == 0) {
							id = -1;
							cardEffectsNextPlayer = false;
							Log.w(PREFIX, "Player is skipped");
						}
						playedCard = true;
						break;
					}
				}
				if (!playedCard) {
					Card tmp = m_deck.dealCard();
					op.addCard(tmp, id);
				}
			}
		}

		if (op.getNumCards() == 0) {
			Log.w(PREFIX, "Opponent " + id + " won!");
			return;
		}

		if (id < opponents.size() - 1) {
			playOpponent(++id);
		} else {
			Log.w(PREFIX, "Player's turn");
		}
	}

	public void resetOpponents() {
		if (opponents != null) {
			for (int i = 0; i < opponents.size(); i++) {
				window.remove(opponents.get(i));
			}
		}
		opponents = new ArrayList<MauOpponent>();
		boolean correctNumber = false;
		int numOfOpponents = 2;

		while (!correctNumber) {
			String s = "";
			s = JOptionPane.showInputDialog("Number of opponents (1-5):");
			if (!s.isEmpty()) {
				for (int i = 0; i < s.length(); i++) {
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
	public void resetStatistics() {
		stats = new Statistics(PREFIX);
	}

	@Override
	public void resetBoard() {}

	@Override
	public MauPlayer getPlayer() {
		return m_player;
	}

	public MauOpponent getOpponent(int id) { return opponents.get(id); }

	@Override
	public Board getBoard() {
		return null;
	}

	@Override
	public MauDeck getDeck() {
		return m_deck;
	}
}
