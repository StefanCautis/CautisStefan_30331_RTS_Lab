package Exercise_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TankGame extends JFrame {
    // Game constants
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int SQUARE_SIZE = 20;
    private static final int TANK_WIDTH = 40;
    private static final int TANK_HEIGHT = 20;
    private static final int SHELL_WIDTH = 5;
    private static final int SHELL_HEIGHT = 10;
    private static final int WIN_CONDITION = 3;

    // Game state
    private int tankPosition = WINDOW_WIDTH / 2;
    private final List<Rectangle> squares = new CopyOnWriteArrayList<>();
    private final List<Rectangle> shells = new CopyOnWriteArrayList<>();
    private final List<Thread> squareThreads = new ArrayList<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicInteger score = new AtomicInteger(0);
    private final AtomicInteger resumesLeft = new AtomicInteger(3);

    public TankGame() {
        setTitle("Tank Game with Shooting");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new TankController());
        setVisible(true);
        setLocationRelativeTo(null);
        resetGame();
    }

    private void resetGame() {
        running.set(true);
        squares.clear();
        shells.clear();
        squareThreads.forEach(Thread::interrupt);
        squareThreads.clear();
        score.set(0);
        tankPosition = WINDOW_WIDTH / 2; // Reset tank position

        for (int i = 0; i < 3; i++) {
            createSquare();
        }

        new Thread(this::gameLoop).start();
    }

    private void createSquare() {
        Rectangle square = new Rectangle((int) (Math.random() * (WINDOW_WIDTH - SQUARE_SIZE)), 0, SQUARE_SIZE, SQUARE_SIZE);
        squares.add(square);
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted() && running.get()) {
                    square.y += 10;
                    if (square.y > WINDOW_HEIGHT) {
                        square.y = 0;
                        square.x = (int) (Math.random() * (WINDOW_WIDTH - SQUARE_SIZE));
                    }
                    Thread.sleep(200);
                    repaint();
                }
            } catch (InterruptedException e) {
                // Thread interrupted, square was shot or game reset
            }
        });
        squareThreads.add(thread);
        thread.start();
    }


    private void gameLoop() {
        while (running.get()) {
            // Move shells
            List<Rectangle> shellsToRemove = new ArrayList<>();
            for (Rectangle shell : shells) {
                shell.y -= 15;
                if (shell.y + SHELL_HEIGHT < 0) {
                    shellsToRemove.add(shell); // Mark off-screen shells for removal
                }
            }
            shells.removeAll(shellsToRemove); // Remove off-screen shells

            // Check for collisions and handle game state
            checkCollisions();

            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    private void checkCollisions() {

        List<Rectangle> shellsToRemove = new ArrayList<>();
        List<Rectangle> squaresToRemove = new ArrayList<>();
        for (Rectangle shell : shells) {
            for (Rectangle square : squares) {
                if (shell.intersects(square)) {
                    score.incrementAndGet(); // Increment score
                    shellsToRemove.add(shell); // Mark the shell for removal
                    squaresToRemove.add(square); // Mark the square for removal

                    // Remove the square's corresponding thread
                    int indexToRemove = squares.indexOf(square);
                    if (indexToRemove != -1 && indexToRemove < squareThreads.size()) {
                        Thread toStop = squareThreads.get(indexToRemove);
                        toStop.interrupt(); // Attempt to stop the thread
                        squareThreads.remove(indexToRemove); // Remove the thread from the list
                    }

                    break; // Break to avoid ConcurrentModificationException
                }
            }
        }
        shells.removeAll(shellsToRemove); // Remove shells that hit squares
        squares.removeAll(squaresToRemove); // Remove squares that were hit

        Rectangle tank = new Rectangle(tankPosition, WINDOW_HEIGHT - TANK_HEIGHT, TANK_WIDTH, TANK_HEIGHT);

        // Check collision between tank and squares
        for (Rectangle square : squares) {
            if (tank.intersects(square)) {
                gameOver();
                return;
            }
        }

        // Check collision between shells and squares
        synchronized (shells) {
            Iterator<Rectangle> shellIterator = shells.iterator();
            while (shellIterator.hasNext()) {
                Rectangle shell = shellIterator.next();
                boolean hit = false;

                Iterator<Rectangle> squareIterator = squares.iterator();
                while (squareIterator.hasNext()) {
                    Rectangle square = squareIterator.next();
                    if (shell.intersects(square)) {
                        score.incrementAndGet(); // Increment score
                        shellIterator.remove(); // Remove the shell
                        squareIterator.remove(); // Remove the square
                        hit = true;
                        break;
                    }
                }

                if (hit) {
                    // Interrupt and remove corresponding square thread
                    int index = squares.indexOf(squares);
                    if (index != -1 && index < squareThreads.size()) {
                        Thread squareThread = squareThreads.get(index);
                        squareThread.interrupt();
                        squareThreads.remove(squareThread);
                    }
                }
            }
        }

        if (score.get() >= WIN_CONDITION) {
            winGame();
        }
    }

    private void winGame() {
        running.set(false);
        squareThreads.forEach(Thread::interrupt);
        squareThreads.clear();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Congrats! You won! Final Score: " + score, "Game Won", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
    }

    private void gameOver() {
        running.set(false);
        squareThreads.forEach(Thread::interrupt);
        squareThreads.clear();

        if (resumesLeft.decrementAndGet() >= 0) {
            SwingUtilities.invokeLater(() -> {
                int response = JOptionPane.showConfirmDialog(this, "You lost! Game over. Score: " + score + "\nWould you like to resume?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "No more resumes left. Exiting. Final Score: " + score);
            System.exit(0);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawTank(g);
        drawSquares(g);
        drawShells(g);
    }

    private void drawTank(Graphics g) {
        g.fillRect(tankPosition, WINDOW_HEIGHT - TANK_HEIGHT, TANK_WIDTH, TANK_HEIGHT);
    }

    private void drawSquares(Graphics g) {
        for (Rectangle square : squares) {
            g.fillRect(square.x, square.y, square.width, square.height);
        }
    }

    private void drawShells(Graphics g) {
        synchronized (shells) {
            for (Rectangle shell : shells) {
                g.fillRect(shell.x, shell.y, SHELL_WIDTH, SHELL_HEIGHT);
            }
        }
    }

    private class TankController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!running.get()) {
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> tankPosition = Math.max(0, tankPosition - 10);
                case KeyEvent.VK_RIGHT -> tankPosition = Math.min(WINDOW_WIDTH - TANK_WIDTH, tankPosition + 10);
                case KeyEvent.VK_SPACE -> shoot();
            }
            repaint();
        }

        private void shoot() {
            if (!running.get()) {
                return;
            }
            Rectangle shell = new Rectangle(tankPosition + TANK_WIDTH / 2 - SHELL_WIDTH / 2, WINDOW_HEIGHT - TANK_HEIGHT - SHELL_HEIGHT, SHELL_WIDTH, SHELL_HEIGHT);
            shells.add(shell);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TankGame::new);
    }
}
