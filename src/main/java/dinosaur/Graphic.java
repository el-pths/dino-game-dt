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
		for (int u = 0; u < Cactuses.cactusesAmount; u++) {
			int[][] cactus = Treatment.returnByNumber(Cactuses.cactusesType[u]);
		//	if ((cactus[0][1] - 5) * Sets.PIXEL >= Dino.nowBounceHeight
					//&& Cactuses.distToCactus[u] <= (int) (Sets.width * 0.1 + Sets.PIXEL * 17)
					//&& (Cactuses.distToCactus[u] + cactus[0][0] * Sets.PIXEL) >= (int) (Sets.width * 0.1)
					//		+ Sets.PIXEL * 17)
				//Main.inGame = false;
			for (int i = 1; i < cactus.length; i++) {
				int x = 0;
				for (int j = 0; j < cactus[i].length; j++) {
					if (j % 2 != 0) {
						g.setColor(Color.BLACK);
						g.fillRect(Sets.PIXEL * i + Cactuses.distToCactus[u],
								(int) (Sets.height * 0.45) + Sets.PIXEL * x + 10 * Sets.PIXEL, Sets.PIXEL,
								Sets.PIXEL * cactus[i][j]);
						x += cactus[i][j];
					} else {
						g.setColor(Color.WHITE);
						g.fillRect(Sets.PIXEL * i + Cactuses.distToCactus[u],
								(int) (Sets.height * 0.45) + Sets.PIXEL * x + 10 * Sets.PIXEL, Sets.PIXEL,
								Sets.PIXEL * cactus[i][j]);
						x += cactus[i][j];
					}
				}
			}

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
