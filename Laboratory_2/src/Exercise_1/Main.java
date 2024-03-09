package Exercise_1;

// The Main class can also be the "Controller" characteristic.
public class Main {
    private static final int noOfThreads = 8;
    public static final int processorLoad = 1_000_000;

    public static void main(String[] args) {

        Window win = new Window(noOfThreads);

        for(int i = 0; i < noOfThreads; i++){
            new Fir(i, win, processorLoad).start(i+2);
        }
    }
}