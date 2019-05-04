package dinosaur;

import java.util.LinkedList;

public class Filter {

	static final int MAX_LIST_SIZE = 10;
	static final int SMOOTH_COEFF = 2;
	static final int CALIBRATION_NEEDED = Integer.MAX_VALUE;

	LinkedList<Point> points = new LinkedList<Point>();

	int gravity = CALIBRATION_NEEDED;

	static class Point {
		int x, y, z;

		Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		int rms() {
			return (int) (Math.sqrt(x * x + y * y + z * z) + 0.5);
		}
	}

	public void newPoint(int x, int y, int z) {
		Point p = new Point(x, y, z);
		points.addFirst(p);
		while (points.size() > MAX_LIST_SIZE) {
			points.removeLast();
		}
		makeSmooth();
	}

	void makeSmooth() {
		if (points.size() < 2) {
			return;
		}
		Point pNew = points.get(0);
		Point pPrev = points.get(1);
		pNew.x = runningAverage(pNew.x, pPrev.x);
		pNew.y = runningAverage(pNew.y, pPrev.y);
		pNew.z = runningAverage(pNew.z, pPrev.z);
	}

	int runningAverage(int newValue, int oldValue) {
		return (int) ((newValue + oldValue * SMOOTH_COEFF) / (SMOOTH_COEFF + 1) + 0.5);
	}

	public int getX() {
		return points.getFirst().x;
	}

	public int getY() {
		return points.getFirst().y;
	}

	public int getZ() {
		return points.getFirst().z;
	}

	public int getG() {
		return gravity;
	}

	public int getRMS() {
		return points.getFirst().rms();
	}

	public void calibrate() {
		if (points.isEmpty()) {
		    return;
		}
		int sum = 0;
		for (Point p : points) {
			sum += p.rms();
		}
		gravity = (int) (sum / points.size() + 0.5);
	}

	// This should return jump height if jump is detected
	// or 0 if not
	// jump height is in percents of gravity, not in pixels!
	public int jumpDetected() {
		// this should be rewritten so we check point
		// some time ago (i.e. at end of list)
		// and the current one.
		// if the old point is much less than current
		// then it is sudden change of acceleration
		// and height could be calculated according to the
		// greatness of this change
		if (getRMS() / (double) gravity > 1.7) {
			return getRMS() * 100 / gravity;
		} else {
			return 0;
		}
	}

}
