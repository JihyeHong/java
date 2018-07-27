package uk.ac.rhul.cs.calculatorwebservice;
import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface CalculatorServer {
	@WebMethod public String calc(String s, int a, int b);
}
