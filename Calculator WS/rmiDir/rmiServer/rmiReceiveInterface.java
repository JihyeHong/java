import java.rmi.*;

public interface rmiReceiveInterface extends Remote
{

   void RemotelyInvocatedMethod(String x) throws RemoteException;

   String RemotelyInvocatedMethodNew(String x) throws RemoteException;

}
