package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Card.Suit;

public class Deck extends CardContainer {
	
	public enum DeckType {
		French, German
	}
	
	private DeckType type;
	private Random rand;
	
	public Deck(DeckType type) {
		this.type = type;
		rand = new Random();
		border = 5;
		maxCardsX = 1;
		maxCardsY = 1;
		addMouseListener(this);
		switch (type) {
		case French:
			loadFrench();
			break;
		case German:
			loadGerman();
			break;
		}
		
	}

	private void loadFrench() {
		try {
			BufferedImage spriteSheet = ImageIO.read(new File("res/suits.jpg"));
			int width = spriteSheet.getWidth() / 13;
			int height = spriteSheet.getHeight() / 4;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 13; j++) {
					BufferedImage bi = new BufferedImage(width, height, spriteSheet.getType());
					Graphics2D g = bi.createGraphics();
					g.drawImage(spriteSheet, 0, 0, width, height, j * width, i * height, (j + 1) * width, (i + 1) * height, null);
					g.dispose();
					addCard(new Card(Suit.values()[i], j, bi));
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadGerman() {
		try {
			BufferedImage spriteSheet = ImageIO.read(new File("res/suits.jpg"));
			int width = spriteSheet.getWidth() / 13;
			int height = spriteSheet.getHeight() / 4;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 13; j++) {
					if (j < 1 || j > 5) {
						BufferedImage bi = new BufferedImage(width, height, spriteSheet.getType());
						Graphics2D g = bi.createGraphics();
						g.drawImage(spriteSheet, 0, 0, width, height, j * width, i * height, (j + 1) * width, (i + 1) * height, null);
						g.dispose();
						addCard(new Card(Suit.values()[i], j, bi));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Card dealCard() {
		if (cards.size() > 0) {
			remove(cards.get(cards.size() - 1));
			revalidate();
			repaint();
			return cards.remove(cards.size() - 1);
		} else {
			return null;
		}
	}
	
	public void shuffle() {
		ArrayList<Card> tmp = new ArrayList<Card>();
		while (!isEmpty()) {
			int index = rand.nextInt(cards.size());
			tmp.add(cards.remove(index));
		}
		for (Card card : tmp) {
			cards.add(card);
		}
	}
	
	public boolean isEmpty() {
		return cards.size() == 0;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, getDimension().width, getDimension().height);
		if (!isEmpty()) {
			for (int i = 0; i < cards.size(); i++) {
				cards.get(i).setFaceUp(false);
				cards.get(i).setLocation(border, border);
			}
		}
	}
	
	@Override
	protected Dimension getDimension() {
		return new Dimension(Card.WIDTH + 2 * border, Card.HEIGHT + 2 * border);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		fireActionPerformed(new ActionEvent(Deck.this, 0, null));
	}
	
	public DeckType getType() {
		return type;
	}
}
