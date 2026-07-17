// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//



import java.util.concurrent.Semaphore;

public class Island {
    // Attributes:
    static Semaphore parkingPermit;
   

    Island(int capacity) {
        parkingPermit = new Semaphore(capacity);
        
    }

}
