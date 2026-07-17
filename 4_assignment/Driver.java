// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//
// Note: Requires the following files: Ferry.java, Island.java, Customer.java,
//      AcquirePermit.java, and RandomWait.java

public class Driver {
    public static void main(String[] args) {
        // Hard-coded for Submission.
        final int PARKING_SPACE = 15;
        final int FERRY_CAPACITY = 5;
        final int CUSTOMER_COUNT = 10;


        System.out.println("Welcome to Ferry Simulator!\n");
        System.out.println("Island parking : " + PARKING_SPACE);
        System.out.println("Ferry capacity: " + FERRY_CAPACITY);
        System.out.println("Customer count: " + CUSTOMER_COUNT + "\n\n");
    
        Ferry ferry = new Ferry(FERRY_CAPACITY, CUSTOMER_COUNT);
        Island island = new Island(PARKING_SPACE);
        Customer []customers = new Customer[CUSTOMER_COUNT];
        
        for(int i = 0; i < CUSTOMER_COUNT; ++i) {
            customers[i] = new Customer();
        }
        
    }
   
}
