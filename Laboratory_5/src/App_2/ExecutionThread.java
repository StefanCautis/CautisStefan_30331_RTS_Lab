package App_2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThread extends Thread {
    ReentrantLock lock1;
    ReentrantLock lock2;
    CountDownLatch latch;
    String name;

    ExecutionThread(ReentrantLock lock1, ReentrantLock lock2, CountDownLatch latch, String name){
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.latch = latch;
        this.name = name;
    }

    public static void produce(int min, int max){
        int k = (int)(Math.round(Math.random()*(max-min)+min)*500);
        for(int i = 0; i < k; i++){
            i++;
            i--;
        }
    }

    @Override
    public void run(){
        try {
            if(name.equals("Thread-1")){
                System.out.println("Thread-1  has started");
                System.out.println("Thread-1 - P1");
                System.out.println("Thread-1 need lock1");
                lock1.lock();
                System.out.println("Thread-1 - P4");
                produce(2,4);
                sleep(4);
                lock1.unlock();
                System.out.println("Thread-1 - P6");
                latch.countDown();
            }
            else if(name.equals("Thread-2")){
                System.out.println("Thread-2 has started");
                System.out.println("Thread-2 - P11");
                System.out.println("Thread-2 need lock1 and lock2");
                lock1.lock();
                lock2.lock();
                System.out.println("Thread-2 - P12");
                produce(3,6);
                sleep(3);
                lock1.unlock();
                lock2.unlock();
                System.out.println("Thread-2 - P13");
                latch.countDown();
            }
            else if(name.equals("Thread-3")){
                System.out.println("Thread-3 has started");
                System.out.println("Thread3 - P2");
                System.out.println("Thread-2 need lock2");
                lock2.lock();
                System.out.println("Thread-3 - P3");
                produce(2,5);
                sleep(5);
                lock2.unlock();
                System.out.println("Thread-3 - P5");
                latch.countDown();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

