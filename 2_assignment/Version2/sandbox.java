import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class sandbox {
    public static void main(String[] argv) throws InterruptedException {
        char t;

        try {
            FileInputStream n = new FileInputStream("input.txt");
            t = (char)n.read();
            n.read();
            n.read();
            n.read();
            System.out.println(n.available());
        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){}       

    
    }
}
