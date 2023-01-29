package game;
//this class creates the bullets
import java.awt.Rectangle;

class Ball extends Rectangle{
    double xx, yy = -10; //position
    double vx, vy = 0;	//speed
    int size = 5; //the diameter
    boolean intersecting; //wether or not a bullet is touching a wall
    boolean player1sbullet; //this shows who the bullet belongs to

    Ball(boolean player1sbullet){
        //puts them off screen, not moving
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
