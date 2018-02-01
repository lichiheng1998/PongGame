package pong2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Ball extends Rectangle {
	// set Sound
	static Clip clip;
	static File bounceSound = new File("Bounce.wav");
	static AudioInputStream audioInputStream;
	Rectangle rBall;
	int x, y, xinc, yinc, d;
	double xSpeed;
	double ySpeed;
	double xAccleration;
	double yAccleration;
	double speed;
	Color c = new Color(153, 153, 255);
	int t = 1;
	int step = 1;
	int extraRange = 0;
	boolean didTrans = false;
	boolean didGoal = false;
	boolean didBounce = false;
	Rectangle portal;
	Ball b = null;
	boolean useful = true;

	Ball(int x, int y, int d, double xSpeed, double ySpeed) {
		this.d = d;
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.step = step;
		rBall = new Rectangle();
		Table.ballList.add(this);
		portal = new Rectangle(0, 0, 0, 0);
		openSound();
	}

	public void setExtraRange(int extraRange) {
		this.extraRange = extraRange;
	}

	public void Bounce(String c) {
		if (c.equals("x"))
			xSpeed = -xSpeed;
		if (c.equals("y"))
			ySpeed = -ySpeed;
		if (c.equals("xy")) {
			ySpeed = -ySpeed;
			xSpeed = -xSpeed;
		}
		this.c = new Color((int) (Math.random() * 255 + 1), (int) (Math.random() * 255 + 1),
				(int) (Math.random() * 255 + 1));
		playSound();
	}

	public void move() {
			x += (int) xSpeed * step;
			y += (int) ySpeed * step;
			Table.bf.repaint();
	}

	public void ifTouchBounce(Player o) {
		int yMove = (int) ySpeed * step;
		int xMove = (int) xSpeed * step;
		int x = this.x + xMove;
		int y = this.y + yMove;
		Rectangle obst = new Rectangle();
		obst.setBounds(o.x - o.extraRange, o.y - o.extraRange, o.width + 2 * o.extraRange, o.height + 2 * o.extraRange);
		if ((x + d >= obst.getX() && x <= obst.getX() + obst.getWidth())
				&& ((ySpeed > 0 && y + d >= obst.getY() && y + d < obst.getY() + yMove) || (ySpeed < 0
						&& y <= obst.getY() + obst.getHeight() && y >= obst.getY() + obst.getHeight() + yMove)))
			Bounce("y");
		if ((y + d >= obst.getY() && y <= obst.getY() + obst.getHeight())
				&& ((xSpeed > 0 && x + d >= obst.getX() && x + d < obst.getX() + xMove) || (xSpeed < 0
						&& x <= obst.getX() + obst.getWidth() && x > obst.getMinX() + obst.getWidth() + xMove)))
			Bounce("x");
	}

	public void ifTouchPortal(ArrayList<Portal> p) {
		if (!portal.contains(this.x, this.y, this.d, this.d))
		didTrans = false;
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).contains(this.x, this.y, this.d, this.d) && !didTrans) {

				int a;
				do {
					a = Tool.getRandom(0, p.size() - 1);
				} while (a == i);
				portal = p.get(a);
				this.x = (int) (p.get(a).getCenterX() - this.d / 2);
				this.y = (int) (p.get(a).getCenterY() - this.d / 2);
				this.didTrans = true;
				return;
			}
		}
	}

	public void ifTouchBallBounce(Ball o) {

		Rectangle obst = new Rectangle();
		this.setBounds(this.x, this.y , this.d, this.d);
		obst.setBounds(o.x, o.y, o.d, o.d );
		double distanceWithO = Math.sqrt((this.getCenterX() - o.getCenterX()) * (this.getCenterX() - o.getCenterX())
				+ (this.getCenterY() - o.getCenterY()) * (this.getCenterY() - o.getCenterY()));
		if(b != null){
		double distanceWithB = Math.sqrt((this.getCenterX() - b.getCenterX()) * (this.getCenterX() - b.getCenterX())
				+ (this.getCenterY() - b.getCenterY()) * (this.getCenterY() - b.getCenterY()));
		if(distanceWithB > this.getWidth()/2 + b.getWidth()/2 + this.extraRange + o.extraRange){
			this.didBounce = false;
		}
		}
		if (distanceWithO <= this.getWidth()/2 + o.getWidth()/2 + this.extraRange + o.extraRange && !didBounce) {
			double xTemp = 0;
			double yTemp = 0;
			xTemp = this.xSpeed;
			yTemp = this.ySpeed;
			this.xSpeed = o.xSpeed;
			this.ySpeed = o.ySpeed;
			o.xSpeed = xTemp;
			o.ySpeed = yTemp;
			this.didBounce = true;
			b = o;
			playSound();
		}
	
	}

	public void ifTouchWallBounce(Rectangle w) {
		if (w.contains(x + (int) xSpeed * step, (int) y + ySpeed * step, d, d) == false) {
			if (x <= w.getX() || x + d >= w.getX() + w.getWidth())
				Bounce("x");
			if (y + d >= w.getY() + w.getHeight() || y <= w.getY())
				Bounce("y");
		}
	}

	public void ifOutFaster(Rectangle w) {
		if (w.contains(x + (int) xSpeed * step, y + (int) ySpeed * step, d, d) == false) {
			this.step = 2;
		} else
			this.step = 1;}
	

	public void split() {
		new Ball(this.x, this.y, this.d, -this.xSpeed, this.ySpeed);
	}

	public void delete() {
		this.useful = false;
	}

	public void ifTouchGoal(Player p, Rectangle r) {
		if (r.contains(this.x, this.y, this.d, this.d))
			didGoal = false;
		if (r.contains(this.x + (int) xSpeed * step, this.y + (int) ySpeed * step, d, d) == false && didGoal == false) {
			if ((p.goalDir.equals("North") && this.y <= r.getY())
					|| (p.goalDir.equals("South") && this.y + d >= r.getY() + r.getHeight())) {
				p.score++;
				didGoal = true;
			}
		}
	}
	   public static void playSound() {
		         if (clip.isRunning())
		            clip.stop();   // Stop the player if it is still running
		         clip.setFramePosition(0); // rewind to the beginning
		         clip.start();     // Start playing
		   }
public static void openSound(){
	try {
		audioInputStream = AudioSystem.getAudioInputStream(bounceSound);
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	} catch (Exception ex) {

		System.out.println("Error with playing sound.");

		ex.printStackTrace();

	}
	}
public static void draw(Graphics g, ArrayList<Ball> b){
	for(int i = 0; i < b.size(); i++){
		g.setColor(b.get(i).c);
		g.fillOval(b.get(i).x, b.get(i).y, b.get(i).d, b.get(i).d);
	}
}
}

