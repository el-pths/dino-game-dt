package dinosaur;

import java.util.LinkedList;

public class Filter {

	static final int MAX_LIST_SIZE = 200;
	static final int SMOOTH_COEFF = 2;
	static final int CALIBRATION_NEEDED = Integer.MAX_VALUE;

	LinkedList<Point> points = new LinkedList<Point>();

	int gravity = CALIBRATION_NEEDED;
	
	double gx = Double.POSITIVE_INFINITY;
	double gy = gx;
	double gz = gx;
	double gabs = gx;
	double dt = 1000;

	class Point {
		int x, y, z;
		long ts;
		double vert; // vertical component normalized to G
		int rms;

		Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.ts = System.currentTimeMillis();
			rms = (int) (Math.sqrt(x * x + y * y + z * z) + 0.5);
			vert = (x * gx + y * gy + z * gz) / (gabs * gabs);
			System.out.println(vert);
		}

		int rms() {
			return rms;
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
		return points.getFirst().rms;
	}

	public void calibrate() {
		if (points.isEmpty()) {
		    return;
		}
		long t = System.currentTimeMillis();
		int sum = 0;
		int cnt = 0;
		gx = gy = gz = 0;
		for (Point p : points) {
		    if (t - p.ts > 1000) {
		        break;
		    }
			sum += p.rms();
			gx += p.x;
			gy += p.y;
			gz += p.z;
			cnt += 1;
		}
		gravity = Math.round(sum / cnt);
		gx /= cnt;
		gy /= cnt;
		gz /= cnt;
		gabs = Math.sqrt(gx * gx + gy * gy + gz * gz);
		dt = (points.get(0).ts - points.get(cnt).ts) / (double) cnt;
		System.out.printf("%f %f %f, %f%n", gx, gy, gz, dt);
	}

	// This should return jump height if jump is detected
	// or 0 if not
	// jump height is in percents of gravity, not in pixels!
	public int jumpDetected() {
	
		if (getRMS() / (double) gravity > 1.7) {
			return getRMS() * 100 / gravity;
		} else {
			return 0;
		}
	}

}
