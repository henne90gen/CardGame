package test;

import log.Log;
import main.Card;
import mau.Mau;
import org.junit.Test;

import java.awt.event.ActionEvent;

/**
 * Created by henne on 20.05.16.
 */
public class MauTest {

    private Mau mau;

    @Test
    public void aceBeforePlayer() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 7));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 8));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 9));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 11));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Hearts, 7));
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11));
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 0));
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 7));

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 1 : "Opponent 1";
        assert mau.getOpponent(1).getNumCards() == 1 : "Opponent 2";
        assert mau.getPlayer().getNumCards() == 1 : "Player";
    }

    @Test
    public void playerDrawsTwoCards() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 7));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Spades, 7));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 9), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 1);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6), 1);


        mau.getOpponent(0).addCard(mau.getDeck().dealCard(), 0);
        mau.getOpponent(1).addCard(mau.getDeck().dealCard(), 1);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        Thread.sleep(500);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 1 : "Opponent 1";
        assert mau.getOpponent(1).getNumCards() == 1 : "Opponent 2";
        assert mau.getPlayer().getNumCards() == 3 : "Player";
    }

    @Test
    public void playerPlaysSevenOnSeven() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 7));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Spades, 6));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 9), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Spades, 11), 1);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6), 1);

        mau.getOpponent(1).addCard(mau.getDeck().dealCard(), 1);
        mau.getPlayer().addCard(mau.getDeck().dealCard());

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        Thread.sleep(500);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 5 : "Opponent 1";
        assert mau.getOpponent(1).getNumCards() == 1 : "Opponent 2";
        assert mau.getPlayer().getNumCards() == 1 : "Player";
    }

    @Test
    public void opponentPlaysSevenOnTwoSevens() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 7));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Spades, 6));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 9), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 6), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 1);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6), 1);

        mau.getPlayer().addCard(mau.getDeck().dealCard());

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        Thread.sleep(500);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 1 : "Opponent 1";
        assert mau.getOpponent(1).getNumCards() == 7 : "Opponent 2";
        assert mau.getPlayer().getNumCards() == 1 : "Player";
    }

    @Test
    public void opponentOnePlaysSeven() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 9));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Spades, 6));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 1);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 1 : "Opponent 1";
        assert mau.getOpponent(1).getNumCards() == 3 : "Opponent 2";
        assert mau.getPlayer().getNumCards() == 1 : "Player";
    }

    @Test
    public void playerPlaysCardOnSeven() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 7));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 8));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Spades, 8));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 11), 0);
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Spades, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 1);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        Thread.sleep(500);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 1 : "Opponent 1: " + mau.getOpponent(0).getNumCards();
        assert mau.getOpponent(1).getNumCards() == 2 : "Opponent 2: " + mau.getOpponent(1).getNumCards();
        assert mau.getPlayer().getNumCards() == 1 : "Player: " + mau.getPlayer().getNumCards();
    }

    @Test
    public void playerPlaysSeven() throws Exception {
        mau = new Mau();
        mau.resetDeck();
        mau.resetPlayer();
        mau.resetOpponents();

        Log.setVerbosity(false);

        mau.getDeck().playCard(mau.getDeck().dealCard());

        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 6));
        mau.getPlayer().addCard(mau.getDeck().getCard(Card.Suit.Diamonds, 8));
        mau.getOpponent(0).addCard(mau.getDeck().getCard(Card.Suit.Spades, 11), 0);
        mau.getOpponent(1).addCard(mau.getDeck().getCard(Card.Suit.Clubs, 11), 1);

        mau.getPlayer().setSelectedCard(0);
        mau.actionPerformed(new ActionEvent(MauTest.this, 0, Mau.PLAYER_TO_BOARD));

        assert mau.getOpponent(0).getNumCards() == 3 : "Opponent 1: " + mau.getOpponent(0).getNumCards();
        assert mau.getOpponent(1).getNumCards() == 2 : "Opponent 2: " + mau.getOpponent(1).getNumCards();
        assert mau.getPlayer().getNumCards() == 1 : "Player: " + mau.getPlayer().getNumCards();
    }
}
