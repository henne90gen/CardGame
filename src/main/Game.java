package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Game implements ActionListener {

	public final static String DECK_TO_BOARD = "deck to card";
	public final static String DECK_TO_PLAYER = "deck to player";
	public final static String BOARD_TO_PLAYER = "board to player";
	public final static String PLAYER_TO_BOARD = "player to board";

	protected Window window;
	protected Statistics stats;
	protected GridBagConstraints gbc;
	
	public Game(String name) {
		window = new Window(name, this);
		resetGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case Window.NEW_GAME:
				stats.stopTimer();
				resetGame();
				break;
			case Window.EXIT_GAME:
				stats.stopTimer();
				if (window.isVisible()) window.dispose();
				else System.exit(0);
				break;
			case Window.RESET_STATISTICS:
				stats.resetStatsFile();
				break;
			case Window.SHOW_STATISTICS:
				String session = "Session:\n    Time played: " + stats.getTimeAsString(stats.getTime()) + "\n    Moves: " + stats.getMoves() + "\n\n";
				String total = "Overall:\n    Time played: " + stats.getTimeAsString(stats.getTotalTime() + stats.getTime()) + "\n    Moves: " + (stats.getTotalMoves() + stats.getMoves());
				JOptionPane.showConfirmDialog(window, session + total, "Statistics", JOptionPane.DEFAULT_OPTION);
				break;
		}
	}

	protected void resetGame() {
		resetStatistics();
		resetDeck();
		resetPlayer();
		resetBoard();
	}

	public abstract Player getPlayer();

	public abstract Board getBoard();

	public abstract Deck getDeck();

	public Statistics getStatistics() {
		return stats;
	}

	public abstract void resetPlayer();

	public abstract void resetBoard();

	public abstract void resetDeck();

	public abstract void resetStatistics();
}
