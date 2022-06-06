package GameObjects.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Character extends JLabel {
    AnimatedImage animImg;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    public int angle = 0;

    public Character(int x, int y, int width, int height){
        super();
        animImg = new AnimatedImage("anim/", 5);
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.setBounds(xPosition, yPosition, width*2, height*2);
    }
    public void rotate(int angle){
        this.angle += Math.toRadians(angle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.removeAll();
        this.updateUI();
        AffineTransform at = g2.getTransform();
        at.rotate(Math.toRadians(this.angle), width, height);
        g2.setTransform(at);
        g2.drawImage(animImg.img, this.width/2, this.height/2, this.width, this.height, this);
        g2.dispose();
    }
}
