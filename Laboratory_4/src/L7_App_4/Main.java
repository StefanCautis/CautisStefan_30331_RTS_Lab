package L7_App_4;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(2);
        ExecutionThread one,two,three;
        one = new ExecutionThread(1, s, 5, (int) Math.round(Math.random() * 3 + 6), 2);
        two= new ExecutionThread(2, s, 3, (int) Math.round(Math.random() * 4 + 7), 2);
        three = new ExecutionThread(3, s, 6, (int) Math.round(Math.random() * 5 + 7), 2);
        one.start();
        two.start();
        three.start();
    }
}
