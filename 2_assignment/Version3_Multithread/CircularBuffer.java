// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    static FileInputStream fin;
    static FileOutputStream fout;
    Boolean consumerDone; // Flag to terminate consumer thread.
    Boolean producerDone; // Flag to terminate producer thread, and 
                          // to notify consumer to consume all items in buffer.

    CircularBuffer(int size, String input, String output) {
        producer = new Semaphore(1);
        consumer = new Semaphore(0);
        buffer = new char[size];
        bufferSize = size;
        nextPut = 0;
        nextGet = 0;
        itemCount = 0;
        producerDone = false;
        consumerDone = false;

        try{
            fin  =new FileInputStream(input);
            fout = new FileOutputStream(output);
        }
        catch(FileNotFoundException e){
            System.out.println("Error");
        }
       

    } // End of constructor().
    
    void put(int amount) {

        try{
            // Get permit for producer thread to enter critical section.
            producer.acquire();

            // Critical section entry:
           
            // Will short if no more data to read from file.
            for(int i = 0; i < amount && fin.available() > 0; ++i) {
                buffer[nextPut] = (char)fin.read();
                nextPut = (++nextPut) % bufferSize;

                // Bookkeeping.
                ++itemCount;
            }

            // No more data to read. Producer is done.
            if(fin.available() == 0) {
                producerDone = true;
                fin.close();
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

    void get(int amount) {
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
                fout.write(buffer[nextGet]);
                nextGet = (++nextGet) % bufferSize;

                // Bookkeeping.
                --itemCount;
            }

            if(consumerDone) {
                fout.close();
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



