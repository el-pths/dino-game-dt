package dinosaur;

public class Generators {
	public static int generateDistanceToNextCactus() {
		return ((int) (Math.random() * Sets.width) + (int) (Sets.width));
	}

	public static int generateNumberOfThisCactus$sType() {
		return (int) (Math.round(Math.random() * 3 + 1));
	}
}
