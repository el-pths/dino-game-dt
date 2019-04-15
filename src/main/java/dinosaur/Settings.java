package dinosaur;

import java.awt.Toolkit;

public class Settings {
	public static final int START_WIDTH = (int) ((Toolkit.getDefaultToolkit()).getScreenSize().width * 0.7),
			START_HEIGHT = (int) (((Toolkit.getDefaultToolkit()).getScreenSize()).height * 0.5), PIXEL = 4;
	public static int speed = 90, width = START_WIDTH, height = START_HEIGHT, koefficient_paraboli = 3;
	public static final int againXX = 200, againYY = 60, againX = (width - againXX) / 2, againY = height / 16;
	public static final int closePortXX = 60, closePortYY = 60, closePortX = (int) (width * 29 / 32),
			closePortY = (int) (height * 3 / 32);
	public static final int restartXX = 110, restartYY = 110;
	public static final int gameoverLabelX = Settings.width / 4, gameoverLabelY = Settings.height / 5,
			gameoverLabelXX = Settings.width / 2, gameoverLabelYY = Settings.height / 6;
}
