// Author: Marcellus Von Sacramento
// Last date modified: 03/14/2025
// Assignment: 3
//

import java.util.concurrent.Semaphore;
import java.util.Random;


public class Student implements Runnable {
    // Fields: 

    Professor professor;
    String name;

    // Waiting queue.
    static Semaphore permitToEnterRoom = new Semaphore(0); 

    // To ensure Professor is ready.
    static Semaphore permitToInquire = new Semaphore(0); 

    // To ensure question is answered.
    static Semaphore permitToExit = new Semaphore(0); 
   
    // Methods: 

    public Student(String name, Professor professor) {
        this.professor = professor;
        this.name = name;

        // Create and start thread.
        new Thread(this, name).start();
    } // End of constructor.

    public void run() {
        delay();
        
        // Students may arrive and enter the queue while 
        // the first student and the Professor are in session.
        System.out.println(name + " entered the office hour queue.");

        try {    
            permitToEnterRoom.acquire();

            // Get the student currently being served.
            professor.serveStudent(this);

            // Waking up Professor.
            System.out.println(name + " entered the office and wakes up Professor " + professor.getName() + "...");
            Professor.permitToWakeUp.release();

            // Get permission to ask a question.
            permitToInquire.acquire();
            questionStart();

            // Wait for an answer. Then, exit.
            questionDone();

            permitToExit.acquire();

            // After some delay...
            System.out.println(name + " accepts the answer and exits the room.");
            Professor.permitToSleep.release();
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    } // End of run().

    public void questionStart() {
        System.out.println(name + " started asking a question...");
        delay();
    } // End of questionStart().

    public void questionDone() {
            // Question done. Allow the Professor to answer.
            System.out.println(name + " finished the question.");
            Professor.permitToAnswer.release();
    } // End of questionDone().

    String getName() { 
        return(name); 
    } // End of getName().

    public void delay() {
        try {
            // delay from 1 second to 2 seconds.
            Thread.sleep(new Random().nextInt(1000, 2000));
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        
    } // End of delay().

}  // End of class Student.


