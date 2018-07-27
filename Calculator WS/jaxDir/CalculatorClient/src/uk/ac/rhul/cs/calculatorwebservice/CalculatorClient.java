package uk.ac.rhul.cs.calculatorwebservice;


import java.util.Scanner;

public class CalculatorClient {
	public static void main(String[] args){
		CalculatorServerImplService calculatorService = new CalculatorServerImplService();
		CalculatorServer calculator = calculatorService.getCalculatorServerImplPort();
		
		System.out.println("Welcome to Java Calculator");
		System.out.println("==========================");
		System.out.print("Your Expression ?");
		
		Scanner sc = new Scanner(System.in);
		
		
		try{
			while(sc.hasNext()){
				int a = sc.nextInt();
				String s = sc.next();
				int b = sc.nextInt();
	
				System.out.println(calculator.calc(s,a,b));
			}
		}catch(Exception e){
    		System.out.println("Expression format should be 'int operator int' ");
    	}
		
		sc.close();
		
	}
}
