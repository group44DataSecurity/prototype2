import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Scanner;

public class Client {
    static Scanner input = new Scanner(System.in); 

    public static void main( String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        int port = 5099;
        //String name = "hello";
        //HelloService service = (HelloService) Naming.lookup("rmi://localhost:"+port+"/"+name);
        //System.out.println("--- " + service.echo("hey server"));

        String name = "print";
        PrinterServiceInterface service = (PrinterServiceInterface) Naming.lookup("rmi://localhost:"+port+"/"+name);

        System.out.println("Client started.");

        if(login()) {
            System.out.println("Authenticated user.");

            service.start();
        }
        
    }



    public static boolean login(){
        System.out.println("Enter your username:");
		String username = input.nextLine();  
			
		System.out.println("Enter your password:");
		String password = input.nextLine();


        return true; //always true for now
    }
}
