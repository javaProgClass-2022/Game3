package game;
import java.awt.Rectangle;

class Wall extends Rectangle{
    //int x,y,width,hight;	//position
    boolean v = true;
    Wall(int x, int y, boolean vertical){
        this.x = x;
        this.y = y;
        this.v = vertical;
        if(vertical) {
            this.width = 3;
            this.height = 100;
        } 
        else {
            this.width = 100;
            this.height = 3;
        }
    }
}