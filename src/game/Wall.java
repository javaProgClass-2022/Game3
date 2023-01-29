package game;
//this will create the walls
import java.awt.Rectangle;

class Wall extends Rectangle{
    //int x,y,width,hight;	//position
    boolean v; //wether or not it is vertical
    Wall(int x, int y, boolean vertical){
        this.x = x;
        this.y = y;
        this.v = vertical;
        //sets the width and height based on wether or not it is vertical
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
