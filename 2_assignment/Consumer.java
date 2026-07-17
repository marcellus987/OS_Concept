// Author: Marcellus Von Sacramento
// Last date modified: 03/04/2025
//

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

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
            new Thread(this, "Producer").start();
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occured.");
        }
    }

    public void run() {
        while(true) {
            //System.out.println("Consumer Entry.");
            int bytesToWrite;
            // If producer not done.
            if(!buffer.getProducerStatus()) {
                bytesToWrite = new Random().nextInt(1, bounds + 1); // + 1 to make include n. 
                // //For debug.
                // System.out.println(bytesToWrite);
                buffer.get(oStream, bytesToWrite);
            }
            else {
                bytesToWrite = buffer.getItemCount();
                //  //For debug.
                //  System.out.println(bytesToWrite);
                buffer.get(oStream, bytesToWrite);
                try {
                    System.out.println("Consumer exit.");
                    oStream.close();
                    break;

                } catch (IOException e) {
                    System.out.println("An error occured.");
                    e.printStackTrace();
                }
            }
        }
    }
};