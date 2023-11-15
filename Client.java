import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Client {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, NoSuchAlgorithmException, FileNotFoundException, IOException, ParseException {
        int port = 5099;

        String name = "print";
        PrinterServiceInterface service = (PrinterServiceInterface) Naming
                .lookup("rmi://localhost:" + port + "/" + name);

        System.out.println("Client started.");

        String[] printers = service.getPrintersList();
        
        clientCallBackInterface clientcallback = new ClientCallBack();
        service.setClientCallBack(clientcallback);

        User user = getUser();
        int sessionID = service.authenticate(user);
        while (sessionID == 0) {
            System.out.println("Try again. Wrong credentials.");
            user = getUser();
            sessionID = service.authenticate(user);
        }

        System.out.println("Authenticated user.");

        // Simulation of user input into the print server.
        service.start(sessionID);
        service.restart(sessionID); 
        service.print(sessionID,"file1.pdf", printers[0]);
        service.print(sessionID,"file2.pdf", printers[0]);
        service.print(sessionID,"file3.pdf", printers[0]);
        service.status(sessionID, printers[0]);
        service.queue(sessionID,printers[0]);
        service.topQueue(sessionID,printers[0], 2);
        service.queue(sessionID,printers[0]);
        service.setConfig(sessionID," myParameter", " 100");
        service.setConfig(sessionID," toner", " blue");
        service.readConfig(sessionID," myParameter");
        service.stop(sessionID);
        System.out.println("-----------------------------------");
        
    }

    public static User getUser() {
        System.out.println("Enter your username:");
        String username = input.nextLine();

        System.out.println("Enter your password:");
        String password = input.nextLine();

        return new User(username, password);
    }
}
