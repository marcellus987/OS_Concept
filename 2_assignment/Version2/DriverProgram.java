// Author: Marcellus Von Sacramento
// Last date modified: 03/05/2025
//

public class DriverProgram {
    public static void main(String[] argv) {
        String inputFilename = argv[0];
        String outputFilename = argv[1];
        int bounds = Integer.parseInt(argv[2]);
        int bufferSize = Integer.parseInt(argv[3]);

        // // Hardcoded.
        // String inputFilename = "input.txt";
        // String outputFilename = "out.txt";
        // int bounds = 5;
        // int bufferSize = 500;
        
        CircularBuffer buffer = new CircularBuffer(bufferSize);

        new Producer(bounds, inputFilename, buffer);
        new Consumer(bounds, outputFilename, buffer);
    }
};