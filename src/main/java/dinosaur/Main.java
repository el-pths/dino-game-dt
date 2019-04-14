package dinosaur;

public class Main {
	
	public static boolean inGame = true;
	
	public static void main(String[] args) {
		FrameAndListener.setFrame();
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
	}
	
}
