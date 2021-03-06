package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Graphic {
    
    private static Font BIG_FONT = new Font("Comic Sans MS", Font.BOLD, 42);
    private static Font NORM_FONT = new Font("Comic Sans MS", Font.BOLD, 28);
    
	public static void writeScore(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(NORM_FONT);
		g.drawString("Jumped cactuses :", (int) (Settings.START_WIDTH * 12 / 20), Settings.START_HEIGHT / 15);
		g.drawString(Integer.toString(FrameAndListener.cactusesBehind), (int) (Settings.START_WIDTH * 12 / 20),
				Settings.START_HEIGHT / 8);
		g.drawString("Your score :", (int) (Settings.START_WIDTH * 17 / 20), Settings.START_HEIGHT / 15);
		g.drawString(Integer.toString(FrameAndListener.score), (int) (Settings.START_WIDTH * 17 / 20),
				Settings.START_HEIGHT / 8);
	}

	public static void drawDino(Graphics g) {
		Dino.pedometr++;
		if (Main.inGame)
			if (Dino.rightLegUp)
				g.drawImage(Dino.dinoright, (int) (Settings.START_WIDTH * 0.1),
						(int) (Settings.START_HEIGHT * 0.58) - Dino.nowBounceHeight, Settings.PIXEL * 20,
						Settings.PIXEL * 22, null);
			else
				g.drawImage(Dino.dinoleft, (int) (Settings.START_WIDTH * 0.1),
						(int) (Settings.START_HEIGHT * 0.58) - Dino.nowBounceHeight, Settings.PIXEL * 20,
						Settings.PIXEL * 22, null);
		else
			g.drawImage(Dino.dinodead, (int) (Settings.START_WIDTH * 0.1),
					(int) (Settings.START_HEIGHT * 0.58) - Dino.nowBounceHeight, Settings.PIXEL * 20,
					Settings.PIXEL * 22, null);
	}

	public static void drawFirstFloor(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Settings.START_WIDTH, Settings.START_HEIGHT);
		if (!Things.isButtonRemoved && Start.choseAnyPort)
			g.drawImage(Things.remove, Settings.closePortX, Settings.closePortY, Settings.closePortXX,
					Settings.closePortYY, null);
		for (int i = 0; i < Field.fieldType.length; i++)
			g.drawImage(Field.defaultTypes[Field.fieldType[i] - 1],
					Field.distToNearestPart + (i - 1) * Field.fieldPartWidth, 367, Field.fieldPartWidth, 50, null);
	}

	public static void drawCactuses(Graphics g) {
		for (int i = 0; i < Cactuses.cactusesAmount; i++) {
			g.drawImage(Treatment.returnByNumber(Cactuses.cactusesType[i]), Cactuses.distToCactus[i],
					(int) (Settings.START_HEIGHT * 0.45) + Settings.PIXEL * 10, Cactuses.widthSize, Cactuses.heightSize,
					null);
			if (Treatment.isDinoInCactus(i))
				Main.inGame = false;
		}
	}

	public static void drawClouds(Graphics g) {
		for (int i = 0; i < Clouds.cloudsAmount; i++) {
			g.drawImage(Clouds.cloud, Clouds.distToCloud[i], Clouds.cloudHeight[i],
					Clouds.sizeByType[Clouds.cloudType[i] - 1][0], Clouds.sizeByType[Clouds.cloudType[i] - 1][1], null);
		}
	}
	
	public static void drawCalibration(Graphics g, int stage) {
	    String msg = stage < 1 ? "Stand still!" : "Calibration";
	    FontMetrics m = g.getFontMetrics(BIG_FONT);
	    g.setColor(Color.GREEN);
		g.setFont(BIG_FONT);
	    g.drawString(msg,
	            (Settings.START_WIDTH - m.stringWidth(msg)) / 2,
	            (Settings.START_HEIGHT - m.getHeight()) / 2);
	}

	public static Image loadImage(String resourcePath) {
		try {
			return ImageIO.read(Graphics.class.getResource(resourcePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
