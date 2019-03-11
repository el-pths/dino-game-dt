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
