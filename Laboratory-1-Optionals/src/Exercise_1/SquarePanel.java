package Exercise_1;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SquarePanel extends JPanel {
    private final Square[] squares = new Square[3];
    private final int windowWidth;
    private final int windowHeight;
    private static final int SQUARE_SIZE = 50;
    private static final int MIN_SPEED = 5;
    private static final int MAX_SPEED = 10;

    public SquarePanel(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void startRound() {
        for (int i = 0; i < squares.length; i++) {
            int speed = new Random().nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
            squares[i] = new Square(i * SQUARE_SIZE * 2 + SQUARE_SIZE, 0, SQUARE_SIZE, speed);
        }
    }

    public boolean roundFinished() {
        for (Square square : squares) {
            if (square.y < windowHeight - square.size) {
                return false;
            }
        }
        return true;
    }

    public void moveSquares() {
        for (Square square : squares) {
            square.y += square.speed;
        }
        repaint();
    }

    public void resetRound() {
        for (Square square : squares) {
            square.y = 0;
        }
        repaint();
        try {
            Thread.sleep(2000); // Wait a bit before starting the next round
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Square square : squares) {
            g.fillRect(square.x, square.y, square.size, square.size);
        }
    }
}

