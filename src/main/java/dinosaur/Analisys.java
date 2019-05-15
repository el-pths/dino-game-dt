package dinosaur;

public class Analisys {

	// Think 0.6 is what we need
	private static double lastSE = 0.0, maxAccessibleValue = 0.6;

	public static void setGravityValueIfSELets(Filter filter) {
		double sum = 0, squaresSum = 0, amount, average;
		for (int i = 0; i < filter.points.size(); i++) {
			double currentValue = filter.points.get(i).rms;
			sum += currentValue;
			squaresSum += currentValue * currentValue;
		}
		amount = filter.points.size();
		average = sum / amount;
		lastSE = Math.sqrt((amount * average * average + squaresSum - 2 * average * sum) / amount);
		if (lastSE <= maxAccessibleValue)
			DataInterpretation.setNormalGravityValue((int) average);
	}

}
