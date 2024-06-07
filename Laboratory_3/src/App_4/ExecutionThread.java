package App_4;

public class ExecutionThread extends Thread {
    Integer[] monitors;
    int sleep, activity_max, activity_min;
    Thread t;

    ExecutionThread(Integer[] monitors, int sleep, int activity_max, int activity_min, Thread t) {
        this.monitors = monitors;
        this.sleep = sleep;
        this.activity_max = activity_max;
        this.activity_min = activity_min;
        this.t = t;
    }
    public void run() {
        switch (this.getName()){
            case "Thread-0":
                System.out.println(this.getName() + "STATE 1");
                try {
                    Thread.sleep(this.sleep * 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName() + "STATE 2");
                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                System.out.println(this.getName() + "STATE 3");

                synchronized (monitors[0]) {
                    synchronized (monitors[1]) {
                        monitors[0].notify();
                        monitors[1].notify();
                    }
                }

                System.out.println(this.getName() + "STATE 4");

            case "Thread-1":
                System.out.println(this.getName() + "STATE 1");
                synchronized (monitors[0]){
                    try {
                        monitors[0].wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(this.getName() + "STATE 2");
                    int k1 = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                    for (int i = 0; i < k1 * 100000; i++) {
                        i++;
                        i--;
                    }
                    System.out.println(this.getName() + "STATE 3");

                    if(this!=null) {
                        try {
                            this.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            case "Thread-2":
                synchronized (monitors[1]){
                    {System.out.println(this.getName() + "STATE 1");
                        try {
                            monitors[1].wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(this.getName() + "STATE 2");
                    int k1 = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                    for (int i = 0; i < k1 * 100000; i++) {
                        i++;
                        i--;
                    }

                    System.out.println(this.getName() + " - STATE 3");

                    if(this!=null){
                    }
                    try {
                        this.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

        }


    }

}