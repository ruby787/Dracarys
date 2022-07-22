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
class World{
    int height;// size of world
    int width;
    public static int score;// score of player
    int numBricks;// number of bricks in world
    Saucer saucer = new Saucer();// saucer object
    int count;  // count for number of bricks
    int startx;// start position of for first brick
    int starty;
    Brick[] bs;// brick array
    final int finallives = 5;// total lives
    int lives;
    boolean gamelost;// keeps track of situation of the game
    boolean gamewon;
    int bonus;// sets number of maximum bonus bricks
    int level;
    Ball b =new Ball();// creates ball object
    boolean isOn = false; //manages whether the game has been started or not
    boolean level2status = true; //manages the start of the second level
    Random rand = new Random();// rand object for random placement of bonus bricks in the brick array
    //keep track of the whether the powerups are currently in use or not
    boolean speed = false;
    boolean bounce = false;
    boolean stop = false;
    boolean meme = false;
    boolean print = false;
    boolean onek = false;
    boolean live = false;
    // created an array of images from which the power ups choose
    BufferedImage[] bi = new BufferedImage[6];
    // created an array of strings which have the name of functionalitie of the powerups
    String[] si = new String[6];
    // more image files for back grounds and game won/ lost situations
    BufferedImage img2 = null;
    BufferedImage img3 = null;
    BufferedImage img4 = null;
    BufferedImage img5 = null;
    boolean gameOver;// game over variable
    boolean level1pic;


    public World(int initWidth, int initHeight, int numBricks){// world object contructor
	width = initWidth;
	height = initHeight;
	this.numBricks = numBricks;
	bonus = 6;

	bs = new Brick[numBricks];
	startx = 0;
	starty = 200;
	int i = 0;
	for (int j = 0; j<4; j++){
	    for (int y = 0; y<bs.length/4; y++){// created bricks with different start x and y values to fill brick array
		bs[i] = new Brick(30, 100, Color.yellow, 10,startx,starty, false, false);
		startx+=105;
		i++;
		}
	    startx = 0;
	    starty+= 35;
	}
	// images for backgrounds
	BufferedImage img = null;
	BufferedImage img11 = null;
	BufferedImage img22 = null;
	BufferedImage img33 = null;
	BufferedImage img44 = null;
	BufferedImage img55 = null;
	try {
	    img = ImageIO.read(new File("Speed.jpg"));
	    img11 = ImageIO.read(new File("1000.jpg"));
	    img22 = ImageIO.read(new File("Lives.jpg"));
	    img33 = ImageIO.read(new File("Bounce.jpg"));
	    img44 = ImageIO.read(new File("Stop.jpg"));
	    img55 = ImageIO.read(new File("Meme.jpg"));
	    img2 = ImageIO.read(new File("game over.jpg"));
	    img3 = ImageIO.read(new File("taki.png"));
	    img4 = ImageIO.read(new File("loser.jpeg"));
	    img5 = ImageIO.read(new File("winner.png"));
	    
	    
	    
	}
	catch (IOException e){
	}
	bi[0] = img;
	bi[1] = img11;
	bi[2] = img22;
	bi[3] = img33;
	bi[4] = img44;
	bi[5] = img55;
	
	
	si[0] = "Speed";
	si[1] = "1000";
	si[2] = "Lives";
	si[3] = "Bounce";
	si[4] = "Stop";
	si[5] = "Meme";
	for(int m=0; m< bonus; m++){// bonus bricks randomly set in the array
	    
	    int k= (int)(rand.nextInt(39));
	    bs[k].points=20;
	    bs[k].color= Color.red;
	    bs[k].bonus = true;
	}
	int aq = 0;
        
	for (int g =0; g< numBricks; g++){// if bonus brick in array power up object set to brick
	    if (bs[g].bonus){
		bs[g].pu = new Powerup((int)(bs[g].startx + (bs[g].width)/2),(int)(bs[g].starty + (bs[g].length)/2),bi[aq], si[aq], false);
		aq++;
		
	    }
	    
	}
	
	
	    
 	
    
	lives = finallives;
	gamelost = false;
	gamewon = false;
	
    }
    public void play(){// music played when brick breaks
    
    try{
	AudioInputStream ais=AudioSystem.getAudioInputStream(new File("ball.wav"));
	Clip clip =AudioSystem.getClip();
	clip.open(ais);
	clip.start();
    } catch(Exception e){
			System.out.println("Ouch");
    }//catch
    
} //play
    public void play2(){// method to play background music in a continious loop
	
	try{
	    AudioInputStream ais=AudioSystem.getAudioInputStream(new File("Game of Thrones.wav"));
	    Clip clip =AudioSystem.getClip();
	    clip.open(ais);
	    clip.start();
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    Thread.sleep(10000);
	} catch(Exception e){
	    System.out.println("Ouch2");
	}//catch
    }
    public void play3(){// method to play audio whenever a power up is absorbed
	try{
	    AudioInputStream ais=AudioSystem.getAudioInputStream(new File("Powerup.wav"));
	    Clip clip =AudioSystem.getClip();
	    clip.open(ais);
	    clip.start();
	    
	} catch(Exception e){
	    System.out.println("Ouch2");
	}//catch
    }
    
    
    
