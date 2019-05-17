package dinosaur;

import java.util.LinkedList;

public class Filter {

	public static Filter filter = new Filter();

	static final double MIN_JUMP = Integer.parseInt(System.getProperty("minJump", "150"));
	static final double GOOD_JUMP = Integer.parseInt(System.getProperty("goodJump", "250"));
	static final int MAX_LIST_SIZE = 200;
	static final int SMOOTH_COEFF = 2;

	public boolean isCalibrated = false;
	public int maxInputValue = 0;

	LinkedList<Point> points = new LinkedList<Point>();

	double gx = Double.POSITIVE_INFINITY;
	double gy = gx;
	double gz = gx;
	double gabs = gx;
	double dt = 1000;

	enum State {
		CALM, SQUAT, PUSH, IN_JUMP
	}

	// variables for analysis
	State state = State.CALM;
	double squatMin;
	double pushMax;
	double pushAccum;
	long lastCalmTs;
	long lastNotCalmTs;
	long pushStartTs;
	long jumpStartTs;

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
			this.rms = (int) (Math.sqrt(x * x + y * y + z * z) + 0.5);
			this.vert = (x * gx + y * gy + z * gz) / (gabs * gabs);
		}

		int rms() {
			return rms;
		}
	}

	public void newPoint(int x, int y, int z) {
		if (x > maxInputValue)
			maxInputValue = x;
		if (y > maxInputValue)
			maxInputValue = y;
		if (z > maxInputValue)
			maxInputValue = z;
		Point p = new Point(x, y, z);
		points.addFirst(p);
		while (points.size() > MAX_LIST_SIZE) {
			points.removeLast();
		}
		makeSmooth();
		analysis();
	}

	public void newPoint(int x, int y, int z, long ts) {
		this.newPoint(x, y, z);
		points.getFirst().ts = ts;
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
		return Double.isInfinite(gabs) || Double.isNaN(gabs) ? Integer.MAX_VALUE : (int) gabs;
	}

	public int getRMS() {
		return points.getFirst().rms;
	}

	public void calibrate() {
		if (points.isEmpty()) {
			return;
		}
		long t = points.getFirst().ts;
		int cnt = 0;
		gx = gy = gz = 0;
		for (Point p : points) {
			if (t - p.ts > 1000) {
				break;
			}
			gx += p.x;
			gy += p.y;
			gz += p.z;
			cnt += 1;
		}
		gx /= cnt;
		gy /= cnt;
		gz /= cnt;
		gabs = Math.sqrt(gx * gx + gy * gy + gz * gz);
		if (cnt >= points.size()) {
			cnt = points.size() - 1;
		}
		dt = (points.get(0).ts - points.get(cnt).ts) / (double) cnt;
		isCalibrated = true;
	}

	public void analysis() {
		Point pCur = points.getFirst();
		if (pCur.vert > 0.91 && pCur.vert < 1.1) {
			lastCalmTs = pCur.ts;
			if (lastCalmTs - lastNotCalmTs > 200) {
				state = State.CALM;
				squatMin = 1;
				pushMax = 1;
			}
		} else {
			lastNotCalmTs = pCur.ts;
		}
		if (state == State.CALM && pCur.vert < 0.85) {
			state = State.SQUAT;
		}
		if (state == State.SQUAT) {
			squatMin = Math.min(squatMin, pCur.vert);
			if (pCur.vert > 1.0) {
				state = State.PUSH;
				pushAccum = 0;
			}
		}
		if (state == State.PUSH) {
			pushMax = Math.max(pushMax, pCur.vert);
			pushAccum += (pCur.vert - 1) * dt;
			if (pushMax > pCur.vert + 0.1 && pCur.vert <= 1.1) {
				state = State.IN_JUMP;
				jumpStartTs = pCur.ts;
			}
		}
		if (state == State.IN_JUMP && pCur.ts - jumpStartTs > 200) {
			if (pCur.ts - jumpStartTs > 200)
				pushAccum = 0;
			if (pCur.ts - jumpStartTs > 300)
				state = State.CALM;
		}
		// System.out.println(state + " " + pCur.vert);
	}

	// This should return jump height if jump is detected
	// or 0 if not
	// jump height is in percents, not in pixels!
	public double jumpDetected() {
		if (state == State.IN_JUMP && pushAccum >= MIN_JUMP) {
			return (((double) pushAccum - (double) MIN_JUMP) / ((double) GOOD_JUMP - (double) MIN_JUMP) * 100.0);
		} else {
			return 0;
		}
	}

}
