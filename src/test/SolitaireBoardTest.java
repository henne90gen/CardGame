package test;

import main.Deck;
import solitaire.SolitaireBoard;

/**
 * Created by Henne on 5/15/2016.
 */
public class SolitaireBoardTest {

    private SolitaireBoard board;
    private Deck deck;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new SolitaireBoard();
        deck = new Deck(Deck.DeckType.French);
    }

    @org.junit.Test
    public void addCard() throws Exception {
        board.addCard(deck.dealCard());

    }

    @org.junit.Test
    public void addCard1() throws Exception {

    }

    @org.junit.Test
    public void removeSelectedCards() throws Exception {

    }

    @org.junit.Test
    public void getSelectedCard() throws Exception {

    }

    @org.junit.Test
    public void getTopCard() throws Exception {

    }

    @org.junit.Test
    public void getDimension() throws Exception {

    }

    @org.junit.Test
    public void areCompatible() throws Exception {

    }

    @org.junit.Test
    public void isTopCard() throws Exception {

    }

    @org.junit.Test
    public void canFinish() throws Exception {

    }

    @org.junit.Test
    public void setSelectedCard() throws Exception {

    }

}