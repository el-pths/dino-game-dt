package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Countdown {

	private static long previousTime = -1, sumTime = 0;
	private static int squareSide = 80;
	private static Count countNow = Count.NINE;
	private static int oneStepTime = 1000, koefOfAnimationSlowness = 1;

	private static enum Count {
		ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
	}

	public static void drawCountdown(Graphics graphics) {
		if (previousTime == -1) {
			previousTime = System.currentTimeMillis();
		} else {
			Background.draw(graphics);
			Field.field.draw(graphics);
			Clouds.clouds.draw(graphics);
			Cactuses.cactuses.draw(graphics);
			Dino.dino.draw(graphics);
			drawWords(graphics);
			graphics.drawImage(getImageOfCountNow(), 617, 275, squareSide, squareSide, null);
			loadSumTime();
			changeCountIfItNeeds();
		}
	}

	private static void loadSumTime() {
		sumTime += (System.currentTimeMillis() - previousTime) / koefOfAnimationSlowness;
		previousTime = System.currentTimeMillis();
	}

	private static void changeCountIfItNeeds() {
		if (sumTime > oneStepTime)
			switch (countNow) {
			case NINE:
				countNow = Count.EIGHT;
				sumTime -= oneStepTime;
				break;
			case EIGHT:
				countNow = Count.SEVEN;
				sumTime -= oneStepTime;
				break;
			case SEVEN:
				countNow = Count.SIX;
				sumTime -= oneStepTime;
				break;
			case SIX:
				countNow = Count.FIVE;
				sumTime -= oneStepTime;
				break;
			case FIVE:
				countNow = Count.FOUR;
				sumTime -= oneStepTime;
				break;
			case FOUR:
				countNow = Count.THREE;
				sumTime -= oneStepTime;
				break;
			case THREE:
				countNow = Count.TWO;
				sumTime -= oneStepTime;
				break;
			case TWO:
				countNow = Count.ONE;
				sumTime -= oneStepTime;
				break;
			case ONE:
				sumTime = 0;
				previousTime = -1;
				countNow = Count.NINE;
				Control.state = Control.State.CALIBRATION;
			}
	}

	private static void drawWords(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		graphics.drawString("Get ready to calibrate gravity acceleration value.", 10, 70);
		graphics.drawString("Please stand and lock in this position.", 10, 200);
		if (countNow == Count.ONE)
			graphics.drawString("Calibration will starts in     second.", 10, 330);
		else
			graphics.drawString("Calibration will starts in     seconds.", 10, 330);
	}

	private static Image getImageOfCountNow() {
		switch (countNow) {
		case ONE:
			return DImage.one;
		case TWO:
			return DImage.two;
		case THREE:
			return DImage.three;
		case FOUR:
			return DImage.four;
		case FIVE:
			return DImage.five;
		case SIX:
			return DImage.six;
		case SEVEN:
			return DImage.seven;
		case EIGHT:
			return DImage.eight;
		default:
			return DImage.nine;
		// Add loading icon instead default giving icon of "three"
		}
	}

}
