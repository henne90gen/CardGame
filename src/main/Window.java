package main;

import log.Log;

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

	private JLayeredPane m_layers;
	private JPanel m_main;
	private JPanel m_animations;
	private ActionListener m_listener;
	private boolean m_animating = false;

	public Window(String name, ActionListener listener) {
		super(name);
		m_listener = listener;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = new Dimension(1080, 1080 * 8 / 16);
		getContentPane().setPreferredSize(dim);
		getContentPane().setMinimumSize(dim);
		setVisible(true);

		setUpMenuBar();

		pack();

		m_layers = new JLayeredPane();
		m_layers.setPreferredSize(dim);
		m_layers.setMinimumSize(dim);
		m_layers.setMaximumSize(dim);
		m_layers.setSize(dim);
		setContentPane(m_layers);

		m_main = new JPanel();
		m_main.setPreferredSize(dim);
		m_main.setMinimumSize(dim);
		m_main.setMaximumSize(dim);
		m_main.setSize(dim);
		m_main.setLayout(new GridBagLayout());
		m_layers.add(m_main, 0, 0);

		m_animations = new CardContainer();
		m_animations.setPreferredSize(dim);
		m_animations.setMaximumSize(dim);
		m_animations.setMinimumSize(dim);
		m_animations.setSize(dim);
		m_animations.setLayout(null);
		m_animations.setOpaque(false);
		m_layers.add(m_animations, 1, 0);

		refresh();
	}

	/**
	 * Moves a card from
	 * @param b The destination board
	 * @param d The deck where the card is going to be taken from
     * @param i The destination stack on the board
     */
	public void animate(Board b, Deck d, int i) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				m_animating = true;
				double speed = 10;
				long lastTime = System.nanoTime();

				Card c = d.getFaceUpCard();
				Point cPoint = c.getLocation();
				cPoint.translate((int) d.getLocation().getX(), (int) d.getLocation().getY());
				Point cDestination = new Point(b.getCardBorder() + i * (Card.WIDTH + b.getCardBorder()), (int)(b.getCardBorder() + b.getNumCardsOnStack(i) * (b.getCardBorder() * 1.75f)));
				cDestination.translate((int) b.getLocation().getX(), (int) b.getLocation().getY());

				Log.w("Window", "X: " + cPoint.getX() + " | Y: " + cPoint.getY());
				Log.w("Window", "X: " + cDestination.getX() + " | Y: " + cDestination.getY());

				c = d.removeFaceUpCard();
				m_animations.add(c);
				double x = cPoint.getX();
				double y = cPoint.getY();
				c.setBounds((int)x, (int)y, Card.WIDTH, Card.HEIGHT);
				refresh();
				while (m_animating) {
					long currentTime = System.nanoTime();
					if (currentTime - lastTime > 1000000000 / 120) {
						lastTime = currentTime;
						double angle = Math.atan((y - cDestination.getY()) / (x - cDestination.getX())) + 3.14159265359;
						x += Math.cos(angle) * speed;
						y += Math.sin(angle) * speed;
						c.setBounds((int)x, (int)y, Card.WIDTH, Card.HEIGHT);
						refresh();
						if (c.getLocation().getX() <= cDestination.getX()) {
							m_animations.remove(c);
							refresh();
							b.addCard(c, i);
							m_animating = false;
						}
					} else {
						try {
							Thread.sleep(((1000000000 / 120) - (currentTime - lastTime)) / 1000000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
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
		m_main.add(c, gbc);
		refresh();
	}

	public void add(JPanel p, GridBagConstraints gbc) {
		m_main.add(p, gbc);
		refresh();
	}
	
	public void remove(CardContainer c) {
		m_main.remove(c);
	}

	public void remove(JPanel p) { m_main.remove(p); }

	public boolean isAnimating() { return m_animating; }

	private void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu game = new JMenu("Game");
		game.setMnemonic(KeyEvent.VK_G);
		menuBar.add(game);

		JMenuItem newGame = new JMenuItem(NEW_GAME, KeyEvent.VK_N);
		newGame.addActionListener(m_listener);
		newGame.setActionCommand(NEW_GAME);
		game.add(newGame);

		JMenuItem exitGame = new JMenuItem(EXIT_GAME, KeyEvent.VK_X);
		exitGame.addActionListener(m_listener);
		exitGame.setActionCommand(EXIT_GAME);
		game.add(exitGame);

		JMenu statistics = new JMenu("Statistics");
		statistics.setMnemonic(KeyEvent.VK_S);
		menuBar.add(statistics);

		JMenuItem showStatistics = new JMenuItem(SHOW_STATISTICS, KeyEvent.VK_S);
		showStatistics.addActionListener(m_listener);
		showStatistics.setActionCommand(SHOW_STATISTICS);
		statistics.add(showStatistics);

		JMenuItem resetStatistics = new JMenuItem(RESET_STATISTICS, KeyEvent.VK_R);
		resetStatistics.addActionListener(m_listener);
		resetStatistics.setActionCommand(RESET_STATISTICS);
		statistics.add(resetStatistics);

		setJMenuBar(menuBar);
	}
}
