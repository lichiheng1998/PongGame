package pong2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class BlackHole extends Rectangle {
	int x;
	int y;
	int d;
	double range;
	double force;

	BlackHole(int x, int y, int d, double range, double force) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.range = range;
		this.force = force;
		Table.holeList.add(this);
	}

	public void applyForce(Ball b) {
		double r;
		Rectangle ball = new Rectangle();
		ball.setBounds(b.x, b.y, b.d, b.d);
		this.setBounds(this.x, this.y, this.d, this.d);
		r = Math.sqrt(Math.pow(this.getCenterX() - ball.getCenterX(), 2)
				+ Math.pow(this.getCenterY() - ball.getCenterY(), 2));
		if (Math.abs(r) <= range) {
			b.xAccleration = (this.getCenterX() - ball.getCenterX()) * force / Math.pow(r, 3);
			b.yAccleration = (this.getCenterY() - ball.getCenterY()) * force / Math.pow(r, 3);
			if (Math.abs(b.xSpeed + b.xAccleration) < 1 || Math.abs(b.ySpeed + b.yAccleration) < 1
					|| Math.abs((int) (b.xSpeed + b.xAccleration)) > 5
					|| Math.abs((int) b.ySpeed + b.yAccleration) > 5) {
				b.xAccleration = 0;
				b.yAccleration = 0;
			}

			b.xSpeed += b.xAccleration;
			b.ySpeed += b.yAccleration;
			ifStopRandomMove(b);
		}
	}

	public void ifStopRandomMove(Ball b) {
		if ((int) (b.xSpeed * b.step) == 0 && (int) (b.xSpeed * b.step) == 0) {
			b.xSpeed = (double) (new Random().nextInt(5) + 1);
			b.ySpeed = (double) (new Random().nextInt(5) + 1);
		}
	}

	public static void draw(Graphics g, ArrayList<BlackHole> b) {
		for (int i = 0; i < b.size(); i++) {
			g.setColor(new Color((int) (Math.random() * 255 + 1), (int) (Math.random() * 255 + 1),
					(int) (Math.random() * 255 + 1)));
			g.fillOval(b.get(i).x, b.get(i).y, b.get(i).d, b.get(i).d);
		}
	}
}
