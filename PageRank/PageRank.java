
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PageRank {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		try {
			pageRank(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

        in.close();
	}
	
	public static void pageRank(Scanner in) throws Exception{
		Map<String, String> urlToSeq = new HashMap<String, String>();
		Map<String, String> seqToUrl = new HashMap<String, String>();
		
		int n = 0;
        while (in.hasNext()){
        	String l = in.nextLine();
            String line[] = l.split(" ");
            urlToSeq.put(line[0], line[1]);
            seqToUrl.put(line[1], line[0]);
            n++;
        }
        
        double [][] A = computeTrans(n,urlToSeq);
        double [] R = computeRank(A);
        sortPrint(R,seqToUrl);
	}
	
	public static String getGraph(Map<String, String> urlToSeq) throws Exception{
	File file = new File("scrape.txt");
        Scanner sc = new Scanner(file);
		String graph = "";
		
        while(sc.hasNext()){
        	String line = sc.nextLine().toLowerCase();		
        	line = line.replaceAll("&quot", "\"");
			String [] array = line.split(" ");
			
			String pageIdx = "";
			String linkIdx = "";
			
			for(int i=0; i<array.length; i++){
				String flag = "";
				String url = "";
				if(array[i].contains("://")){
					if(array[i].startsWith("http") && UrlExtractor.matchServer(array[i],"guardian")){ //page
						flag = "page";
						url = array[i];
					}else if(array[i].startsWith("href")){ //link
						flag = "link";
						url = array[i].split("\"")[1];
					}
					
					if(urlToSeq.containsKey(url)){
//						System.out.println(flag.equals("page")?"pageUrl:"+url:"linkUrl:"+url);
						pageIdx = flag.equals("page")?urlToSeq.get(url):pageIdx;
						linkIdx = flag.equals("link")?urlToSeq.get(url):linkIdx;
						
						if(!pageIdx.isEmpty() && !linkIdx.isEmpty()){
//							System.out.println("page:"+pageIdx);
//							System.out.println("link:"+linkIdx);
							graph = graph + pageIdx + " " + linkIdx + " ";
						}
					}
				}
			}
        }
        
        sc.close();
        
        return graph;
	}
	
	public static double[][] computeTrans(int n, Map<String,String> urlToSeq) throws Exception{

        int [][] M = new int[n][n];
        int [] degree = new int[n];
        
        String graph = getGraph(urlToSeq);
        String [] graphArr = graph.split(" ");
        
        for(int k=0; k<graphArr.length/2; k++){
        	int i = Integer.parseInt(graphArr[k*2]);
        	int j = Integer.parseInt(graphArr[k*2+1]);
        	
        	degree[i]++;
        	M[i][j]++;
        }
        
        double [][] trans = new double[n][n];
        for(int i=0; i<n; i++){
        	for(int j=0; j<n; j++){
            	if(degree[i]==0.0){
            		//When outDegree of a page is 0,
            		//To make the matrix stochastic (all rows sum to 1), make that page equally likely to transit to every other page.
            		trans[i][j] = 1/(double)n;
            	}else{
            		double q = Double.isNaN(M[i][j]*0.9/degree[i])?0.0:M[i][j]*(0.9/degree[i]);
            		double p = q + 0.10/n;
            		trans[i][j] = p;
            	}
        	}
        }
        
        return trans;
		
	}
	
	public static double[] computeRank(double [][] A){
		int n = A.length;

		double [] rank = new double [n];
		rank[0] = 1.0;
		
		double [] e = new double [n];
		boolean flag = true;
		
		while(flag){
			double [] newRank = new double[n];
			for(int j=0; j<n; j++){
				double temp = 0.0;
				for(int k=0; k<n; k++){
					temp = temp + rank[k]*A[k][j];
					newRank[j] =  temp;
				}
				e[j] = rank[j]*0.1;
			}
			
			for(int i=0; i<n; i++){
				if(Math.abs(rank[i]-newRank[i])<e[i]){
					flag = false;
				}
			}
			rank = newRank;
		}
		
		return rank;
	}
	
	public static void sortPrint(double [] R, Map<String,String> seqToUrl){
		int n = R.length;
		double[] copy = new double[n];
		
		for (int i=0; i<n; i++) {
			double a = R[i];
			copy[i] = a;
		}
		
		int[] rank = new int[n];
		Arrays.sort(copy);
		List<String> arr = new ArrayList<String>();
		
		for(int i=0; i<n; i++){
			arr.add(String.valueOf(copy[i]));
		}

		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(R[i] == Double.parseDouble(arr.get(j)) ){
					arr.set(j,"99999");
			        rank[i] = j;
			        break;
				}
			}
		}
		
		int ten = (int)(n*0.1);
		String [] most = new String[ten];
		String [] least = new String[ten];
		
		for(int i=0; i<ten; i++){
			for(int j=0; j<n; j++){
				if(rank[j]==i){
					least[i] = seqToUrl.get(String.valueOf(j));
				}
			}
		}
		
		for(int i=0; i<ten; i++){
			for(int j=0; j<n; j++){
				if(rank[j]==(n-(i+1))){
					most[i] = seqToUrl.get(String.valueOf(j));
				}
			}
		}
		
		System.out.println("Most popular 10%:");
		for(int k=0; k<ten; k++){
			System.out.println("URL"+k+" "+most[k]);
		}
		
		System.out.println("\nLeast popular 10%:");
		for(int k=0; k<ten; k++){
			System.out.println("URL"+k+" "+least[k]);
		}
	}
}
