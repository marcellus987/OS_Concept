// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.util.Random;

public class Consumer implements Runnable{
    // Fields:
    CircularBuffer buffer;
    
    int bounds;
    static int conCount;

    // Methods:
    Consumer(int n, CircularBuffer buffer) {
        this.buffer = buffer;
        bounds = n;
        conCount = 0;
        new Thread(this, "Consumer" + ++conCount).start();
      
    } // End of constructor.

    public void run() {
        int bytesToRead;

        while(!buffer.getConsumerStatus()) {
            bytesToRead = new Random().nextInt(1, bounds + 1); // + 1 to make include n.
            if(bytesToRead <= buffer.count()) {
                buffer.get(bytesToRead);
            }
        }
    } // End of run().
} // End of class Consumer.