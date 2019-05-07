package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

public class Dino {

	public static Dino dino;
	public String name;
	private DinoState state;
	private static Image leftLeg = Imagies.loadImage("/dinoleft.png"), rightLeg = Imagies.loadImage("/dinoright.png"),
			standing = Imagies.loadImage("/dinoisamazed.png");
	private int heightRestriction, bounceHeight, counterStandingOnTheLeg, restrictionStandingOnTheLeg, horizontalIndent,
			verticalIndent, width, height;
	private boolean moveUp;

	private enum DinoState {
		JUMP, STAND, RUN_RIGHT_LEG, RUN_LEFT_LEG;
	}

	public Dino(String name) {
		this.name = name;
		this.state = DinoState.STAND;
		this.bounceHeight = 0;
		this.heightRestriction = 200;
		this.moveUp = true;
		this.counterStandingOnTheLeg = 0;
		this.restrictionStandingOnTheLeg = 7;
		this.horizontalIndent = 134;
		this.verticalIndent = 315;
		this.width = 80;
		this.height = 88;
	}

	public void drawAndRecordDinoParams(Graphics graphics) {
		graphics.drawImage(this.getImage(), this.horizontalIndent, this.verticalIndent - this.bounceHeight, this.width,
				this.height, null);
		this.recordJumpIfJump();
		this.recordCounterStandingOnTheLeg();
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

	private void recordJumpIfJump() {
		if (this.state == DinoState.JUMP) {
			if (this.moveUp)
				this.bounceHeight += this.giveScenarium();
			else
				this.bounceHeight -= this.giveScenarium();
			if (this.moveUp && this.bounceHeight >= this.heightRestriction)
				this.moveUp = false;
			if (!this.moveUp && this.bounceHeight - this.giveScenarium() <= 0) {
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
			j = (int) (2.5);
		else {
			if (i >= 0.734693873)
				j = (int) (2.8 * 2.5);
			else {
				if (i >= 0.510204082)
					j = (int) (4 * 2.5);
				else {
					j = (int) (5.5 * 2.5);
				}
			}
		}
		return j;
	}

	public void startJump() {
		this.state = DinoState.JUMP;
	}

}
