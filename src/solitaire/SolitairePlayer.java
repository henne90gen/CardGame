package solitaire;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Card;
import main.Player;
import main.Card.Suit;

public class SolitairePlayer extends Player {

	private Card[] suits;
	
	public SolitairePlayer() {
		border = 10;
		maxCardsX = 4;
		maxCardsY = 1;
		cards = null;
		selectedCard = -1;
		suits = new Card[maxCardsX];
		addMouseListener(this);
		try {
			BufferedImage spriteSheet = ImageIO.read(new File("res/suit-icons.png"));
			int width = spriteSheet.getWidth() / maxCardsX;
			int height = spriteSheet.getHeight();
			for (int i = 0; i < maxCardsX; i++) {
				BufferedImage bi = new BufferedImage(width, height, spriteSheet.getType());
				Graphics2D g = bi.createGraphics();
				g.drawImage(spriteSheet, 0, 0, Card.WIDTH, Card.HEIGHT, i * width, 0, (i + 1) * width, height, null);
				g.dispose();
				addCard(new Card(Suit.values()[i], -1, bi), i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCard(Card card, int stack) {
		if (card != null) {
			if (suits[stack] !=  null) {
				remove(suits[stack]);
			}
			suits[stack] = card;
			add(suits[stack]);
			revalidate();
			repaint();
		}
	}
	
	@Override
	public Card getSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		int tmp = selectedCard;
		selectedCard = -1;
		return suits[tmp];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < maxCardsX; i++) {
			int x = border + (Card.WIDTH + border) * i;
			int y = border;
			suits[i].setLocation(x, y);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < suits.length; i++) {
			if (suits[i].isMouseHover()) {
				selectedCard = i;
			}
		}
		fireActionPerformed(new ActionEvent(SolitairePlayer.this, 0, null));
	}
}
