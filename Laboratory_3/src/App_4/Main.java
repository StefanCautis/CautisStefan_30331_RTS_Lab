package App_4;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Integer[] monitors = {0,0};
        ExecutionThread t1 = new ExecutionThread(monitors, 7,3,2, null);
        t1.start();
        new ExecutionThread(monitors, 0, 5,3, t1).start();
        new ExecutionThread(monitors, 0, 6,4, t1).start();

    }
}
