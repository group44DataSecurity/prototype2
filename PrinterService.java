import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
//needed to write the log file
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrinterService extends UnicastRemoteObject implements PrinterServiceInterface {

    private HashMap<String, Printer> printers; // list with all the printers
    private HashMap<String, String> configs; // List with all parameters and values

    // Create the log file
    String fileName = "log.txt"; // TODO: Add logs for remaining commands

    public PrinterService() throws RemoteException {
        super();
        printers = new HashMap<String, Printer>();
        printers.put("Printer1", new Printer("Printer1"));
        configs = new HashMap<String, String>();
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
}
