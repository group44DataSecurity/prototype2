
import java.util.*;

public class Printer {

    private String name; // Name of the printer
    private Boolean status; // Status of the printer
    private LinkedList<Job> printerQueue; // Queue for printer jobs
    private int prevJobNumber;

    private class Job {
        int jobNumber;
        String fileName;

        public Job(String fileName) {
            this.fileName = fileName;
            this.jobNumber = prevJobNumber;
            prevJobNumber++;
        }
    }

    public Printer(String name) {
        this.name = name;
        prevJobNumber = 0;
        status = true;
        printerQueue = new LinkedList<Job>();

    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }


    public List<String> queue() { // lists the print queue for a given printer on the user's display in lines of
                          // the form <job number> <file name>
        List<String> queuelist = new ArrayList<String>();
        for (Job j : printerQueue) {
            queuelist.add("Job number: " + j.jobNumber + "   " + "Filename: " + j.fileName); // TODO: print on user's display
        }
        
        return queuelist;
    } 

    public void addToQueue(String fileName) { // adds job to printer's queue
        printerQueue.add(new Job(fileName));
    }

    public void topQueue(int job) { // moves job to the top of the queue
        Job foundJob = null;
        for (Job j : printerQueue) {
            if (j.jobNumber == job) {
                foundJob = j;
                System.out.println("job number: " + job + "moved on top");
                break;
            }
        }
        if (foundJob == null) {
            return;
        }
        printerQueue.remove(foundJob);
        printerQueue.add(0, foundJob);
    }

    public void clearPrinterQueue() { // Clears the printer's queue so it can restart
        printerQueue.clear();
    }
}