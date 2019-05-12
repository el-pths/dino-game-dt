package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

public class Countdown {

	private static long previousTime = -1, sumTime = 0;
	private static int maxSquareSideValue = 200;
	private static count countNow = count.THREE;
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
			Background.draw(graphics);
			Field.field.draw(graphics);
			Clouds.clouds.draw(graphics);
			Cactuses.cactuses.draw(graphics);
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
			case THREE:
				countNow = count.TWO;
				sumTime -= oneStepTime;
				break;
			case TWO:
				countNow = count.ONE;
				sumTime -= oneStepTime;
				break;
			case ONE:
				sumTime = 0;
				previousTime = -1;
				countNow = count.THREE;
				Control.state = Control.State.SETTING_G_P;
			}
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
