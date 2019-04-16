package dinosaur;

import java.awt.Image;

public class Field {

	public static int fieldPartWidth = 70, distToNearestPart = fieldPartWidth;
	public static int[] fieldType = new int[21];
	public static Image[] defaultTypes = { Graphic.loadImage("/field1.png"), Graphic.loadImage("/field2.png"),
			Graphic.loadImage("/field3.png"), Graphic.loadImage("/field4.png"), Graphic.loadImage("/field5.png"),
			Graphic.loadImage("/field6.png"), Graphic.loadImage("/field7.png"), Graphic.loadImage("/field8.png"),
			Graphic.loadImage("/field9.png"), Graphic.loadImage("/field10.png"), Graphic.loadImage("/field11.png"),
			Graphic.loadImage("/field12.png"), Graphic.loadImage("/field13.png"), Graphic.loadImage("/field14.png"),
			Graphic.loadImage("/field15.png"), Graphic.loadImage("/field16.png"), Graphic.loadImage("/field17.png"),
			Graphic.loadImage("/field18.png"), Graphic.loadImage("/field19.png"), Graphic.loadImage("/field20.png"),
			Graphic.loadImage("/field21.png"), Graphic.loadImage("/field22.png"), Graphic.loadImage("/field23.png") };

	public static void setField() {
		for (int i = 0; i < fieldType.length; i++)
			fieldType[i] = Generators.generateFieldPartTypeNubm();
	}

}
