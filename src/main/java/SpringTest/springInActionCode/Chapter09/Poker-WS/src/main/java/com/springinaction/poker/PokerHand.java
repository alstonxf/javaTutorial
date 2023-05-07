package SpringTest.springInActionCode.Chapter09.Poker;

public class PokerHand {
  public PokerHand() {}
  
  public PokerHand(Card[] cards) {
    this.cards = cards;
  }
  
  private Card[] cards;
  
  public void setCards(Card[] cards) {
    this.cards = cards;
  }
  
  public Card[] getCards() {
    return cards;
  }
}
