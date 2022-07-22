import java.awt.Color;
import java.awt.Graphics;

class Brick{
    int length;
    int width;
    Color color;
    int points;
    boolean broken;// boolean that see for the broken status of a brick object
    Pair start;
    int startx;
    int starty;
    boolean bonus;//wether that brick has boolean or not
    Powerup pu;//power up value in brick
	    

    public Brick(int l, int w, Color c, int p,int x,int y , boolean broken, boolean b){// create brick object
			length=l;
			width=w;
			color=c;
			points=p;
			broken= this.broken;
			bonus=b;
			startx = x;
			starty = y;
			pu = null;
			
		}
    public void draw(Graphics g){ //draws brick object as a rectangle
		 	g.setColor(this.color);
		 	g.fillRect(startx,starty,width,length);
		 }
    public void brickBroke(){ /// changes broken to true when called and adds points of the brick to the score
		     
	this.broken=true;
	World.score+=this.points;
			
	
    }
    public void update (World w, double t){// runs update and when a brick is a bonus and is broken sets the start value of powerup boolean to true
	if (bonus == true && broken == true)
	    pu.go = true;
    }
		    
	
    
}
