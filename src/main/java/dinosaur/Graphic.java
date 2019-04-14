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
		g.drawString("Jumped cactuses :", (int) (Sets.width * 12 / 20), Sets.height / 15);
		g.drawString(Integer.toString(FrameAndListener.cactusesBehind), (int) (Sets.width * 12 / 20), Sets.height / 8);
		g.drawString("Your score :", (int) (Sets.width * 17 / 20), Sets.height / 15);
		g.drawString(Integer.toString(FrameAndListener.score), (int) (Sets.width * 17 / 20), Sets.height / 8);
	}

	public static void drawDino(Graphics g) {
		Dino.pedometr++;
		if (Main.inGame)
			if (Dino.rightLegUp)
				g.drawImage(Dino.dinoright, (int) (Sets.width * 0.1), (int) (Sets.height * 0.58) - Dino.nowBounceHeight,
						Sets.PIXEL * 20, Sets.PIXEL * 22, null);
			else
				g.drawImage(Dino.dinoleft, (int) (Sets.width * 0.1), (int) (Sets.height * 0.58) - Dino.nowBounceHeight,
						Sets.PIXEL * 20, Sets.PIXEL * 22, null);
		else
			g.drawImage(Dino.dinodead, (int) (Sets.width * 0.1), (int) (Sets.height * 0.58) - Dino.nowBounceHeight,
					Sets.PIXEL * 20, Sets.PIXEL * 22, null);
	}

	public static void drawFirstFloor(Graphics g) {
		g.setColor(Color.WHITE);
		if (Main.inGame) {
			g.fillRect(0, 0, Sets.width, Sets.height);
		} else {
			g.fillRect(0, 0, Sets.width, Sets.againY);
			g.fillRect(0, Sets.againY, Sets.againX, Sets.againYY);
			g.fillRect(Sets.againX + Sets.againXX, Sets.againY, Sets.width - Sets.againX - Sets.againXX, Sets.againYY);
			g.fillRect(0, Sets.againY + Sets.againYY, Sets.width, Sets.height - Sets.againY - Sets.againYY);
		}
		if (!Things.isButtonRemoved && Start.choseAnyPort)
			g.drawImage(Things.remove, Sets.closePortX, Sets.closePortY, Sets.closePortXX, Sets.closePortYY, null);
		g.setColor(Color.BLACK);
		g.fillRect(0, (int) (Sets.height * 0.7), Sets.width, Sets.PIXEL / 2);
	}

	public static void drawCactuses(Graphics g) {
		for (int i = 0; i < Cactuses.cactusesAmount; i++) {
			g.drawImage(Treatment.returnByNumber(Cactuses.cactusesType[i]), Cactuses.distToCactus[i],
					(int) (Sets.height * 0.45) + Sets.PIXEL * 10, Cactuses.widthSize, Cactuses.heightSize, null);
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
