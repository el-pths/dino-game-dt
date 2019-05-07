package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.util.stream.IntStream;

public class Field {

	public static Field field;
	private int fieldPartWidth, distToNearestPart, verticalIndent, height;
	private Image[] fieldTypes;
	private static Image[] defaultTypes = IntStream.rangeClosed(1, 23)
			.mapToObj(x -> Imagies.loadImage("/field" + x + ".png")).toArray(Image[]::new);

	public Field() {
		this.fieldPartWidth = 70;
		this.distToNearestPart = this.fieldPartWidth;
		this.fieldTypes = new Image[21];
		this.generateWholeField();
		this.verticalIndent = 360;
		this.height = 54;
	}

	public void drawAndRecordField(Graphics graphics) {
		for (int i = 0; i < fieldTypes.length; i++)
			graphics.drawImage(fieldTypes[i], distToNearestPart + i * fieldPartWidth, verticalIndent, fieldPartWidth,
					height, null);
		recordField();
	}

	private void recordField() {
		if (distToNearestPart < -1 * fieldPartWidth) {
			addNewPartAndDeleteOldest();
			distToNearestPart += fieldPartWidth;
		} else {
			distToNearestPart -= 3;
		}
	}

	private void addNewPartAndDeleteOldest() {
		for (int i = 0; i < fieldTypes.length - 1; i++)
			fieldTypes[i] = fieldTypes[i + 1];
		fieldTypes[fieldTypes.length - 1] = getGeneratedFieldPart();
	}

	private static Image getGeneratedFieldPart() {
		int type = (int) (Math.random() * 23);
		return defaultTypes[type];
	}

	private void generateWholeField() {
		for (int i = 0; i < fieldTypes.length; i++)
			fieldTypes[i] = getGeneratedFieldPart();
	}

}
