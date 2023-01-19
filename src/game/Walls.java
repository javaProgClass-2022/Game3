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

import java.awt.geom.AffineTransform;

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

public class TankGame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TankGame(); 
			}
		});		
    }

    final static int PANW = 500;
	final static int PANH = 400;
	final static int TIMERSPEED = 10;

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
		double vx, vy = 0;	//speed
		int size = 5;
		boolean intersecting;
		boolean player1sbullet;

		Ball( boolean player1sbullet){
			this.x = -10;
			this.y = -10;
            this.width = size;
            this.height = size;
			this.vx = vx; //move this many pixels each time
			this.vy = vy;
			this.intersecting = false;
			this.player1sbullet = player1sbullet;
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
	
	class Tank extends Rectangle{

		int angle;
		Tank(int x, int y){
			this.x = x;
			this.y = y;
			this.width = 20;
			this.height = 30;
			this.angle = 90;
		}
		
	}
	
    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Ball> bullets = new ArrayList<Ball>();
	Tank tank1 = new Tank(100,100);
	Tank tank2 = new Tank(200,100);

    TankGame() {
		createAndShowGUI();
		startTimer();
		createMap();

		for (int i = 0;i<30;i++){  //creats 30 bullets for player 1 off screen
			bullets.add(new Ball(true));
		}

		for (int i = 0;i<30;i++){  //creats 30 bullets for player 2 off screen
			bullets.add(new Ball(false));
		}

		fire(100,100,30,true);
	}

	public void createMap(){
		walls.add( new Wall(200,200, true) );
        walls.add( new Wall(250,200, false) );
	}

    public void moveAndBounceBall(Ball b) {

		for(Wall w : walls){
            if (w.intersects(b) && !b.intersecting){
                if (w.v){
                    b.vx *= -1;
                }else{
                    b.vy *= -1;
                }
				b.intersecting = true;
            }else{
				b.intersecting = false;
			}
        }

		b.x += b.vx; 
		b.y += b.vy;

		if (b.x + b.size < 0 ||b.x + b.size > PANW) {
			b.vx *= -1; 
		}  

		// if (b.y < 0) { 
		// 	b.vy *= -1;// bounce off the top if it's moving up
		// 	//b.y -= b.vy; // undo the last move  <<< this may not be necessary
		// }

		if (b.y + b.size > PANH  || b.y + b.size < 0) { //bounce off bottom
			b.vy *= -1;
		}
        
	}

	public void moveTank(){

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
			


			// Save the current transformation
			AffineTransform oldTransform = g2.getTransform();

			// Rotate the rectangle around its center
			g2.rotate(tank1.angle, tank1.x + tank1.width, tank1.y + tank1.height);
            g.fillRect(tank1.x, tank1.y, tank1.width, tank1.height);
			g2.setTransform(oldTransform);

			g2.rotate(tank1.angle, tank1.x + tank1.width, tank1.y + tank1.height);
			g.fillRect(tank2.x, tank2.y, tank2.width, tank2.height);
			g2.setTransform(oldTransform);
			moveTank();
			
            for(Rectangle w : walls){
                g.fillRect(w.x, w.y, w.width, w.height);
            }
			for(Ball b : bullets){	
				g.fillOval(b.x, b.y, b.width,b.height);	
            	moveAndBounceBall(b);
			}
		}
	}

	public void fire(int x, int y, int angle, boolean player1sbullet){
		for(Ball b : bullets){	
			if (b.player1sbullet==player1sbullet && b.vx==0 && b.vy==0){
				b.x=x;
				b.y=y;
				b.vx = Math.cos(Math.toRadians(angle))*5;
				b.vy = Math.sin(Math.toRadians(angle))*5;
				
				System.out.println(Double.toString(b.vx));
				break;
			}
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        drPanel.repaint();
        
    }	

    
}
