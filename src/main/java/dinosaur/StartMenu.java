package dinosaur;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class StartMenu {

	private static DButton startGame;
	public static boolean isMenuSetted = false;

	public static void set(JFrame window) {
		startGame = new DButton(window, 200, 200, Imagies.loadImage("/start4.png"), "StartGame");
		isMenuSetted = true;
	}

	public static void drawStartMenu(Graphics graphics) {
		fillFon(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.draw(graphics);
		Window.makeBlur(4);
		startGame.draw(graphics);
	}

	private static void fillFon(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1344, 540);
	}

	public static void correctAndRecord(Graphics graphics, int windowWidth, int windowHeight, double position) {
		startGame.setTouchableLocation(windowWidth, windowHeight);
		Field.field.recordField(position);
		Clouds.clouds.record(position);
		Cactuses.cactuses.record(position);
		Dino.dino.record(position);
		Clouds.clouds.record(position);
	}

}
