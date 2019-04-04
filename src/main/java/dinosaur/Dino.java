package dinosaur;

import java.awt.Image;

public class Dino {
	static int nowBounceHeight = 0, pedometr = 0, jumpHeight = 200;
	static boolean isNowInAir = false, isJump = false, isOurMoveUp = true, rightLegUp = true;
	final static int amountSteps = 7;
	
	static Image dinodead = Graphic.loadImage("/dinodead.png");
	static Image dinoright = Graphic.loadImage("/dinoright.png");
	static Image dinoleft = Graphic.loadImage("/dinoleft.png");
}
