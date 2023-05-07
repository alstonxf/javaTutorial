package SpringTest.springInActionCode.Chapter01.Knight.src.main.java.com.springinaction.chapter01.knight;

@SuppressWarnings("serial")
public class GrailNotFoundException extends QuestFailedException {
  public GrailNotFoundException() {}
  
  public GrailNotFoundException(String message) {
    super(message);
  }
}
