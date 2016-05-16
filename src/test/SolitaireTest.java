package test;

import main.Card;
import org.junit.Test;
import solitaire.Solitaire;

/**
 * Created by Henne on 5/15/2016.
 */
public class SolitaireTest {

    Solitaire solitaire;

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

}