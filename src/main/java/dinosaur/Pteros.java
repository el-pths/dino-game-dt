package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Pteros {

	public static Pteros pteros;
	public Ptero[] list;
	public int amount, amountGenerated;

	private static final int maxPterosAmount = 10, edgeOfField = -300;

	public static enum State {
		WING_UP, WING_DOWN;
	}

	private Pteros() {
		this.amount = 0;
		this.list = new Ptero[maxPterosAmount];
		this.amountGenerated = 0;
	}

	public static void setPteros() {
		pteros = new Pteros();
	}

	public class Ptero {
		public int distance, verticalIndent;
		public TouchableRectangle[] touchRectsWingUp, touchRectsWingDown;
		private int width, height, counterHoldingWing, restrictionHoldingWing;
		public State state;

		public Ptero() {
			this.state = State.WING_UP;
			this.width = 100;
			this.height = 90;
			this.distance = 1400;
			this.verticalIndent = 140;
			this.counterHoldingWing = 0;
			this.restrictionHoldingWing = 16;
			this.setTouchableRectangles();
		}

		public class TouchableRectangle {
			public int leftIndent, upperIndent, width, height;

			public TouchableRectangle(int leftIndent, int upperIndent, int width, int height) {
				this.leftIndent = leftIndent;
				this.upperIndent = upperIndent;
				this.width = width;
				this.height = height;
			}
		}

		private void setTouchableRectangles() {
			touchRectsWingDown = new TouchableRectangle[4];
			touchRectsWingDown[0] = new TouchableRectangle(22, 18, 8, 23);
			touchRectsWingDown[1] = new TouchableRectangle(4, 23, 18, 18);
			touchRectsWingDown[2] = new TouchableRectangle(35, 36, 8, 50);
			touchRectsWingDown[3] = new TouchableRectangle(43, 45, 54, 18);
			touchRectsWingUp = new TouchableRectangle[4];
			touchRectsWingUp[0] = new TouchableRectangle(22, 18, 8, 23);
			touchRectsWingUp[1] = new TouchableRectangle(4, 23, 18, 18);
			touchRectsWingUp[2] = new TouchableRectangle(30, 5, 41, 40);
			touchRectsWingUp[3] = new TouchableRectangle(39, 50, 52, 9);
		}

		public void draw(Graphics graphics) {
			setTouchableRectangles();
			graphics.drawImage(getImageDependingState(), distance, verticalIndent, width, height, null);
		}

		private Image getImageDependingState() {
			if (state == State.WING_DOWN)
				return DImage.pteroDown;
			else
				return DImage.pteroUp;
		}

		public void record(double position) {
			recordDistance(position);
			recordCounterStandingOnTheLeg();
		}

		private void recordDistance(double position) {
			distance -= (int) (Control.recordingStep * position);
		}

		private void recordCounterStandingOnTheLeg() {
			if (counterHoldingWing > restrictionHoldingWing) {
				switch (state) {
				case WING_UP:
					state = State.WING_DOWN;
					break;
				case WING_DOWN:
					state = State.WING_UP;
					break;
				}
				counterHoldingWing = 0;
			}
			counterHoldingWing++;
		}
	}

	public void record(double position) {
		for (int i = 0; i < amount; i++)
			list[i].record(position);
		ifItNeedsDeleteFirst();
	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < amount; i++)
			list[i].draw(graphics);
		drawTouchRects(graphics);
	}

	private void drawTouchRects(Graphics graphics) {
		graphics.setColor(Color.GREEN);
		for (int i = 0; i < amount; i++)
			if (pteros.list[i].state == State.WING_DOWN) {
				for (int j = 0; j < list[i].touchRectsWingDown.length; j++)
					graphics.fillRect(list[i].distance + list[i].touchRectsWingDown[j].leftIndent,
							list[i].verticalIndent + list[i].touchRectsWingDown[j].upperIndent,
							list[i].touchRectsWingDown[j].width, list[i].touchRectsWingDown[j].height);
			} else {
				for (int j = 0; j < list[i].touchRectsWingUp.length; j++)
					graphics.fillRect(list[i].distance + list[i].touchRectsWingUp[j].leftIndent,
							list[i].verticalIndent + list[i].touchRectsWingUp[j].upperIndent,
							list[i].touchRectsWingUp[j].width, list[i].touchRectsWingUp[j].height);
			}
	}

	public void generateNewPtero() {
		list[amount] = new Ptero();
		amount++;
		amountGenerated++;
	}

	private void ifItNeedsDeleteFirst() {
		if (amount > 0 && list[0].distance < edgeOfField)
			removeFirst();
	}

	private void removeFirst() {
		for (int i = 0; i < amount && i < maxPterosAmount; i++)
			list[i] = list[i + 1];
		amount--;
	}
}
