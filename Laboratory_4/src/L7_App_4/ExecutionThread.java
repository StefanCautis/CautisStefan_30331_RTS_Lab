package L7_App_4;

import java.util.concurrent.Semaphore;

public class ExecutionThread extends Thread {
    int name, sleep, k, permit;
    Semaphore s;

    ExecutionThread(int n, Semaphore s, int sleep, int k, int permit) {
        this.name = n;
        this.s = s;
        this.sleep = sleep;
        this.k = k;
        this.permit = permit;
    }

    public void run() {
        while (true) {

            System.out.println("Thread"+name+ " State 1");
            try {
                this.s.acquire(this.permit);
                System.out.println("Thread " + name + " took a token from the semaphore");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + name + " State 2");

            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }
            System.out.println("Thread  " + name + " released a token from the semaphore");
            this.s.release(this.permit);
            System.out.println("Thread " + name + " State 3");
            try {
                Thread.sleep(sleep * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + name + " State 4");
        }
    }
}
