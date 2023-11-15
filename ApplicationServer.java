import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import javax.print.PrintService;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException, NoSuchAlgorithmException, FileNotFoundException, IOException, ParseException {
        int port = 5099;
        Registry registry = LocateRegistry.createRegistry(port);


        String name = "print";
        PrinterService service = new PrinterService();
        //service.getSessionToken(sessionToken);

        
        System.out.println("Creating registry for port: " + port);
        //Registry registry = LocateRegistry.createRegistry(port);
        System.out.println("rebinding service to name: " + name);
        registry.rebind(name, service);
        
    

    }
}
