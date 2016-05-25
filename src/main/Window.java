package main;

import log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
	private boolean m_waitForNextThread = true;

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
	 * Moves the top card from the deck to the specified stack on the CardContainer
	 * @param destination The destination card container
	 * @param source The card container where the card is going to be taken from
     * @param i The destination stack on the destination card container
     */
	public void animate(CardContainer destination, Deck source, int i) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				m_animating = true;

				Card c = source.getFaceUpCard();
				Point cPoint = c.getLocation();
				cPoint.translate((int) source.getLocation().getX(), (int) source.getLocation().getY());
				Point cDestination = destination.getNextCardLocation(i);
				cDestination.translate((int) destination.getLocation().getX(), (int) destination.getLocation().getY());

				c = source.removeFaceUpCard();
				animate(c, cPoint, cDestination);
				destination.addCard(c, i);
				refresh();
			}
		}).start();
	}

	public void animate(Board b, Card source, int destination) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				m_animating = true;

				b.setSelectedCard(source);

				Card c = b.getSelectedCard();

				ArrayList<Card> cards = b.removeSelectedCards();
				ArrayList<Thread> threads = new ArrayList<Thread>();

				m_waitForNextThread = true;

				for (int i = cards.size() - 1; i >= 0; i--) {
					int index = i;
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							Log.w("Window", cards.get(index).getValueAsString() + " of " + cards.get(index).getSuit().toString());
							/*cPoint.translate(0, (int)(index * (b.getCardBorder() * 1.75f)));*/

							// Starting point for the card
							Point cPoint = c.getLocation();
							cPoint.translate((int) b.getLocation().getX(), (int) b.getLocation().getY());
							cPoint.translate(0, (int)(index * (b.getCardBorder() * 1.75f)));

							// Destination point for the card
							Point cDestination = b.getNextCardLocation(destination);
							cDestination.translate((int) b.getLocation().getX(), (int) b.getLocation().getY());
							cDestination.translate(0, (int)(index * (b.getCardBorder() * 1.75f)));

							Log.w("Window", "sourceX: " + cPoint.getY() + " | destinationX: " + cDestination.getY());
							m_waitForNextThread = false;
							animate(cards.get(index), cPoint, cDestination);
						}
					});
					threads.add(t);
				}

				for (int i = 0; i < threads.size(); i++) {
					if (i > 0) {
						while (m_waitForNextThread) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						m_waitForNextThread = true;
					}
					threads.get(i).start();
				}

				for (Thread t : threads) {
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				b.addCard(cards, destination);
				refresh();
			}
		}).start();
	}

	private void animate(Card c, Point source, Point destination) {
		double nspu = 1000000000 / 60;		// nanoseconds per update with an update rate of 60 updates per second
		long lastTime = System.nanoTime();

		m_animations.add(c);
		double x = source.getX();
		double y = source.getY();
		double speed = source.distance(destination) / 40;
		while (m_animating) {
			long currentTime = System.nanoTime();
			if (currentTime - lastTime > nspu) {
				lastTime = currentTime;
				double angle = Math.atan((y - destination.getY()) / (x - destination.getX()));
				if ((x - destination.getX()) > 0) {
					angle += 3.14159265359;
				}
				x += Math.cos(angle) * speed;
				y += Math.sin(angle) * speed;
				c.setBounds((int) x, (int) y, Card.WIDTH, Card.HEIGHT);
				refresh();
				if (c.getLocation().distance(destination) <= 2.5) {
					m_animations.remove(c);
					m_animating = false;
				}
			} else {
				try {
					Thread.sleep((long) ((nspu) - (currentTime - lastTime)) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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
