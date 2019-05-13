package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import dinosaur.Control.State;

public class Dino {

	public static Dino dino, presentable;
	public String name;
	private DinoState state;
	private static Image leftLeg, rightLeg, standing;
	private int heightRestriction, counterStandingOnTheLeg, restrictionStandingOnTheLeg;
	public int bounceHeight, verticalIndent, height, horizontalIndent, width;
	private double jumpKoef;
	private boolean moveUp;
	public boolean isJumpMeaningStartingGameFinished;
	public TouchablePoint[] touchPoints;

	private static double koefStep = 0.1;

	private enum DinoState {
		JUMP, STAND, RUN_RIGHT_LEG, RUN_LEFT_LEG;
	}

	private Dino(String name, int restrictionStandingOnTheLeg, int horizontalIndent, int verticalIndent,
			double jumpKoefParab) {
		this.name = name;
		this.bounceHeight = 0;
		this.heightRestriction = 300;
		this.moveUp = true;
		if (Control.state != State.SETTING_S_M) {
			this.isJumpMeaningStartingGameFinished = false;
			this.state = DinoState.JUMP;
		} else if (restrictionStandingOnTheLeg == 0) {
			this.isJumpMeaningStartingGameFinished = true;
			this.state = DinoState.STAND;
		} else {
			this.isJumpMeaningStartingGameFinished = true;
			this.state = DinoState.RUN_RIGHT_LEG;
		}
		this.counterStandingOnTheLeg = 0;
		this.restrictionStandingOnTheLeg = restrictionStandingOnTheLeg;
		this.horizontalIndent = horizontalIndent;
		this.verticalIndent = verticalIndent;
		this.width = 80;
		this.height = 82;
		this.jumpKoef = jumpKoefParab;
		this.touchPoints = new TouchablePoint[10];
		this.setTouchRects();
	}

	public static void setDino(String name, int restrictionStandingOnTheLeg, int horizontalIndent, int verticalIndent,
			double jumpKoefParab) {
		dino = new Dino(name, restrictionStandingOnTheLeg, horizontalIndent, verticalIndent, jumpKoefParab);
		presentable = new Dino(name, 0, 1000, 340, jumpKoefParab);
	}

	public static void loadDinoImagies() {
		leftLeg = DImage.loadImage("/dinoleft.png");
		rightLeg = DImage.loadImage("/dinoright.png");
		standing = DImage.loadImage("/dinoisamazed.png");
	}

	public class TouchablePoint {
		public int leftIndent, upperIndent;

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

	public void draw(Graphics graphics) {
		graphics.drawImage(this.getImage(), this.horizontalIndent, this.verticalIndent - this.bounceHeight, this.width,
				this.height, null);
		drawTouchPoints(graphics);
	}

	private void drawTouchPoints(Graphics graphics) {
		graphics.setColor(Color.RED);
		for (int i = 0; i < touchPoints.length; i++)
			graphics.fillRect(horizontalIndent + touchPoints[i].leftIndent,
					verticalIndent + touchPoints[i].upperIndent - bounceHeight, 1, 1);
	}

	public void record(double position) {
		recordJumpIfJump(position);
		recordCounterStandingOnTheLeg();
	}

	private void recordJumpIfJump(double position) {
		if (this.state == DinoState.JUMP) {
			position *= Window.FULL_PASS_TIME / Window.NORMAL_FULL_PASS_TIME;
			if (this.moveUp)
				this.bounceHeight += this.giveScenarium() * position;
			else
				this.bounceHeight -= this.giveScenarium() * position;
			if (this.moveUp && this.bounceHeight >= this.heightRestriction)
				this.moveUp = false;
			if (!this.moveUp && this.bounceHeight - this.giveScenarium() * position <= 0) {
				this.bounceHeight = 0;
				this.moveUp = true;
				if (restrictionStandingOnTheLeg == 0)
					this.state = DinoState.STAND;
				else
					this.state = DinoState.RUN_RIGHT_LEG;
				if (!isJumpMeaningStartingGameFinished)
					isJumpMeaningStartingGameFinished = true;
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
		if ((this.state == DinoState.RUN_LEFT_LEG || this.state == DinoState.RUN_RIGHT_LEG)
				&& restrictionStandingOnTheLeg != 0) {
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

	public void changeDinoKoefParab(boolean isThisPlus) {
		if (isThisPlus)
			jumpKoef += koefStep;
		else
			jumpKoef -= koefStep;
	}

	public double getJumpKoef() {
		return jumpKoef;
	}

	public void setJumpKoef(double newKoef) {
		jumpKoef = newKoef;
	}

}
