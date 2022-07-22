import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
class Ball{
    double radius;
    Color color;  
    Pair velocity; 
    Pair position;

    
    public Ball(){// ball object constructor takes no parameters set color, radius, start position, start velocity
	position = new Pair(500.0,500.0);
	velocity = new Pair(-300.0, -250.0);
	color=Color.red;
	radius=10.0;
    }	
    public void draw(Graphics g){// draws a ball object as an oval
	g.setColor(color);
	g.fillOval((int)(position.x), (int)(position.y), (int)(2*radius), (int)(2*radius));
    }
    public void update(World w, double time){//takes in time and a world object then changes position accordingly
	position = position.add(velocity.times(time));
	bounce(w);
    }
    public void setVelocity(Pair v){//method to set velocity of ball
	velocity = v;
    }
    private void bounce(World w){// method to check and bounce the ball object whenever collision with either saucer or brick is detected
	
	double a1 = 0;
	double a2 = 0;
	double a3 = 0;
	double a4 = 0;
	double b1 = 0;
	double b2 = 0;
	double b3 = 0;
	double b4 = 0;
	for(int i = 0; i<w.bs.length; i++){
	    b1 = w.bs[i].startx;//left edge x component
	    b2 = w.bs[i].starty;//top edge y component
	    b3 = b1 + w.bs[i].width;//right edge x component
	    b4 = b2 + w.bs[i].length;//bottom  edge y component
	    //following if clauses check the edges of the brick objects contact with ball 
	    if( position.y + radius <= b2 + 5.0 && position.y + radius >= b2 - 1.0 && position.x >= b1 && position.x <=b3 && w.bs[i].broken == false && velocity.y >0){
		velocity.flipY();// flips velocity of ball 
		w.bs[i].brickBroke();// calls brickbroke method of brick contacted which sets the boolean value of bricks broken to true
		w.count++;//add to the count of break
		w.play();//play audio when there is contact
		
	    }
	    if (position.y <= b4 + 1.0 && position.y >= b4 - 5.0  && position.x >= b1 && position.x <=b3 && w.bs[i].broken == false && velocity.y < 0){
		velocity.flipY();
		w.bs[i].brickBroke();
		w.count++;
		w.play();
	    }
	    
	    if (position.x <= b3  && position.x  >= b3 - 5.0 && position.y >= b2 && position.y <= b4 && w.bs[i].broken == false && velocity.x < 0){
		velocity.flipX();
		w.bs[i].brickBroke();
		w.count++;
		w.play();
	    }
	    if (position.x + radius >= b1  && position.x + radius <= b1 + 5.0 && position.y >= b2 && position.y <= b4 && w.bs[i].broken == false && velocity.x > 0){
		velocity.flipX();
		w.bs[i].brickBroke();
		w.count++;
		w.play();
	    } 
	}     
		    
			      
			
	a1 = w.saucer.position.x;// records coordinates of saucer
	a2 = w.saucer.position.y;
	a3 = a1 + w.saucer.width;
	a4 = a2 + w.saucer.length;
	// checks contact of ball with saucer
	// bounces accordingly except from the bottom part of the saucer as continous bouncing decreased number of lifes more than in needed 
	if( position.y + radius <= a2 + 5.0 && position.y + radius >= a2 - 1.0 && position.x >= a1 && position.x <=a3 && velocity.y >0){
	    velocity.flipY();
	    
	  
	    
	}
			  
			    
	if (position.x <= a3  && position.x  >= a3 - 5.0 && position.y >= a2 && position.y <= a4 && velocity.x < 0){
	    velocity.flipX();
	    
	}
	if (position.x + radius >= a1  && position.x + radius <= a1 + 5.0 && position.y >= a2 && position.y <= a4 && velocity.x > 0){
	    velocity.flipX();
	    
	    
	} 
	//takes care of bouncing on the edge of the screen
	if (position.x-radius < 0){
	    velocity.flipX();
	    position.x = radius;
	}
	else if (position.x + radius > w.width){
	    velocity.flipX();
	    position.x = w.width - radius;
	    
	}
	if (position.y - radius < 0){
	    velocity.flipY();
	    position.y = radius;
	    
	}
	int a = 0;
	if(position.y + radius >  w.height){
	    if (w.lives > 0){    // checks to see if the player still has lives left
		velocity.flipY();//flips if it does then decreases lives left
		position.y = w.height - radius;
		if (!w.bounce)// checks if bounce power up is in play before decreasing lives
		    w.lives--;
	    }
	}
	if (w.lives == 0 && position.y + radius > w.height)//if no lives are left and the ball hits the bottom edge. Game Over
	    w.gamelost = true;
    }          
    

    
}
