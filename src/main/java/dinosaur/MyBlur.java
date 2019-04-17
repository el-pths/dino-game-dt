package dinosaur;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class MyBlur {
	public static BufferedImage fon;

	public static void setFon(BufferedImage img) {
		int size = 3;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		for (int i = 0; i < data.length; i++)
			data[i] = weight;
		fon = (new ConvolveOp(new Kernel(size, size, data), ConvolveOp.EDGE_NO_OP, null)).filter(img, null);
	}
}
