package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankT extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private int xPos = 50;
    private int yPos = 50;
    private int speed = 5;
    private double angle = 0;

    public TankT() {
        setPreferredSize(new Dimension(400, 400));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        AffineTransform oldTransform = g2d.getTransform(); // Save the current transformation

        g2d.rotate(angle, xPos + 25, yPos + 25); // Rotate the rectangle around its center
        g.fillRect(xPos, yPos, 50, 50);  // Draw the rectangle at the current x and y position
        g2d.setTransform(oldTransform);   // Restore the old transformation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // Get the key that was pressed
        if (key == KeyEvent.VK_W) {  // Check if the W key was pressed
            // Calculate the new x and y positions based on the current angle
            double newX = xPos + speed * Math.cos(angle);
            double newY = yPos + speed * Math.sin(angle);

            // Update the x and y positions
            xPos = (int) newX;
            yPos = (int) newY;
        }

        if (key == KeyEvent.VK_S) {    // Check if the S key was pressed

            // Calculate the new x and y positions based on the current angle
            double newX = xPos - speed * Math.cos(angle);
            double newY = yPos - speed * Math.sin(angle);

            // Update the x and y positions
            xPos = (int) newX;
            yPos = (int) newY;
        }

        if (key == KeyEvent.VK_A) {    // Check if the A key was pressed

            // Decrease the angle of the rectangle by a small amount
        	angle -= 0.1;
        }
        
        if (key == KeyEvent.VK_D) {     // Check if the D key was pressed

            // Increase the angle of the rectangle by a small amount
            angle += 0.1;
        }
        repaint();   // Repaint the panel to update the position and rotation of the rectangle

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Rectangle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TankT panel = new TankT();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}