package main;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Game implements ActionListener {

	public final static String PLAY_TO_BOARD = "Deal card";
	public final static String PLAY_TO_PLAYER = "Move card to player";

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
