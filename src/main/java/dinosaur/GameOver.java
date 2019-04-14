package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameOver {

	private static Image gameover = Graphic.loadImage("/gameover.png");
	private static Image restartButtonImg = Graphic.loadImage("/restartButton.png");

	static JFrame frame = new JFrame("Dino Game");
	static JButton restartButton = new JButton("");

	public static void gameOver(Graphics g, JFrame f) {
		Things.setButtonReplay();
		Graphic.drawFirstFloor(g);
		Graphic.drawCactuses(g);
		Graphic.drawDino(g);
		Graphic.writeScore(g);
		g.drawImage(gameover, Sets.gameoverLabelX, Sets.gameoverLabelY, Sets.gameoverLabelXX, Sets.gameoverLabelYY, null);
		g.drawImage(restartButtonImg, Sets.gameoverLabelX + (Sets.gameoverLabelXX - Sets.restartXX) / 2,
				Sets.gameoverLabelY + Sets.gameoverLabelYY + Sets.height / 16, Sets.restartXX, Sets.restartYY, null);
	}
}
