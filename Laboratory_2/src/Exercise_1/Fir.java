package Exercise_1;

import java.util.Observable;

// The "Fir" class can also be the "Model" characteristic.
public class Fir extends Observable implements Runnable {
    int id;
    Window win;
    int processorLoad;
    Thread thread;
    public Fir(int id, Window win, int processorLoad) {
        this.id = id;
        this.win = win;
        this.processorLoad = processorLoad;
    }

    public void start(int priority){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
            thread.setPriority(priority);
        }
    }
    @Override
    public void run() {
        int c = 0;
        while(c < 1000) {
            for(int j = 0; j < this.processorLoad; j++) {
                j++;j--;

                // These methods need to be added in order to
                // mark the change in the bar.
                this.setChanged();
                this.notifyObservers();
            }
            c++;
            this.win.setProgressValue(id, c);
        }
    }
}