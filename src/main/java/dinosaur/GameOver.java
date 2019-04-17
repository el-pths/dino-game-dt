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

	public static void gameOver(Graphics g) {
		Things.setButtonReplay();
		Graphic.drawFirstFloor(g);
		Graphic.drawClouds(g);
		Graphic.drawCactuses(g);
		Graphic.drawDino(g);
		Graphic.writeScore(g);
		g.drawImage(restartButtonImg, Settings.gameoverLabelX + (Settings.gameoverLabelXX - Settings.restartXX) / 2,
				Settings.gameoverLabelY + Settings.gameoverLabelYY + Settings.height / 16, Settings.restartXX,
				Settings.restartYY, null);
		g.drawImage(gameover, Settings.gameoverLabelX, Settings.gameoverLabelY, Settings.gameoverLabelXX,
				Settings.gameoverLabelYY, null);
		Things.setButtonGetSettings();
		Settings.drawSettingButton(g);
	}
}
