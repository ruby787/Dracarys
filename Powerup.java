import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.*;
class Powerup{
    int width;
    int length;
    BufferedImage img;// image of the powerup when printed
    String use;// sets what kind of powerup it is
    Pair velocity;
    Pair position;
    boolean go;// tell the saucer to start going
    boolean used;// sets whether the power up has been used or not
    

    public Powerup(int x, int y, BufferedImage i, String u, boolean g) {//create powerup object
	width = 20;
	length = 20;
	img = i;
	use = u;
	position = new Pair (x,y);
	velocity = new Pair (0.0, 100.0);
	go = g;
	used = false;
	
    }
    public void draw(Graphics g){// draws image of the power up depending on the type
	g.drawImage(img,(int)position.x,(int)position.y,null);

    }
    public void update(World w, double time){// update the powerup object position and calls absorb which checks contact with saucer
	position = position.add(velocity.times(time));
	absorb(w);
    }
    public void absorb (World w){
	double a1 = 0;
	double a2 = 0;
	double a3 = 0;
	double a4 = 0;
	a1 = w.saucer.position.x;
	a2 = w.saucer.position.y;
	a3 = a1 + w.saucer.width;
	a4 = a2 + w.saucer.length;
	//if contact with saucer
	if(position.y + length >= a2 - 1.0 && position.y <= a4 + 1.0 && position.x <= a3 + 1.0 && position.x + width >= a1) {
	    go = false; // stop the powerups movement
	    used = true; // tell that the power up has been used
	    w.Powerups(use);// Send the type of powerup to the powerup method in world
	    w.play3();// plays power up music when broken
	    position = new Pair (5000.0,5000.0);  
	}
    }
    

}
