package dinosaur;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public class Treatment {

	public static int makeJump(int realHeight) {
		double c = 55, ceilHeight = 320;
		int i = (int) (((1 - Math.exp((-realHeight + ceilHeight / 3.8) / c)) * ceilHeight));
		return i;
	}

	public static boolean isDinoInCactus(int i) {
		boolean is = false;
		boolean firstTest = Dino.nowBounceHeight < (Cactuses.heightSize / 3)
				&& (((int) (Settings.START_WIDTH * 0.1) + Settings.PIXEL * 18)) > (Cactuses.distToCactus[i])
				&& (int) (Settings.START_WIDTH * 0.1) < (Cactuses.distToCactus[i] + Cactuses.widthSize
						- Settings.PIXEL * 5);
		boolean secondTest = Dino.nowBounceHeight > Cactuses.heightSize / 3
				&& Dino.nowBounceHeight <= Cactuses.heightSize
				&& (((int) (Settings.START_WIDTH * 0.1) + Settings.PIXEL * 18)) > (Cactuses.distToCactus[i] + 40)
				&& (int) (Settings.START_WIDTH * 0.1) < (Cactuses.distToCactus[i] + Cactuses.widthSize - 20);
		if (firstTest || secondTest) {
			is = true;
		}
		return is;
	}

	public static void processedPressedKey(int key) {
		if (Main.inGame) {
			if (!Dino.isNowInAir) {
				switch (key) {
				case KeyEvent.VK_SPACE:
					Dino.jumpHeight = makeJump((int) (165 + Math.random() * 35));
					Dino.isJump = true;
					try {
						Sound.makeJumpSound();
					} catch (LineUnavailableException | IOException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
				case KeyEvent.VK_1:
					Dino.jumpHeight = 400;
					Dino.isJump = true;
					try {
						Sound.makeJumpSound();
					} catch (LineUnavailableException | IOException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
				case KeyEvent.VK_2:
					Dino.jumpHeight = 250;
					Dino.isJump = true;
					try {
						Sound.makeJumpSound();
					} catch (LineUnavailableException | IOException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
				case KeyEvent.VK_3:
					Dino.jumpHeight = 100;
					Dino.isJump = true;
					try {
						Sound.makeJumpSound();
					} catch (LineUnavailableException | IOException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		} else {
			if (key == KeyEvent.VK_R) {
				Main.startGame();
			}
		}
	}

	public static void recordClouds() {
		if (Clouds.distToCloud[0] < -300) {
			for (int i = 0; i < Clouds.cloudsAmount - 1; i++) {
				Clouds.distToCloud[i] = Clouds.distToCloud[i + 1];
				Clouds.cloudHeight[i] = Clouds.cloudHeight[i + 1];
				Clouds.cloudType[i] = Clouds.cloudType[i + 1];
			}
			if (Clouds.cloudsAmount < 50) {
				Clouds.distToCloud[Clouds.cloudsAmount - 1] = 0;
				Clouds.cloudHeight[Clouds.cloudsAmount - 1] = 0;
				Clouds.cloudType[Clouds.cloudsAmount - 1] = 0;
			}
			Clouds.cloudsAmount--;
		}
		for (int i = 0; i < Clouds.cloudsAmount; i++)
			Clouds.distToCloud[i] -= Settings.START_WIDTH / 400;
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
		if (Field.distToNearestPart < -Field.fieldPartWidth) {
			for (int i = 0; i < Field.fieldType.length - 1; i++)
				Field.fieldType[i] = Field.fieldType[i + 1];
			Field.fieldType[Field.fieldType.length - 1] = Generators.generateFieldPartTypeNubm();
			Field.distToNearestPart += Field.fieldPartWidth;
		}
		Field.distToNearestPart -= Settings.START_WIDTH / 180;
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
