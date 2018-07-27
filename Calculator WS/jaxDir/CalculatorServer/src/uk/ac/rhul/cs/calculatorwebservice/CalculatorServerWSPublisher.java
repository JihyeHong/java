package uk.ac.rhul.cs.calculatorwebservice;
import javax.xml.ws.Endpoint;

public class CalculatorServerWSPublisher {
	public static void main(String[] args)
	{
		Endpoint.publish("http://localhost:31007/WS/CalculatorServer", new CalculatorServerImpl());
	}
}
