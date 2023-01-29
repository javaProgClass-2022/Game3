package game;
//this will create the tank objects
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
class Tank extends Rectangle{
    BufferedImage img;
    BufferedImage img2;
    int angle;
    int speed;
    double xx; //position
    double yy;
    Tank(int x, int y){
        this.xx = x;
        this.yy = y;
        this.angle = 0;
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 30;
        this.angle = 90;
        this.speed = 5;
        img = Walls.loadImage("src/game/tank2.png");
        img2 = Walls.loadImage("src/game/tank1.png");

    }	
}
