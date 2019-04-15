package dinosaur;

public class Generators {
	public static int generateDistanceToNextCactus() {
		return ((int) (Math.random() * Settings.width) + (int) (Settings.width));
	}

	public static int generateNumberOfThisCactus$sType() {
		return (int) (Math.round(Math.random() * 8 + 1));
	}
}