    public void makeBricks2(){// create level 2 with new brick arrangement and new bonus brick settings similar implementation as level 1
	if(level2status){
	    bs=null;
	    bi=null;
	    si=null;
	    
	    
	    numBricks=40;
	    startx= 30;
	    starty=200;
	    bs = new Brick[numBricks];
	    bi = new BufferedImage[6];
	    si = new String[6];
	    
	    
	    int jj = 0;
	    for(int j=0; j<5;j++){
		startx= 30;
		for(int i=0; i<numBricks/5; i++){
		    
		    bs[jj]=new Brick(30,100,Color.yellow, 10, startx, starty, false, false);
		    startx+=130;
		    jj++;
		}
		starty+=50;
	    }
	    for(int m=0; m< bonus; m++){
		
		int k= (int)(rand.nextDouble()*numBricks);
		bs[k].points=20;
		bs[k].color= Color.red;
		bs[k].bonus = true;
		
	    }

	    
	    BufferedImage img000 = null;
	    BufferedImage img111 = null;
	    BufferedImage img222 = null;
	    BufferedImage img333 = null;
	    BufferedImage img444 = null;
	    BufferedImage img555 = null;
	    try {
		img000 = ImageIO.read(new File("Speed.jpg"));
		img111 = ImageIO.read(new File("1000.jpg"));
		img222 = ImageIO.read(new File("Lives.jpg"));
		img333 = ImageIO.read(new File("Bounce.jpg"));
		img444 = ImageIO.read(new File("Stop.jpg"));
		img555 = ImageIO.read(new File("Meme.jpg"));
	    }
	    catch (IOException e){
	    }
	    bi[0] = img000;
	    bi[1] = img111;
	    bi[2] = img222;
	    bi[3] = img333;
	    bi[4] = img444;
	    bi[5] = img555;
	    
	    
	    si[0] = "Speed";
	    si[1] = "1000";
	    si[2] = "Lives";
	    si[3] = "Bounce";
	    si[4] = "Stop";
	    si[5] = "Meme";
	    
	    int aq=0;
	    
	    for (int g =0; g< numBricks; g++){
		if (bs[g].bonus){
		    bs[g].pu = new Powerup((int)(bs[g].startx + (bs[g].width)/2),(int)(bs[g].starty + (bs[g].length)/2),bi[aq], si[aq], false);
		    aq++;
		}	
	    }
	    level2status = false;
	}
    }
    

    
    public void Powerups(String s){// power up method that checks what the speicifc use string of activated power up is and sets the value of the activation boolean to true in some instances with a set timer within method
	if (s.equals("Speed")){
	    speed = true;
	}
	if (s.equals("1000")){
	    score = score + 1000;
	    onek = true;
	}
	if (s.equals("Lives")){
	    live = true;
	    if (lives == finallives)
		lives = finallives+1;
	    else
		lives = finallives;
	}
	if (s.equals("Bounce")){
	    bounce = true;
	    new Timer(10000, new ActionListener(){
		    public void actionPerformed(ActionEvent arg0){
			bounce=false;
		    }}).start();
	}
	if (s.equals("Stop")){
	    stop = true;
	    
	}
	if (s.equals("Meme")){
	    meme = true;
	    new Timer(10000, new ActionListener(){
		    public void actionPerformed(ActionEvent arg0){
			meme=false;
		    }}).start();
	    
	    
	}
    }
    public void drawBricks(Graphics g){// method to run through brick arrays and print all bricks that haven't been broken
	for (int s= 0; s < bs.length; s++)
	    if (bs[s].broken == false)
		bs[s].draw(g);
	
    }
    

    
    
    
    public void drawBall(Graphics g){// calls draw method of ball
	
	b.draw(g);
    }
    public void updateBall(double time){// calls update method of ball
	b.update(this, time);
    }
    public void drawSaucer(Graphics g){// calls draw method of ball
	saucer.draw(g);
    }
    public void drawPowerup(Graphics g){// calls draw method of powerups the have been turned on to go but not activated yet
	for (int s= 0; s < numBricks ; s++){
	    if(bs[s].pu != null)
		if(bs[s].pu.go == true && bs[s].pu.used == false)
		    bs[s].pu.draw(g);
	}
    }
    // draw images methods for backgrounds
    public void drawimg(Graphics g, int x, int y){
	g.drawImage(img2, x,y, 1024, 768, null );
    }
    public void drawimg2(Graphics g, int x, int y){
	g.drawImage(img3, x,y, 1024, 768, null );
    }
    public void drawimg3(Graphics g, int x, int y){
	g.drawImage(img4, x,y, 1024, 768, null );
    }
    public void drawimg4(Graphics g, int x, int y){
	g.drawImage(img5, x,y, 1024, 768, null );
    }
    
    public void updateSaucer(double time){// calls update method of saucer
	    saucer.update(this,time);
    }
    
    public void updateBricks (double time){// call update method of bricks by iterating 
	for (int i = 0; i <numBricks; i++)
	    bs[i].update(this,time);
    }
    public void updatePowerup (double time){// call update power when it has been turned on but not activated yet
	for (int i = 0; i< numBricks; i++)
	    if (bs[i].pu !=null)
		if(bs[i].pu.go == true && bs[i].pu.used == false)
		    bs[i].pu.update(this,time);
    }
}
