package dinosaur;

import dinosaur.Cactuses.Cactus;
import dinosaur.Pteros.Ptero;

public class EnemyLogic {

	private static int reactDist = 900;
	private static double possobilityPercents = 99;

	public static void reactIfCollisionHappened(Dino dino, Cactuses cactuses) {
		if (runThoughCactuses(dino, cactuses)) {
			Control.state = Control.State.SETTING_GO_M;
		}
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

	public static void reactIfCollisionHappened(Dino dino, Pteros pteros) {
		if (runThoughPteros(dino, pteros)) {
			Control.state = Control.State.SETTING_GO_M;
		}
	}

	private static boolean runThoughPteros(Dino dino, Pteros pteros) {
		boolean collision = false;
		for (int i = 0; i < pteros.amount && !collision; i++)
			if (pteros.list[i].distance > -100 && pteros.list[i].distance < 400)
				collision = runThoughTouchRects(dino, pteros.list[i]);
		return collision;
	}

	private static boolean runThoughTouchRects(Dino dino, Ptero currentPtero) {
		boolean collision = false;
		if (currentPtero.state == Pteros.State.WING_DOWN)
			for (int i = 0; currentPtero != null && i < currentPtero.touchRectsWingDown.length && !collision; i++)
				collision = runThoughTouchDinoPoints(dino,
						currentPtero.touchRectsWingDown[i].leftIndent + currentPtero.distance,
						currentPtero.verticalIndent + currentPtero.touchRectsWingDown[i].upperIndent
								+ currentPtero.touchRectsWingDown[i].height,
						currentPtero.touchRectsWingDown[i].leftIndent + currentPtero.distance
								+ currentPtero.touchRectsWingDown[i].width,
						currentPtero.verticalIndent + currentPtero.touchRectsWingDown[i].upperIndent);
		else
			for (int i = 0; currentPtero != null && i < currentPtero.touchRectsWingUp.length && !collision; i++)
				collision = runThoughTouchDinoPoints(dino,
						currentPtero.touchRectsWingUp[i].leftIndent + currentPtero.distance,
						currentPtero.verticalIndent + currentPtero.touchRectsWingUp[i].upperIndent
								+ currentPtero.touchRectsWingUp[i].height,
						currentPtero.touchRectsWingUp[i].leftIndent + currentPtero.distance
								+ currentPtero.touchRectsWingUp[i].width,
						currentPtero.verticalIndent + currentPtero.touchRectsWingUp[i].upperIndent);
		return collision;
	}

	private static boolean runThoughTouchDinoPoints(Dino dino, int leftRectBorder, int upperRectBorder,
			int rightRectBorder, int downRectBorder) {
		boolean collision = false;
		for (int i = 0; i < dino.touchPoints.length && !collision; i++)
			collision = isCollisionDetected(leftRectBorder, upperRectBorder, rightRectBorder, downRectBorder,
					dino.horizontalIndent + dino.touchPoints[i].leftIndent,
					dino.verticalIndent - dino.bounceHeight + dino.touchPoints[i].upperIndent);
		return collision;
	}

	private static boolean isCollisionDetected(int leftRectBorder, int upperRectBorder, int rightRectBorder,
			int downRectBorder, int pointX, int pointY) {
		return (pointX >= leftRectBorder && pointX <= rightRectBorder && pointY <= upperRectBorder
				&& pointY >= downRectBorder);
	}

	public static void generateEnemyIfItNeeds(Cactuses cactuses, Pteros pteros) {
		int longestDistTo = 0;
		if (cactuses.amount > 0 && cactuses.list[cactuses.amount - 1].distance > longestDistTo)
			longestDistTo = cactuses.list[cactuses.amount - 1].distance;
		if (pteros.amount > 0 && pteros.list[pteros.amount - 1].distance > longestDistTo)
			longestDistTo = pteros.list[pteros.amount - 1].distance;
		if (longestDistTo < reactDist || longestDistTo == 0) {
			if (Math.random() * 100 > possobilityPercents)
				if (pteros.amountGenerated * 3 > cactuses.amountGenerated || cactuses.amountGenerated == 0) {
					if (cactuses.amount <= cactuses.list.length)
						Cactuses.cactuses.generateNewCactus();
				} else {
					if (pteros.amount <= pteros.list.length)
						Pteros.pteros.generateNewPtero();
				}
		}
	}

	public static void setReactDist(int newReactDist) {
		reactDist = newReactDist;
	}

	public static void setPossobilityPercents(double newPossobilityInPersents) {
		possobilityPercents = newPossobilityInPersents;
	}
}
