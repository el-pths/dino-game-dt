package denisdino;

public class Cactus {
	MainAndWindow main;
	public static int[][] cactusBig = { { 15, 30 }, { 6, 6 }, { 5, 8 }, { 6, 8 }, { 11, 3 }, { 11, 3, 15 }, { 1, 29 },
			{ 0, 30 }, { 0, 30 }, { 0, 30 }, { 1, 29 }, { 13, 3, 13 }, { 13, 3 }, { 7, 9 }, { 6, 9 }, { 7, 7 } };
	public static int[][] cactusSmall = { { 14, 20 }, { 20 }, { 13, 6 }, { 12, 7 }, { 13, 7 }, { 17, 3, 8 }, { 9, 19 },
			{ 8, 20 }, { 8, 20 }, { 9, 19 }, { 17, 3, 8 }, { 13, 7 }, { 12, 7 }, { 13, 6 }, { 20 } };
	public static int[][] cactusMid = { { 13, 28 }, { 9, 8, 11 }, { 9, 10 }, { 10, 10 }, { 16, 4, 10 }, { 3, 27 },
			{ 2, 28 }, { 2, 28 }, { 3, 27 }, { 12, 4, 14 }, { 12, 4 }, { 8, 7 }, { 7, 7 }, { 8, 5 } };
	public static int[][] cactusTrio1 = { { 37, 30 }, { 11, 8 }, { 10, 10 }, { 11, 10 }, { 17, 4, 10 }, { 4, 27 },
			{ 3, 28 }, { 3, 28 }, { 4, 27 }, { 11, 4, 16 }, { 11, 4, 16 }, { 7, 7 }, { 6, 7, 3, 5 }, { 7, 5, 3, 7, 9 },
			{ 16, 7, 8 }, { 20, 3, 8 }, { 12, 19 }, { 11, 20 }, { 11, 20 }, { 12, 19 }, { 20, 3, 8 }, { 16, 7, 8 },
			{ 15, 7, 9 }, { 8, 6, 2 }, { 7, 8, 7 }, { 8, 8, 6 }, { 13, 3, 6 }, { 13, 3, 15 }, { 1, 29 }, { 0, 30 },
			{ 0, 30 }, { 0, 30 }, { 1, 29 }, { 14, 3, 13 }, { 14, 3 }, { 7, 9 }, { 6, 9 }, { 7, 7 } };

	public static int generateDistanceToNextCactus() {
		return ((int) (Math.random() * Settings.SCREEN_WIDTH) + (int) (Settings.SCREEN_WIDTH / 3));
	}

	public static int generateNumberOfThisCactus$sType() {
		return (int) (Math.round(Math.random() * 3 + 1));
	}
}