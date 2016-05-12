package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Window extends JFrame {
	
	public final static String NEW_GAME = "New Game";
	
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
		
		JMenuItem menuItem = new JMenuItem(NEW_GAME, KeyEvent.VK_N);
		menuItem.addActionListener(listener);
		menuItem.setActionCommand(NEW_GAME);
		menu.add(menuItem);
		
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
