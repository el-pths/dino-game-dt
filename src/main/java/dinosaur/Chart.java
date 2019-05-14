package dinosaur;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

import dinosaur.Control.State;

public class Chart {

	public static State previousState;
	public static boolean pause = false;

	private static Scanner sc;

	private static Chart x = new Chart(120, 10, Window.bufferedImageWidth / 3, Window.bufferedImageHeight / 2, axis.X);

	private int leftIndent, upperIndent, width, height;
	private axis purpose;

	private static enum axis {
		X, Y, Z, RMS;
	}

	private Chart(int leftIndent, int upperIndent, int width, int height, axis purpose) {
		this.leftIndent = leftIndent;
		this.upperIndent = upperIndent;
		this.width = width;
		this.height = height;
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

	private void drawChart(Graphics graphics) {
		setGraphicsColor(graphics);
		for (int i = 0; i < Filter.filter.points.size() - 1; i++)
			if (getMaxValue() != 0)
				graphics.drawLine(i * getListSize() / width + leftIndent,
						-1 * getPointValue(i) * height / getMaxValue() + upperIndent + height / 2,
						(i + 1) * getListSize() / width + leftIndent,
						-1 * getPointValue(i + 1) * height / getMaxValue() + upperIndent + height / 2);
		drawAxis(graphics);
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

	private int getListSize() {
		return Filter.filter.points.size();
	}

	private int getMaxValue() {
		return Filter.filter.maxInputValue;
	}

	private void drawAxis(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(leftIndent, upperIndent + height / 2, leftIndent + width, upperIndent + height / 2);
	}

	public static void draw(Graphics graphics) {
		if (!pause) {
			Background.draw(graphics);
			drawAllAxises(graphics);
		}
		DButton.closeChartButton.draw(graphics);
	}

	private static void drawAllAxises(Graphics graphics) {
		x.drawChart(graphics);
	}

	public static void correctButtonsTouchableSpace(int windowWidth, int windowHeight) {
		DButton.closeChartButton.setTouchableLocation(windowWidth, windowHeight);
	}

	public static void set(Window window) {
		DButton.setButton(window, 20, 20, 80, 80, DImage.chartsButton, DButton.buttonPurpose.CLOSE_CHART);
	}

	public static void readFromFile() {
		checkScanner();
		if (!pause)
			recordFromFile();
	}

	private static void checkScanner() {
		if (sc == null || !sc.hasNext())
			setScanner();
	}

	private static void setScanner() {
		sc = new Scanner(Chart.class.getResourceAsStream("/jumps.txt"));
	}

	private static void recordFromFile() {
		Filter.filter.addPoint(sc.nextInt(), sc.nextInt(), sc.nextInt());
	}

}
