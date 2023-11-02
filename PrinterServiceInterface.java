
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface PrinterServiceInterface extends Remote {
    
    public void print(int sessionID, String filename, String printer) throws RemoteException; // prints file filename on the specified printer

    public void queue(int sessionID, String printer) throws RemoteException; // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>

    public void topQueue(int sessionID, String printer, int job) throws RemoteException; // moves job to the top of the queue

    public void start() throws RemoteException; // starts the print server

    public void stop() throws RemoteException; // stops the print server

    public void restart() throws RemoteException; // stops the print server, clears the print queue and starts the print server again

    public void status(int sessionID, String printer) throws RemoteException; // prints status of printer on the user's display

    public void readConfig(int sessionID, String parameter) throws RemoteException; // prints the value of the parameter on the print server to the user's display

    public void setConfig(int sessionID, String parameter, String value) throws RemoteException; // sets the parameter on the print server to value

    public void initPrinters(String[] printers) throws RemoteException;
    
    public String[] getPrintersList() throws RemoteException;

    public boolean authenticate (User user ) throws RemoteException, NoSuchAlgorithmException;

    public void setClientCallBack(clientCallBackInterface clientcallback) throws RemoteException;

    public int getSessionId() throws RemoteException;

}
