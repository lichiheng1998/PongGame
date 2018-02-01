package pong2;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongListener extends Thread implements KeyListener {
	Player player;
	boolean KeyUp;
	boolean KeyDown;
	boolean KeyLeft;
	boolean KeyRight;
	int k;

	PongListener(Player p) {
		this.player = p;
		Table.bf.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Key keys = new Key(e);
		// TODO Auto-generated method stub
		k = e.getKeyCode();
		if (k == player.upKey) {
			KeyUp = true;
		}
		if (k == player.downKey) {
			KeyDown = true;
		}
		if (k == player.leftKey) {
			KeyLeft = true;
		}
		if (k == player.rightKey) {
			KeyRight = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int n = e.getKeyCode();
		// TODO Auto-generated method stub
		if (n == player.upKey) {
			KeyUp = false;
		}
		if (n == player.downKey) {
			KeyDown = false;
		}
		if (n == player.leftKey) {
			KeyLeft = false;
		}
		if (n == player.rightKey) {
			KeyRight = false;
		}
	}

	public void update() {
		if ((KeyUp || KeyDown) && player.width > player.height) {
			player.turn();
			if (!Table.wall.contains(player.x, player.y, player.width, player.height))
				player.turn();
		}
		if ((KeyLeft || KeyRight) && player.width < player.height) {
			player.turn();
			if (!Table.wall.contains(player.x, player.y, player.width, player.height))
				player.turn();
		}
		if (KeyDown && Table.wall.contains(player.x, player.y + player.speed, player.width, player.height)) {
			player.toSouth(player.speed);
			player.ifTouchPortalNorth(Table.portalList, Table.blackHoleArea);
		}
		if (KeyUp && Table.wall.contains(player.x, player.y - player.speed, player.width, player.height)) {
			player.toNorth(player.speed);
			player.ifTouchPortalSouth(Table.portalList, Table.blackHoleArea);
		}
		if (KeyLeft && Table.wall.contains(player.x - player.speed, player.y, player.width, player.height)) {
			player.toWest(player.speed);
			player.ifTouchPortalEast(Table.portalList, Table.blackHoleArea);
		}
		if (KeyRight && Table.wall.contains(player.x + player.speed, player.y, player.width, player.height)) {
			player.toEast(player.speed);
			player.ifTouchPortalWest(Table.portalList, Table.blackHoleArea);
		}
	}

	public void run() {
		while (!Table.GameOver) {
			update();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
