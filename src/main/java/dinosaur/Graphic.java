package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Graphic {

	public static void writeScore(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
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
		g.setColor(Color.BLACK);
		g.fillRect(0, (int) (Settings.START_HEIGHT * 0.7), Settings.START_WIDTH, Settings.PIXEL / 2);
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

	public static Image loadImage(String resourcePath) {
		try {
			return ImageIO.read(Graphics.class.getResource(resourcePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
