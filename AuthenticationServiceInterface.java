import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthenticationServiceInterface extends Remote{
    public int getToken() throws RemoteException; // gets session token
}
