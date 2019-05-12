package dinosaur;

import dinosaur.Cactuses.Cactus;

public class CollisionLogic {

	public static void reactIfCollisionHappened(Dino dino, Cactuses cactuses) {
		if (runThoughCactuses(dino, cactuses)) 
			Control.state = Control.State.SETTING_GO_M;
	}

	private static boolean runThoughCactuses(Dino dino, Cactuses cactuses) {
		boolean collision = false;
		for (int i = 0; i < cactuses.amount && !collision; i++)
			if (cactuses.list[i].distance > -100 && cactuses.list[i].distance < 400)
				collision = runThoughTouchRects(dino, cactuses.list[i]);
		return collision;
	}

	private static boolean runThoughTouchRects(Dino dino, Cactus currentCactus) {
		boolean collision = false;
		for (int i = 0; currentCactus != null && i < currentCactus.touchRects.length && !collision; i++)
			collision = runThoughTouchDinoPoints(dino, currentCactus.touchRects[i].leftIndent + currentCactus.distance,
					currentCactus.verticalIndent + currentCactus.touchRects[i].upperIndent
							+ currentCactus.touchRects[i].height,
					currentCactus.touchRects[i].leftIndent + currentCactus.distance + currentCactus.touchRects[i].width,
					currentCactus.verticalIndent + currentCactus.touchRects[i].upperIndent);
		return collision;
	}

	private static boolean runThoughTouchDinoPoints(Dino dino, int leftRectBorder, int upperRectBorder,
			int rightRectBorder, int downRectBorder) {
		boolean collision = false;
		for (int i = 0; i < dino.touchPoints.length; i++)
			collision = isCollisionDetected(leftRectBorder, upperRectBorder, rightRectBorder, downRectBorder,
					dino.horizontalIndent - dino.touchPoints[i].leftIndent + dino.width,
					dino.verticalIndent - dino.bounceHeight + dino.touchPoints[i].upperIndent);
		return collision;
	}

	private static boolean isCollisionDetected(int leftRectBorder, int upperRectBorder, int rightRectBorder,
			int downRectBorder, int pointX, int pointY) {
		return (pointX >= leftRectBorder && pointX <= rightRectBorder && pointY <= upperRectBorder
				&& pointY >= downRectBorder);
	}

}
