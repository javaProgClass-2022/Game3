package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.util.ArrayList;


public class TankTrouble implements ActionListener {

	private class Ball {
		int x,y;	//position
		int vx, vy;	//speed
		Color color;
		int size = 40;

		Ball(int x, int y, int vx, int vy, Color color){
			this.x = x;
			this.y = y;
			this.vx = vx; //move this many pixels each time
			this.vy = vy;
			this.color = color;
		}
	}

	private class Square {
		int x1, x2, y1, y2;	//sides of rectangle	
		Color color;

		Square(int x1, int x2, int y1, int y2, Color color) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1; 
			this.y2 = y2;
			this.color = color;
		}
	}

	Square grid [][]= new Square[5][5];

	public static void main(String[] args) {
		//using this makes animation more reliable
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TankTrouble(); 
			}
		});					
	}

	//global variables and constants
	DrawingPanel panel;
	
	static final int PANW = 800;
	static final int PANH = 600;
	
	Ball ball = new Ball(200,300,-1,-1,Color.yellow);
	Rectangle box = new Rectangle (700,350,40,40);
	
	Timer animationTimer = new Timer(1, this); //delay
	double angle = 0.0;

	TankTrouble() {
		JFrame window = new JFrame("Bouncing Ball");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawingPanel();
		window.add(panel);
		window.pack();  // because JPanel will set the size
		window.setLocationRelativeTo(null);
		window.setVisible(true);	

		animationTimer.setInitialDelay(1000); // wait a second before starting
		animationTimer.start();
	}

	public class DrawingPanel extends JPanel {
		DrawingPanel(){
			this.setPreferredSize(new Dimension(PANW, PANH));
			this.setBackground(new Color(222,255,255));
		}

		@Override
		public void paintComponent(Graphics g) {
			// super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			// rotate the background
			g.setColor(Color.RED);			
			g2.rotate(Math.toRadians(angle), PANW/2, PANH/2);			
			g.fillRect(box.x, box.y, box.width, box.height);		
			g2.rotate(Math.toRadians(-angle), PANW/2, PANH/2);	     

			g.setColor(ball.color);			
			g.fillOval(ball.x, ball.y, ball.size, ball.size);
			g.setColor(Color.GRAY);

			g.drawLine(PANW/2, PANH/2 , ball.x + ball.size/2, ball.y + ball.size/2);
			g.drawLine(box.x, box.y , ball.x + ball.size/2, ball.y + ball.size/2);
			addObjects(g2);
			moveAndBounceBall(ball, grid, g2);		
		}
	}	


	int w = PANW/5;		
	int h = PANH/5;

	public void addObjects(Graphics2D gs) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				grid[i][j] = new Square(w*((5*i+j)%5), w*((5*i+j)%5) + w, h*((5*i+j)/5), h*((5*i+j)/5) + h, new Color(0,0,0,50)); // x1, x2, y1, y2, color
				gs.setColor(new Color(0,0,0,50));
				gs.fillRect(w*((5*i+j)%5), h*((5*i+j)/5), w, h);
				gs.drawRect(w*((5*i+j)%5), h*((5*i+j)/5), w, h);
			}
		}
	}


	public void moveAndBounceBall(Ball b, Square[][] s, Graphics2D g3) {
		b.x += b.vx;
		b.y += b.vy;

		if (b.x + b.size < 0) {
			b.x = PANW; // teleport to the other side
		}  

		if (b.y < 0 && b.vy < 0) { 
			b.vy *= -1;// bounce off the top if it's moving up
			//b.y -= b.vy; // undo the last move  <<< this may not be necessary
			b.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		}

		if (b.y + b.size > PANH  && b.vy > 0) { //bounce off bottom
			b.vy *= -1;
			//b.y -= b.vy; // undo the last move
			b.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		}

//		for(int i = 0; i < 5; i++) {
//			for(int j = 0; j < 5; j++) {
//				if(b.x + b.size > s[i][j].x1 && b.x + b.size < s[i][j].x2 && b.y + b.size > s[i][j].y1 && b.x + b.size < s[i][j].y2) {
//					s[i][j].color = Color.green;
//					g3.setColor(Color.green.darker());
//					g3.fillRect(w*((5*i+j)%5), h*((5*i+j)/5), w, h);
//				}
//			}
//		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.1;
		panel.repaint();
	}	
}
