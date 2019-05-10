package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

public class Clouds {

	public static Clouds clouds;

	private static Image cloudImage;
	private static int skyEdge = -300, movingSpeed = 2;

	private Cloud[] cloudsList;
	private int amount;

	private Clouds() {
		this.cloudsList = new Cloud[10];
		this.amount = 0;
	}

	public static void setClouds() {
		clouds = new Clouds();
	}

	public static void loadCloudImage() {
		cloudImage = Imagies.loadImage("/cloud.png");
	}

	private class Cloud {
		private String type;
		private Image icon;
		private int distance, width, height, verticalIndent;

		public Cloud() {
			this.setRandomDistanceAndFlyingHeight();
			this.setIcon();
			this.setType();
			this.setSizes();
		}

		private void setIcon() {
			icon = cloudImage;
		}

		private void setType() {
			int random = (int) (Math.random() * 3.0);
			switch (random) {
			case 0:
				type = "big";
				break;
			case 1:
				type = "mid";
				break;
			default:
				type = "small";
				break;
			}
		}

		private void setRandomDistanceAndFlyingHeight() {
			distance = (int) (1344.0 + Math.random() * 100.0);
			verticalIndent = (int) (height + Math.random() * 200.0);
		}

		private void setSizes() {
			switch (type) {
			case "big":
				width = 300;
				height = 130;
			case "mid":
				width = 275;
				height = 115;
			case "small":
				width = 150;
				height = 65;
			}
		}

		private void draw(Graphics graphics) {
			graphics.drawImage(icon, distance, verticalIndent, width, height, null);
		}

		private void record(double position) {
			distance -= (int) ((double) movingSpeed * position);
		}

	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < amount; i++)
			cloudsList[i].draw(graphics);
	}

	public void record(double position) {
		for (int i = 0; i < amount; i++)
			cloudsList[i].record(position);
		ifItNeedsDelete();
		ifItNeedsCreate();
	}

	private void ifItNeedsDelete() {
		if (amount > 0 && cloudsList[0].distance < skyEdge)
			deleteFirst();
	}

	private void deleteFirst() {
		for (int i = 0; i < amount && i < cloudsList.length - 1; i++)
			cloudsList[i] = cloudsList[i + 1];
		amount--;
	}

	private void ifItNeedsCreate() {
		if (cloudsList[0] == null || (amount < cloudsList.length && cloudsList[amount - 1].distance < 400))
			create();
	}

	private void create() {
		if (amount < cloudsList.length) {
			cloudsList[amount] = new Cloud();
			amount++;
		}
	}

}
