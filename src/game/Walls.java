package game;
/* Add comments here
 * 
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Rectangle;



import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.MainGame.DrawingPanel;

public class Walls implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Walls(); 
			}
		});		
    }

    final static int PANW = 500;
	final static int PANH = 400;
	final static int TIMERSPEED = 1;

    /***** instance variables (global) *****/
	DrawingPanel drPanel = new DrawingPanel();

    void createAndShowGUI() {
		JFrame frame = new JFrame("Awesome game!");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
		frame.setResizable(false);
		

		frame.add(drPanel);
		frame.pack();
		frame.setLocationRelativeTo( null );		
		frame.setVisible(true);		
	}

    void startTimer() {		
		Timer timer = new Timer(TIMERSPEED, this);
		timer.start();
    }

    class Ball extends Rectangle{
		// int x,y;	//position
		int vx, vy;	//speed
		int size = 10;

		Ball(int x, int y, int vx, int vy){
			this.x = x;
			this.y = y;
            this.width = size;
            this.height = size;
			this.vx = vx; //move this many pixels each time
			this.vy = vy;
		}
	}

    class Wall extends Rectangle{
		//int x,y,width,hight;	//position
        boolean v = true;
		Wall(int x, int y, boolean vertical){
			this.x = x;
			this.y = y;
            this.v = vertical;
			if(vertical){
                this.width = 2;
                this.height = 100;
            } else{
                this.width = 100;
                this.height = 2;
            }

		}
	}
	
	
	
    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Ball> bullets = new ArrayList<Ball>();
    
    Ball b1 = new Ball(200,300,3,2);
    Wall w1 = new Wall(200,200, true);

    Walls() {
		createAndShowGUI();
		startTimer();

        bullets.add(b1);
        walls.add( w1 );
        walls.add( new Wall(200,200, false) );

        // for(Rectangle w : walls){
        //     System.out.println(w.x+", "+w.y+", "+w.width+", "+w.height+", ");
        // }
	}

    public void moveAndBounceBall(Ball b, Graphics g) {
		b.x += b.vx; 
		b.y += b.vy;

		if (b.x + b.size < 0 ||b.x + b.size > PANW) {
			b.vx *= -1; // teleport to the other side
		}  

		if (b.y < 0 && b.vy < 0) { 
			b.vy *= -1;// bounce off the top if it's moving up
			//b.y -= b.vy; // undo the last move  <<< this may not be necessary
		}

		if (b.y + b.size > PANH  || b.y + b.size < 0) { //bounce off bottom
			b.vy *= -1;
			//b.y -= b.vy; // undo the last move
		}


        for(Wall w : walls){
            if (w.intersects(b)){
                if (w.v){
                    b.vx *= -1;
                }else{
                    b.vy *= -1;
                }
            }
        }
	}

    class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setBackground(Color.LIGHT_GRAY);
			this.setPreferredSize(new Dimension(PANW,PANH));  //remember that the JPanel size is more accurate than JFrame.
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.setStroke(new BasicStroke(2));			
			g.drawString("Here is your drawing panel", 100,100);
			
            //g.fillRect(wall1.x, wall1.y, wall1.w, wall1.h);
			g.fillOval(b1.x, b1.y, 10,10);	
            for(Rectangle w : walls){
                g.fillRect(w.x, w.y, w.width, w.height);
            }	
            moveAndBounceBall(b1, g2);
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        drPanel.repaint();
        
    }	

    
}
