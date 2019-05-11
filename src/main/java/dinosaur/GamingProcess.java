package dinosaur;

import java.awt.Color;
import java.awt.Graphics;

public class GamingProcess {

	public static void draw(Graphics graphics) {
		fillFon(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.draw(graphics);
		// draw pause button
	}

	private static void fillFon(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1344, 540);
	}

	public static void checkAndRecord(double position, int windowWidth, int windowHeight) {
		// Button setTouch
		Field.field.recordField(position);
		Clouds.clouds.record(position);
		Cactuses.cactuses.record(position);
		Dino.dino.record(position);
		Clouds.clouds.record(position);
		CollisionLogic.reactIfCollisionHappened(Dino.dino, Cactuses.cactuses);
	}

}
