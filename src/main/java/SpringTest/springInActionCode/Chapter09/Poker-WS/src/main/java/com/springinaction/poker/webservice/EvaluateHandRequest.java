package SpringTest.springInActionCode.Chapter09.Poker;

import com.springinaction.poker.Card;

public class EvaluateHandRequest {
  private Card[] hand;
  
  public EvaluateHandRequest() {}

  public Card[] getHand() {
    return hand;
  }

  public void setHand(Card[] cards) {
    this.hand = cards;
  }
}
