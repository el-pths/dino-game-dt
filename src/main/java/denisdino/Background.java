package denisdino;

import java.awt.Color;
import java.awt.Graphics;

public class Background {
	MainAndWindow main;
	public static int[][] hummock1 = { { 2, 1, 1 }, { 1, 1, 2 }, { 0, 1, 3 }, { 0, 1, 3 }, { 0, 1, 3 }, { 1, 1, 2 },
			{ 2, 1, 1 }, { 2, 1, 1 } };
	public static int[][] hummock2 = { { 2, 1, 1 }, { 1, 1, 2 }, { 1, 1, 2 }, { 0, 1, 3 }, { 0, 1, 3 }, { 0, 1, 3 },
			{ 1, 1, 2 }, { 2, 1, 1 } };

	public static void printFirstFloor(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, (int) (Settings.SCREEN_HEIGHT * 0.7), Settings.SCREEN_WIDTH, Settings.PIXEL / 2);
	}
}