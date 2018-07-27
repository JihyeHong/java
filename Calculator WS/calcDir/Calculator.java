import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

	public static void main(String[] args) {
		System.out.println("Welcome to Java Calculator");
		System.out.println("==========================");
		System.out.println("Example: 2 * 8");
		System.out.print("Expression? ");
		Scanner sc = new Scanner(System.in);

		try{			
			
			while(sc.hasNext()){
				
				int a = sc.nextInt();
				String s = sc.next();
				int b = sc.nextInt();
				
				if(s.equals("+")){
					System.out.println("Your answer is " + add(a,b));
				}else if(s.equals("-")){
					System.out.println("Your answer is " + sub(a,b));
				}else if(s.equals("*")){
					System.out.println("Your answer is " + multi(a,b));
				}else if(s.equals("/")){
					if(b==0){
						System.out.println("Denominator must not zero.");
					}else{
						System.out.println("Your answer is " + div(a,b));
					}
				}else{
					System.out.println("The operator must be +,-,* or /");
				}
			
			}
			
			sc.close();
			
		}catch(InputMismatchException e){
			System.out.println("Expression format should be 'int operator int' ");
			System.out.println("Example 5 * 6");
		}
		
	}
	
	public static int add(int a, int b){
		return a+b;
	}
	
	public static int sub(int a, int b){
		return a-b;
	}
	
	public static int multi(int a, int b){
		return a*b;
	}
	
	public static int div(int a, int b){
		return a/b;
	}
}
