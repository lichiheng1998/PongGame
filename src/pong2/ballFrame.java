package pong2;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class ballFrame extends JFrame{
	 public void creatUI(JPanel t){
		 System.out.println(t.getHeight());
		 System.out.println(t.getWidth());
		  this.setSize(t.getWidth()+45, t.getHeight()+80);
		  this.setResizable(true);
		  this.getContentPane().setBackground(Color.WHITE);  
	      this.setLocationRelativeTo(null);
	      this.add(t);
	      this.setTitle("PONG!");
	 }
}


