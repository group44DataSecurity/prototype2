
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PrinterServiceInterface extends Remote {
    
    public void print(String filename, String printer) throws RemoteException; // prints file filename on the specified printer

    //public void start(); // starts the print server

    //public void stop(); // stops the print server

    //public void restart(); // stops the print server, clears the print queue and starts the print server again

    //public void status(String printer); // prints status of printer on the user's display

    //public void readConfig(String parameter); // prints the value of the parameter on the print server to the user's display

    //public void setConfig(String parameter, String value); // sets the parameter on the print server to value
}
