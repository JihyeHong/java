
import java.util.Scanner;

public class UrlExtractor {

	public static void main(String[] args) throws Exception {
		
		//Test Excercise1

		System.out.println("=================== Question 1");
		Scanner in = new Scanner(System.in); //three-page.txt

		scanAndPrint(in);
		in.close();
		
		//Test Excercise2-3
		/*
		System.out.println("=================== Question 2-3");
		System.out.println(matchServer("http://www.google.com", "google.com"));
		System.out.println(matchServer("http://www.theguardian.com", "GUARDIAN"));
		System.out.println(matchServer("http://www.Guardian.co.uk/global-development","guardian"));
		System.out.println(matchServer(null, "guardian.co.uk"));
		System.out.println(matchServer("http://theguardian.com", "theguardian.com"));
		System.out.println(matchServer("https://www.barclays.co.uk", "Barclays"));
		System.out.println(matchServer("http://www.Guardian.co.uk/global-development", "global"));
		*/
					
		//Test Excercise2-4
		/*
		System.out.println("=================== Question 2-4");
		
		Scanner in2 = new Scanner(System.in);
		scanAndPrint(in2, "theguardian");
		
		in2.close();
		*/
		
		
	}
	
	public static void scanAndPrint (Scanner in) {
		while(in.hasNext()){
			String line = in.nextLine().toLowerCase();

			String [] array = line.split("<");
				
			for(int i=0; i<array.length; i++){
				if(array[i].contains("a href")){
					String url = array[i].split("\"")[1];	
					if(!url.isEmpty()){
						System.out.println(MyURL.parseURL(url));
					}
				}
			}	
		}
	}
	
	public static void scanAndPrint (Scanner in, String str) {
		while(in.hasNext()){
			String line = in.nextLine().toLowerCase();			
			String [] array = line.split("<");
				
			for(int i=0; i<array.length; i++){
				if(array[i].contains("a href")){
					String url = array[i].split("\"")[1];
					if(!url.isEmpty() && matchServer(url, str)){
						
						System.out.println(MyURL.parseURL(url));
					}
				}
			}	
		}
	}
	
	
	public static boolean matchServer(String url, String str){	
		boolean result = false;
		if(url!=null && str!=null){
			if(null!=MyURL.parseURL(url)){
				url = url.toLowerCase();
				str = str.toLowerCase();
				String [] urlSplit = url.split("/");
				result = urlSplit[2].contains(str);
			}
		}
		
		//System.out.println(result);
		return result;
	}
}

