import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class serverRmi extends java.rmi.server.UnicastRemoteObject
implements rmiReceiveInterface		
{
	    static int      thisPort;
	    String   thisAddress;

	    Registry registry;    // rmi registry for lookup the remote objects.
	  
	    public serverRmi() throws RemoteException
	    {
	    		    
	    	try
	        {
	            // get the IP address of this host.
	            thisAddress= (InetAddress.getLocalHost()).toString();
	        }

	        catch(Exception e)
	        {
	            throw new RemoteException("can't get inet address.");
	        }

	        //thisPort= 31007; //assign static port 
	        
	        //Or if we want to assign  automatically a free port
	        /*
	        try {
				thisPort=this.findFreePort();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	         */	        
	        
	        System.out.println("this address="+thisAddress+",port="+thisPort);
	        
		try {
	        // create the registry and bind the name and object.

	        	registry = LocateRegistry.createRegistry( thisPort );
	            
	        	// We will use the RMIServer name by the client to identify this Server
	        	// so we need to note this name
	        	registry.rebind("RMIServer", this);
	        }
		        
	        catch(RemoteException e)
	        {
	        	throw e;
	        }
	    }

		@Override
		public String RemotelyInvocatedMethodNew(String passedData) throws RemoteException {
	        return calculatorNew(passedData);
		}
		
	    public String calculatorNew(String passedData){

	    	String result = null;
	    	
	    	try{
	    		
	    		String [] strArr = new String [3];
		        strArr = passedData.split(" ");
		        String s = strArr[0];
		        int a = Integer.parseInt(strArr[1]);
		        int b = Integer.parseInt(strArr[2]);
	    		
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
    
        //Remotely Invocated Method by a Client
        public void RemotelyInvocatedMethod(String passedData) throws RemoteException
        {
            //Print the data that was passed by remotely invocating this method by a client
            System.out.println(passedData);
        }
	    
	    

	    /************************************ SUPPORT METHODS ***********************************************/
	    public static int findFreePort() throws IOException 
		{
			ServerSocket server = new ServerSocket(0);
			int port = server.getLocalPort();
			server.close();
			return port;
		}

	    

	    static public void main(String args[])
	    {
	        try
	        {
	        	Scanner sc = new Scanner(System.in);
	        	System.out.println("Enter a server port");
	        	thisPort = sc.nextInt();
	        	serverRmi s = new serverRmi();
	        }

	        catch (Exception e) 
	        {
	        	e.printStackTrace();
	           	System.exit(1);
	    	}
	    }	   
	


}

