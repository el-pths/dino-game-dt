package denisdino;

import java.awt.Toolkit;

public class Settings {
	MainAndWindow main;
	final static int SCREEN_WIDTH = (int) ((Toolkit.getDefaultToolkit()).getScreenSize().width * 0.7),
			SCREEN_HEIGHT = (int) (((Toolkit.getDefaultToolkit()).getScreenSize()).height * 0.5);
	public static final int PIXEL = (int) Math.round(Math.log(SCREEN_WIDTH * SCREEN_HEIGHT) / 3) + 1, JUMP_HEIGHT = 250,
			FEET_RESTRICION = 7, time$sRestrictionToFlight = 1;
	public static int SPEED = 80, koefficient_paraboli = 3;
}
