package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;

public class Cactuses {

	public static Cactuses cactuses;

	public int amount;
	public Cactus[] list;

	private static final int maxCactusesAmount = 10, edgeOfField = -300, checkDist = 900;
	private static Image[] imgs;
	private static String[] types = { "b1ig1", "b1ig2", "b1ig3", "b1ig4", "b1ig5", "b1ig6", "m2id1", "m2id2", "d3if1" };

	public Cactuses() {
		this.list = new Cactus[maxCactusesAmount];
		this.amount = 0;
	}

	public Cactus getCactus(int num) {
		return list[num];
	}

	public static void setCactuses() {
		cactuses = new Cactuses();
	}

	public class Cactus {
		public int distance;
		public TouchableRectangle[] touchRects;
		public Image icon;
		private int width, height;
		public int verticalIndent;
		private String type;

		public Cactus() {
			this.setType();
			this.setIcon();
			this.setSizes();
			this.distance = 1400;
			this.verticalIndent = Dino.dino.height + Dino.dino.verticalIndent - height + 15;
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
			switch (Arrays.asList(types).indexOf(type)) {
			case 0:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 29, 10, 39);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 108);
				touchRects[2] = new TouchableRectangle(42, 16, 10, 39);
				break;
			case 1:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 29, 10, 39);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 108);
				touchRects[2] = new TouchableRectangle(42, 16, 10, 39);
				break;
			case 2:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 16, 13, 33);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 108);
				touchRects[2] = new TouchableRectangle(42, 16, 10, 46);
				break;
			case 3:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 29, 10, 39);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 108);
				touchRects[2] = new TouchableRectangle(42, 16, 10, 39);
				break;
			case 4:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 13, 10, 55);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 108);
				touchRects[2] = new TouchableRectangle(42, 26, 10, 52);
				break;
			case 5:
				touchRects = new TouchableRectangle[3];
				touchRects[0] = new TouchableRectangle(3, 13, 10, 42);
				touchRects[1] = new TouchableRectangle(19, 3, 17, 109);
				touchRects[2] = new TouchableRectangle(42, 16, 10, 46);
				break;
			case 6:
				touchRects = new TouchableRectangle[4];
				touchRects[0] = new TouchableRectangle(2, 30, 12, 36);
				touchRects[1] = new TouchableRectangle(20, 2, 16, 105);
				touchRects[2] = new TouchableRectangle(35, 25, 74, 37);
				touchRects[3] = new TouchableRectangle(77, 2, 16, 105);
				break;
			case 7:
				touchRects = new TouchableRectangle[4];
				touchRects[0] = new TouchableRectangle(2, 14, 12, 35);
				touchRects[1] = new TouchableRectangle(20, 2, 16, 105);
				touchRects[2] = new TouchableRectangle(77, 2, 16, 107);
				touchRects[3] = new TouchableRectangle(35, 27, 74, 37);
				break;
			case 8:
				touchRects = new TouchableRectangle[5];
				touchRects[0] = new TouchableRectangle(2, 39, 9, 31);
				touchRects[1] = new TouchableRectangle(16, 7, 11, 102);
				touchRects[2] = new TouchableRectangle(26, 16, 54, 7);
				touchRects[3] = new TouchableRectangle(80, 2, 16, 107);
				touchRects[4] = new TouchableRectangle(102, 27, 12, 35);
				break;
			}
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
			graphics.drawImage(icon, distance, verticalIndent, width, height, null);
		}

	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < amount; i++)
			list[i].draw(graphics);
	}

	public void record(double position) {
		for (int i = 0; i < this.amount; i++)
			this.list[i].record(position);
		ifItNeedsGenerateCactus();
		ifItNeedsDeleteFirst();
	}

	public void ifItNeedsGenerateCactus() {
		if (amount < maxCactusesAmount && (amount == 0 || list[amount - 1].distance < checkDist))
			createNewCactus();
	}

	public void createNewCactus() {
		list[amount] = new Cactus();
		amount++;
	}

	public void ifItNeedsDeleteFirst() {
		if (list[0].distance < edgeOfField)
			list[0].remove();
	}

	public static void loadCactusesImagies() {
		imgs = new Image[types.length];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = Imagies.loadImage((String) ("/" + types[i] + ".png"));
	}
}