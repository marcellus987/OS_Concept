// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.util.Random;

public class Producer implements Runnable {

    // Fields:
    CircularBuffer buffer;
    
    int bounds;
    static int prodCount;



    // End of Fields.

    Producer(int n,CircularBuffer buffer) {
        this.buffer = buffer;
        bounds = n;
        prodCount = 0;
        new Thread(this, "Producer" + ++prodCount).start();
    } // End of constructor.


    public void run() {
        while(!buffer.getProducerStatus())
        {
            int bytesToRead = new Random().nextInt(1, bounds + 1); // + 1 to make include n.

            // If there is enough space for the desired number of bytes to put in buffer.
            if(bytesToRead <= buffer.availableSlot()) {
                buffer.put(bytesToRead);
            }
        }
    }
} // End of class Producer.

