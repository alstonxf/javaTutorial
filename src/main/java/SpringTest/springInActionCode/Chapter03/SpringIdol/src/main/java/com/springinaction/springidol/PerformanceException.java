package SpringTest.springInActionCode.Chapter03.SpringIdol.src.main.java.com.springinaction.springidol;

@SuppressWarnings("serial")
public class PerformanceException extends RuntimeException {
  public PerformanceException() {
    super();
  }
  
  public PerformanceException(String message) {
    super(message);
  }
}
