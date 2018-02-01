package pong2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.xml.crypto.Data;

public class Table extends JPanel implements ActionListener {
	static File back = new File("Hard rain.wav");
	static Clip clip;
	static AudioInputStream audioInputStream;
	// generate variables of table class
	int x;
	int y;
	int width;
	int height;
	static Image backGround;
	static Image leaderboard;
	// generate portal
	final static int portalAmount = 4;
	static int poisonNum;
	static int splitNum;
	static Image portal;
	static ImageIcon port;
	// buttons
	static JButton restart;
	static JButton quit;
	// generate walls
	static Rectangle wall;
	static Rectangle blackHoleArea;
	// generate goals

	// generate pong frame
	static ballFrame bf;
	static ballFrame startingFrame;
	// generate table panel
	static Table leaderPanel;
	static Table table;
	// generate two players
	static Player player1;
	static Player player2;
	// generate leaderboard
	static String[][] leaderBoard;
	// generate lists
	static int holeAmount;
	static int ballAmount;
	static ArrayList<Ball> ballList;
	static ArrayList<BlackHole> holeList;
	static ArrayList<Kit> kitList;
	static ArrayList<Portal> portalList;
	static boolean GameOver;
	// generate Timers
	static Timer t;
	static int currentTime;
	static int currentTime2;
	static int gameTime;
	static Date date;
	static KeyEvent k;
	static Random rand = new Random();
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");

