// Author: Marcellus Von Sacramento
// Last date modified: 03/04/2025
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
            
            //System.out.println("Producer Entry.");
            int bytesToRead = new Random().nextInt(1, bounds + 1); // + 1 to make include n.
             //For debug.
            //  System.out.println(bytesToRead);
            buffer.put(iStream, bytesToRead);
        }
        try {
            System.out.println("Producer Exit.");
            iStream.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

};
