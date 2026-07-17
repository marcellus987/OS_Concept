// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//

// Purpose: Just to avoid writing try-catch block in multiple source code.

import java.util.concurrent.Semaphore;


public class AcquirePermit {
    public static void acquire(Semaphore permit) {
        try {
            permit.acquire();
        }
        catch(InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void acquirePermits(Semaphore permit, int permitCount) {
        try {
            permit.acquire(permitCount);
        }
        catch(InterruptedException e) {
            System.out.println(e);
        }
    }
}
