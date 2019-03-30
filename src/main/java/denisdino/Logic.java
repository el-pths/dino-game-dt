package denisdino;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Logic {
	MainAndWindow main;
	static boolean inGame = true;
	static BufferedImage gameOverImage;
	
	static {
	    try {
	        gameOverImage = ImageIO.read(Logic.class.getResourceAsStream("/gameover.png"));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

	public static void printGameOver(Graphics g) {
		g.drawImage(gameOverImage,
				Settings.SCREEN_WIDTH / 3, Settings.SCREEN_HEIGHT / 3,
				Settings.SCREEN_WIDTH / 3, Settings.SCREEN_HEIGHT / 3, null);
	}

	public static void ifDinoIsInBounce() {
		if (Dino.thisBounce$sHeight >= Settings.JUMP_HEIGHT && Dino.timeInAir < Settings.time$sRestrictionToFlight) {
			Dino.timeInAir++;
		} else {
			Dino.timeInAir = 0;
			if (Dino.thisBounce$sHeight > 0) {
				if (Dino.thisBounce$sHeight >= Settings.JUMP_HEIGHT)
					Dino.isOurMoveUp = false;
				if (Dino.isOurMoveUp)
					Dino.thisBounce$sHeight += Settings.PIXEL * 3;
				else
					Dino.thisBounce$sHeight -= Settings.PIXEL * 3;
			} else {
				Dino.isNowInAir = false;
				Dino.thisBounce$sHeight = 0;
			}
			if (Dino.isJump) {
				Dino.isNowInAir = true;
				Dino.isJump = false;
				Dino.thisBounce$sHeight += Settings.PIXEL * 4;
				Dino.isOurMoveUp = true;
			}
		}
	}
}
import java.awt.Color;
import java.awt.Graphics;

public class Logic {
	MainAndWindow main;
	static boolean inGame = true;
	public static int[][] GameOver = { { 2, 4, 5, 6, 1 }, { 1, 6, 3, 8 }, { 0, 2, 4, 2, 2, 1, 6, 1 },
			{ 0, 1, 6, 1, 2, 1, 6, 1 }, { 0, 1, 3, 1, 2, 1, 2, 1, 6, 1 }, { 0, 1, 3, 4, 2, 1, 6, 1 },
			{ 0, 1, 3, 4, 2, 8 }, { 0, 1, 3, 4, 3, 6, 1 }, { 18 }, { 2, 6, 2, 5, 3 }, { 1, 7, 2, 6, 2 },
			{ 0, 2, 3, 1, 9, 2, 1 }, { 0, 1, 4, 1, 10, 2 }, { 0, 2, 3, 1, 9, 2, 1 }, { 1, 7, 2, 6, 2 },
			{ 2, 6, 2, 5, 3 }, { 18 }, { 0, 8, 2, 8 }, { 0, 8, 2, 1, 2, 2, 2, 1 }, { 1, 2, 7, 1, 2, 2, 2, 1 },
			{ 2, 2, 6, 1, 2, 2, 2, 1 }, { 1, 2, 7, 1, 2, 2, 2, 1 }, { 0, 8, 2, 1, 2, 2, 2, 1 }, { 0, 8, 2, 1, 6, 1 },
			{ 18 }, { 0, 8, 2, 8 }, { 0, 1, 2, 2, 2, 1, 2, 8 }, { 0, 1, 2, 2, 2, 1, 2, 1, 4, 1, 2 },
			{ 0, 1, 2, 2, 2, 1, 2, 1, 4, 2, 1 }, { 0, 1, 2, 2, 2, 1, 2, 1, 3, 4 }, { 0, 1, 2, 2, 2, 1, 2, 5, 1, 2 },
			{ 0, 1, 6, 1, 3, 4, 2, 1 } };

	public static void printGameOver(Graphics g) {
		for (int i = 0; i < GameOver.length; i++) {
			int x = 0;
			g.setColor(Color.RED);
			for (int j = 0; j < GameOver[i].length; j++) {
				if (j % 2 != 0) {
					g.fillRect((int) (Settings.SCREEN_WIDTH * 0.4) + Settings.PIXEL * 2 * i,
							(int) (Settings.SCREEN_HEIGHT * 0.3) + Settings.PIXEL * 2 * x, Settings.PIXEL * 2,
							Settings.PIXEL * 2 * GameOver[i][j]);
					x += GameOver[i][j];
				} else {
					x += GameOver[i][j];
				}
			}
		}
	}

	public static void ifDinoIsInBounce() {
		if (Dino.thisBounce$sHeight >= Settings.JUMP_HEIGHT && Dino.timeInAir < Settings.time$sRestrictionToFlight) {
			Dino.timeInAir++;
		} else {
			if (Dino.timeInAir >= Settings.time$sRestrictionToFlight)
				Dino.isOurMoveUp = false;
			if (Dino.isOurMoveUp && Dino.thisBounce$sHeight
					+ giveScenarium(Dino.thisBounce$sHeight, Settings.JUMP_HEIGHT) >= Settings.JUMP_HEIGHT) {
				Dino.thisBounce$sHeight = Settings.JUMP_HEIGHT;
			}
			if (!Dino.isOurMoveUp
					&& Dino.thisBounce$sHeight - giveScenarium(Dino.thisBounce$sHeight, Settings.JUMP_HEIGHT) <= 0) {
				Dino.thisBounce$sHeight = 0;
				Dino.isNowInAir = false;
			}
			if (Dino.isNowInAir) {
				if (Dino.isOurMoveUp) {
					Dino.thisBounce$sHeight += giveScenarium(Dino.thisBounce$sHeight, Settings.JUMP_HEIGHT);
				} else {
					Dino.thisBounce$sHeight -= giveScenarium(Dino.thisBounce$sHeight, Settings.JUMP_HEIGHT);
				}
			}
			if (Dino.isJump) {
				Dino.isJump = false;
				Dino.thisBounce$sHeight += giveScenarium(Dino.thisBounce$sHeight, Settings.JUMP_HEIGHT);
				Dino.isOurMoveUp = true;
				Dino.isNowInAir = true;
				Dino.timeInAir = 0;
			}
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
}
