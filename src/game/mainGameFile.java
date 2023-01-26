package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.lang.Math;

public class Walls extends JFrame implements KeyListener {
	static final int PANW = 500;
	static final int PANH = 400;
	static final int SLEEPTIME = 10;	//in milliseconds
	static final int TIMERSPEED = 10;
	static final int delayTime = 50; 
	static int blueScore = 0;
	static int redScore = 0;
	static String blueScoreSTR = "0";
	static String redScoreSTR = "0";
	int bullets = 30; // bullet counter
	

	Timer animationTimer = new Timer(4, new Timer1());
	int actualTime1 = 0;
	int actualTime2 = 0;


	DrawingPanel panel;
	boolean isPlaying = true;

	class Timer1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			actualTime1++;
			actualTime2++;
			panel.repaint();
		}
	}

	
	Tank tank1 = new Tank(50,60);
	Tank tank2 = new Tank(400,300);


	void createGUI() {
		JFrame window = new JFrame("Tank Trouble");

		window.setSize(PANW, PANH);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);

		panel = new DrawingPanel();
		panel.setBackground(Color.WHITE);
		panel.addKeyListener(this);
		panel.setFocusable(true);
		panel.requestFocus();
		

		window.add(panel);
		window.setVisible(true);
	}

	boolean checkTankintersect(Tank tank){
		for(Wall w : walls){
			if (w.intersects(tank)){
				return true;
			}
		}
		return false;
	}

	// With this method, two keys can be down at once allowing smooth diagonal movement
	void moveTank() {
		if (isKeyDown(KeyEvent.VK_A)) tank1.angle-=5;		
		if (isKeyDown(KeyEvent.VK_D)) tank1.angle+=5;
		if (isKeyDown(KeyEvent.VK_W)){
			if(checkTankintersect(tank1)){
				tank1.yy -=Math.cos(Math.toRadians(tank1.angle))*1.5;
				tank1.xx +=Math.sin(Math.toRadians(tank1.angle))*1.5;
			}else{
				tank1.yy += Math.cos(Math.toRadians(tank1.angle))*1.5;
				tank1.xx -= Math.sin(Math.toRadians(tank1.angle))*1.5;
			}
		}
		if (isKeyDown(KeyEvent.VK_S)) {
			if(checkTankintersect(tank1)){
				tank1.yy +=Math.cos(Math.toRadians(tank1.angle))*1.5;
				tank1.xx -=Math.sin(Math.toRadians(tank1.angle))*1.5;
			}else{
				tank1.yy -= Math.cos(Math.toRadians(tank1.angle))*1.5;
				tank1.xx += Math.sin(Math.toRadians(tank1.angle))*1.5;
			}
		}
		if (isKeyDown(KeyEvent.VK_C) && actualTime1 > delayTime) {
			fire(tank1.x+tank1.width/2, tank1.y+tank1.height/2, tank1.angle+90, true);
			actualTime1 = 0;
		}

		
		if (isKeyDown(KeyEvent.VK_LEFT)) tank2.angle-=5;		
		if (isKeyDown(KeyEvent.VK_RIGHT)) tank2.angle+=5;
		if (isKeyDown(KeyEvent.VK_UP)){
			if(checkTankintersect(tank2)){
				tank2.yy -=Math.cos(Math.toRadians(tank2.angle))*1.5;
				tank2.xx +=Math.sin(Math.toRadians(tank2.angle))*1.5;
			}else{
				tank2.yy += Math.cos(Math.toRadians(tank2.angle))*1.5;
				tank2.xx -= Math.sin(Math.toRadians(tank2.angle))*1.5;
			}
		}
		if (isKeyDown(KeyEvent.VK_DOWN)) {
			if(checkTankintersect(tank2)){
				tank2.yy +=Math.cos(Math.toRadians(tank2.angle))*1.5;
				tank2.xx -=Math.sin(Math.toRadians(tank2.angle))*1.5;
			}else{
				tank2.yy -= Math.cos(Math.toRadians(tank2.angle))*1.5;
				tank2.xx += Math.sin(Math.toRadians(tank2.angle))*1.5;
			}
		}
		if (isKeyDown(KeyEvent.VK_SPACE) && actualTime2 > delayTime) {
			fire(tank2.x+tank2.width/2, tank2.y+tank2.height/2, tank2.angle+90, false);
			actualTime2 = 0;
		}

		tank1.x = (int)tank1.xx;
		tank1.y = (int)tank1.yy;
		tank2.x = (int)tank2.xx;
		tank2.y = (int)tank2.yy;
		panel.repaint();		
	}
	
	ArrayList<Wall> walls = new ArrayList<Wall>();
	
    ArrayList<Ball> bulletCounter = new ArrayList<Ball>();

    
    Walls() throws InterruptedException { // this exception is required for Thread.sleep()
		createGUI();
		animationTimer.start();
		createMap();

		for (int i = 0;i<300;i++){  // creates 30 bullets for player 1 off screen
			bulletCounter.add(new Ball(true));
		}

		for (int i = 0;i<300;i++){  // creates 30 bullets for player 2 off screen
			bulletCounter.add(new Ball(false));
		}

		while(isPlaying) {
			moveTank();
			hitTank();
			Thread.sleep(SLEEPTIME);
		}
	}

	public void createMap() { //make the walls and add to walls arraylist
		walls.add(new Wall(0,100, false));
		walls.add(new Wall(0,200, false));
		walls.add(new Wall(0,250, false));
		walls.add(new Wall(130,100, true));
		walls.add(new Wall(500,100, false));
		walls.add(new Wall(350,200, true));
		walls.add(new Wall(500,100, false));
		walls.add(new Wall(400,250, false));
		walls.add(new Wall(200,100, true));
		walls.add(new Wall(250,140, false));
		walls.add(new Wall(200,250, true));
	}

    public void moveAndBounceBall(Ball b, Tank tank1, Tank tank2) {

		for(Wall w : walls){
            if (w.intersects(b) && !b.intersecting){
                if (w.v){
                    b.vx *= -1;
                }
				else{
                    b.vy *= -1;
                }
				b.intersecting = true;
            }
			else{
				b.intersecting = false;
			}
        }

		b.xx += b.vx; 
		b.yy += b.vy;
		b.x= (int)b.xx;
		b.y= (int)b.yy;

		if (b.x + b.size < 0 ||b.x + b.size > PANW) {
			b.vx *= -1; 
		}  
		if (b.y + b.size > PANH  || b.y + b.size < 0) { //bounce off bottom
			b.vy *= -1;
		}  
	}
	
    class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setBackground(Color.LIGHT_GRAY);
			this.setPreferredSize(new Dimension(PANW,PANH));  //remember that the JPanel size is more accurate than JFrame.
			this.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.BLACK);

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.RED);
			g.drawString(blueScoreSTR,400,50);
			g2.setColor(new Color(50, 170, 255));
			g.drawString(redScoreSTR,450,50);
			g2.setColor(Color.BLACK);

			// Save the current transformation
			AffineTransform oldTransform = g2.getTransform();

			// Rotate the rectangle around its center
			g2.rotate(Math.toRadians(tank1.angle), tank1.x + tank1.width/2, tank1.y + tank1.height/2);
            
			if (tank1.img == null) {
				g.fillRect(tank1.x, tank1.y, tank1.width, tank1.height);
			}
			else {
				g.drawImage(tank1.img2, tank1.x, tank1.y, tank1.width, tank1.height, null);

			}

			//g.fillRect(tank1.x, tank1.y, tank1.width, tank1.height);
			g2.setTransform(oldTransform);

			g2.rotate(Math.toRadians(tank2.angle), tank2.x + tank2.width/2, tank2.y + tank2.height/2);

			if (tank1.img == null){
				g.fillRect(tank2.x, tank2.y, tank2.width, tank2.height);
			}
			else{
				g.drawImage(tank2.img, tank2.x, tank2.y, tank2.width, tank2.height, null);

			}
			g2.setTransform(oldTransform);
			
            for(Rectangle w : walls){
                g.fillRect(w.x, w.y, w.width, w.height);
            }
			for(Ball b : bulletCounter){	
				g.fillOval(b.x, b.y, b.width,b.height);	
            	moveAndBounceBall(b, tank1, tank2);
			}
		}
	}

	public void fire(int x, int y, double angle, boolean player1sbullet){
		for(Ball b : bulletCounter){	
			if (b.player1sbullet == player1sbullet && b.vx == 0 && b.vy == 0){
				b.vx = Math.cos(Math.toRadians(angle))*1;
				b.vy = Math.sin(Math.toRadians(angle))*1;
				b.xx=x+b.vx*20;
				b.yy=y+b.vy*20;
				break;
			}
		}
	}
	
	private final int numKeyCodes = 256; /** size of keysDown array **/
	private boolean[] keysDown = new boolean [numKeyCodes];	/** Array of booleans representing characters currently held down **/

	public synchronized boolean isKeyDown(int key) {
		// if ((key >=0) & (key < numKeyCodes)) 
		return keysDown[key];
		// return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode ();
		keysDown[key] = true;

		switch (key) {
		case KeyEvent.VK_A:
		case KeyEvent.VK_D:
		case KeyEvent.VK_W:
		case KeyEvent.VK_S:
		case KeyEvent.VK_C:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_SPACE:
		break;
		}
		panel.repaint();
	}

	void hitTank(){
		for(Ball bullet: bulletCounter){
			if(bullet.intersects(tank1)){
				System.out.println("hit");
				bullet.xx=bullet.yy=-10;
				bullet.vx=bullet.vy=0;
				bullet.vx=bullet.vy=0;
				for(Ball b : bulletCounter){
				b.xx=b.yy=-10;
				b.vx=b.vy=0;
				}
				tank1.xx = 60;
				tank1.yy = 50;
				tank2.xx = 400;
				tank2.yy = 300;
				blueScore++;
				blueScoreSTR = "" + blueScore;
			}
			if(bullet.intersects(tank2)){
				System.out.println("2");
				bullet.xx=bullet.yy=-10;
				bullet.vx=bullet.vy=0;
				for(Ball b : bulletCounter){
				b.xx=b.yy=-10;
				b.vx=b.vy=0;
				}
				tank1.xx = 60;
				tank1.yy = 50;
				tank2.xx = 400;
				tank2.yy = 300;
				redScore++;
				redScoreSTR = "" + redScore;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) throws InterruptedException {
		new Walls();
	}

	static BufferedImage loadImage(String filename) {
		BufferedImage img = null;			
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, "An image failed to load: " + filename , "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return img;
	}
}
