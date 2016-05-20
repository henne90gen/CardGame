package test;

import main.Card;
import org.junit.Test;
import solitaire.Solitaire;

/**
 * Created by Henne on 5/15/2016.
 */
public class SolitaireBoardTest {

    private Solitaire solitaire;

    @Test
    public void areCompatible() throws Exception {
        solitaire = new Solitaire();
        solitaire.resetBoard();
        solitaire.resetDeck();
        assert solitaire.getBoard().areCompatible(solitaire.getDeck().getCard(Card.Suit.Clubs, 4), solitaire.getDeck().getCard(Card.Suit.Hearts, 3));
    }

    @Test
    public void isTopCard() throws Exception {
        solitaire = new Solitaire();
        solitaire.resetBoard();
        solitaire.resetDeck();
        solitaire.getBoard().addCard(solitaire.getDeck().getCard(Card.Suit.Clubs, 4), 0);
        solitaire.getBoard().setSelectedCard(0, 0);
        assert solitaire.getBoard().isTopCard(solitaire.getBoard().getSelectedCard());
    }
}