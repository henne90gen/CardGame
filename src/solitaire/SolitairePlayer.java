package solitaire;

import main.Card;
import main.Card.Suit;
import main.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SolitairePlayer extends Player {

	private ArrayList<Card>[] stacks;
	
	public SolitairePlayer() {
		border = 10;
		maxCardsX = 4;
		maxCardsY = 1;
		cards = null;
		selectedCard = -1;
		//suits = new Card[maxCardsX];
		stacks = new ArrayList[(int)maxCardsX];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = new ArrayList();
		}
		addMouseListener(this);
		setLayout(null);
		try {
			BufferedImage spriteSheet = ImageIO.read(new File("res/suit-icons.png"));
			int width = (int) (spriteSheet.getWidth() / maxCardsX);
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
	
	@Override
	public Card getSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		return stacks[selectedCard].get(stacks[selectedCard].size() - 1);
	}

	@Override
	public Card removeSelectedCard() {
		if (selectedCard == -1) {
			return null;
		}
		return stacks[selectedCard].remove(stacks[selectedCard].size() - 1);
	}

	public Card getCard(int suit) {
		return stacks[suit].get(stacks[suit].size() - 1);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[i].size(); j++) {
				int x = border + (Card.WIDTH + border) * i;
				int y = border;
				stacks[i].get(j).setBounds(x, y, Card.WIDTH, Card.HEIGHT);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i].get(stacks[i].size() - 1).isMouseHover()) {
				selectedCard = i;
			}
		}
		fireActionPerformed(new ActionEvent(SolitairePlayer.this, 0, null));
	}

	public boolean gameIsFinished() {
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i].get(stacks[i].size() - 1).getValue() != 12) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getNumCardsOnStack(int i) { return 0; }

	@Override
	public Point getNextCardLocation(int i) {
		return new Point(border + i * (Card.WIDTH + border), border);
	}
}
