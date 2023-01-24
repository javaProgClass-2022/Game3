package Graphics;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// import Graphics.TankGame.Ball;
// import Graphics.TankGame.Tank;
// import Graphics.TankGame.Wall;

public class AnimationAndKeys2 extends JFrame implements KeyListener, ActionListener {
	static final int PANW = 500;
	static final int PANH = 400;
	static final int SLEEPTIME = 10;	//in milliseconds
	static final int TIMERSPEED = 10;


	DrawingPanel panel;
	boolean isPlaying = true;
	
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

		Ball(boolean player1sbullet){
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
			if(vertical) {
                this.width = 2;
                this.height = 100;
            } 
			else {
                this.width = 100;
                this.height = 2;
            }
		}
	}

	class Tank extends Rectangle{
		int angle;
		int speed;
		double xx = 100;
		double yy = 200;
		Tank(int x, int y){
			this.angle = 90;
			this.x = x;
			this.y = y;
			this.width = 20;
			this.height = 30;
			this.angle = 90;
			this.speed = 5;
		}	
	}
	Tank tank1 = new Tank(100,100);
	Tank tank2 = new Tank(200,200);
	Rectangle ship = new Rectangle(tank1.x, tank1.y, tank1.width,tank1.height);

	int bullet = 30; // bullet counter

	void createGUI() {
		JFrame window = new JFrame("Tank Trouble");

		window.setSize(PANW, PANH);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);

		panel = new DrawingPanel();
		panel.setBackground(Color.WHITE);
		panel.addKeyListener(this);
		panel.setFocusable(true);
		panel.requestFocus();

		window.add(panel);
		window.setVisible(true);
	}
	
	// With this method, two keys can be down at once allowing smooth diagonal movement
	void moveTank() {
		if (isKeyDown(KeyEvent.VK_A)) tank1.angle-=5;		
		if (isKeyDown(KeyEvent.VK_D)) tank1.angle+=5;
		if (isKeyDown(KeyEvent.VK_W)){
			tank1.yy +=Math.cos(Math.toRadians(tank1.angle))*2;
			tank1.xx -=Math.sin(Math.toRadians(tank1.angle))*2;
		}
		if (isKeyDown(KeyEvent.VK_S)) {
			tank1.yy -=Math.cos(Math.toRadians(tank1.angle))*2;
			tank1.xx +=Math.sin(Math.toRadians(tank1.angle))*2;
		}
		if (isKeyDown(KeyEvent.VK_C)) fire(tank1.x+tank1.width/2, tank1.y+tank1.height/2, tank1.angle+90, true);

		
		if (isKeyDown(KeyEvent.VK_LEFT)) tank2.angle-=5;		
		if (isKeyDown(KeyEvent.VK_RIGHT)) tank2.angle+=5;
		if (isKeyDown(KeyEvent.VK_UP)){
			tank2.yy +=Math.cos(Math.toRadians(tank2.angle))*2;
			tank2.xx -=Math.sin(Math.toRadians(tank2.angle))*2;
		}
		if (isKeyDown(KeyEvent.VK_DOWN)) {
			tank2.yy -=Math.cos(Math.toRadians(tank2.angle))*2;
			tank2.xx +=Math.sin(Math.toRadians(tank2.angle))*2;
		}
		if (isKeyDown(KeyEvent.VK_SPACE)) fire(tank2.x+tank2.width/2, tank2.y+tank2.height/2, tank2.angle+90, false);


		tank1.x = (int)tank1.xx;
		tank1.y = (int)tank1.yy;
		tank2.x = (int)tank2.xx;
		tank2.y = (int)tank2.yy;
		panel.repaint();		
	}
	
	ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Ball> bulletCounter = new ArrayList<Ball>();

    
    AnimationAndKeys2() throws InterruptedException { // this exception is required for Thread.sleep()
		createGUI();
		startTimer();
		createMap();

		for (int i = 0;i<300;i++){  // creates 30 bullets for player 1 off screen
			bulletCounter.add(new Ball(true));
		}

		for (int i = 0;i<300;i++){  // creates 30 bullets for player 2 off screen
			bulletCounter.add(new Ball(false));
		}

		while(isPlaying) {
			moveTank();
			Thread.sleep(SLEEPTIME);
		}
	}

	public void createMap(){
		walls.add(new Wall(200,200, true));
        walls.add(new Wall(250,200, false));
	}

    public void moveAndBounceBall(Ball b, Tank tank1, Tank tank2) {

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

			if (w.intersects(tank1)){
                if (w.v){
                    tank1.xx = w.x;
                }else{
                    tank1.yy = w.y;
                }
            }

			if (w.intersects(tank2)){
                if (w.v){
                    tank2.xx = w.x;
                }else{
                    tank2.yy = w.y;
                }
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
			//g.fillRect(tank1.x, tank1.y, ship.width, ship.height);

			//g.drawString("Weapon = " + bulletCounter, 30, 20);	
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.setStroke(new BasicStroke(2));			
			g.drawString("Here is your drawing panel", 100,100);
			
			// Save the current transformation
			AffineTransform oldTransform = g2.getTransform();

			// Rotate the rectangle around its center
			g2.rotate(Math.toRadians(tank1.angle), tank1.x + tank1.width/2, tank1.y + tank1.height/2);
            g.fillRect(tank1.x, tank1.y, tank1.width, tank1.height);
			g2.setTransform(oldTransform);

			g2.rotate(Math.toRadians(tank2.angle), tank2.x + tank2.width/2, tank2.y + tank2.height/2);
            g.fillRect(tank2.x, tank2.y, tank2.width, tank2.height);
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
				b.x=x;
				b.y=y;
				b.vx = Math.cos(Math.toRadians(angle))*5;
				b.vy = Math.sin(Math.toRadians(angle))*5;
				
				System.out.println(Double.toString(b.vx));
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

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        panel.repaint();
    }	  

	public static void main(String[] args) throws InterruptedException {
		new AnimationAndKeys2();
	}
}
