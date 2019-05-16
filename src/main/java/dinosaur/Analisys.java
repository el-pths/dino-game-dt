package dinosaur;

public class Analisys {

	// Think 0.6 is what we need
	private static double lastSE = 0.0, maxAccessibleValue = 0.6;
	private static int numberOfCalls = 0, step = 10000;

	public static void setGravityValueIfSELets(Filter filter) {
		double sum = 0, squaresSum = 0, amount, average;
		for (int i = 0; i < filter.points.size(); i++) {
			try {
				double currentValue = filter.points.get(i).rms;
				sum += currentValue;
				squaresSum += currentValue * currentValue;
			} catch (NullPointerException e) {
				//Do nothing
			}
		}
		amount = filter.points.size();
		average = sum / amount;
		lastSE = Math.sqrt((amount * average * average + squaresSum - 2 * average * sum) / amount);
		if (lastSE <= maxAccessibleValue) {
			numberOfCalls = 0;
			Filter.filter.calibrate();
			return;
		}
		if (numberOfCalls > step) {
			numberOfCalls -= step;
			maxAccessibleValue *= 1.17;
		}
		numberOfCalls++;
	}

}
