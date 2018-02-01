package pong2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Kit extends Rectangle {
	int d;
	int x;
	int y;
	Color c = Color.green;
	String function;
	static Image energyBall;
	static Image poisonIcon;

	Kit(int x, int y, int d, String function) {
		this.x = x;
		this.y = y;
		this.d = d;
		Table.kitList.add(this);
		this.function = function;
		this.setBounds(this.x, this.y, this.d, this.d);
		try {
			energyBall = ImageIO.read(new File("energyBallX.png"));
			poisonIcon = ImageIO.read(new File("Poison-icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean wasEatenBy(Ball b) {
		b.setBounds(b.x, b.y, b.d, b.d);
		if (b.contains(this.getCenterX(), this.getCenterY())) {
			return true;
		}
		return false;
	}
	public static void draw(Graphics g, ArrayList<Kit> b){
		for(int i = 0; i < b.size(); i++){
			if (b.get(i).function.equals("poison")){
				g.drawImage(poisonIcon, b.get(i).x, b.get(i).y, null);
			    }
			else
				g.drawImage(energyBall, b.get(i).x, b.get(i).y, null);	
		}
	}

	public void setColor(Color c) {
		this.c = c;
	}
}
