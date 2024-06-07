package App_3;

import java.util.concurrent.CountDownLatch;

public class Main {
    private static final CountDownLatch latch = new CountDownLatch(3);
    private static final Integer[] monitor = {0,1};
    public static final void produce(int min, int max) {
        int k = (int)(Math.round(Math.random()*(max-min)+min)*500);
        for(int i = 0; i < k; i++){
            i++;
            i--;
        }
    }
    public static void main(String[] args) {
        Runnable thread1 = () -> {
            try{

                System.out.println("Thread-1 has started");
                System.out.println("Thread-1 - P0");
                Thread.sleep(7);
                System.out.println("Thread-1 - P1");
                produce(2,3);
                synchronized (monitor[0]) {
                    synchronized (monitor[1]) {
                        monitor[0].notify();
                        monitor[1].notify();
                    }
                }
                System.out.println("Thread-1 - P2");
                latch.countDown();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        };

        Runnable thread2 = () -> {
            try {

                System.out.println("Thread-2 has started");
                System.out.println("Thread-2 - P3");
                synchronized (monitor[0]) {
                    monitor[0].wait();
                    Thread.sleep(3);
                    System.out.println("Thread-2 - P4");
                    produce(3,5);
                    System.out.println("Thread-2 - P5");
                    latch.countDown();
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        };

        Runnable thread3 = () -> {
            try{

                System.out.println("Thread-3 has started");
                System.out.println("Thread3 - P7");
                synchronized (monitor[1]){
                    monitor[1].wait();
                    Thread.sleep(5);
                    System.out.println("Thread-3 - P8");
                    produce(4,6);
                    System.out.println("Thread-3 - P9");
                    latch.countDown();
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        };

        Thread t1 = new Thread(thread1,"Thread-1");
        Thread t2 = new Thread(thread2,"Thread-2");
        Thread t3 = new Thread(thread3, "Thread-3");

        try {
            t1.start();
            t2.start();
            t3.start();
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
