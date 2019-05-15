package dinosaur;

public class DataInterpretation {

	private static int normalGravityValue = 0;
	private static boolean isNormalGravityValueSetted = false;

	public static void setNormalGravityValue(int value) {
		isNormalGravityValueSetted = false;
		normalGravityValue = value;
	}

	public static int getNormalGravityValue() {
		return normalGravityValue;
	}

	public static boolean isNormalGravityValueSetted() {
		return isNormalGravityValueSetted;
	}

}
