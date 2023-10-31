import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface clientCallBackInterface extends Remote {
    void printOnClient(String message) throws RemoteException;
}
