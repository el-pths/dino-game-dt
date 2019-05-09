package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;

public class Cactuses {

	public static Cactuses cactuses;

	public int amount;
	public Cactus[] list;

	private static final int maxCactusesAmount = 10, edgeOfField = -300;
	private static Image[] imgs;
	private static String[] types = { "b1ig1", "b1ig2", "b1ig3", "b1ig4", "b1ig5", "b1ig6", "m2id1", "m2id2", "d3if1" };

	public Cactuses() {
		this.list = new Cactus[maxCactusesAmount];
		this.amount = 0;
	}

	public static void setCactuses() {
		cactuses = new Cactuses();
	}

	private class Cactus {
		public int distance;
		private TouchableRectangle[] touchRects;
		public Image icon;
		private int width, height;
		private String type;

		public Cactus() {
			this.touchRects = new TouchableRectangle[3];
			this.setType();
			this.setIcon();
			this.setSizes();
			this.distance = 1400;
			this.setTouchableRectangles();
		}

		private class TouchableRectangle {
			private int leftIndent, upperIndent, width, height;

			public TouchableRectangle(int leftIndent, int upperIndent, int width, int height) {
				this.leftIndent = leftIndent;
				this.upperIndent = upperIndent;
				this.width = width;
				this.height = height;
			}
		}

		private void setTouchableRectangles() {
			touchRects[0] = new TouchableRectangle(10, 10, 10, 10);
		}

		private void setType() {
			this.type = types[(int) (Math.random() / 0.112)];
		}

		private void setIcon() {
			this.icon = imgs[(Arrays.asList(types).indexOf(this.type))];
		}

		private void setSizes() {
			this.height = (int) ((double) Dino.dino.height * 1.4);
			this.width = (int) ((double) this.icon.getWidth(null)
					* ((double) this.height / (double) this.icon.getHeight(null)));
		}

		private void checkToRemove() {
			if (distance < edgeOfField)
				remove();
		}

		private void remove() {
			for (int i = 0; i < amount && i < maxCactusesAmount - 1; i++) {
				list[i] = list[i + 1];
			}
			amount--;
		}

		private void record(double position) {
			this.distance -= (int) (Control.recordingStep * position);
		}

		public void draw(Graphics graphics) {
			graphics.drawImage(icon, distance, Dino.dino.height + Dino.dino.verticalIndent - height + 15, width, height,
					null);
			checkToRemove();
			graphics.setColor(Color.BLUE);
			for (int i = 0; i < touchRects.length; i++)
				if (touchRects[i] != null)
					graphics.fillRect(distance + touchRects[i].leftIndent,
							Dino.dino.height + Dino.dino.verticalIndent - height + 15 + touchRects[i].upperIndent,
							touchRects[i].width, touchRects[i].height);
		}

	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < amount; i++)
			list[i].draw(graphics);
		cactuses.ifItNeedsGenerateCactus();
	}

	public void ifItNeedsGenerateCactus() {
		if (Math.random() > 0.9) {
			createNewCactus();
		}
	}

	public void createNewCactus() {
		if (amount < maxCactusesAmount) {
			list[amount] = new Cactus();
			amount++;
		}
	}

	public void record(double position) {
		for (int i = 0; i < this.amount; i++)
			this.list[i].record(position);
	}

	public static void loadCactusesImagies() {
		imgs = new Image[types.length];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = Imagies.loadImage((String) ("/" + types[i] + ".png"));
	}
}