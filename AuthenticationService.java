import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;



public class AuthenticationService extends UnicastRemoteObject implements AuthenticationServiceInterface{
    public int token;

    public AuthenticationService() throws RemoteException {
        super();
        
        Random random = new Random();
        token= random.nextInt();
    }

    public int getToken() throws RemoteException{
        return token;
    }



    public boolean isActive(int token){
        return true;
    }

}
