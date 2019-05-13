package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

public class Pteros {

	public static Pteros pteros;
	public Ptero[] list;
	public int amount, amountGenerated;

	private static final int maxPterosAmount = 10, edgeOfField = -300;

	private static enum State {
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
		public TouchableRectangle[] touchRects;
		private int width, height, counterHoldingWing, restrictionHoldingWing;
		private State state;

		public Ptero() {
			this.state = State.WING_UP;
			this.width = 100;
			this.height = 90;
			this.distance = 1400;
			this.verticalIndent = 140;
			this.counterHoldingWing = 0;
			this.restrictionHoldingWing = 16;
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

		}

		public void draw(Graphics graphics) {
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
