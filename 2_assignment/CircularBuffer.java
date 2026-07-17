// Author: Marcellus Von Sacramento
// Last date modified: 03/04/2025
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class CircularBuffer {
    char[] buffer;
    int bufferSize;
    int nextPut; // For producer.
    int nextGet; // For consumer.
    int itemCount;
    Boolean producerDone;
    

    // Set permit to 1 to ensure producer runs first.
    static Semaphore producer;
    static Semaphore consumer;

    CircularBuffer(int bufferSize) {
        producer = new Semaphore(bufferSize);
        consumer = new Semaphore(0);
        this.buffer = new char[bufferSize];
        this.bufferSize = bufferSize;
        nextPut = 0;
        nextGet = 0;
        itemCount = 0;
        producerDone = false;
    } // End of constructor().
    
    void put(FileInputStream iStream, int amount) {

        // Will only produce if there is enough space in buffer.
        try {
            producer.acquire(amount);
            try {
                // Put item to next available slot.
                for(int i = 0; i < amount && i < iStream.available() && itemCount < bufferSize; ++i){
                    ++itemCount;
                    buffer[nextPut] = (char)iStream.read();
                    nextPut = ++nextPut % bufferSize;

                    // Increment resource amount for consumer.
                    // That is, amount of consumable item.
                    consumer.release(i); 
                    
                }

                // Producer done.
                if(iStream.available() == 0) {
                    producerDone = true;
                }
            }
            catch(IOException e) {
                System.out.println("An error occured");
                e.printStackTrace();
            }
        }
        catch(InterruptedException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    } // End of put().

    void get(FileOutputStream oStream, int amount) {
        try {
            consumer.acquire(amount);
            try {
                // Transfer item from buffer to file.
                for(int i = 0; i < amount && itemCount > 0; ++i){
                    --itemCount;
                    oStream.write(buffer[nextGet]);
                    nextGet = ++nextGet % bufferSize;

                    // Increment resource amount for producer.
                    // That is, available slot for produced item.
                    producer.release(i); 
                }
            }
            catch(IOException e) {
                System.out.println("An error occured");
                e.printStackTrace();
            }
        }
        catch(InterruptedException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
    

    Boolean getProducerStatus() { return(producerDone); }

    int getItemCount() { return(itemCount); }
}



