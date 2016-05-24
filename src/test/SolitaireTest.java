package test;

import main.Card;
import org.junit.Test;
import solitaire.Solitaire;

import java.awt.event.ActionEvent;

import static main.Game.DECK_TO_BOARD;

/**
 * Created by Henne on 5/15/2016.
 */
public class SolitaireTest {

    Solitaire solitaire;

    @Test
    public void animation() throws Exception {
        solitaire = new Solitaire();
        solitaire.resetDeck();
        solitaire.resetBoard();
        solitaire.getBoard().addCard(solitaire.getDeck().getCard(Card.Suit.Clubs, 12), 0);
        solitaire.getBoard().addCard(solitaire.getDeck().getCard(Card.Suit.Diamonds, 12), 1);

        solitaire.getDeck().showNextCard();
        solitaire.actionPerformed(new ActionEvent(SolitaireTest.this, 0, DECK_TO_BOARD));

        Thread.sleep(600);

        assert solitaire.getBoard().getTopCard(0).getSuit() == Card.Suit.Diamonds && solitaire.getBoard().getTopCard(0).getValue() == 11;
    }

    @Test
    public void moveCardToPlayer() throws Exception {
        solitaire = new Solitaire();
        solitaire.resetDeck();
        solitaire.resetBoard();
        for (int i = 0; i < 13; i++) {
            solitaire.getBoard().addCard(solitaire.getDeck().dealCard(), (int) (i / 7));
        }
        solitaire.getBoard().setSelectedCard(1, 5);
        solitaire.moveCardToPlayer(solitaire.getBoard().getSelectedCard());
        assert solitaire.getPlayer().getCard(Card.Suit.Diamonds.ordinal()).getSuit() == Card.Suit.Diamonds &&
                solitaire.getPlayer().getCard(Card.Suit.Diamonds.ordinal()).getValue() == 0;
    }

    @Test
    public void autoFinishGame() throws Exception {
        solitaire = new Solitaire();
        solitaire.resetDeck();
        solitaire.resetBoard();
        for (int i = 13; i >= 0; i--) {
            solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Diamonds : Card.Suit.Clubs, i), 0);
        }
        for (int i = 13; i >= 0; i--) {
            solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Clubs : Card.Suit.Diamonds, i), 1);
        }
        for (int i = 13; i >= 0; i--) {
            solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Hearts : Card.Suit.Spades, i), 2);
        }
        for (int i = 13; i >= 0; i--) {
            solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Spades : Card.Suit.Hearts, i), 3);
        }
        solitaire.autoFinishGame();
        for (int i = 0; i < 4; i++) {
            assert solitaire.getPlayer().getCard(i).getValue() == 12 : solitaire.getPlayer().getCard(i).getValue();
        }
    }

    @Test
    public void autoFinishGameWithCardsOnDeck() throws Exception {
        for (int j = 0; j < 5; j++) {
            solitaire = new Solitaire();
            solitaire.resetDeck();
            solitaire.resetBoard();

            for (int i = 13; i >= 0; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Diamonds : Card.Suit.Clubs, i), 0);
            }
            for (int i = 13; i >= 0; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Clubs : Card.Suit.Diamonds, i), 1);
            }

            for (int i = 13; i >= 9; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Hearts : Card.Suit.Spades, i), 2);
            }
            for (int i = 6; i >= 0; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Hearts : Card.Suit.Spades, i), 5);
            }

            for (int i = 13; i >= 10; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Spades : Card.Suit.Hearts, i), 3);
            }
            for (int i = 5; i >= 0; i--) {
                solitaire.getBoard().addCard(solitaire.getDeck().getCard((i % 2 == 0) ? Card.Suit.Spades : Card.Suit.Hearts, i), 4);
            }

            solitaire.getDeck().shuffle();
            solitaire.autoFinishGame();

            for (int i = 0; i < 4; i++) {
                assert solitaire.getPlayer().getCard(i).getValue() == 12 : solitaire.getPlayer().getCard(i).getValue();
            }
        }
    }
}