package dinosaur;

public class Generators {
	public static int generateDistanceToNextCactus() {
		return ((int) (Math.random() * Settings.START_WIDTH) + (int) (Settings.START_WIDTH * 5 / 6));
	}

	public static int generateFistanceToNextCloud() {
		return ((int) (Math.random() * Settings.START_WIDTH) + (int) (Settings.START_WIDTH * 5 / 6))
				+ Settings.START_WIDTH / 2;
	}

	public static int generateCloudHeight() {
		return (int) ((Settings.START_HEIGHT * 0.18) + (Math.random() * Settings.START_HEIGHT * 0.25));
	}

	public static int generateNumberOfThisCactus$sType() {
		return (int) (Math.round(Math.random() * 8 + 1));
	}
}
