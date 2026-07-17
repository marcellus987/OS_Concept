
public class DriverProgram {
    public static void main(String[] argv) {
        String inputFilename = argv[0];
        String outputFilename = argv[1];
        int bounds = Integer.parseInt(argv[2]);
        int bufferSize = Integer.parseInt(argv[3]);
        
        CircularBuffer buffer = new CircularBuffer(bufferSize);

        new Producer(bounds, inputFilename, buffer);
        new Consumer(bounds, outputFilename, buffer);
    }
};