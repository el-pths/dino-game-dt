package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Settings {
	public static double koefficient_paraboli = 2.5;
	public static final int START_WIDTH = 1344, START_HEIGHT = 540, PIXEL = 4;
	public static int speed = 60, width = START_WIDTH, height = START_HEIGHT;
	public static final int againXX = 200, againYY = 60, againX = (width - againXX) / 2, againY = height / 16;
	public static final int closePortXX = 60, closePortYY = 60, closePortX = (int) (width * 57 / 64),
			closePortY = (int) (height * 6 / 32);
	public static final int restartXX = 110, restartYY = 110;
	public static final int gameoverLabelX = Settings.width / 4, gameoverLabelY = Settings.height / 5,
			gameoverLabelXX = Settings.width / 2, gameoverLabelYY = Settings.height / 6;
	public static final int settingsButtonX = 10, settingsButtonY = 10, settingsButtonXX = 50, settingsButtonYY = 50;

	public static Image settingButton = Graphic.loadImage("/settingButton.png");
	public static boolean settingWindow = false;

	public static void getSettingsMenu(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
	}

	public static void drawSettingButton(Graphics g) {
		g.drawImage(settingButton, settingsButtonX, settingsButtonY, settingsButtonXX, settingsButtonYY, null);
	}
}