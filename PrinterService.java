import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class PrinterService extends UnicastRemoteObject implements PrinterServiceInterface {

    private HashMap<String, Printer> printers; // list with all the printers
    
    public PrinterService() throws RemoteException {
        super();
    }

    public void print(String filename, String printer){ /// prints file filename on the specified printer

    }

    public void start() throws RemoteException{ // starts the print server

    }

    public void stop() throws RemoteException{ // stops the print server

    }

    public void restart() throws RemoteException{ // stops the print server, clears the print queue and starts the print server again

    }

    public void status(String printer) throws RemoteException{ // prints status of printer on the user's display

    }

    public void readConfig(String parameter) throws RemoteException{ // prints the value of the parameter on the print server to the user's display

    }

    public void setConfig(String parameter, String value) throws RemoteException{ // sets the parameter on the print server to value

    }
}
