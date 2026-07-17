// Author: Marcellus Von Sacramento
// Last date modified: 04/01/2025
// Assignment: 4
//

// Purpose: For simulating delay when: Customers arriving; 
//          Ferry is traveling; and Customer staying in the island.
//          Also, just to avoid writing try-catch block in multiple source code.

import java.util.Random;

public class RandomWait {
    public static void randWait(final int MIN_TIME, final int MAX_TIME) {
        try {
            Thread.sleep((new Random().nextInt(MIN_TIME, MAX_TIME) * 1000));
        }
        catch(InterruptedException e) {
            System.out.println(e);
        }
    }
}
