import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//needed to write the log file
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
// Encryption
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PrinterService extends UnicastRemoteObject implements PrinterServiceInterface {

    private HashMap<String, Printer> printers; // list with all the printers
    private HashMap<String, String> configs; // List with all parameters and values
    private Map<String, List<byte[]>> database = new HashMap<String, List<byte[]>>(); // Username and Hashed p/w database

    private List<User> loggedClientList = new ArrayList<>();
    
    private int sessionToken;

    // Create the log file
    String fileName = "log.txt"; // TODO: Add logs for remaining commands

    public PrinterService() throws RemoteException, NoSuchAlgorithmException {
        super();
        printers = new HashMap<String, Printer>();
        configs = new HashMap<String, String>();

        
        loggedClientList.add(new User("client1", "password1"));
        loggedClientList.add(new User("client2", "passowrd2"));
        
        for (User loggedClient : loggedClientList) {
            PasswordEncryptStore(loggedClient, loggedClient.getPassword(), generateSalt());
        }
        
    }

    public void PasswordEncryptStore(User user, String password, byte[] salted) throws NoSuchAlgorithmException {
        // Encyrpt the password.
        byte[] hashPassword = hashPassword(password, salted);

        //Create a list containing the two byte[] hashed pw and salt.
        List<byte[]> pwsaltList = new ArrayList<>();
        pwsaltList.add(hashPassword);
        pwsaltList.add(salted);

        // Add the username and list containing encrypted pw and salt to the HashMap (our simulation of a database)
        database.put(user.getUsername(), pwsaltList);
    } 

    public static byte[] generateSalt() throws NoSuchAlgorithmException{
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt); // Link salt to the hash
        byte[] hasedBytes = messageDigest.digest(password.getBytes());
        messageDigest.reset(); // Reset digest to ensure no values are stored.
        return hasedBytes;
    }

    public void initPrinters(String[] printerList) throws RemoteException {
        for (String p : printerList) {
            printers.put(p, new Printer(p));
        }
    }

    private Printer getPrinter(String printer) {
        Printer foundPrinter = printers.get(printer);
        if (foundPrinter == null) {
            // TODO: Return error / add new printer?
            return null;
        }
        return foundPrinter;
    }

    public void print(String filename, String printer) throws RemoteException { /// prints file filename on the
                                                                                /// specified printer
        Printer foundPrinter = getPrinter(printer);
        if (foundPrinter != null) {
            foundPrinter.addToQueue(filename);
        }
    }

    public void queue(String printer) throws RemoteException { // lists the print queue for a given printer on the
                                                               // user's display in lines of the form <job number> <file
                                                               // name>
        Printer foundPrinter = getPrinter(printer);
        if (foundPrinter != null) {
            foundPrinter.queue();
        }
    }

    public void topQueue(String printer, int job) throws RemoteException { // moves job to the top of the queue
        Printer foundPrinter = getPrinter(printer);
        if (foundPrinter != null) {
            foundPrinter.topQueue(job);
        }
    }

    public void start() throws RemoteException { // starts the print server
        logEntry("--Print server started.");

    }

    public void stop() throws RemoteException { // stops the print server
        logEntry("--Print server stopped.");
    }

    public void restart() throws RemoteException { // stops the print server, clears the print queue and starts the
                                                   // print server again
        stop();
        for (Printer p : printers.values()) {
            p.clearPrinterQueue();
        }
        start();
    }

    public void status(String printer) throws RemoteException { // prints status of printer on the user's display

    }

    public void readConfig(String parameter) throws RemoteException { // prints the value of the parameter on the print
                                                                      // server to the user's display
        System.out.println(configs.get(parameter)); // TODO: print on user's display
    }

    public void setConfig(String parameter, String value) throws RemoteException { // sets the parameter on the print
                                                                                   // server to value
        configs.put(parameter, value);
    }

    private void logEntry(String text) { // Writes the log file
        try {
            FileWriter fileWriter = new FileWriter(fileName, true); // Create a FileWriter with the file name
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); // Create a BufferedWriter to efficiently
                                                                            // write to the file
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(User user) throws RemoteException, NoSuchAlgorithmException{
        
        if (database.containsKey(user.getUsername())) {
            List<byte[]> pwsaltList = database.get(user.getUsername());

            byte[] password = pwsaltList.getFirst();
            byte[] salt = pwsaltList.getLast();
            if (password.equals(hashPassword(user.getPassword(), salt))) {
                return true;
            }
        } 

        return false;
    }
    public int getSessionToken(int token){
        sessionToken=token; 
        return sessionToken;
    }
}
