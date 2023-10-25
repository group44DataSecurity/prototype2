
import java.util.HashMap;

public class Printer{

    private String name; // Name of the printer
    private String status; // Status of the printer
    private HashMap <Integer, String> printerQueue; // Queue for printer jobs


    public Printer(String name){
        this.name = name;
        //printerQueue = new HashMap<>;

    }

    public String getName(){
        return name;
    }

    public String getStatus(){
        return status;
    }

    public String setStatus(){
        return this.status = status;
    }

    public void queue(String printer){ // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>

    } 

    
    public void addToQueue(){ // adds job to printer's queue

    }

    public void topQueue(String printer, int job){ // moves job to the top of the queue

    }


    public void clearPrinterQueue(){ // Clears the printer's queue so it can restart

    }
}