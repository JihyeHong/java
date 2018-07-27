package uk.ac.rhul.cs.calculatorwebservice;

public class Calculator {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Java Calculator");
		System.out.println("==========================");
		
		int a = Integer.parseInt(args[0]);
		String cal = args[1];
		int b = Integer.parseInt(args[2]);
		
		if(cal.equals("+")){
			System.out.println(addition(a,b));
		}
		
		
	}
	
	public static int addition(int a, int b){
		return a+b;
	}
	
	public static int subtraction(int a, int b){
		return a-b;
	}
	
	public static int multiplication(int a, int b){
		return a*b;
	}
	
	public int division(int a, int b){
		return a/b;
	}
	


}
