package dinosaur;

import java.awt.Image;
import java.util.stream.IntStream;

public class Field {

	public static int fieldPartWidth = 70, distToNearestPart = fieldPartWidth;
	public static int[] fieldType = new int[21];
	public static Image[] defaultTypes = IntStream.rangeClosed(1, 23)
            .mapToObj(x -> Graphic.loadImage("/field" + x + ".png")).toArray(Image[]::new);

	public static void setField() {
		for (int i = 0; i < fieldType.length; i++)
			fieldType[i] = Generators.generateFieldPartTypeNubm();
	}

}
