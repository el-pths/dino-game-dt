package dinosaur;

import java.awt.Color;
import java.awt.Graphics;

public class Background {

	public static void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, Window.bufferedImageWidth, Window.bufferedImageHeight);
	}

}
