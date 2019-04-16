package dinosaur;

import java.awt.Image;
import java.awt.event.KeyEvent;

public class Treatment {

	public static int makeJump() {
		double realHeight = 6000 + Math.random() * 4000, c = 10000, ceilHeight = 500; 
		int i = (int) ((1 - Math.exp(-realHeight / c)) * ceilHeight);
		return i;
	}

	public static boolean isDinoInCactus(int i) {
		boolean is = false;
		if (Dino.nowBounceHeight < (Cactuses.heightSize - Settings.PIXEL * 8)
				&& (((int) (Settings.START_WIDTH * 0.1) + Settings.PIXEL * 20)) > (Cactuses.distToCactus[i])
				&& (int) (Settings.START_WIDTH * 0.1) < (Cactuses.distToCactus[i] + Cactuses.widthSize
						+ Settings.PIXEL * 4))
			is = true;
		return is;
	}

	public static void processedPressedKey(int key) {
		if (Main.inGame) {
			if (!Dino.isNowInAir) {
				if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3))
					Dino.isJump = true;
				if (key == KeyEvent.VK_SPACE)
					Dino.jumpHeight = makeJump();
				if (key == KeyEvent.VK_1)
					Dino.jumpHeight = 400;
				if (key == KeyEvent.VK_2)
					Dino.jumpHeight = 250;
				if (key == KeyEvent.VK_3)
					Dino.jumpHeight = 100;
			}
		} else {
			if (key == KeyEvent.VK_R) {
				Main.startGame();
			}
		}
	}

	public static void recordField() {
		if (Cactuses.distToCactus[0] < -200) {
			for (int i = 0; i < Cactuses.cactusesAmount; i++) {
				Cactuses.distToCactus[i] = Cactuses.distToCactus[i + 1];
				Cactuses.cactusesType[i] = Cactuses.cactusesType[i + 1];
			}
			if (Cactuses.cactusesAmount < 50) {
				Cactuses.cactusesType[Cactuses.cactusesAmount - 1] = 0;
				Cactuses.distToCactus[Cactuses.cactusesAmount - 1] = 0;
			}
			Cactuses.cactusesAmount--;
		}
		for (int i = 0; i < Cactuses.cactusesAmount; i++) {
			if (Cactuses.distToCactus[i] > (int) (Settings.START_WIDTH * 0.1)
					&& (Cactuses.distToCactus[i] - Settings.START_WIDTH / 180) < (int) (Settings.START_WIDTH * 0.1))
				FrameAndListener.cactusesBehind++;
			Cactuses.distToCactus[i] -= Settings.START_WIDTH / 180;
		}
	}

	public static Image returnByNumber(int a) {
		switch (a) {
		case 1:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig1;
		case 2:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig2;
		case 3:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig3;
		case 4:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig4;
		case 5:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig5;
		case 6:
			Cactuses.widthSize = Settings.PIXEL * 16;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.b1ig6;
		case 7:
			Cactuses.widthSize = Settings.PIXEL * 44;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.d3if1;
		case 8:
			Cactuses.widthSize = Settings.PIXEL * 32;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.m2id1;
		default:
			Cactuses.widthSize = Settings.PIXEL * 32;
			Cactuses.heightSize = Settings.PIXEL * 31;
			return Cactuses.m2id2;
		}
	}

	public static void ifDinoIsInBounce() {
		if (Dino.isNowInAir) {
			if (Dino.isOurMoveUp
					&& Dino.nowBounceHeight + giveScenarium(Dino.nowBounceHeight, Dino.jumpHeight) >= Dino.jumpHeight) {
				Dino.nowBounceHeight = Dino.jumpHeight;
				Dino.isOurMoveUp = false;
			}
			if (!Dino.isOurMoveUp && Dino.nowBounceHeight - giveScenarium(Dino.nowBounceHeight, Dino.jumpHeight) <= 0) {
				Dino.nowBounceHeight = 0;
				Dino.isNowInAir = false;
			}
			if (Dino.isNowInAir) {
				if (Dino.isOurMoveUp) {
					Dino.nowBounceHeight += giveScenarium(Dino.nowBounceHeight, Dino.jumpHeight);
				} else {
					Dino.nowBounceHeight -= giveScenarium(Dino.nowBounceHeight, Dino.jumpHeight);
				}
			}
		}
		if (Dino.isJump) {
			Dino.isJump = false;
			Dino.nowBounceHeight += giveScenarium(Dino.nowBounceHeight, Dino.jumpHeight);
			Dino.isOurMoveUp = true;
			Dino.isNowInAir = true;
		}
	}

	private static final int giveScenarium(int ourHeightNow, int restriction) {
		double i = ((double) (ourHeightNow)) / ((double) (restriction));
		int j;
		if (i >= 0.93132749)
			j = (int) (1 * Settings.koefficient_paraboli);
		else {
			if (i >= 0.734693873)
				j = (int) (2.8 * Settings.koefficient_paraboli);
			else {
				if (i >= 0.510204082)
					j = (int) (4 * Settings.koefficient_paraboli);
				else {
					j = (int) (5.5 * Settings.koefficient_paraboli);
				}
			}
		}
		return j;
	}

	public static final void check() {
		if (Dino.pedometr >= Dino.amountSteps) {
			FrameAndListener.score++;
			Dino.pedometr = 0;
			if (Dino.rightLegUp)
				Dino.rightLegUp = false;
			else
				Dino.rightLegUp = true;
		}
	}

}
