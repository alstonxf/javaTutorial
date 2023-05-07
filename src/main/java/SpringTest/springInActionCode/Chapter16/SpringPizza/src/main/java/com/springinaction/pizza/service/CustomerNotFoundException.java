package SpringTest.springInActionCode.Chapter16.SpringPizza.src.main.java.com.springinaction.pizza.service;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends Exception {
  public CustomerNotFoundException() {}
  
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
