import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Client {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, NoSuchAlgorithmException {
        int port = 5099;
        // String name = "hello";
        // HelloService service = (HelloService)
        // Naming.lookup("rmi://localhost:"+port+"/"+name);
        // System.out.println("--- " + service.echo("hey server"));

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
        service.start();
        //service.restart(); 
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
        service.stop();
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
