package App_1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {

    private static final Semaphore semaphore1 = new Semaphore(1);
    private static final Semaphore semaphore2 = new Semaphore(1);

    private static void produce(int min, int max) {
        int k = (int)(Math.round(Math.random()*(max-min)+min)*500);
        for (int i = 0; i < k; i++) {
            i++;
            i--;
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);

        Runnable thread1 = () -> {
            try {
                while (true) {
                    System.out.println("Thread-1 - P1");
                    produce(2, 4);
                    System.out.println("Thread-1 need semaphore1");
                    semaphore1.acquire(1);
                    System.out.println("Thread-1 - P4");
                    produce(4, 6);
                    System.out.println("Thread-1 need semaphore2");
                    semaphore1.release(); // moved here to avoid deadlock

                    semaphore2.acquire(1);
                    System.out.println("Thread-1 - P6");
                    Thread.sleep(4);

                    semaphore2.release();
                    System.out.println("Thread-1 - P7");
                    barrier.await();
                }

            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
        };

        Runnable thread2 = () -> {
            try {
                while(true) {
                    System.out.println("Thread-2 - P2");
                    produce(3, 5);
                    System.out.println("Thread-2 need semaphore2");
                    semaphore2.acquire(1);
                    System.out.println("Thread-2 - P3");
                    produce(5, 7);
                    System.out.println("Thread-2 need semaphore1");
                    semaphore1.acquire(1);
                    System.out.println("Thread-2 - P5");
                    Thread.sleep(5);
                    semaphore1.release();
                    semaphore2.release();
                    System.out.println("Thread-2 - P8");
                    barrier.await();
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        };

        Thread t1 = new Thread(thread1,"Thread-1");
        Thread t2 = new Thread(thread2,"Thread-2");

        t1.start();
        t2.start();

    }
}
