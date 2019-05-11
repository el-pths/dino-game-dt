package dinosaur;

import java.awt.Graphics;

import javax.swing.JFrame;

import dinosaur.DButton.buttonPurpose;

public class GameOver {
	
	public static void setRestartButton(JFrame window) {
		int width = 180, height = 170;
		DButton.setButton(window, Window.bufferedImageWidth / 2 - width / 2, Window.bufferedImageHeight / 2 * 9 / 8,
				width, height, DImage.restartButtonImg, buttonPurpose.RESTART_GAME);
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.draw(graphics);
		Window.makeSmoothBlur(5, 500);
		DImage.gameoverImg.draw(graphics);
		DButton.restartButton.draw(graphics);
	}

	public static void correctButtonsTouchableSpace(int windowWidth, int windowHeight) {
		DButton.restartButton.setTouchableLocation(windowWidth, windowHeight);
	}

}
