

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ArticleIndexer {

	public static void main(String[] args) throws Exception {
		//File file = new File("/Users/jihye/Desktop/RHUL Term1/Java Project/article2content.txt");
		//Scanner in = new Scanner(file);
		Scanner in = new Scanner(System.in);

		articleIndexer(in);
		in.close();
		
	}
	
	public static Map<String, Integer> articleIndexer(Scanner in){

		Map<String, Integer> result = new HashMap<String, Integer>();
		int value = 0;
		
		while(in.hasNext()){
			String line = in.nextLine().toLowerCase();
			line = line.replaceAll("&quot", "\"");
			
			String [] array = line.split(" ");

			for(int i=0; i<array.length; i++){
				if(array[i].contains("://")){
					String url = "";
					if(array[i].contains("href")){
						url = array[i].split("\"")[1];	
					}else{
						url = array[i];
					}

					if(!result.containsKey(url) && UrlExtractor.matchServer(url,"guardian")){
						result.put(url, value);
						System.out.println(url + " " + value);  
						value++;
					}

				}
			}//for

		}//while
		
		return result;
	}
	
}
