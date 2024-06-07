package Exercise_1;

import javax.swing.*;

public class Main extends JFrame {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;
    private static final SquarePanel panel = new SquarePanel(WINDOW_WIDTH, WINDOW_HEIGHT);
    private static final int GAME_ROUNDS = 3;

    public Main() {
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.setVisible(true);
    }

    public void startGame() {

        Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int round = 0; round < GAME_ROUNDS; round++) {

                    panel.startRound();

                    while (!panel.roundFinished()) {

                        panel.moveSquares();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    panel.resetRound();
                }
            }
        });

        gameThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.startGame();
        });
    }
}
