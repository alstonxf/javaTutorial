package SpringTest.springInActionCode.Chapter16.SpringPizza.src.main.java.com.springinaction.pizza.service;

import com.springinaction.pizza.domain.Order;

public interface PricingEngine {
  public float calculateOrderTotal(Order order);
}
