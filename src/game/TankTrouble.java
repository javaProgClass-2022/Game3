package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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
	
	private class Point {
		int x, y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	

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

	static final int PANW = 700;
	static final int PANH = 700;
	static final int squareW = PANW/7;		
	static final int squareH = PANH/7;

	Ball ball = new Ball(200, 300, -1, -1, Color.yellow);
	Square grid [][]= new Square[7][7];
	
	Point points [][] = new Point[8][8];
	

	Timer animationTimer = new Timer(1, this); //delay
	double angle = 0.0;

	TankTrouble() {
		JFrame window = new JFrame("Tank Trouble");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawingPanel();
		window.add(panel);
		window.pack();  // because JPanel will set the size
		window.setLocationRelativeTo(null);
		window.setResizable(false);
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

			g.setColor(ball.color);			
			g.fillOval(ball.x, ball.y, ball.size, ball.size);
			g.setColor(Color.GRAY);

			g.drawLine(PANW/2, PANH/2 , ball.x + ball.size/2, ball.y + ball.size/2);
			addObjects(g2);
			moveAndBounceBall(ball, grid, g2);		
		}
	}	
	
	public void addPoints(Graphics2D gs) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				points[i][j] = new Point(100*j, 100*i);
				gs.setColor(new Color(0,0,0,50));
				gs.fillRect(squareW*((7*i+j)%7), squareH*((7*i+j)/7), squareW, squareH);
				gs.drawRect(squareW*((7*i+j)%7), squareH*((7*i+j)/7), squareW, squareH);
			}
		}
	}

	public void addObjects(Graphics2D gs) {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				grid[i][j] = new Square(squareW*((7*i+j)%7), squareW*((7*i+j)%7) + squareW, squareH*((7*i+j)/7), squareH*((7*i+j)/7) + squareH, new Color(0,0,0,50)); // x1, x2, y1, y2, color
				gs.setColor(new Color(0,0,0,50));
				gs.fillRect(squareW*((7*i+j)%7), squareH*((7*i+j)/7), squareW, squareH);
				gs.drawRect(squareW*((7*i+j)%7), squareH*((7*i+j)/7), squareW, squareH);
			}
		}
	}

	public void moveAndBounceBall(Ball b, Square[][] s, Graphics2D g3) {
		b.x += b.vx;
		b.y += b.vy;

		if (b.x < 0 && b.vx < 0) { // bounce of left if it's moving right
			b.vx *= -1;
		} 

		if (b.x + b.size > PANW && b.vx > 0) { // bounce off right if it's moving left
			b.vx *= -1;
		} 

		if (b.y < 0 && b.vy < 0) { // bounce off the top if it's moving up
			b.vy *= -1;
		}

		if (b.y + b.size > PANH  && b.vy > 0) { // bounce off bottom
			b.vy *= -1;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.1;
		panel.repaint();
	}	
}