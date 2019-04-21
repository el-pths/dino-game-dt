package dinosaur;

public class Main {

	public static boolean inGame = true;

	public static void main(String[] args) {
	    if (args.length > 0 && args[0].equals("chart")) {
	        chart.Chart.main(args);
	        return;
	    }
		Start.haveToChoosePort();
	}

	public static void startGame() {
		FrameAndListener.cactusesBehind = 0;
		FrameAndListener.score = 0;
		GameOver.restartButton.setSize(0, 0);
		Dino.rightLegUp = true;
		Dino.isJump = false;
		Dino.isNowInAir = false;
		Dino.pedometr = 0;
		Dino.nowBounceHeight = 0;
		inGame = true;
		Cactuses.cactusesAmount = 0;
		Clouds.cloudsAmount = 0;
		Field.setField();
	}

}
