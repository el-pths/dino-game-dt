package dinosaur;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class StartMenu {

	private static DButton startGame;

	private static void setStartMenu(JFrame window) {
		startGame = new DButton(window, 700, 100, 200, 200, Imagies.loadImage("/restartButton.png"), "StartGame");
	}

	public static void showStartMenu(Graphics graphics, JFrame window, double position) {
		if (startGame == null)
			setStartMenu(window);
		fillFon(graphics);
		showStartButtonAndCorrectTouchableLocation(graphics, window.getWidth(), window.getHeight());
		Field.field.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.drawAndRecordParams(graphics, position);
		if (!Control.pause) {
			Field.field.recordField(position);
			Cactuses.cactuses.record(position);
		}
	}

	private static void fillFon(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1344, 540);
	}

	private static void showStartButtonAndCorrectTouchableLocation(Graphics graphics, int windowWidth,
			int windowHeight) {
		graphics.drawImage(startGame.icon, startGame.horizontalIndent, startGame.verticalIndent, startGame.width,
				startGame.height, null);
		startGame.setTouchableLocation(windowWidth, windowHeight);
	}

}
