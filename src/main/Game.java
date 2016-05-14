package main;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Game implements ActionListener {

	public final static String DECK_TO_BOARD = "deck to card";
	public final static String DECK_TO_PLAYER = "deck to player";
	public final static String BOARD_TO_PLAYER = "board to player";
	public final static String PLAYER_TO_BOARD = "player to board";

	protected Window window;
	protected GridBagConstraints gbc;
	protected Player player;
	protected Board board;
	protected Deck deck;
	
	public Game(String name) {
		window = new Window(name, this);
		resetGame();
	}
	
	protected abstract void resetGame();
}
