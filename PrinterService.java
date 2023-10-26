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

    public void start(){ // starts the print server

    }

    public void stop(){ // stops the print server

    }

    public void restart(){ // stops the print server, clears the print queue and starts the print server again

    }

    public void status(String printer){ // prints status of printer on the user's display

    }

    public void readConfig(String parameter){ // prints the value of the parameter on the print server to the user's display

    }

    public void setConfig(String parameter, String value){ // sets the parameter on the print server to value

    }
}
