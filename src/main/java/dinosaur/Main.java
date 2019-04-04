package dinosaur;

public class Main {
	
	static boolean inGame = true;
	
	public static void main(String[] args) {
		FrameAndListener.setFrame();
	}

	public static void startGame() {
		GameOver.again.setSize(0, 0);
		Dino.rightLegUp = true;
		Dino.isJump = false;
		Dino.isNowInAir = false;
		Dino.pedometr = 0;
		Dino.nowBounceHeight = 0;
		inGame = true;
		Cactuses.cactusesAmount = 0;
	}
	
}
