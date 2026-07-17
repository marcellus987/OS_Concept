// Author: Marcellus Von Sacramento
// Last date modified: 03/14/2025
// Assignment: 3
//

import java.util.concurrent.Semaphore;
import java.util.Random;


public class Professor implements Runnable {
    // Fields:

    int studentsInQueue;
    Student curStudent;
    String name;

    // To wake up Professor.
    static Semaphore permitToWakeUp = new Semaphore(0);

    // To ensure student finished the question before answering.
    static Semaphore permitToAnswer = new Semaphore(0);

    // To allow Professor to sleep when there is no student.
    static Semaphore permitToSleep = new Semaphore(0);

    // Methods: 
    
    Professor(String name, int q) {
        this.name = name;
        studentsInQueue = q;
        new Thread(this, name).start();
    } // End of constructor.
    
    public void run() {
        while(studentsInQueue > 0){
            System.out.println("Professor " + name + " is sleeping...\n");

            // Allow student to get in the room.
            Student.permitToEnterRoom.release();

            try {
                // Simulate sleep by placing Professor thread to wait queue.
                permitToWakeUp.acquire();
                System.out.println("Professor " + name + " woke up.");

                // Listen to student's question.
                System.out.println("Professor started listening to " + curStudent.getName() + "\'s question...");
                Student.permitToInquire.release();

                // Wait for student to finish asking question.
                permitToAnswer.acquire();

                // Answer student's question.
                answerStart();
                answerDone();

                // Allow student to exit session.
                Student.permitToExit.release();

                if(studentsInQueue > 0) {
                    // Wait for student to accept the answer.
                    permitToSleep.acquire();
                    System.out.println("Professor " + name + " went back to sleep...\n");
                }
            }
            catch(InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("\n--- Professor " + name + " got tired and went home. ---");
    } // End of run().

    public void answerStart() {
        System.out.println("Professor " + name + " answering " + curStudent.getName() + "\'s question...");
        delay();
    } // End of answerStart().

    public void answerDone() {
        // Bookkeeping.
        --studentsInQueue;
        System.out.println("Professor " + name + " finished answering " + curStudent.getName() + "\'s question.");
    } // End of answerDone().

    String getName() {
        return(name); 
    } // End of getName().


    // To be called by student.
    void serveStudent(Student student) {
        this.curStudent = student;
    } // End of serveStudent().

    public void delay() {
        try {
            // delay from 1 second to 2 seconds.
            Thread.sleep(new Random().nextInt(1000, 2000));
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        
    } // End of delay().

} // End of class Professor.
