package Exercise_1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

// The Window class can also be the "View" characteristic.

// Change from the "extends Thread" to "implements Runnable".
public class Window extends JFrame implements Observer {
    ArrayList<JProgressBar> bars = new ArrayList<>();

    public Window(int nrThreads){

        super("Window");
        // Set the layout to null which allows us to control the (x, y)
        // coordinates of the components.
        setLayout(null);

        // Set the width and height of the window frame.
        setSize(450,400);

        // Exit when you press "X" button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Center the window frame when it is opened.
        setLocationRelativeTo(null);

        // Prevent the window frame from being resized.
        setResizable(false);

        // Make the window frame visible.
        setVisible(true);

        getContentPane().setBackground(Color.black);

        init(nrThreads);
    }

    private void init(int n){
        for(int i = 0; i < n; i++) {
            JProgressBar pb = new JProgressBar();

            // Returns the progress bar maximum value.
            pb.setMaximum(1000);

            pb.setBounds(50,(i + 1) * 30,350,20);
            this.add(pb);
            pb.setBackground(Color.darkGray);
            pb.setForeground(Color.RED);
            this.bars.add(pb);
        }
    }
    public void setProgressValue(int id, int val){
        // setValue = set initial value.
        bars.get(id).setValue(val);
    }

    @Override
    public void update(Observable o, Object arg) {}
}