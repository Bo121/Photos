package cn.tx.snow.demo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyImage extends JPanel{

    // Define a member image object
    BufferedImage bgImage;

    public static void main(String[] args) {
        // Create a window
        JFrame frame = new JFrame();
        // Set up the size of the window
        frame.setSize(1000, 700);
        // Set up the title
        frame.setTitle("Digital Album");
        // Display the window in the middle 
        frame.setLocationRelativeTo(null);
        // Turn off the window, and stop the JVM
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.EXIT_ON_CLOSE = 3, 所以括号内可以为3

        // Create a panel object
        MyImage myImage = new MyImage();
        // Place the panel object on the window.
        frame.add(myImage);
        // Call the method to initialize the image
        myImage.initImgs();

        // Write a method that continuously changes 'ff', making the image transition from light to dark, and start a new thread
        myImage.begin();

        // Make the window visible
        frame.setVisible(true);
    }

    // Image display ratio value
    float ff = 0f;

    // Define the index value of the array
    int num = 0;

    /**
     * Change the value of ff continuously 
     */
    public void begin() {
        // Start the thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Change the value of ff continuously 
                while (true) {
                    // Obtain the images from the array
                    bgImage = images[num];
                    // increment the num
                    num++;
                    // if num is equal to 4
                    if (num == 4) {
                        num = 0;
                    }
                    while (true) {
                        if (ff < 100f) {
                            ff += 2f;
                            repaint();
                        } else {
                            ff = 0f;
                            break;
                        }

                        // sleep
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    // Graphic g draw the images
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // What to draw
        if(bgImage != null) {
            // Add a fade-in effect
            graphics2D.setComposite(AlphaComposite.SrcOver.derive(ff / 100f));
            // Draw the image onto the window
            graphics2D.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);
        }
    }

    // Define an array of image type
    BufferedImage[] images = new BufferedImage[4];

    /**
     *  Load some pre-prepared images
     */
    public void initImgs() {
        try {
            for (int i = 1; i <= 4; i++) {
                // Load an image for each iteration
                BufferedImage image = ImageIO.read(MyImage.class.getResource("/images/bg" + i + ".png"));
                // Every time an image object is read, store it in the array
                images[i-1] = image;
            }

            // assign a value to the member variable
            bgImage = images[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
