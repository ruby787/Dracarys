// Dagim Belte & Malyaka Imran


import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.*;
import java.util.*;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class Main extends JPanel implements KeyListener,MouseListener{//Main Class extenfing JPanel

	//Set Dimension for the World

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    //Set the way the world works
    public static final int FPS = 60;
    World world;

    //Constantly runs to detect changes

    class Runner implements Runnable{
	public void run()
	{
	    world.play2();// runs method to play back ground music
	    while(true){
		if (world.isOn){// when start game is clicked and in on is turn true runs 
		    if ( !world.gamelost || !world.gamewon ){ //while the game is not lost or won it runs
			
			world.updateBricks(1.0/ (double)FPS);
			if (!world.stop){// gives the functionality of stopping the ball mid flight as the stop powerup is used
			    world.updateBall(1.0 / (double)FPS);
			}
			world.updateSaucer(1.0/ (double)FPS);//call update methods in world
		
			world.updatePowerup(1.0/(double)FPS);
		    }
		}
		repaint();
		
		try{
		    Thread.sleep(1000/FPS);
		}
		catch(InterruptedException e){}
	    }

	} 
    }
    public void keyPressed(KeyEvent e) {//takes in what value has been pressed
        char c=e.getKeyChar();
	Pair a;
	Pair b = new Pair (0.0,0.0);
	Pair q = new Pair (0.0,730.0);
	Pair d = new Pair (904.0,730.0);
	if (c == 'a'){// when key a is pressed moves to the right
	    if(world.speed == true){// adds functionality of increasing speed of saucer when speed power up is used
		a = new Pair (-5000.0, 0.0);
		
		new Timer(10000, new ActionListener(){// timed event for the increase in speed to end after a set time of 10 seconds
			public void actionPerformed(ActionEvent arg0){
			    world.speed=false;
			}}).start();
	    }
	    else {
		a = new Pair(-600.0, 0.0);
		
	    }
	    
	    if (world.saucer.position.x <= 0){
		world.saucer.setAcceleration(b);
		world.saucer.setVelocity(b);
		world.saucer.setPosition(q);
	    }
	    world.saucer.setAcceleration(a);
	    
	    
	}
	else if (c == 'd'){// when key d is pressed moves to the right
	    if (world.speed == false)
		a = new Pair(600.0, 0.0);
	    else 
		a = new Pair (5000.0, 0.0);
	    if (world.saucer.position.x + world.saucer.width >= WIDTH){
		world.saucer.setAcceleration(b);
		world.saucer.setVelocity(b);
		world.saucer.setPosition(d);
	    }
	    world.saucer.setAcceleration(a);
	}
	
	
	
	
	
    }
    public void keyReleased(KeyEvent e) {// gives what key has just been released
        char c=e.getKeyChar();
	Pair a = new Pair (0.0,0.0);
	if (c == 'a'){// when a is released sets acceleration and velocity of saucer to zero
	    world.saucer.setAcceleration(a);
	    world.saucer.setVelocity(a);
	    
	    
	}
	else if (c == 'd'){// when d is released sets acceleration and velocity of saucer to zero
	    
	    world.saucer.setAcceleration(a);
	    world.saucer.setVelocity(a);
	    
	}
	
    }
    public void keyTyped(KeyEvent e) {
	    char c = e.getKeyChar();
	    
    }
    public void addNotify() {// for key and mouse listener
        super.addNotify();
        requestFocus();
    }
    @Override     
    public void mouseClicked(MouseEvent e){// method gets which coordinated the mouse has been clicked in
	
	if(e.getX()>= 250 && e.getX()<=650 && e.getY()>= 530 && e.getY()<=630){//starts game when start game button boxed is clicked
	    world.isOn=true;
	    world.level = 1;
	 }
	
	else if (e.getX()>=250 && e.getX()<=850 && e.getY()>=350 && e.getY()<=500 && world.level == 3 ){ // in transition to level two, click on next level box to go to next level 
	    world.level=2;
	    
       	}
       	
	else if(e.getX()>=250 && e.getX()<=850 && e.getY()>=600 && e.getY()<=750 && world.level == 3){// Shows game over when game shows
	    world.gameOver=true;
	}
	// difficulty dependent on speed of ball
	else if(e.getX()>=400 && e.getX()<=480 && e.getY()>=250 && e.getY()<=300){// select difficulty easy
	    world.b.setVelocity(new Pair(-150,-150));
	    
    }
	
	else if(e.getX()>=380 && e.getX()<=500 && e.getY()>=320 && e.getY()<=370){// select difficulty medium
	    world.b.setVelocity(new Pair(-200,-200));}
    //g.fillRect(350, 390, 200, 50);
	else if(e.getX()>=350 && e.getX()<=550 && e.getY()>=390 && e.getY()<=440){// select diffivulty hard
	    world.b.setVelocity(new Pair(-400,-250));
    }
	
	
       	
    }//mouse clicked
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }   

    
    public Main(){// contructure for main
	world = new World(WIDTH, HEIGHT, 40);
	addKeyListener(this);
	addMouseListener(this);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
	mainThread.start();
	
    }
    
    public static void main(String[] args){
	JFrame frame = new JFrame("Dracarys ");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Main mainInstance = new Main(); //creates an instance of main which frame is run on;
	frame.setContentPane(mainInstance);
	frame.pack();
	frame.setVisible(true);
	
    }
    

    public void paintComponent(Graphics g) {// paint component of our JPanel
	super.paintComponent(g);    	

	world.drawimg2(g,0,0);// draws background image
	if (!world.isOn) {//first page for when game hasn't been started
	    g.setColor(Color.yellow);    
	    Font stringFont = new Font( "SansSerif", Font.PLAIN, 50);
	    g.setFont(stringFont);
	    g.drawString("WELCOME TO BRICK BREAKER!!" , 150,100);
	    g.setColor(Color.red);    
	    stringFont = new Font( "SansSerif", Font.PLAIN, 50);
	    g.setFont(stringFont);
	    g.fillRect(250, 530, 400, 100);
	    g.setColor(Color.yellow);
	    g.drawString("PLAY GAME!", 300,600);
	    stringFont = new Font( "SansSerif", Font.PLAIN, 30);
	    g.setFont(stringFont);
	    g.setColor(Color.red);
	    g.drawString("Difficulty Level", 350,200);
	    stringFont = new Font( "SansSerif", Font.PLAIN, 30);
	    g.setFont(stringFont);
	    g.setColor(Color.red);
	    g.fillRect(400, 250, 80, 50);
	    g.setColor(Color.yellow);
	    g.drawString("EASY", 400,280);
	    stringFont = new Font( "SansSerif", Font.PLAIN, 30);
	    g.setFont(stringFont);
	    g.setColor(Color.red);
	    g.fillRect(380, 320, 120, 50);
	    g.setColor(Color.yellow);
	    g.drawString("MEDIUM", 380,350);
	    g.setColor(Color.red);
	    g.fillRect(350, 390, 200, 50);
	    g.setColor(Color.yellow);
	    g.drawString("DIFFICULT", 375, 420);
	    
	    
	    
	    
	}
	else {
	    
	    if(world.level==1){// what is printed when the game started level 1
		
		if (world.count == world.numBricks)// checks whether number of broken bricks equals total bricks on level
		    world.gamewon =true;
		
		if(world.gamelost == false && world.gamewon == false){// runs when the game is neither won or lost
		    
		    g.setColor(Color.green);
		    Font stringFont = new Font( "Times New Java", Font.ITALIC, 32);
		    g.setFont(stringFont);
		    g.drawString("Score:" + world.score, 30,30);// prints score of the player
		    g.drawString("Lives:" ,750,30);
		    g.setColor(Color.WHITE);
		    if (world.bounce){// Prints what bounce power up does  for the time limit
			g.drawString("Bouncing on the lower ende doesn't take lives for ten seconds",80,55);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.bounce=false;}}).start();//implements the timer
		    }
		    if (world.speed){// Prints what the speeding up of the saucer does and implements timer for the printing
			g.drawString("Boost for 10 second",350,80);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.speed=false;}}).start();
		    }if (world.meme){ // just a troll powerup does nothing
			g.drawString("Hey!",480,105);
			
			new Timer(10000, new ActionListener(){
					    public void actionPerformed(ActionEvent arg0){
						world.meme=false;}}).start();
		    }if (world.stop){// prints what stop does for the time period of the act
			world.print = true;
			if (world.print){
			    g.drawString("You've got the ability to freeze time but only for 8 seconds, use it well.",10,130);
			}
			new Timer(8000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.print=false;
				    world.stop = false;
				    
				    
				    
				}}).start();
					    	
			
			
		    }if (world.live){// similarly prints explanation of functionality
			g.drawString("Elixir of health, hurray",300,155);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.live=false;}}).start();
		    }if (world.onek){// explanation of functionality
			g.drawString("Sooooo much pointsss",400,180);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.onek=false;}}).start();
				}
		    
		    int a = 850;
		    g.setColor(Color.RED);
		    
		    for (int i = 0; i < world.lives; i++){
			g.fillOval(a,10,20,20);
			a = a + 30;
		    }//for
		    // draw calls the draw methods in world for ball, saucer, bricks, powerup
		    g.setColor(Color.yellow);
		    world.drawBall(g);
		    world.drawSaucer(g);
		    world.drawBricks(g);
		    world.drawPowerup(g);
		} else if (world.gamewon == true){// implements going to the next level
		    
		    world.count=0;
		    world.gamewon=false;
		    world.gamelost = false;
		    world.level=3;
		    
		} else {
		    world.drawimg3(g,0,0);// draw game lost 
		    
		}//else
		
	    } //world level 1
	    if(world.level==3){// implements the in between page of the game
		
		g.setColor(Color.yellow);    
		Font stringFont = new Font( "SansSerif", Font.PLAIN, 50);
		g.setFont(stringFont);
		g.drawString("LEVEL 2" , 100,100);
		g.drawRect(250,350, 600, 150);
		g.drawString("Click if u wanna play!" , 300,430);
		g.drawRect(250,600, 600, 150);
		g.drawString("Click if u wanna quit!", 300,680 );
		
		
		}
	    if (world.level==2){// implements level 2 of the game similarly to level one with just new arrangement of Bricks

		if (world.count == world.numBricks)
		    world.gamewon = true;
		
		if(world.gamelost == false && world.gamewon == false){
		    
		    g.setColor(Color.green);
		    Font stringFont = new Font( "Times New Java", Font.ITALIC, 32);
		    g.setFont(stringFont);
		    world.makeBricks2();
		    g.drawString("Score:" + world.score, 30,30);
		    g.drawString("Lives:" ,800,30);
		    g.setColor(Color.WHITE);
		    if (world.bounce){
			g.drawString("Bouncing on the lower ende doesn't take lives for ten seconds",80,55);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
						world.bounce=false;}}).start();
		    }
		    if (world.speed){
			g.drawString("Boost for 10 second",350,55);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.speed=false;}}).start();
		    }if (world.meme){
			g.drawString("Hey!",480,55);
				    
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.meme=false;}}).start();
		    }if (world.stop){
			world.print = true;
			if (world.print){
			    g.drawString("You've got the ability to freeze time but only for 8 seconds, use it well.",10,55);
			    
			    new Timer(8000, new ActionListener(){
				    public void actionPerformed(ActionEvent arg0){
					world.print=false;
					world.stop = true;
					
					
				    }}).start();
			}
			
					   	
					   
		    }if (world.live){
			g.drawString("Elixir of health, hurray",300,55);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.live=false;}}).start();
		    }if (world.onek){
			g.drawString("Sooooo much pointsss",400,55);
			
			new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				    world.onek=false;}}).start();
		    }
		    int a = 900;
		    g.setColor(Color.RED);
		    
		    for (int i = 0; i < world.lives; i++){
			g.fillOval(a,10,20,20);
			a = a + 30;
		    }//for
		    
		    g.setColor(Color.yellow);
		    world.drawBall(g);
		    world.drawSaucer(g);
		    
		    world.drawBricks(g);
		    world.drawPowerup(g);
		    
		} else if (world.gamewon == true){
		    world.drawimg4(g,0,0);
		    
		    
		} else {
		    
		    world.drawimg3(g,0,0);// Game Lost
		    
		} //else
		
	    } //level 2
	    if(world.gameOver==true){
		world.drawimg(g,0,0);// Game Over
	    }
	    
	} //isOn
	
    }
}
