package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class DImage {

	public static DImage gameoverImg;
	public static Image one, two, three, restartButtonImg, continueButtonImg, pauseButtonImg, settingsButtonImg;
	private Image icon;
	private int leftIndent, upperIndent, width, height;

	private DImage(Image icon, int leftIndent, int upperIndent, int width, int height) {
		this.icon = icon;
		this.leftIndent = leftIndent;
		this.upperIndent = upperIndent;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics graphics) {
		graphics.drawImage(icon, leftIndent, upperIndent, width, height, null);
	}

	public static Image loadImage(String resourcePath) {
		try {
			return ImageIO.read(Graphics.class.getResource(resourcePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void loadAllImagies() {
		loadNumbersImagies();
		loadGameoverImage();
		loadRestartButtonImage();
		loadPauseButtonImage();
		loadContinueButtonImage();
		loadSettingsButtonImage();
	}

	private static void loadNumbersImagies() {
		one = loadImage("/one.png");
		two = loadImage("/two.png");
		three = loadImage("/three.png");
	}

	private static void loadGameoverImage() {
		int width = 960, height = 180;
		gameoverImg = new DImage(loadImage("/gameover.png"), Window.bufferedImageWidth / 2 - width / 2,
				Window.bufferedImageHeight / 2 - height / 2 - 100, width, height);
	}

	private static void loadRestartButtonImage() {
		restartButtonImg = loadImage("/restartButton.png");
	}

	private static void loadContinueButtonImage() {
		continueButtonImg = loadImage("/start4.png");
	}

	private static void loadPauseButtonImage() {
		pauseButtonImg = loadImage("/pause.png");
	}
	
	private static void loadSettingsButtonImage() {
		settingsButtonImg = loadImage("/settingButton.png");
	}
}
