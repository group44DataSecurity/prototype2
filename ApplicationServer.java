import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.print.PrintService;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException {
        //Registry registry = LocateRegistry.createRegistry(5099);
        //registry.rebind("hello", new HelloServant());

        int port = 5099;
        String name = "print";
        PrinterService service = new PrinterService();

        System.out.println("Creating registry for port: " + port);
        Registry registry = LocateRegistry.createRegistry(port);
        System.out.println("rebinding service to name: " + name);
        registry.rebind(name, service);
    

    }
}
