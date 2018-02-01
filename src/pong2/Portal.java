package pong2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Portal extends Rectangle {
	static ImageIcon port;
	int xSpeed;
	int ySpeed;
	boolean northTouch;
	boolean eastTouch;
	boolean westTouch;
	boolean southTouch;
	Portal(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
		port = new ImageIcon("portal (2).gif");
		Table.portalList.add(this);
		xSpeed = 0;
		ySpeed = 0;
	}
	public static void drawPortal(Graphics g, ArrayList<Portal> p){
		for(int i = 0; i < p.size(); i++){
			g.drawImage(port.getImage(), p.get(i).x, p.get(i).y,null);
		}
	}
}
