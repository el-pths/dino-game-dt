package dinosaur;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class BImage extends BufferedImage {

	public BImage(int width, int height, int imgType) {
		super(width, height, imgType);
	}

	public void blur(int strengeth) {
		getGraphics().drawImage(getBlurImage(strengeth), 0, 0, getWidth(), getHeight(), null);
	}

	private BufferedImage getBlurImage(int strengeth) {
		float weight = 1.0f / (strengeth * strengeth);
		float[] data = new float[strengeth * strengeth];
		for (int i = 0; i < data.length; i++)
			data[i] = weight;
		return (new ConvolveOp(new Kernel(strengeth, strengeth, data), ConvolveOp.EDGE_NO_OP, null)).filter(this, null);
	}

}
