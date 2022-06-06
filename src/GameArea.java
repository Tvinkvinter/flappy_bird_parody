import GameObjects.Character.Character;
import GameObjects.Obstacle;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameArea extends JPanel implements Runnable {
    JLabel background = new JLabel(new ImageIcon("background.png"));
    JLabel scorePanel = new JLabel();
    JLabel recordPanel = new JLabel();
    private final int AreaWidth = 800;
    private final int AreaHeight = 600;
    final int numberOfObstacles = 3;
    private final ArrayList<Obstacle> obstacles = new ArrayList<>(numberOfObstacles);
    private int obstacleSpeed = 2;
    int birdSize = 75;
    public Character bird = new Character(100, 100, birdSize, birdSize);
    float gravitationSpeed = 0.4f;
    float birdUpSpeed = 0;
    float birdDeltaAngle = 0;
    private int score = 0;
    private int record = 0;
    private boolean gameover = false;

    public GameArea() {
        super();
        this.setLayout(null);
        this.setBounds(0, 0, AreaWidth, AreaHeight);

        background.setBounds(0, 0, AreaWidth, AreaHeight);
        this.add(background);

        // defining set of obstacles
        for (int i = 0; i < numberOfObstacles; i++) {
            obstacles.add(new Obstacle());
            obstacles.get(i).spawn();
            obstacles.get(i).xPosition += 300 * i;
            this.add(obstacles.get(i), 0);
        }

        this.add(bird, 0);

        scorePanel.setBounds(700, 0, 100, 50);
        scorePanel.setText("<html><font color=white size=5> Score: " + score + "</font></html>");
        this.add(scorePanel, 0);

        recordPanel.setBounds(10, 0, 100, 50);
        recordPanel.setText("<html><font color=white size=5> Record: " + record + "</font></html>");
        this.add(recordPanel, 0);

        this.addKeyListener(new ActionManager());
        new Thread(this).start();
    }

    class ActionManager implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                birdUpSpeed = 7;
                birdDeltaAngle = -6;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    }

    // game manager function
    @Override
    public void run() {
        while (true) {
            // moving obstacles and counting scores
            for (int i = 0; i < numberOfObstacles; i++) {
                obstacles.get(i).xPosition -= obstacleSpeed + score / 10;
                obstacles.get(i).setBounds(obstacles.get(i).xPosition, obstacles.get(i).yPosition, 100, 600);
                if (obstacles.get(i).xPosition < -100) obstacles.get(i).spawn();
                if (bird.xPosition + birdSize / 2 < obstacles.get(i).xPosition + 100 &&
                        bird.xPosition + birdSize / 2 + obstacleSpeed > obstacles.get(i).xPosition + 100) {
                    score++;
                    scorePanel.setText("<html><font color=white size=5> Score: " + score + "</font></html>");
                }
            }

            // bird moving
            birdUpSpeed -= gravitationSpeed;
            bird.yPosition -= birdUpSpeed;

            if(bird.angle + birdDeltaAngle < -60) bird.angle = -60;
            else if(bird.angle + birdDeltaAngle > 60) bird.angle = 60;
            else bird.angle += birdDeltaAngle;
            birdDeltaAngle += 0.3;

            bird.setBounds(bird.xPosition, bird.yPosition, bird.width * 2, bird.height * 2);

            // collide checking
            for (Obstacle obs : obstacles) {
                if (bird.xPosition + birdSize * 2 - birdSize / 2 > obs.xPosition &&
                        bird.xPosition + birdSize / 2 < obs.xPosition + 100 &&
                        (bird.yPosition + birdSize / 2 < obs.upper_obstacle - 20 ||
                                bird.yPosition + birdSize * 2 - birdSize / 2 > obs.lower_obstacle + 20)) {
                    gameover = true;
                    break;
                }
            }

            // if a player loses the game
            if (gameover) {
                bird.yPosition = 100;
                bird.angle = 0;
                birdUpSpeed = 0;
                birdDeltaAngle = 0;
                bird.setBounds(bird.xPosition, bird.yPosition, birdSize, birdSize);
                for (int i = 0; i < numberOfObstacles; i++) {
                    obstacles.get(i).spawn();
                    obstacles.get(i).xPosition += 300 * i;
                    obstacles.get(i).setBounds(obstacles.get(i).xPosition, obstacles.get(i).yPosition, 100, 600);
                }
                if(score > record) record = score;
                score = 0;
                scorePanel.setText("<html><font color=white size=5> Score: " + score + "</font></html>");
                recordPanel.setText("<html><font color=white size=5> Record: " + record + "</font></html>");
                gameover = false;
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}