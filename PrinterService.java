import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//needed to write the log file
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// Encryption
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class PrinterService extends UnicastRemoteObject implements PrinterServiceInterface {

    private HashMap<String, Printer> printers; // list with all the printers
    private HashMap<String, String> configs; // List with all parameters and values
    private Map<String, List<byte[]>> database = new HashMap<String, List<byte[]>>(); // Username and Hashed p/w database
    private String[] printersNames = {"Printer1","Printer2","Printer3"};
    private List<User> loggedClientList = new ArrayList<>();
    private clientCallBackInterface clientCallBack;
    private AuthenticationServiceInterface session;
    
    private int sessionToken;

    // Create the log file
    String fileName = "log.txt"; // TODO: Add logs for remaining commands

    public PrinterService() throws RemoteException, NoSuchAlgorithmException {
        super();
        printers = new HashMap<String, Printer>();
        configs = new HashMap<String, String>();

        
        loggedClientList.add(new User("client1", "password1"));
        loggedClientList.add(new User("client2", "passowrd2"));
        //initialize printers 
        
        initPrinters(printersNames);

        

        for (User loggedClient : loggedClientList) {
            PasswordEncryptStore(loggedClient, loggedClient.getPassword(), generateSalt());
        }
        
    }

    public void setClientCallBack(clientCallBackInterface c) throws RemoteException{
        this.clientCallBack = c;
    } 

    public int getSessionId() throws RemoteException{
        session = new AuthenticationService();
        int sessionID = session.getToken();
        clientCallBack.printOnClient("The session ID token is: " + sessionID);
        logEntry("Session ID assigned");
        return sessionID;
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
    public String[] getPrintersList() throws RemoteException    {
        return printersNames;
    }

    private Printer getPrinter(String printer) {
        Printer foundPrinter = printers.get(printer);
        if (foundPrinter == null) {
            // TODO: Return error / add new printer?
            return null;
        }
        return foundPrinter;
    }

    public void print(int sessionIDClient,String filename, String printer) throws RemoteException { /// prints file filename on the
                                                                                /// specified printer
        if (sessionIDClient == session.getToken()){

        
            Printer foundPrinter = getPrinter(printer);
            if (foundPrinter != null) {
                foundPrinter.addToQueue(filename);
                clientCallBack.printOnClient("Printing " + filename + " on printer name: " + printer);
                logEntry("print function invoked - session ID: " + sessionIDClient);
            }
        }
    }

    public void queue(int sessionIDClient, String printer) throws RemoteException { // lists the print queue for a given printer on the
                                                               // user's display in lines of the form <job number> <file
                                                               // name>
        if (sessionIDClient == session.getToken()){
            Printer foundPrinter = getPrinter(printer);
            if (foundPrinter != null) {
                List<String> queueList = foundPrinter.queue();
                //printing the queue list
                for(String queue : queueList){
                    clientCallBack.printOnClient(queue);
                }

            } else{
                clientCallBack.printOnClient("queue is empty");
            }
            logEntry("queue function invoke - session ID: " + sessionIDClient);
        }
    }

    public void topQueue(int sessionIDClient,String printer, int job) throws RemoteException { // moves job to the top of the queue
        
        if (sessionIDClient == session.getToken()){
            Printer foundPrinter = getPrinter(printer);
            if (foundPrinter != null) {
                foundPrinter.topQueue(job);
                clientCallBack.printOnClient("moving job number: " + job + " on top");
            }
            logEntry("Top queue function invoke - session ID: " + sessionIDClient);
        }
    }

    public void start() throws RemoteException { // starts the print server
        logEntry("--Print server started.");
        clientCallBack.printOnClient("starting the printer server");

    }

    public void stop() throws RemoteException { // stops the print server
        logEntry("--Print server stopped.");
        clientCallBack.printOnClient("stopping the printer server");
    }

    public void restart() throws RemoteException { // stops the print server, clears the print queue and starts the
                                                   // print server again
        stop();
        for (Printer p : printers.values()) {
            p.clearPrinterQueue();
        }
        start();

        logEntry("--Print server restarted.");
    }

    public void status(int sessionIDClient, String printer) throws RemoteException { // prints status of printer on the user's display
        if (sessionIDClient == session.getToken()){
            Printer foundPrinter = getPrinter(printer);
            if (foundPrinter.getStatus()){
                clientCallBack.printOnClient("Printer is active");
            }else{
                clientCallBack.printOnClient("Printer is not active");
            }
            logEntry("status function invoked - session ID: " + sessionIDClient);
        }
        
    }

    public void readConfig(int sessionIDClient, String parameter) throws RemoteException { // prints the value of the parameter on the print
                                                                      // server to the user's display
        
        if (sessionIDClient == session.getToken()){
            clientCallBack.printOnClient("reading configuration: " + configs.get(parameter)); // TODO: print on user's display


            logEntry("readConfig function invoked, printing on display - session ID: " + sessionIDClient);
        }
    }

    public void setConfig(int sessionIDClient, String parameter, String value) throws RemoteException { // sets the parameter on the print
                                                                                   // server to value
        if (sessionIDClient == session.getToken()){
            configs.put(parameter, value);
            
            clientCallBack.printOnClient("setting configuration" + parameter + " value: " + value);
            logEntry("setConfig function invoked - session ID: " + sessionIDClient);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////   
/////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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

    public static String toHex(byte[] bytes){
        StringBuilder hexString = new StringBuilder(); //(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public int authenticate(User user) throws RemoteException, NoSuchAlgorithmException{
        
        if (database.containsKey(user.getUsername())) {
            List<byte[]> pwsaltList = database.get(user.getUsername());

            byte[] password = pwsaltList.getFirst();
            byte[] salt = pwsaltList.getLast();
            byte[] passwordIn = hashPassword(user.getPassword(), salt);
            
            // Check user entered password against out hashed and salted database
            if (toHex(password).equals(toHex(passwordIn))) {
                return getSessionId();
            }  
        } 

        return 0;
    }
    public int getSessionToken(int token){
        sessionToken=token; 
        return sessionToken;
    }

    public void printWithCallback(String message) throws RemoteException{
        clientCallBack.printOnClient(message);
    }
}
