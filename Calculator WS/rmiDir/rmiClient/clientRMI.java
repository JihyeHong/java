import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;

public class clientRMI 
{
    static String serverAddress;
    static String serverPort;
    
	public clientRMI(String BilLocation)
	{
		//For the Remote Server   
		rmiReceiveInterface rmiServer;
	       
		Registry registry;
       //Here we need to add the IP address of the SERVER and its port
       //String serverAddress="localhost";
       //String serverPort="31007";
       
       //We will pass this string to the Remotely Invocated method by this Client
       //String text= "Hello";
       	       
       try{
           // get the registry
           registry=LocateRegistry.getRegistry(
               serverAddress,
               (new Integer(serverPort)).intValue()
           );
           
           // look up the remote object. The RMIServer is the name of the SERVER
           // we need to make sure we spell it correctly
           rmiServer = (rmiReceiveInterface)(registry.lookup("RMIServer"));
           
           String data = "Hello";
	    // call the remote method
	       rmiServer.RemotelyInvocatedMethod(data);
	      
       }
       catch(RemoteException e){
           e.printStackTrace();
       }
       catch(NotBoundException e){
           e.printStackTrace();
       }
	}
	
	static public void main(String args[])
	{

	   //For the Remote Server   
	   rmiReceiveInterface rmiServer;    
	   Registry registry;
		
       //Here we need to add the IP address of the SERVER and its port
	   Scanner sc = new Scanner(System.in);
	   
	   System.out.println("Enter a server address.");
       serverAddress = sc.next(); 
       
       System.out.println("Enter a server port.");
       serverPort = sc.next(); 
       
       //We will pass this string to the Remotely Invocated method by this Client      
       try{
           // get the registry
           registry=LocateRegistry.getRegistry(
               serverAddress,
               (new Integer(serverPort)).intValue()
           );
           
           // look up the remote object. The RMIServer is the name of the SERVER
           // we need to make sure we spell it correctly
           rmiServer = (rmiReceiveInterface)(registry.lookup("RMIServer"));
           
           System.out.println("Welcome to Java Calculator");
   		   System.out.println("==========================");
   		   System.out.println("Example: 2 * 8");
   		   System.out.print("Expression? ");
   		
           while(sc.hasNext()){
        	   
        	   String a = sc.next();
       	       String s = sc.next();
       	       String b = sc.next();
       	       
       	       String data = s+" "+a+" "+b;
       	       
               // call the remote method
       	       System.out.println(rmiServer.RemotelyInvocatedMethodNew(data));
           }
           
           sc.close();
	      	       
	      
       }
       catch(RemoteException e){
           e.printStackTrace();
       }
       catch(NotBoundException e){
           e.printStackTrace();
       }
	    
	}
}


