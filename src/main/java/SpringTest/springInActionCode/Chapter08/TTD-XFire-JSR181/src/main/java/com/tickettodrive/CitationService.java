package SpringTest.springInActionCode.Chapter08.TTD;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CitationService {
   @WebMethod
   public Citation[] getCitationsForVehicle(String state, String plateNumber);
}
