package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Countdown {

	private static long previousTime = -1, sumTime = 0;
	private static int maxSquareSideValue = 200;
	private static count countNow = count.ONE;
	private static int oneStepTime = 2000, koefOfAnimationSlowness = 1;

	private static enum count {
		ONE, TWO, THREE;
	}

	public static void drawCountdown(Graphics graphics) {
		if (previousTime == -1) {
			previousTime = System.currentTimeMillis();
		} else {
			loadSumTime();
			changeCountIfItNeeds();
			fillFon(graphics);
			Field.field.draw(graphics);
			Dino.dino.draw(graphics);
			int squareSide = (int) ((double) maxSquareSideValue * (1000.0 / (double) sumTime));
			graphics.drawImage(getImageOfCountNow(), 672 - squareSide / 2, 270 - squareSide / 2, squareSide, squareSide,
					null);
		}
	}

	private static void loadSumTime() {
		sumTime += (System.currentTimeMillis() - previousTime) / koefOfAnimationSlowness;
		previousTime = System.currentTimeMillis();
	}

	private static void changeCountIfItNeeds() {
		if (sumTime > oneStepTime)
			switch (countNow) {
			case ONE:
				countNow = count.TWO;
				sumTime -= oneStepTime;
				break;
			case TWO:
				countNow = count.THREE;
				sumTime -= oneStepTime;
				break;
			case THREE:
				sumTime = 0;
				previousTime = -1;
				countNow = count.ONE;
				Dino.dino.startJump();
				Window.setKeyboardListener();
				Window.setFullPassTime(22.0);
				Control.state = Control.State.GAMMING_PROCESS;
			}
	}

	private static void fillFon(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1344, 540);
	}

	private static Image getImageOfCountNow() {
		switch (countNow) {
		case ONE:
			return DImage.one;
		case TWO:
			return DImage.two;
		default:
			return DImage.three;
		// Add loading icon instead default giving icon of "three"
		}
	}

}
