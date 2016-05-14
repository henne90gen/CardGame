package main;

import mau.MauBoard;
import mau.MauPlayer;
import solitaire.Solitaire;
import solitaire.SolitaireBoard;
import solitaire.SolitaireDeck;
import solitaire.SolitairePlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card extends JComponent implements MouseListener {
	
	public final static int WIDTH = 73;
	public final static int HEIGHT = 98;

    public enum SuitColor {
        Red, Black
    }

	public enum Suit {
		Clubs, Spades, Hearts, Diamonds
	}
	
	private Suit suit;
	private int value;
	private BufferedImage front, back;
	private boolean faceUp;
	private boolean highlighted;
	private boolean mouseHover;
	
	public Card(Suit suit, int value, BufferedImage front) {
		this.suit = suit;
		this.value = value;
		this.front = front;
		faceUp = true;
		highlighted = false;
		mouseHover = false;
		addMouseListener(this);
		try {
			back = ImageIO.read(new File("res/back.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		if (faceUp) {
			g.drawImage(front, 0, 0, null);
		} else {
			g.drawImage(back, 0, 0, null);
		}
	}
	
	public Suit getSuit() {
		return suit;
	}

    public SuitColor getSuitColor() {
        if (getSuit() == Suit.Hearts || getSuit() == Suit.Diamonds) {
            return SuitColor.Red;
        } else {
            return SuitColor.Black;
        }
    }

	public int getValue() {
		return value;
	}
	
	public String getValueAsString() {
		String result = "";
		switch (value) {
		case 0:
			result = "Ace";
			break;
		case 1:
			result = "Two";
			break;
		case 2:
			result = "Three";
			break;
		case 3:
			result = "Four";
			break;
		case 4:
			result = "Five";
			break;
		case 5:
			result = "Six";
			break;
		case 6:
			result = "Seven";
			break;
		case 7:
			result = "Eight";
			break;
		case 8:
			result = "Nine";
			break;
		case 9:
			result = "Ten";
			break;
		case 10:
			result = "Jack";
			break;
		case 11:
			result = "Queen";
			break;
		case 12:
			result = "King";
			break;
		}
		return result;
	}
	
	private Dimension getDimension() {
		return new Dimension(73, 98);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return getDimension();
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getDimension();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getDimension();
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public boolean isMouseHover() {
		return mouseHover;
	}

	public void setMouseHover(boolean mouseHover) {
		this.mouseHover = mouseHover;
	}
	
	public boolean isFaceUp() {
		return faceUp;
	}

	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (getParent().getClass() == Deck.class) {
			((Deck)getParent()).mouseClicked(e);
		} else if (getParent().getClass() == GenericPlayer.class) {
			((GenericPlayer)getParent()).mouseClicked(e);
		} else if (getParent().getClass() == SolitairePlayer.class) {
			((SolitairePlayer)getParent()).mouseClicked(e);
		} else if (getParent().getClass() == GenericBoard.class) {
			((GenericBoard)getParent()).mouseClicked(e);
		} else if (getParent().getClass() == SolitaireBoard.class) {
			((SolitaireBoard)getParent()).mouseClicked(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setMouseHover(true);
		getParent().revalidate();
		getParent().repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setMouseHover(false);
		getParent().revalidate();
		getParent().repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
        e.translatePoint((int)this.getLocation().getX(), (int)this.getLocation().getY());
		if (getParent().getClass() == Deck.class) {
			((Deck)getParent()).mousePressed(e);
		} else if (getParent().getClass() == SolitaireDeck.class) {
			((SolitaireDeck)getParent()).mousePressed(e);
		} else if (getParent().getClass() == GenericPlayer.class) {
			((GenericPlayer)getParent()).mousePressed(e);
		} else if (getParent().getClass() == SolitairePlayer.class) {
			((SolitairePlayer)getParent()).mousePressed(e);
		} else if (getParent().getClass() == MauPlayer.class) {
			((MauPlayer)getParent()).mousePressed(e);
		} else if (getParent().getClass() == GenericBoard.class) {
			((GenericBoard)getParent()).mousePressed(e);
		} else if (getParent().getClass() == SolitaireBoard.class) {
			((SolitaireBoard)getParent()).mousePressed(e);
		} else if (getParent().getClass() == MauBoard.class) {
			((MauBoard)getParent()).mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
