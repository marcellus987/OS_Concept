// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

public class Producer implements Runnable {

    // Fields:
    CircularBuffer buffer;
    FileInputStream iStream;
    int bounds;



    // End of Fields.

    Producer(int n, String filename, CircularBuffer buffer) {
        this.buffer = buffer;
        bounds = n;
        try {
            iStream = new FileInputStream(filename);
            new Thread(this, "Producer").start();
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occured.");
        }
    } // End of constructor.


    public void run() {
        while(!buffer.getProducerStatus())
        {
            int bytesToRead = new Random().nextInt(1, bounds + 1); // + 1 to make include n.

            // If there is enough space for the desired number of bytes to put in buffer.
            if(bytesToRead <= buffer.availableSlot()) {
                buffer.put(iStream, bytesToRead);
            }
        }

        // Producer done. Close input stream.
        try {
            iStream.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
} // End of class Producer.

