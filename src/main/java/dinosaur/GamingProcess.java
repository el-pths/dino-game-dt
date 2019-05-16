package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosaur.DButton.buttonPurpose;

public class GamingProcess {

	public static void setPauseButton(Window window) {
		int width = 38, height = 50, leftIndent = 20, upperIndent = 20;
		DButton.setButton(window, leftIndent, upperIndent - 10, width, height, DImage.pauseButtonImg,
				buttonPurpose.PAUSE);
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Pteros.pteros.draw(graphics);
		Dino.dino.draw(graphics);
		DButton.pauseButton.draw(graphics);
	}

	public static void checkAndRecord(double position, int windowWidth, int windowHeight) {
		DButton.pauseButton.setTouchableLocation(windowWidth, windowHeight);
		Field.field.recordField(position);
		Clouds.clouds.record(position);
		Cactuses.cactuses.record(position);
		Pteros.pteros.record(position);
		Dino.dino.record(position);
		Clouds.clouds.record(position);
		EnemyLogic.reactIfCollisionHappened(Dino.dino, Cactuses.cactuses);
		if (Control.state != Control.State.GAMMING_PROCESS)
			return;
		EnemyLogic.reactIfCollisionHappened(Dino.dino, Pteros.pteros);
		if (Control.state != Control.State.GAMMING_PROCESS)
			return;
		EnemyLogic.generateEnemyIfItNeeds(Cactuses.cactuses, Pteros.pteros);
	}

	public static void waitingMode(Graphics graphics, double position) {
		Background.draw(graphics);
		Field.field.draw(graphics);
		Dino.dino.draw(graphics);
		drawLabel(graphics);
	}

	private static void drawLabel(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Comic Sans MS", Font.BOLD, 70));
		graphics.drawString("Don't move. Calibration in progress", 50, 100);
	}

}
