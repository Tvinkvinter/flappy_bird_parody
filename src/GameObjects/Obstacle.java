package GameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle extends JPanel {
    BufferedImage img;
    public int xPosition;
    public int yPosition;
    public int upper_obstacle = 300;
    public int lower_obstacle = upper_obstacle + 200;

    public Obstacle(){
        super();
        this.setOpaque(false);
        try {
            img = ImageIO.read(new File("obstacle.png"));
        } catch (IOException e) {
            img = null;
        }
        this.setBounds(xPosition, yPosition, 100, 600);
    }

    public void spawn()
    {
        xPosition = 800;
        yPosition = 0;
        upper_obstacle = (int) (Math.random() * 301) + 50;
        lower_obstacle = upper_obstacle + 200;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, 0, lower_obstacle, 100, 500, null);
        AffineTransform at = g2.getTransform();
        at.rotate(Math.toRadians(180), 50, (int) (upper_obstacle / 2));
        g2.setTransform(at);
        g2.drawImage(this.img, 0, 0, 100, 500, null);
        g2.dispose();
    }
}
