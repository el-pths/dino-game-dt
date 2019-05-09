package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Dino {

	public static Dino dino;
	public String name;
	private DinoState state;
	private static Image leftLeg = Imagies.loadImage("/dinoleft.png"), rightLeg = Imagies.loadImage("/dinoright.png"),
			standing = Imagies.loadImage("/dinoisamazed.png");
	private int heightRestriction, bounceHeight, counterStandingOnTheLeg, restrictionStandingOnTheLeg, horizontalIndent,
			width;
	public int verticalIndent, height;
	public double jumpKoef;
	private boolean moveUp;
	private TouchablePoint[] touchPoints;

	private enum DinoState {
		JUMP, STAND, RUN_RIGHT_LEG, RUN_LEFT_LEG;
	}

	public Dino(String name, int restrictionStandingOnTheLeg, int horizontalIndent, int verticalIndent, int width,
			int height, double jumpKoefParab) {
		this.name = name;
		this.state = DinoState.STAND;
		this.bounceHeight = 0;
		this.heightRestriction = 300;
		this.moveUp = true;
		this.counterStandingOnTheLeg = 0;
		this.restrictionStandingOnTheLeg = restrictionStandingOnTheLeg;
		this.horizontalIndent = horizontalIndent;
		this.verticalIndent = verticalIndent;
		this.width = width;
		this.height = height;
		this.jumpKoef = jumpKoefParab;
		this.touchPoints = new TouchablePoint[10];
		this.setTouchRects();
	}

	private class TouchablePoint {
		private int leftIndent, upperIndent;

		public TouchablePoint(int leftIndent, int upperIndent) {
			this.leftIndent = leftIndent;
			this.upperIndent = upperIndent;
		}
	}

	private void setTouchRects() {
		touchPoints[0] = new TouchablePoint(78, 27);
		touchPoints[1] = new TouchablePoint(63, 47);
		touchPoints[2] = new TouchablePoint(55, 56);
		touchPoints[3] = new TouchablePoint(51, 62);
		touchPoints[4] = new TouchablePoint(47, 67);
		touchPoints[5] = new TouchablePoint(47, 84);
		touchPoints[6] = new TouchablePoint(33, 84);
		touchPoints[7] = new TouchablePoint(21, 84);
		touchPoints[8] = new TouchablePoint(6, 58);
		touchPoints[9] = new TouchablePoint(3, 54);
	}

	public void drawAndRecordParams(Graphics graphics, double position) {
		graphics.drawImage(this.getImage(), this.horizontalIndent, this.verticalIndent - this.bounceHeight, this.width,
				this.height, null);
		this.recordJumpIfJump(position);
		this.recordCounterStandingOnTheLeg();
		graphics.setColor(Color.RED);
		for (int i = 0; i < touchPoints.length; i++)
			if (touchPoints[i] != null)
				graphics.fillRect(touchPoints[i].leftIndent + horizontalIndent,
						touchPoints[i].upperIndent + verticalIndent, 1, 1);
	}

	public void startJump() {
		this.state = DinoState.JUMP;
	}

	private Image getImage() {
		if (Control.state == Control.State.GAME_OVER)
			return standing;
		else if (this.state == DinoState.RUN_LEFT_LEG)
			return leftLeg;
		else if (this.state == DinoState.RUN_RIGHT_LEG)
			return rightLeg;
		else
			return standing;
	}

	private void recordJumpIfJump(double position) {
		if (this.state == DinoState.JUMP) {
			position *= Window.FULL_PASS_TIME / Window.NORMALL_FULL_PASS_TIME;
			if (this.moveUp)
				this.bounceHeight += this.giveScenarium() * position;
			else
				this.bounceHeight -= this.giveScenarium() * position;
			if (this.moveUp && this.bounceHeight >= this.heightRestriction)
				this.moveUp = false;
			if (!this.moveUp && this.bounceHeight - this.giveScenarium() * position <= 0) {
				this.bounceHeight = 0;
				this.moveUp = true;
				this.state = DinoState.RUN_RIGHT_LEG;
			}
		}

	}

	private int giveScenarium() {
		double i = ((double) (this.bounceHeight)) / ((double) (this.heightRestriction));
		int j;
		if (i >= 0.93132749)
			j = (int) (this.jumpKoef);
		else {
			if (i >= 0.734693873)
				j = (int) (2.8 * this.jumpKoef);
			else {
				if (i >= 0.510204082)
					j = (int) (4 * this.jumpKoef);
				else {
					j = (int) (5.5 * this.jumpKoef);
				}
			}
		}
		return j;
	}

	private void recordCounterStandingOnTheLeg() {
		if (this.state == DinoState.RUN_LEFT_LEG || this.state == DinoState.RUN_RIGHT_LEG) {
			if (this.counterStandingOnTheLeg > this.restrictionStandingOnTheLeg) {
				if (this.state == DinoState.RUN_LEFT_LEG)
					this.state = DinoState.RUN_RIGHT_LEG;
				else
					this.state = DinoState.RUN_LEFT_LEG;
				this.counterStandingOnTheLeg = 0;
			}
			this.counterStandingOnTheLeg++;
		}
	}

}