	public static void main(String Args[]) {
		try {
			backGround = ImageIO.read(new File("backGround.png"));
			leaderboard = ImageIO.read(new File("leaderboard.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		openSound();
		poisonNum = 0;
		splitNum = 0;
		table = new Table(10, 10, 1000, 1200);
		port = new ImageIcon("portal (2).gif");
		// set starting UI
		bf = new ballFrame();
		startingFrame = new ballFrame();
		StartingPanel sp = new StartingPanel();
		startingFrame.creatUI(sp);
		startingFrame.setVisible(true);
		startingFrame.setFocusable(false);
		restart = new JButton("restart");
		quit = new JButton("quit");
		restart.setBounds(800, 1100, 80, 20);
		quit.setBounds(700, 1100, 80, 20);
		bf.add(restart);
		bf.add(quit);
		bf.setFocusable(true);
		restart.setVisible(false);
		quit.setVisible(false);
		quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				System.exit(0);
			}
		});
		restart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				table = new Table(10, 10, 1000, 1200);
				startingFrame.setVisible(true);
				bf.setVisible(false);
				quit.setVisible(false);
				restart.setVisible(false);
			}
		});
	}

	// initialize table
	Table(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setBounds(x, y, width, height);
	}

	public void paintComponent(Graphics g) {
		if (GameOver) {
			drawLeaderBoard(g);
		} else
			drawGame(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// fileRecord();)
		// TODO Auto-generated method stub
		if (!GameOver) {

			currentTime++;
			currentTime2++;
			if (currentTime >= 2500) {
				generateKit(1, "splitKit");
				if (ballList.size() - poisonNum > 1) {
					generateKit(1, "poison");
				}
				currentTime = 0;
			}
			if (currentTime2 > 100) {
				gameTime--;
				currentTime2 = 0;
				if (gameTime == -1) {
					player1.speed = 0;
					player2.speed = 0;
					if (player1.score > player2.score)
						JOptionPane.showMessageDialog(null, player1.name + " is winner");
					else if (player1.score < player2.score)
						JOptionPane.showMessageDialog(null, player2.name + " is winner");
					else if (player1.score == player2.score)
						JOptionPane.showMessageDialog(null, "There is no winner");
					date = new Date();
					fileRecordWrite(player1.name, "" + player1.score, df.format(date));
					fileRecordWrite(player2.name, "" + player2.score, df.format(date));
					fileRecordRead();
					bubbleSort(leaderBoard);
					clip.stop();
					clip.setFramePosition(0);
					GameOver = true;
					t.stop();
					quit.setVisible(true);
					restart.setVisible(true);
				}
			}
			ballJudge();
		}
	}

	public static void generateHole(int holeAmount) {
		for (int i = 0; i < holeAmount; i++) {
			new BlackHole(
					rand.nextInt((int) (blackHoleArea.getX() + blackHoleArea.getWidth() - blackHoleArea.getX() + 1))
							+ (int) blackHoleArea.getX(),
					rand.nextInt((int) (blackHoleArea.getY() + blackHoleArea.getHeight() - blackHoleArea.getY() + 1))
							+ (int) blackHoleArea.getY(),
					20, 1000, 1000);
		}
	}

	public static void generateBall(int ballAmount) {
		for (int i = 0; i < ballAmount; i++) {
			new Ball(
					rand.nextInt((int) (blackHoleArea.getX() + blackHoleArea.getWidth() - blackHoleArea.getX() + 1))
							+ (int) blackHoleArea.getX(),
					rand.nextInt((int) (blackHoleArea.getY() + blackHoleArea.getHeight() - blackHoleArea.getY() + 1))
							+ (int) blackHoleArea.getY(),
					40, (int) Math.pow(1, rand.nextInt(2) + 1), (int) Math.pow(1, rand.nextInt(2) + 1));
		}
	}


	public void generateKit(int KitAmount, String function) {
		if (function.equals("poison"))
			poisonNum += KitAmount;
		if (function.equals("split"))
			splitNum += KitAmount;
		for (int i = 0; i < KitAmount; i++) {
			new Kit(rand.nextInt((int) (blackHoleArea.getX() + blackHoleArea.getWidth() - blackHoleArea.getX() + 1))
					+ (int) blackHoleArea.getX(),
					rand.nextInt((int) (blackHoleArea.getY() + blackHoleArea.getHeight() - blackHoleArea.getY() + 1))
							+ (int) blackHoleArea.getY(),
					40, function);
		}
	}

	public void fileRecordRead() {
		String str = "";
		String file = "highscore.txt";
		int row = 0;
		FileReader fileobj = null;
		try {
			fileobj = new FileReader(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Scanner rowCounter = new Scanner(fileobj);
		while (rowCounter.hasNext()) {
			row++;
			rowCounter.nextLine();
		}
		rowCounter.close();
		leaderBoard = new String[row][3];
		try {
			fileobj = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner input = new Scanner(fileobj);
		for (int a = 0; a < row; a++) {
			for (int b = 0; b < 3; b++) {
				if (input.hasNext()) {
					if (b == 2) {
						leaderBoard[a][b] = input.nextLine();
					} else
						leaderBoard[a][b] = input.next();
				}
			}

		}
		input.close();
	}

	public void fileRecordWrite(String name, String score, String data) {
		File f = new File("highscore.txt");
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fileWritter);
			bw.write(data + " " + score + " " + name);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void creatPool() {
		restart.setVisible(false);
		quit.setVisible(false);
		// set List
		ballList = new ArrayList<Ball>();
		holeList = new ArrayList<BlackHole>();
		kitList = new ArrayList<Kit>();
		portalList = new ArrayList<Portal>();
		// table = new Table(10, 10, 1000, 1200);
		// set Frame
		bf.creatUI(table);
		// set Board
		wall = new Rectangle(table.x, table.y, table.width, table.height);
		// set BlackHole Area
		blackHoleArea = new Rectangle(table.x + 200, table.y + 200, table.width - 400, table.height - 400);
		generateHole(holeAmount);
		// generate Balls
		generateBall(ballAmount);
		generatePortal(portalAmount);
		t = new Timer(10, table);
		currentTime = 0;
		currentTime2 = 0;
		gameTime = 120;
	}

	public void ballJudge() {
		int n = 0;
		for (int i = 0; i < ballList.size(); i++) {
			ballList.get(i).ifOutFaster(blackHoleArea);
			ballList.get(i).ifTouchBounce(player1);
			ballList.get(i).ifTouchBounce(player2);
			blackHoleEffect(ballList.get(i));
			ballList.get(i).ifTouchPortal(portalList);
			kitEffect(Table.ballList.get(i));
			ballList.get(i).ifTouchGoal(player1, wall);
			ballList.get(i).ifTouchGoal(player2, wall);
			if (i + 1 < ballList.size()) {
				for (int a = i + 1; a < ballList.size(); a++) {
					ballList.get(i).ifTouchBallBounce(ballList.get(a));
					n++;
				}
			}
			ballList.get(i).ifTouchWallBounce(Table.wall);
			ballList.get(i).move();
			clearBall(i);
		}
	}

	public void drawGame(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(table.x - 1, table.y - 1, table.width + 2, table.height + 2);
		g.setColor(new Color(252, 157, 154));
		g.drawImage(backGround, table.x, table.y, null);
		g.setColor(new Color(249, 205, 173));
		g.setColor(Color.black);
		g.setFont((new Font("Helvetica", Font.PLAIN, 50)));
		if (gameTime < 20)
			g.setColor(Color.red);
		else
			g.setColor(Color.white);
		g.drawString("" + gameTime + "s", table.x, table.y + 50);
		g.setColor(Color.WHITE);
		g.drawString("" + player1.name + " Score:" + player1.score, table.x + table.width - 500, table.y + 50);
		g.drawString("" + player2.name + " Score:" + player2.score, table.x + table.width - 500, table.y + table.height);
		player1.draw(g);
		player2.draw(g);
		Portal.drawPortal(g, portalList);
		Kit.draw(g, kitList);
		Ball.draw(g, ballList);
		BlackHole.draw(g, holeList);
	}

	public static void print2dArray(String[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int a = 0; a < arr[i].length; a++) {
				System.out.print(" " + arr[i][a]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void blackHoleEffect(Ball b) {
		for (int k = 0; k < Table.holeList.size(); k++) {
			Table.holeList.get(k).applyForce(b);
		}
	}

	public static void bubbleSort(String[][] arr) {
		boolean didSwap = false;
		for (int i = arr.length - 1; i > 0; i--) {
			for (int a = 0; a < i; a++) {
				if (Integer.parseInt(arr[a][1]) < Integer.parseInt(arr[a + 1][1])) {
					swap(arr, a, a + 1);
					didSwap = true;
				}
			}
			if (didSwap == false) {
				return;
			}
			didSwap = false;
		}
	}

	public static void kitEffect(Ball b) {
		for (int k = 0; k < kitList.size(); k++) {
			if (kitList.get(k).wasEatenBy(b)) {
				if (kitList.get(k).function.equals("splitKit")) {
					b.split();
					splitNum--;
				}
				if (kitList.get(k).function.equals("poison")) {
					b.delete();
					poisonNum++;
				}
				kitList.remove(k);
			}
		}
	}

	public static void swap(String[][] arr, int a, int b) {
		String temp = "";
		for (int i = 0; i < 3; i++) {
			temp = arr[a][i];
			arr[a][i] = arr[b][i];
			arr[b][i] = temp;
		}
	}

	public static void generatePortal(int portalAmount) {
		for (int i = 0; i < portalAmount; i++) {
			new Portal(
					Tool.getRandom((int) blackHoleArea.getX(),
							(int) (blackHoleArea.getX() + blackHoleArea.getWidth() - 80)),
					Tool.getRandom((int) blackHoleArea.getY(),
							(int) (blackHoleArea.getY() + blackHoleArea.getHeight() - 80)),
					80, 80);
		}
	}

	public static void clearBall(int b) {
		if (!ballList.get(b).useful)
			ballList.remove(b);
	}

	public static void drawLeaderBoard(Graphics g) {
		if (leaderBoard != null) {
			g.drawImage(leaderboard, table.x, table.y, null);
			g.setColor(Color.BLUE);
			g.setFont((new Font("Helvetica", Font.PLAIN, 50)));
			g.drawString("Name", 10, 50);
			g.drawString("Score", 320, 50);
			g.drawString("Data", 630, 50);
			int row = 150;
			int column = 10;
			g.setColor(Color.black);
			if (leaderBoard.length <= 10) {
				for (int r = 0; r < leaderBoard.length; r++) {
					for (int c = leaderBoard[r].length - 1; c >= 0; c--) {
						if (3 - c != 1) {
							if (c == 0)
								column += 200;
							else
								column += 310;
						}
						g.drawString(leaderBoard[r][c], column, row);
					}
					row += 100;
					column = 10;
				}
			} else {
				for (int r = 0; r < 10; r++) {
					for (int c = leaderBoard[r].length - 1; c >= 0; c--) {
						if (3 - c != 1) {
							if (c == 0)
								column += 200;
							else
								column += 310;
						}
						g.drawString(leaderBoard[r][c], column, row);
					}
					row += 100;
					column = 10;
				}
			}
		}
	}

	public static void playSound() {
		if (!clip.isRunning())
			clip.start(); // Start playing
	}

	public static void openSound() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(back);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception ex) {

			System.out.println("Error with playing sound.");

			ex.printStackTrace();

		}
	}
}
