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

	public static int generateCloudTypeNum() {
		return (int) (Math.round(Math.random() * 2 + 1));
	}

	public static int generateFieldPartTypeNubm() {
		int i = (int) (Math.round(Math.random() * 22) + 1);
		if (i == 1 || i == 2 || i == 16) {
			if (Math.random() > 0.6)
				return i;
			else
				return (int) (Math.round(Math.random() * 22) + 1);
		} else if (i == 15) {
			if (Math.random() > 0.2)
				return i;
			else
				return (int) (Math.round(Math.random() * 22) + 1);
		}
		return i;
	}

	public static int generateNumberOfThisCactus$sType() {
		return (int) (Math.round(Math.random() * 8 + 1));
	}
}
