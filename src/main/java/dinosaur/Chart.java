package dinosaur;

import java.awt.Color;
import java.awt.Graphics;

import dinosaur.Control.State;

public class Chart {

	public static State previousState;

	private static Chart x = new Chart(10, 100, 1000, axis.X);

	private int leftIndent, upperIndent, width;
	private axis purpose;

	private static enum axis {
		X, Y, Z, RMS;
	}

	private Chart(int leftIndent, int upperIndent, int width, axis purpose) {
		this.leftIndent = leftIndent;
		this.upperIndent = upperIndent;
		this.width = width;
		this.purpose = purpose;
	}

	private void setGraphicsColor(Graphics graphics) {
		switch (purpose) {
		case X:
			graphics.setColor(Color.RED);
			break;
		case Y:
			graphics.setColor(Color.RED);
			break;
		case Z:
			graphics.setColor(Color.RED);
			break;
		case RMS:
			graphics.setColor(Color.RED);
			break;
		default:
			graphics.setColor(Color.RED);
			break;
		}
	}

	private void drawAxis(Graphics graphics) {
		setGraphicsColor(graphics);
		for (int i = 0; i < Filter.filter.points.size() - 1; i++)
			graphics.drawLine(i, getPointValue(i), i + 1, getPointValue(i + 1));
	}

	private int getPointValue(int i) {
		switch (purpose) {
		case X:
			return Filter.filter.points.get(i).getX();
		case Y:
			return Filter.filter.points.get(i).getY();
		case Z:
			return Filter.filter.points.get(i).getZ();
		case RMS:
			return Filter.filter.points.get(i).getRMS();
		default:
			return 0;
		}
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		x.drawAxis(graphics);
		DButton.closeChartButton.draw(graphics);
	}
	
	public static void correctButtonsTouchableSpace(int windowWidth, int windowHeight) {
		DButton.closeChartButton.setTouchableLocation(windowWidth, windowHeight);
	}

	public static void set(Window window) {
		DButton.setButton(window, 100, 100, 100, 100, DImage.chartsButton, DButton.buttonPurpose.CLOSE_CHART);
	}

}
