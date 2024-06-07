package L4_App_2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Lock l1 = new ReentrantLock();
        Lock l2 = new ReentrantLock();
        new ExecutionThread(l1, l2,2,4,4,6, 4).start();
        new ExecutionThread(l2, l1,3,5,5,7, 5).start();
    }
}
