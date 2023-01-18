package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankT extends JPanel implements KeyListener {

    static int tankX = 50;
    static int tankY = 50;
    static int speed = 5;
    static double angle = 0;

    public TankT() {
        setPreferredSize(new Dimension(400, 400));
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintTank(Graphics g, Graphics2D g2) {
        g.setColor(Color.RED);
        g2.rotate(angle, tankX + 25, tankY + 25); // Rotate the rectangle around its center
        g.fillRect(tankX, tankY, 50, 50);  // Draw the rectangle at the current x and y position
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // gets the key that was pressed
        if (key == KeyEvent.VK_W) {  // 'W' key was pressed
            // Calculate new x and y positions based on the current angle
            double newX = tankX + speed * Math.cos(angle);
            double newY = tankY + speed * Math.sin(angle);
            
            // Update the x and y positions
            tankX = (int) newX;
            tankY = (int) newY;
        }

        if (key == KeyEvent.VK_S) {    // 'S' key was pressed
            double newX = tankX - speed * Math.cos(angle);
            double newY = tankY - speed * Math.sin(angle);
            
            tankX = (int) newX;
            tankY = (int) newY;
        }

        if (key == KeyEvent.VK_A) {    // 'A' key was pressed

            // Decrease the angle of the rectangle by a small amount
        	angle -= 0.1;
        }
        
        if (key == KeyEvent.VK_D) {     // 'D' key was pressed

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