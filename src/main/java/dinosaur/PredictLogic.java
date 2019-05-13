package dinosaur;

public class PredictLogic {

	private static int reactDistance = 25;

	public static void predictCollisionAndReact() {
		checkCactusesDistances();
	}

	private static void checkCactusesDistances() {
		for (int i = 0; i < Cactuses.cactuses.amount; i++)
			if (Cactuses.cactuses.list[i].distance > Dino.dino.horizontalIndent
					&& Cactuses.cactuses.list[i].distance < Dino.dino.horizontalIndent + Dino.dino.width
							+ reactDistance)
				react();
	}

	private static void react() {
		Dino.dino.startJump();
	}

}
