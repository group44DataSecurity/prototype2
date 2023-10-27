import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;

import javax.print.PrintService;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException, NoSuchAlgorithmException {
        //Registry registry = LocateRegistry.createRegistry(5099);
        //registry.rebind("hello", new HelloServant());

        int port = 5099;
        Registry registry = LocateRegistry.createRegistry(port);

        String AUTHname = "session";
        AuthenticationService authenticationService = new AuthenticationService();
        registry.rebind(AUTHname, authenticationService);
        int sessionToken = authenticationService.getToken();
        System.out.println("--------Session token:" + sessionToken);


        String name = "print";
        PrinterService service = new PrinterService();
        service.getSessionToken(sessionToken);
        System.out.println("Creating registry for port: " + port);
        //Registry registry = LocateRegistry.createRegistry(port);
        System.out.println("rebinding service to name: " + name);
        registry.rebind(name, service);
    

    }
}
