package dinosaur;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GameOver {
	
	static Image gameover = Graphic.loadImage("/gameover.png");
	
	static JFrame frame = new JFrame("Dino Game");
	static JButton again = new JButton("Play again");
	
	public static void gameOver(Graphics g, JFrame f) {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(Sets.width, Sets.height);
		f.add(new FrameAndListener());
		f.setVisible(true);
		f.setLayout(null);
		
		again.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		again.setBounds(Sets.againX, Sets.againY, Sets.againXX, Sets.againYY);
		again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.startGame();
			}
		});
		f.add(again);
		
		Graphic.drawFirstFloor(g);
		Graphic.drawCactuses(g);
		Graphic.drawDino(g);
		g.drawImage(gameover, Sets.width / 4, Sets.height / 3, Sets.width / 2,
				Sets.height / 3, null);
	}
}
