package SpringTest.springInActionCode.Chapter08.TicketToDrive.src.main.java.com.tickettodrive;

public interface CitationService {
  public Citation[] getCitationsForVehicle(
      String state, String plateNumber);
}
