package dinosaur;

import java.awt.event.KeyEvent;

public class Treatment {
	static void processedPressedKey(int key) {
		if (Main.inGame) {
			if (!Dino.isNowInAir) {
				if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3))
					Dino.isJump = true;
				if (key == KeyEvent.VK_SPACE)
					Dino.jumpHeight = 250;
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
			Cactuses.distToCactus[i] -= Sets.width / 180;
		}
	}

	public static int[][] returnByNumber(int a) {
		if (a == 1)
			return Cactuses.cactusBig;
		if (a == 2)
			return Cactuses.cactusMid;
		if (a == 3)
			return Cactuses.cactusSmall;
		else
			return Cactuses.cactusMid;
	}

	public static void ifDinoIsInBounce() {
		if (Dino.isNowInAir){
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
			j = (int) (1 * Sets.koefficient_paraboli);
		else {
			if (i >= 0.734693873)
				j = (int) (2.8 * Sets.koefficient_paraboli);
			else {
				if (i >= 0.510204082)
					j = (int) (4 * Sets.koefficient_paraboli);
				else {
					j = (int) (5.5 * Sets.koefficient_paraboli);
				}
			}
		}
		return j;
	}

	public static final void check() {
		if (Dino.pedometr >= Dino.amountSteps) {
			Dino.pedometr = 0;
			if (Dino.rightLegUp)
				Dino.rightLegUp = false;
			else
				Dino.rightLegUp = true;
		}
	}

}
