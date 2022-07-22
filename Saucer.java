import java.awt.Color;
import java.awt.Graphics;
class Saucer{
    int width;
    int length;
    Color color;
    Pair velocity;
    Pair position;
    Pair acceleration;
    
    
    public Saucer(){
	
	position = new Pair(500.0, 730.0);
	velocity = new Pair(0.0,0.0);
	acceleration = new Pair(0.0,0.0);
	color=Color.red;
	width = 120;
	length = 20;
    }	
    public void draw(Graphics g){// draws saucer as a rectangle
	g.setColor(color);
	g.fill3DRect((int)position.x,(int)position.y, width,length,true);
    }
    public void update(World w, double time){// updates saucer position based on acceleration and velocity
	position = position.add(velocity.times(time));
	velocity = velocity.add(acceleration.times(time));
    }
    public void setVelocity(Pair v){// set saucer velocity
	velocity = v;
    }
    public void setAcceleration(Pair a){// set acceleration
	acceleration = a;
    }
    public void setPosition(Pair p){// sets position
	position = p;
    }
    
}
