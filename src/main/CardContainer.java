package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CardContainer extends JPanel implements MouseListener {
	
	protected int maxCardsX, maxCardsY, border;
	protected String actionCommand;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card) {
		if (card != null) {
			for (Card c : cards) {
				remove(c);
			}
			cards.add(card);
			for (int i = cards.size() - 1; i >= 0; i--) {
				add(cards.get(i));
			}
			revalidate();
			repaint();
		}
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
	
	public int getMaxCardsX() {
		return maxCardsX;
	}
	
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
}
