package uk.ac.rhul.cs.calculatorwebservice;

import javax.jws.WebService;

@WebService(endpointInterface="uk.ac.rhul.cs.calculatorwebservice.CalculatorServer")
public class CalculatorServerImpl implements CalculatorServer{

	@Override
	public String calc(String s, int a, int b) {

		String result = null;
    	
    	try{
			if(s.equals("+")){
				result = "Your answer is " + (a+b);
			}else if(s.equals("-")){
				result = "Your answer is " + (a-b);
			}else if(s.equals("*")){
				result = "Your answer is " + (a*b);
			}else if(s.equals("/")){
				if(b==0){
					result = "Denominator must not zero.";
				}else{
					result = "Your answer is " + (a/b);
				}
			}else{
				result = "The operator must be +,-,* or /";
			}
    	}catch(Exception e){
    		result = "Expression format should be 'int operator int' ";
    	}
		
		return result;
	}
	
}
