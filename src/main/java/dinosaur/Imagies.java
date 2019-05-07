package dinosaur;

import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Imagies {
	public static Image loadImage(String resourcePath) {
		try {
			return ImageIO.read(Graphics.class.getResource(resourcePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
