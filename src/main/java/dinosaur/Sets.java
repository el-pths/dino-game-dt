package dinosaur;

import java.awt.Toolkit;

public class Sets {
	public static final int START_WIDTH = (int) ((Toolkit.getDefaultToolkit()).getScreenSize().width * 0.7),
			START_HEIGHT = (int) (((Toolkit.getDefaultToolkit()).getScreenSize()).height * 0.5), PIXEL = 4;
	public static int speed = 60, width = START_WIDTH, height = START_HEIGHT, koefficient_paraboli = 3;
	public static int againXX = 200, againYY = 60, againX = (width - againXX) / 2, againY = height / 16;
}
