import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;



public class AuthenticationService extends UnicastRemoteObject implements AuthenticationServiceInterface{
    public int token;
    private Map<Integer, clientCallBackInterface> activeSession = new HashMap<Integer, clientCallBackInterface>();

    public AuthenticationService(clientCallBackInterface cliencallback) throws RemoteException {
        super();
        
        Random random = new Random();
        token= random.nextInt();
        activeSession.put(token, cliencallback);
    }

    public int getToken() throws RemoteException{
        return token;

    }

    public void closeSession() {
        activeSession.remove(token);
    }

    public boolean isActive(int token){
        return true;
    }

}
