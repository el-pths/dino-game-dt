package denisdino;

import java.awt.Color;
import java.awt.Graphics;

public class Dino {
	MainAndWindow main;
	public static int[][] pose = { {} };
	public static boolean isNowInAir = false, isJump = false, isOurMoveUp = true;
	public static int pedometr = 0, thisBounce$sHeight = 0, timeInAir;
	public static int[][] stand = { { 8, 6, 8 }, { 10, 5, 7 }, { 11, 5, 6 }, { 12, 5, 5 }, { 12, 6, 4 }, { 11, 11 },
			{ 10, 10, 1, 1 }, { 9, 10, 3 }, { 8, 10, 4 }, { 1, 18, 3 }, { 0, 22 }, { 0, 1, 1, 15, 4, 1 }, { 0, 16, 6 },
			{ 0, 15, 7 }, { 0, 8, 2, 1, 11 }, { 0, 6, 1, 1, 2, 2, 10 }, { 0, 6, 1, 1, 14 }, { 0, 6, 1, 1, 14 },
			{ 0, 6, 16 }, { 1, 5, 16 } };
	public static int[][] rightLegRaised = { { 8, 6, 8 }, { 10, 5, 7 }, { 11, 5, 6 }, { 12, 5, 5 }, { 12, 6, 4 },
			{ 11, 11 }, { 10, 10, 1, 1 }, { 9, 10, 3 }, { 8, 10, 4 }, { 1, 17, 4 }, { 0, 19, 3 },
			{ 0, 1, 1, 15, 1, 1, 3 }, { 0, 16, 6 }, { 0, 15, 7 }, { 0, 8, 2, 1, 11 }, { 0, 6, 1, 1, 2, 2, 10 },
			{ 0, 6, 1, 1, 14 }, { 0, 6, 1, 1, 14 }, { 0, 6, 16 }, { 1, 5, 16 } };
	public static int[][] leftLegRaised = { { 8, 6, 8 }, { 10, 5, 7 }, { 11, 5, 6 }, { 12, 5, 5 }, { 12, 6, 4 },
			{ 11, 10, 1 }, { 10, 9, 1, 1, 1 }, { 9, 9, 4 }, { 8, 10, 4 }, { 1, 18, 3 }, { 0, 22 },
			{ 0, 1, 1, 15, 4, 1 }, { 0, 16, 6 }, { 0, 15, 7 }, { 0, 8, 2, 1, 11 }, { 0, 6, 1, 1, 2, 2, 10 },
			{ 0, 6, 1, 1, 14 }, { 0, 6, 1, 1, 14 }, { 0, 6, 16 }, { 1, 5, 16 } };
	public int[][] postition = stand;

	public static void printDino(Graphics g) {
		pedometr++;
		for (int i = 0; i < pose.length; i++) {
			int x = 0;
			for (int j = 0; j < pose[i].length; j++) {
				if (j % 2 != 0) {
					g.setColor(new Color(75, 60, 60));
					g.fillRect((int) (Settings.SCREEN_WIDTH * 0.1) + Settings.PIXEL * i,
							(int) (Settings.SCREEN_HEIGHT * 0.58) + Settings.PIXEL * x - thisBounce$sHeight,
							Settings.PIXEL, Settings.PIXEL * pose[i][j]);
					x += pose[i][j];
				} else {
					x += pose[i][j];
				}
			}
		}
	}
}