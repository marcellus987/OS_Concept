// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

public class DriverProgram {
    public static void main(String[] argv) {
        // String inputFilename = argv[0];
        // String outputFilename = argv[1];
        // int bounds = Integer.parseInt(argv[2]);
        // int bufferSize = Integer.parseInt(argv[3]);

        // Hardcoded.
        String inputFilename = "k.mkv";
        String outputFilename = "out.mkv";
        int bounds = 5;
        int bufferSize = 500;
        
        CircularBuffer buffer = new CircularBuffer(bufferSize, inputFilename, outputFilename);

        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);


        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);
        new Producer(bounds, buffer);
        new Consumer(bounds, buffer);

        
        
    }
};