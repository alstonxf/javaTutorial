package SpringTest.springInActionCode.Chapter16.SpringPizza.src.main.java.com.springinaction.pizza.domain;


public class CashPayment extends Payment {
  public CashPayment() {}
  
  public String toString() {
    return "CASH:  $" + getAmount();
  }
}
