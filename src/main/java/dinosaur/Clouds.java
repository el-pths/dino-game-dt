package dinosaur;

import java.awt.Image;

public class Clouds {

	public static int cloudsAmount = 0;
	public static int[] distToCloud = new int[10], cloudHeight = new int[10], cloudType = new int[10];
	public static Image cloud = Graphic.loadImage("/cloud.png");
	public static int[][] sizeByType = {{300, 130}, {150, 65}, {275, 115}};

}
