package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.util.stream.IntStream;

public class Field {
	public static Field field;

	private FieldPart[] parts;

	private final int fieldPartWidth, verticalIndent, height;
	private static Image[] defaultTypes;

	private Field(int fieldPartWidth, int partsAmount, int verticalIndent, int height) {
		this.fieldPartWidth = fieldPartWidth;
		this.parts = new FieldPart[partsAmount];
		this.generateWholeField();
		this.verticalIndent = verticalIndent;
		this.height = height;
	}

	public static void loadFieldPartsImagies() {
		defaultTypes = IntStream.rangeClosed(1, 23).mapToObj(x -> Imagies.loadImage("/field" + x + ".png"))
				.toArray(Image[]::new);
	}

	public static void setField(int fieldPartWidth, int partsAmount, int verticalIndent, int height) {
		field = new Field(fieldPartWidth, partsAmount, verticalIndent, height);
	}

	private class FieldPart {
		public int distanceTo;
		public Image icon;

		public FieldPart(int dist) {
			this.distanceTo = dist;
			this.icon = getGeneratedFieldPart();

		}
	}

	private static Image getGeneratedFieldPart() {
		int type = (int) (Math.random() * 23);
		return defaultTypes[type];
	}

	private void generateWholeField() {
		for (int i = 0; i < parts.length; i++)
			parts[i] = new FieldPart((-1 + i) * fieldPartWidth);
	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < parts.length; i++)
			graphics.drawImage(parts[i].icon, parts[i].distanceTo, verticalIndent, fieldPartWidth, height, null);
	}

	public void recordField(double position) {
		if (parts[0].distanceTo < -1 * fieldPartWidth)
			addNewPartAndDeleteOldest();
		for (int i = 0; i < parts.length; i++)
			parts[i].distanceTo -= (int) (Control.recordingStep * position);
	}

	private void addNewPartAndDeleteOldest() {
		for (int i = 0; i < parts.length - 1; i++)
			parts[i] = parts[i + 1];
		parts[parts.length - 1] = new FieldPart(parts[parts.length - 2].distanceTo + fieldPartWidth);
	}
}
