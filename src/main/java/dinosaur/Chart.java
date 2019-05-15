package dinosaur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Scanner;

import dinosaur.Control.State;

public class Chart {

	public static State previousState;
	public static boolean pause = false;

	private static Scanner sc;

	private static Chart x = new Chart(84, 20, 336, 189, Axis.X, "Axis X");
	private static Chart y = new Chart(504, 20, 336, 189, Axis.Y, "Axis Y");
	private static Chart z = new Chart(924, 20, 336, 189, Axis.Z, "Axis Z");
	private static Chart rms = new Chart(66, 230, 1220, 430, Axis.RMS, "Axis RMS");

	private int leftIndent, upperIndent, width, height;
	private Axis purpose;
	@SuppressWarnings("unused")
	private String name;

	private static enum Axis {
		X, Y, Z, RMS;
	}

	private Chart(int leftIndent, int upperIndent, int width, int height, Axis purpose, String name) {
		this.leftIndent = leftIndent;
		this.upperIndent = upperIndent;
		this.width = width;
		this.height = height;
		this.purpose = purpose;
		this.name = name;
	}

	private void setGraphicsColor(Graphics graphics) {
		switch (purpose) {
		case X:
			graphics.setColor(Color.RED);
			break;
		case Y:
			graphics.setColor(Color.BLUE);
			break;
		case Z:
			graphics.setColor(Color.YELLOW);
			break;
		case RMS:
			graphics.setColor(Color.GREEN);
			break;
		default:
			graphics.setColor(Color.ORANGE);
			break;
		}
	}

	private void drawChart(Graphics graphics) {
		setGraphicsColor(graphics);
		for (int i = Filter.filter.points.size() - 2, u = 0; i >= 0; i--, u++)
			try {
				if (getMaxValue() != 0 && Filter.filter.points.get(i) != null) {
					int point = getPointValue(i), nextPoint = getPointValue(i + 1);
					graphics.drawLine((int) ((double) (u + 1) * (double) width / (double) getListSize()) + leftIndent,
							-1 * (int) ((double) point * (double) height / (2.0 * (double) getMaxValue())) + upperIndent
									+ height / 2,
							(int) ((double) u * (double) width / (double) getListSize()) + leftIndent,
							-1 * (int) ((double) nextPoint * (double) height / (2.0 * (double) getMaxValue()))
									+ upperIndent + height / 2);
				}
			} catch (NullPointerException e) {
				// Do nothing
			}
		drawAxis(graphics);
		if (purpose == Axis.RMS)
			drawNormalG(graphics);
		System.out.println(Filter.filter.jumpDetected());
	}

	private int getPointValue(int i) {
		switch (purpose) {
		case X:
			return Filter.filter.points.get(i).x;
		case Y:
			return Filter.filter.points.get(i).y;
		case Z:
			return Filter.filter.points.get(i).z;
		case RMS:
			return Filter.filter.points.get(i).rms;
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

	private void drawNormalG(Graphics graphics) {
		graphics.setColor(Color.ORANGE);
		graphics.drawLine(leftIndent,
				upperIndent + height / 2
						- (int) ((double) DataInterpretation.getNormalGravityValue() * (double) height
								/ (2.0 * (double) getMaxValue())),
				leftIndent + width,
				upperIndent + height / 2 - (int) ((double) DataInterpretation.getNormalGravityValue() * (double) height
						/ (2.0 * (double) getMaxValue())));
	}

	public static void draw(Graphics graphics) {
		if (!pause) {
			Background.draw(graphics);
			drawAllAxises(graphics);
		}
		DButton.closeChartButton.draw(graphics);
	}

	private static void drawAllAxises(Graphics graphics) {
		((Graphics2D) graphics).setStroke(new BasicStroke(2));
		x.drawChart(graphics);
		y.drawChart(graphics);
		z.drawChart(graphics);
		rms.drawChart(graphics);
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
		sc = new Scanner(Chart.class.getResourceAsStream("/j1.txt"));
	}

	private static void recordFromFile() {
		Filter.filter.newPoint(sc.nextInt(), sc.nextInt(), sc.nextInt());
	}

}
