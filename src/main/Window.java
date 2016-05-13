package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Window extends JFrame {
	
	public final static String NEW_GAME = "New Game";
	public final static String EXIT_GAME = "Exit";

	private JPanel main;
	
	public Window(String name, ActionListener listener) {
		super(name);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1080, 1080 * 9 / 16));
		setMinimumSize(getPreferredSize());
		setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		menuBar.add(menu);

		JMenuItem newGame = new JMenuItem(NEW_GAME, KeyEvent.VK_N);
		newGame.addActionListener(listener);
		newGame.setActionCommand(NEW_GAME);
		menu.add(newGame);

		JMenuItem exitGame = new JMenuItem(EXIT_GAME, KeyEvent.VK_X);
		exitGame.addActionListener(listener);
		exitGame.setActionCommand(EXIT_GAME);
		menu.add(exitGame);

		setJMenuBar(menuBar);
		
		main = new JPanel();
		main.setPreferredSize(getSize());
		main.setMinimumSize(getSize());
		main.setMaximumSize(getSize());
		main.setSize(getSize());
		main.setLayout(new GridBagLayout());
		add(main);
	}
	
	public void add(CardContainer c, GridBagConstraints gbc) {
		main.add(c, gbc);
		revalidate();
		repaint();
	}
	
	public void remove(CardContainer c) {
		main.remove(c);
	}
}
