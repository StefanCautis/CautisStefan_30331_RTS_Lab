package App_2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        final ReentrantLock lock1 = new ReentrantLock();
        final ReentrantLock lock2 = new ReentrantLock();


        while(true) {
            CountDownLatch latch = new CountDownLatch(3);

            ExecutionThread t1 = new ExecutionThread(lock1, lock2, latch, "Thread-1");
            ExecutionThread t2 = new ExecutionThread(lock1, lock2, latch, "Thread-2");
            ExecutionThread t3 = new ExecutionThread(lock1, lock2, latch, "Thread-3");

            try {
                t1.start();
                t2.start();
                t3.start();
                latch.await();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
