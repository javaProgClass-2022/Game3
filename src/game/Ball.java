package game;
import java.awt.Rectangle;

class Ball extends Rectangle{
    // int x,y;	//position
    double xx, yy = -10;
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
