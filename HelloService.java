import org.w3c.dom.ls.LSOutput;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public interface HelloService extends Remote {
    public String echo(String input) throws RemoteException;
            
}