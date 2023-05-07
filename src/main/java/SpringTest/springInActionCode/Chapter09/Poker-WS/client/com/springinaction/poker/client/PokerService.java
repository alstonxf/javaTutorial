package SpringTest.springInActionCode.Chapter09.Poker;

import com.springinaction.poker.webservice.EvaluateHandRequest;
import com.springinaction.poker.webservice.EvaluateHandResponse;

public interface PokerService {
  public EvaluateHandResponse EvaluateHand(EvaluateHandRequest request);
}
