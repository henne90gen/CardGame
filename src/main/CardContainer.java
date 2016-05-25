package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CardContainer extends JPanel implements MouseListener {
	
	protected float maxCardsX, maxCardsY;
	protected int border;
	protected String actionCommand;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	protected ArrayList<Card>[] stacks;
	protected boolean initiating;
	
	public void addCard(Card card) {
		if (card != null) {
			for (Card c : cards) {
				remove(c);
			}
			cards.add(card);
			for (int i = cards.size() - 1; i >= 0; i--) {
				add(cards.get(i));
			}
			refresh();
		}
	}

	public void addCard(Card card, int stack) {
		if (card != null) {
			card.setFaceUp(!initiating);
			for (Card c : stacks[stack]) {
				remove(c);
			}
			stacks[stack].add(card);
			for (int i = stacks[stack].size() - 1; i >= 0; i--) {
				add(stacks[stack].get(i));
			}
			refresh();
		}
	}

	public void addCard(ArrayList<Card> c, int stack) {
		for (int i = 0; i < c.size(); i++) {
			addCard(c.get(i), stack);
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

	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}
	
	public void setActionCommand(String command) {
		this.actionCommand = command;
	}
	
	protected void fireActionPerformed(ActionEvent e) {
		Object[] listeners = listenerList.getListenerList();
		ActionEvent event = null;
		for (int i = listeners.length-2; i >= 0; i-=2) {
			if (listeners[i] == ActionListener.class) {
				if (event == null) {
					String command = e.getActionCommand();
					if (command == null) {
						command = actionCommand;
					}
					event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, command, e.getWhen(), e.getModifiers());
				}
				((ActionListener)listeners[i+1]).actionPerformed(event);
			}
		}
	}

	public int getCardBorder() { return border; }

	public double getMaxCardsX() {
		return maxCardsX;
	}

	public int getNumCards() {
		return cards.size();
	}

	public int getNumCardsOnStack(int i) { return stacks[i].size(); }

	protected Dimension getDimension() { return new Dimension(0, 0); }
	
	@Override
	public Dimension getPreferredSize() { return getDimension(); }
	
	@Override
	public Dimension getMinimumSize() { return getDimension(); }
	
	@Override
	public Dimension getMaximumSize() { return getDimension(); }
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	public Point getNextCardLocation(int i) { return null; }
}
