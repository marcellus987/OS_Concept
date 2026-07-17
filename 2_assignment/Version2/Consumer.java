// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;

public class Consumer implements Runnable{
    // Fields:
    CircularBuffer buffer;
    FileOutputStream oStream;
    int bounds;

    // Methods:
    Consumer(int n, String filename, CircularBuffer buffer) {
        this.buffer = buffer;
        bounds = n;
        try {
            oStream = new FileOutputStream(filename);
            new Thread(this, "Consumer").start();
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occured.");
        }
    } // End of constructor.

    public void run() {
        int bytesToRead;

        while(!buffer.getConsumerStatus()) {
            bytesToRead = new Random().nextInt(1, bounds + 1); // + 1 to make include n.
            if(bytesToRead <= buffer.count()) {
                buffer.get(oStream, bytesToRead);
            }
        }

        // Close output stream.
        try {
            oStream.close();
        }
        catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    } // End of run().
} // End of class Consumer.