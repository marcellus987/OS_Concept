// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//



import java.util.concurrent.Semaphore;

public class Ferry implements Runnable {
    // Attributes:
    private final int MIN_WAIT_TIME = 2; // 2s.
    private final int MAX_WAIT_TIME = 3; // 3s.
    
    int ferryCapacity;
    int customerCount; // Amount of customer to serve.
    boolean ferryInMainland; // To check if ferry is anchored in mainland or island.
    boolean customersWaiting; // Just for outputting specific message.

    // For "Customer" to acquire to board the ferry.
    // Ferry queue in the mainland and island, respectively.
    static Semaphore ferryPermitToIsland = new Semaphore(0);
    static Semaphore ferryPermitToMainland = new Semaphore(0);

    // For "Customer" to acquire for printing to output device.
    static Semaphore inIsland = new Semaphore(0);
    static Semaphore inMainland = new Semaphore(0);
   

    // Methods:

    Ferry(int capacity, int customerCount) {
        this.customerCount = customerCount; // For terminating thread.
        ferryInMainland = true;
        customersWaiting = false;
        ferryCapacity = capacity;

        // Run Ferry thread.
        new Thread(this, "Ferry").start();
    } // End of constructor.

    public void run() {
        while(customerCount > 0) {
            // Give some time for customers.
            randWait();

            // Admit customers who wants to go to the island. 
            // Only if there are parking available in the island.
            if(ferryPermitToIsland.hasQueuedThreads() && Island.parkingPermit.availablePermits() > 0) {
                customersWaiting = true;
                if(ferryInMainland) {
                    // The maximum numbers of cars the ferry can transport to the island.
                    int capacity = Math.min(Island.parkingPermit.availablePermits(),
                                   Math.min(ferryCapacity, ferryPermitToIsland.getQueueLength()));
                                  
                    admitToQueue(ferryPermitToIsland, capacity);
                    goToIsland(); // Has random wait.
                    inIsland.release(inIsland.getQueueLength());
                }
                else { //Else, they must wait until the ferry returns.

                    // Ferry is in the island, and no customers requesting to go back to mainland.
                    // Then, ferry must go back to mainland and pick up customers waiting there.
                    if(!ferryPermitToMainland.hasQueuedThreads()) {
                        customersWaiting = false;
                        System.out.println("No customers want to return to mainland. Ferry returning to mainland to pick up waiting customers.");
                        goToMainland();
                    }
                }
            }
            
            // Admit customers that want to go back to mainland.
            if(ferryPermitToMainland.hasQueuedThreads()) {
                customersWaiting = true;
                // Ferry in island.
                if(!ferryInMainland) {
                    // The maximum numbers of cars the ferry can transport back to the mainland.
                    int capacity = Math.min(ferryCapacity, ferryPermitToMainland.getQueueLength());
                    
                    admitToQueue(ferryPermitToMainland, capacity);
                    goToMainland(); // Has random wait.
                    inMainland.release(inMainland.getQueueLength());

                    // Bookkeeping for terminating the thread.
                    customerCount -= capacity; 
                    AcquirePermit.acquirePermits(Customer.offBoard, capacity);
                }
                else {
                    if(!ferryPermitToIsland.hasQueuedThreads()) {
                        customersWaiting = false;
                        System.out.println("No customers want to go to island at the moment. Ferry returning to island to pickup waiting customers.");
                        goToIsland();
                    }
                }
                System.out.println("\nRemaining customer (both in island and mainland (excluding those that have returned)): " + customerCount + "\n");
            }     
        }
    } // End of run().


    public void admitToQueue(Semaphore permit, int customerCount) {
        System.out.println("\nAt scheduled departure, received " + customerCount + " permit request(s).");
        permit.release(customerCount);
        AcquirePermit.acquirePermits(Customer.onBoard, customerCount);
        System.out.println("\n" + customerCount + " car(s) loaded.\n");
        
    } // End of admitToQueue.

    public void goToMainland() {
        if(customersWaiting) {
            System.out.println("All customers boarded the ferry.");
        }
        System.out.println("\nDeparting island...\n");
        randWait();
        System.out.println("\nArrived to mainland.\n");
        ferryInMainland = true;
    } // End of goToMainland.
    
    public void goToIsland() {
        if(customersWaiting) {
            System.out.println("All customers boarded the ferry.");
        }
        System.out.println("\nDeparting mainland...\n");
        randWait();
        System.out.println("\nArrived to island.\n");
        ferryInMainland = false;
    } // End of goToIsland.

    public void randWait() {
        RandomWait.randWait(MIN_WAIT_TIME, MAX_WAIT_TIME);
    } // End of randwait.

} // End of class Ferry.