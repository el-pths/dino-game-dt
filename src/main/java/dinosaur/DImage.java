package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class DImage {

	public static DImage gameoverImg, settingsMenuBackgroud;
	public static Image one, two, three, four, five, six, seven, eight, nine, restartButtonImg, continueButtonImg,
			pauseButtonImg, settingsButtonImg, plusButton, minusButton, chartsButton, pteroUp, pteroDown, soundOnImg,
			soundOffImg, recalibrateImg;
	public static Image[] salto = new Image[12];
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
		loadPlusMinusButtonsImagies();
		loadChartsImage();
		loadSTMFon();
		loadPteroImagies();
		loadSaltoImagies();
		loadSoundSettingButtonsImagies();
		loadRecalibrateButtonImage();
	}

	private static void loadNumbersImagies() {
		one = loadImage("/one.png");
		two = loadImage("/two.png");
		three = loadImage("/three.png");
		four = loadImage("/four.png");
		five = loadImage("/five.png");
		six = loadImage("/six.png");
		seven = loadImage("/seven.png");
		eight = loadImage("/eight.png");
		nine = loadImage("/nine.png");
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

	private static void loadPlusMinusButtonsImagies() {
		plusButton = loadImage("/plus3.png");
		minusButton = loadImage("/minus3.png");
	}

	private static void loadChartsImage() {
		chartsButton = loadImage("/landscape.png");
	}

	private static void loadSTMFon() {
		settingsMenuBackgroud = new DImage(loadImage("/axilSchemepng.png"), 0, 0, Window.bufferedImageWidth,
				Window.bufferedImageHeight);
	}

	private static void loadPteroImagies() {
		pteroDown = loadImage("/pterodown.png");
		pteroUp = loadImage("/pteroup.png");
	}

	private static void loadSaltoImagies() {
		for (int i = 0; i < salto.length; i++)
			salto[i] = loadImage("/salto" + (i + 1) + ".png");
	}

	private static void loadSoundSettingButtonsImagies() {
		soundOnImg = loadImage("/soundOn.png");
		soundOffImg = loadImage("/soundOff.png");
	}

	private static void loadRecalibrateButtonImage() {
		recalibrateImg = loadImage("/recalibrate.png");
	}
}
