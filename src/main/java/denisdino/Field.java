package denisdino;

import java.awt.Color;
import java.awt.Graphics;

public class Field {
	MainAndWindow main;
	static public int[] distToObject = new int[50], object$sType = new int[50];
	static public int objects$Amount = 0;

	public static void recordField() {
		if (distToObject[0] < -200) {
			for (int i = 0; i < objects$Amount; i++) {
				distToObject[i] = distToObject[i + 1];
				object$sType[i] = object$sType[i + 1];
			}
			if (objects$Amount < 50) {
				object$sType[objects$Amount - 1] = 0;
				distToObject[objects$Amount - 1] = 0;
			}
			objects$Amount--;
		}
		for (int i = 0; i < objects$Amount; i++) {
			distToObject[i] -= Settings.SCREEN_WIDTH / 180;
		}
	}

	public static void printCactuses(Graphics g) {
		for (int u = 0; u < objects$Amount; u++) {
			int[][] cactus = returnByNumber(object$sType[u]);
			if ((cactus[0][1] - 5) * Settings.PIXEL >= Dino.thisBounce$sHeight
					&& distToObject[u] <= (int) (Settings.SCREEN_WIDTH * 0.1 + Settings.PIXEL * 17)
					&& (distToObject[u] + cactus[0][0] * Settings.PIXEL) >= (int) (Settings.SCREEN_WIDTH * 0.1)
							+ Settings.PIXEL * 17)
				Logic.inGame = false;
			for (int i = 1; i < cactus.length; i++) {
				int x = 0;
				for (int j = 0; j < cactus[i].length; j++) {
					if (j % 2 != 0) {
						g.setColor(Color.BLACK);
						g.fillRect(Settings.PIXEL * i + distToObject[u],
								(int) (Settings.SCREEN_HEIGHT * 0.45) + Settings.PIXEL * x + 10 * Settings.PIXEL,
								Settings.PIXEL, Settings.PIXEL * cactus[i][j]);
						x += cactus[i][j];
					} else {
						g.setColor(Color.WHITE);
						g.fillRect(Settings.PIXEL * i + distToObject[u],
								(int) (Settings.SCREEN_HEIGHT * 0.45) + Settings.PIXEL * x + 10 * Settings.PIXEL,
								Settings.PIXEL, Settings.PIXEL * cactus[i][j]);
						x += cactus[i][j];
					}
				}
			}

		}
	}

	public static int[][] returnByNumber(int a) {
		if (a == 1)
			return Cactus.cactusBig;
		if (a == 2)
			return Cactus.cactusMid;
		if (a == 3)
			return Cactus.cactusSmall;
		else
			return Cactus.cactusTrio1;
	}
}
