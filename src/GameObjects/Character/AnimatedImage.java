package GameObjects.Character;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

class AnimatedImage implements Runnable {
    BufferedImage img = null;
    private Raster[] frames;
    private int i; // number of current frame

    AnimatedImage(String path, int count) {
        //setting multiple frames from files
        frames = new Raster[count];
        for (i = 0; i < count; i++) {
            try {
                frames[i] = ImageIO.read(new File(path + (i + 1) + ".png")).getData();
            } catch (Exception e) {
                frames[i] = null;
            }
        }

        // setting initial image
        try {
            img = ImageIO.read(new File(path  + "1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(this).start();

    }

    public void run() {
        while (true) {
            // clearing a previous image
            Graphics2D g = img.createGraphics();
            g.setBackground(new Color(0, 0, 0, 0));
            g.clearRect(0, 0, img.getWidth()+1, img.getHeight());
            g.dispose();

            // setting a new image
            i++;
            i = i % frames.length;
            img.setData(frames[i]);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                break;
            }

        }
    }
}