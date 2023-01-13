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


public class BounceOffRectangle implements ActionListener {

	private class Ball extends Rectangle {
		int vx, vy;	//speed
		Color color = Color.BLUE;
		
		Ball(int x, int y, int vx, int vy){
			this.x = x;
			this.y = y;
			this.vx = vx; 
			this.vy = vy;
			height = width = 40;			
		}
	}

	
	public static void main(String[] args) {
		//using this makes animation more reliable
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BounceOffRectangle(); 
			}
		});					
	}

	//global variables and constants
	DrawingPanel panel;
	
	static final int PANW = 800;
	static final int PANH = 600;
	
	Ball ball = new Ball(200,300,-1,-1);
	Rectangle box = new Rectangle (500,300,200,10);
	
	Timer animationTimer = new Timer(3, this);
	

	BounceOffRectangle() {
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
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);			
			
			g.setColor(Color.RED);						
			g.fillRect(box.x, box.y, box.width, box.height);				     

			g.setColor(ball.color);			
			g.fillOval(ball.x, ball.y, ball.width, ball.height);			
		}
	}	


	void moveBall() {
		ball.x += ball.vx;
		ball.y += ball.vy;

		
		if (ball.x < 0 && ball.vx < 0) {
			ball.vx *= -1;
		}  

		if (ball.y < 0 && ball.vy < 0) { 
			ball.vy *= -1;// bounce off the top if it's moving up
			//ball.y -= ball.vy; // undo the last move  <<< this may not be necessary			
		}

		if (ball.x + ball.width > PANW  && ball.vx > 0) { //bounce off bottom
			ball.vx *= -1;						
		}
		if (ball.y + ball.height > PANH  && ball.vy > 0) { //bounce off bottom
			ball.vy *= -1;						
		}
	}
	
	void collideBall() {
		//this assumes that it's a thin horizontal box
		if (ball.intersects(box)) {
			ball.y -= ball.vy; // undo the last move
			ball.vy *= -1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {			
		moveBall();
		collideBall();
		panel.repaint();
	}	
}
