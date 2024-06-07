package L4_App_2;

import java.util.concurrent.locks.Lock;

public class ExecutionThread extends Thread {
    Lock l1, l2;

    int activity_min1, activity_max1, activity_min2, activity_max2, sleep;

    public ExecutionThread(Lock l1, Lock l2, int activity_min1, int activity_max1, int activity_min2, int activity_max2, int sleep) {
        this.l1 = l1;
        this.l2 = l2;
        this.activity_min1 = activity_min1;
        this.activity_max1 = activity_max1;
        this.activity_min2 = activity_min2;
        this.activity_max2 = activity_max2;
    }

    public void run() {
        System.out.println(this.getName() + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activity_max1 - activity_min1) + activity_min1);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        if(l1.tryLock()) {
            try {
                System.out.println(this.getName() + " - STATE 2");
                int t = (int) Math.round(Math.random() * (activity_max2 - activity_min2) + activity_min2);
                for (int i = 0; i < t * 100000; i++) {
                    i++;
                    i--;
                }
                if(l2.tryLock()) {
                    try {
                        System.out.println(this.getName() + " - STATE 3");
                        try {
                            Thread.sleep(sleep * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    finally {
                        l2.unlock();
                    }
                }
            }
            finally {
                l1.unlock();
            }
        }
        System.out.println(this.getName() + " - STATE 4");
    }
}
