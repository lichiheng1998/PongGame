package pong2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
	int x;
	int y;
	int width;
	int height;
	int speed;
	int extraRange = 3;
	int upKey;
	int downKey;
	int leftKey;
	int rightKey;
	int score;
	String name;
	String goalDir; 
	Color c;
	PongListener pl;

	Player(int x, int y, int width, int height, int speed, String goalDir) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.score = 0;
		pl = new PongListener(this);
		pl.start();
		this.goalDir = goalDir;
	}

	public void setKey(int up, int down, int left, int right) {
		upKey = up;
		downKey = down;
		leftKey = left;
		rightKey = right;
	}

	void setExtraRange(int extraRange) {
		this.extraRange = extraRange;
	}

	void toWest(int speed) {
		x -= speed;	
	}

	void toEast(int speed) {
		x += speed;
	}

	void toNorth(int speed) {
		y -= speed;	
	}

	void toSouth(int speed) {
		y += speed;	
	}

	void turn() {
		int temp = this.width;
		this.width = this.height;
		this.height = temp;
		if (width < height) {
			x += (height - width) / 2;
			y -= (height - width) / 2;
		}
		if (width > height) {
			y += (width - height) / 2;
			x -= (width - height) / 2;
		}
		Table.bf.repaint();
	}

	public void draw(Graphics g) {
		g.setColor(this.c);
		g.fillRect(this.x, this.y, this.width, this.height);
	}

	public void setColor(Color c) {
		this.c = c;
	}
	public void ifTouchPortalSouth(ArrayList<Portal> a, Rectangle r){
		for(int i = 0; i < a.size(); i++){
			if(this.x + this.width >= a.get(i).getX() && this.x <= a.get(i).getX() + a.get(i).getWidth() && this.y == (int) (a.get(i).getY() + a.get(i).getHeight())){
				a.get(i).ySpeed = -1;
				if(a.get(i).getY() + a.get(i).ySpeed >= r.getY() && a.get(i).getY() + a.get(i).getHeight() + a.get(i).ySpeed <= r.getY() + r.getHeight())
				a.get(i).setLocation((int)a.get(i).getX(), (int)(a.get(i).getY() + a.get(i).ySpeed));
			}
		}
	}
	public void ifTouchPortalNorth(ArrayList<Portal> a, Rectangle r){
        for(int i = 0; i < a.size(); i++){
			if(this.x + this.width >= a.get(i).getX() && this.x <= a.get(i).getX() + a.get(i).getWidth() && this.y + this.height == (int) (a.get(i).getY())){
				a.get(i).ySpeed = 1;
				if(a.get(i).getY() + a.get(i).ySpeed >= r.getY() && a.get(i).getY() + a.get(i).getHeight() + a.get(i).ySpeed <= r.getY() + r.getHeight())
				a.get(i).setLocation((int)a.get(i).getX(), (int)(a.get(i).getY() + a.get(i).ySpeed));
			}
		}
	}
	public void ifTouchPortalEast(ArrayList<Portal> a,Rectangle r){
        for(int i = 0; i < a.size(); i++){
			if(this.y + this.height >= a.get(i).getY() && this.y <= a.get(i).getY() + a.get(i).getHeight() && this.x == (int) (a.get(i).getX()+ a.get(i).getWidth())){
				a.get(i).xSpeed = -1;
				if(a.get(i).getX() + a.get(i).xSpeed >= r.getX() && a.get(i).getX() + a.get(i).getWidth() + a.get(i).xSpeed <= r.getX() + r.getWidth())
				a.get(i).setLocation((int)(a.get(i).getX() + a.get(i).xSpeed), (int)a.get(i).getY());
			}
		}
	}
	public void ifTouchPortalWest(ArrayList<Portal> a, Rectangle r){
	    for(int i = 0; i < a.size(); i++){
				if(this.y + this.height >= a.get(i).getY() && this.y <= a.get(i).getY() + a.get(i).getHeight() && this.x + this.width == (int) (a.get(i).getX())){
				a.get(i).xSpeed = 1;
				if(a.get(i).getX() + a.get(i).xSpeed >= r.getX() && a.get(i).getX() + a.get(i).getWidth() + a.get(i).xSpeed <= r.getX() + r.getWidth())
				a.get(i).setLocation((int)(a.get(i).getX() + a.get(i).xSpeed), (int)a.get(i).getY());
			}
		}
	}
}
