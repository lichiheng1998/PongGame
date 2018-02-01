package pong2;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SpringLayout;
import javax.swing.JInternalFrame;
import java.awt.FlowLayout;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

public class StartingPanel extends JPanel {
	private JTextField holeInput;
	private JTextField player1Name;
	private JTextField player2Name;
	private JTextField ballInput;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public StartingPanel() {
		setBackground(new Color(51, 204, 153));
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 204, 255), new Color(0, 204, 204),
				new Color(0, 204, 51), new Color(0, 204, 0)));

		player1Name = new JTextField();
		player1Name.setForeground(new Color(51, 0, 255));
		player1Name.setBounds(303, 203, 135, 27);
		player1Name.setHorizontalAlignment(SwingConstants.CENTER);
		player1Name.setColumns(10);
		setLayout(null);
		add(player1Name);

		player2Name = new JTextField();
		player2Name.setForeground(new Color(51, 0, 255));
		player2Name.setHorizontalAlignment(SwingConstants.CENTER);
		player2Name.setBounds(303, 281, 135, 27);
		player2Name.setColumns(10);
		add(player2Name);

		holeInput = new JTextField();
		holeInput.setForeground(new Color(51, 0, 255));
		holeInput.setBounds(303, 359, 135, 27);
		holeInput.setHorizontalAlignment(SwingConstants.CENTER);
		holeInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		holeInput.setColumns(10);
		add(holeInput);

		ballInput = new JTextField();
		ballInput.setForeground(new Color(51, 0, 255));
		ballInput.setBounds(303, 437, 135, 27);
		ballInput.setHorizontalAlignment(SwingConstants.CENTER);
		ballInput.setColumns(10);
		add(ballInput);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.setBackground(new Color(0, 255, 255));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("ËÎÌו", Font.PLAIN, 23));
		btnNewButton.setBounds(318, 512, 120, 57);
		buttonGroup.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Table.holeAmount = Integer.parseInt(holeInput.getText());
					Table.ballAmount = Integer.parseInt(ballInput.getText());
					Table.player1 = new Player(Table.table.x + Table.table.width / 2 - 75, 110, 150, 30, 1, "North");
					Table.player1.setKey(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
					Table.player1.setColor(new Color(254, 67, 101));
					Table.player2 = new Player(Table.table.x + Table.table.width / 2 - 75, 1080, 150, 30, 1, "South");
					Table.player2.setKey(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
					Table.player2.setColor(new Color(131, 175, 155));
					Table.player1.name = player1Name.getText();
					Table.player2.name = player2Name.getText();
					Table.GameOver = false;
				} catch (NumberFormatException i) {
					System.out.println("you can't type in string");
					Table.GameOver = true;
				}
				if (!Table.GameOver) {
					holeInput.setText("");
					ballInput.setText("");
					player1Name.setText("");
					player2Name.setText("");
					Table.creatPool();
					Table.t.start();
					Table.startingFrame.setVisible(false);
					Table.bf.setVisible(true);
					Table.playSound();
				}
			}
		});
		add(btnNewButton);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(290, 422, 0, 0);
		add(horizontalBox);

		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setBounds(113, 457, 0, 0);
		add(horizontalBox_1);
		this.setSize(735, 741);

		JLabel lblNameOfPlayer = new JLabel("Name of player one");
		lblNameOfPlayer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		lblNameOfPlayer.setBounds(253, 167, 222, 21);
		add(lblNameOfPlayer);

		JLabel lblPongPongPong = new JLabel("PONG PONG PONG !");
		lblPongPongPong.setForeground(new Color(51, 102, 102));
		lblPongPongPong.setFont(new Font("Cambria", Font.ITALIC, 48));
		lblPongPongPong.setBounds(195, 54, 424, 63);
		add(lblPongPongPong);

		JLabel lblPoweredByAdam = new JLabel("powered by Adam ");
		lblPoweredByAdam.setBounds(566, 705, 154, 21);
		add(lblPoweredByAdam);

		JLabel label_1 = new JLabel("Name of player two");
		label_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		label_1.setBounds(253, 245, 222, 21);
		add(label_1);

		JLabel lblBlackholeAmount = new JLabel("BlackHole Amount");
		lblBlackholeAmount.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		lblBlackholeAmount.setBounds(253, 323, 222, 21);
		add(lblBlackholeAmount);

		JLabel lblBallAmount = new JLabel("Ball Amount");
		lblBallAmount.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		lblBallAmount.setBounds(284, 401, 154, 21);
		add(lblBallAmount);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\lichi\\Pictures\\ad4.png"));
		lblNewLabel.setBounds(521, 459, 214, 231);
		add(lblNewLabel);
	}
}
