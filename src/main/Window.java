package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Window extends JFrame {
	
	public final static String NEW_GAME = "New Game";
	public final static String EXIT_GAME = "Exit";
	public final static String RESET_STATISTICS = "Reset";
	public final static String SHOW_STATISTICS = "Show";

	private JPanel main;
	private ActionListener m_listener;
	
	public Window(String name, ActionListener listener) {
		super(name);
		m_listener = listener;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1080, 1080 * 9 / 16));
		setMinimumSize(getPreferredSize());
		setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu game = new JMenu("Game");
		game.setMnemonic(KeyEvent.VK_G);
		menuBar.add(game);

		JMenuItem newGame = new JMenuItem(NEW_GAME, KeyEvent.VK_N);
		newGame.addActionListener(listener);
		newGame.setActionCommand(NEW_GAME);
		game.add(newGame);

		JMenuItem exitGame = new JMenuItem(EXIT_GAME, KeyEvent.VK_X);
		exitGame.addActionListener(listener);
		exitGame.setActionCommand(EXIT_GAME);
		game.add(exitGame);

		JMenu statistics = new JMenu("Statistics");
		statistics.setMnemonic(KeyEvent.VK_S);
		menuBar.add(statistics);

		JMenuItem showStatistics = new JMenuItem(SHOW_STATISTICS, KeyEvent.VK_S);
		showStatistics.addActionListener(listener);
		showStatistics.setActionCommand(SHOW_STATISTICS);
		statistics.add(showStatistics);

		JMenuItem resetStatistics = new JMenuItem(RESET_STATISTICS, KeyEvent.VK_R);
		resetStatistics.addActionListener(listener);
		resetStatistics.setActionCommand(RESET_STATISTICS);
		statistics.add(resetStatistics);

		setJMenuBar(menuBar);
		
		main = new JPanel();
		main.setPreferredSize(getSize());
		main.setMinimumSize(getSize());
		main.setMaximumSize(getSize());
		main.setSize(getSize());
		main.setLayout(new GridBagLayout());
		add(main);

		pack();
	}

	public void refresh() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				revalidate();
				repaint();
			}
		});
	}

	@Override
	public void dispose() {
		super.dispose();
		setVisible(false);
		m_listener.actionPerformed(new ActionEvent(Window.this, 0, EXIT_GAME));
	}

	public void add(CardContainer c, GridBagConstraints gbc) {
		main.add(c, gbc);
		refresh();
	}

	public void add(JPanel p, GridBagConstraints gbc) {
		main.add(p, gbc);
		refresh();
	}
	
	public void remove(CardContainer c) {
		main.remove(c);
	}

	public void remove(JPanel p) { main.remove(p); }
}
