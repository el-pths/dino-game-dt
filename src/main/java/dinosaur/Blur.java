package dinosaur;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Blur {

	public static void blur(BufferedImage img) {
		img = setFon(img);
	}

	private static BufferedImage setFon(BufferedImage img) {
		int size = 3;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		for (int i = 0; i < data.length; i++)
			data[i] = weight;
		return (new ConvolveOp(new Kernel(size, size, data), ConvolveOp.EDGE_NO_OP, null)).filter(img, null);
	}
}
