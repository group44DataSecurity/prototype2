import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallBack extends UnicastRemoteObject implements clientCallBackInterface {
    public ClientCallBack() throws RemoteException{
        super();
    }

    public void printOnClient(String message) throws RemoteException{
        System.out.println("From server: " + message);
    }
}
