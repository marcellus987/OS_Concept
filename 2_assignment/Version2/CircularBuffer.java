// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class CircularBuffer {

    // Fields:
    char[] buffer;
    int bufferSize;
    int nextPut; // For producer.
    int nextGet; // For consumer.
    int itemCount;
    static Semaphore producer;
    static Semaphore consumer;
    Boolean consumerDone; // Flag to terminate consumer thread.
    Boolean producerDone; // Flag to terminate producer thread, and 
                          // to notify consumer to consume all items in buffer.

    CircularBuffer(int size) {
        producer = new Semaphore(1);
        consumer = new Semaphore(0);
        buffer = new char[size];
        bufferSize = size;
        nextPut = 0;
        nextGet = 0;
        itemCount = 0;
        producerDone = false;
        consumerDone = false;
    } // End of constructor().
    
    void put(FileInputStream iStream, int amount) {

        try{
            // Get permit for producer thread to enter critical section.
            producer.acquire();

            // Critical section entry:
           
            // Will short if no more data to read from file.
            for(int i = 0; i < amount && iStream.available() > 0; ++i) {
                buffer[nextPut] = (char)iStream.read();
                nextPut = (++nextPut) % bufferSize;

                // Bookkeeping.
                ++itemCount;
            }

            // No more data to read. Producer is done.
            if(iStream.available() == 0) {
                producerDone = true;
            }
        }
        catch (InterruptedException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        catch(IOException IOErr) {
            System.out.println("IO Error occured.");
            IOErr.printStackTrace();
        }

        consumer.release(); // Release permit for consumer.
    } // End of put().

    void get(FileOutputStream oStream, int amount) {
        try{
            // Get permit for consumer thread to enter critical section.
            consumer.acquire(); 

            // Critical section entry:

            // If producer is done, consume all items in buffer
            // and set consumer flag. This must be check inside
            // critical section because produce modifies "producerDone".
            if(producerDone) {
                amount = itemCount;
                consumerDone = true;
            }

            for(int i = 0; i < amount; ++i) {
                oStream.write(buffer[nextGet]);
                nextGet = (++nextGet) % bufferSize;

                // Bookkeeping.
                --itemCount;
            }
        }
        catch (InterruptedException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        catch(IOException IOErr) {
            System.out.println("IO Error occured.");
            IOErr.printStackTrace();
        }
        
        producer.release(); // Release permit for producer.
    } // End of get().
    

    Boolean getProducerStatus() { return(producerDone); }
    Boolean getConsumerStatus() { return(consumerDone); }
    int count() { return(itemCount); }
    int availableSlot() { return(bufferSize - itemCount); }
} // End of class CircularBuffer.



