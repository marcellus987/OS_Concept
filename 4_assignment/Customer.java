// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//


import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    // Attributes:

    private final int MIN_WAIT_TIME = 1; // 1s.
    private final int MAX_WAIT_TIME = 4; // 4s.
    
    static Semaphore onBoard = new Semaphore(0);
    static Semaphore offBoard = new Semaphore(0);

    static int customerNumber = 0;
    String name; // To keep track of 

    // Methods: 

    Customer() {
        ++customerNumber;
        name = "Car #" + customerNumber;

        new Thread(this, "Car").start();
    } // End of constructor.

    public void run() {

        // 1: Get in the ferry.
        randWait();
        System.out.println(name + " arrived at the port. Requesting permit to enter ferry.");
        AcquirePermit.acquire(Ferry.ferryPermitToIsland);
        System.out.println(name + "'s request to Island trip granted. Now boarding ferry...");
        onBoard.release();

        // 2: Get island parking permit.
        AcquirePermit.acquire(Ferry.inIsland);
        AcquirePermit.acquire(Island.parkingPermit);

        // 3: Stay in the island.
        System.out.println(name + " arrived at the island. Parking Permit acquired. Now enjoying the island.");
        randWait();

         // 4: Get out of the island.
        System.out.println("\n" + name + " requesting return to mainland.");
        Island.parkingPermit.release();
        AcquirePermit.acquire(Ferry.ferryPermitToMainland);
        System.out.println(name + "'s request to Mainland trip granted. Now boarding ferry...");
        onBoard.release();
        AcquirePermit.acquire(Ferry.inMainland);
        System.out.println(name + " has returned to Mainland.");
        offBoard.release();
    } // End of run.


    

    // Simulate Stay in Island.
    public void stayInIsland() {
        randWait();
    } // End of stayInIsland


    public void randWait() {
        RandomWait.randWait(MIN_WAIT_TIME, MAX_WAIT_TIME);
    } // End of randWait.


}
