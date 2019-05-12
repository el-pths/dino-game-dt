package dinosaur;

import java.util.LinkedList;

public class Filter {

	public static Filter filter;
	private static int MAX_LIST_SIZE = 100;

	public LinkedList<Point> points;

	public class Point {
		private int x, y, z;

		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public int getRMS() {
			return (x * x + y * y + z * z);
		}
	}

	private Filter(int pointsAmount) {
		this.points = new LinkedList<Point>();
	}

	public static void setFilter(int pointsAmount) {
		filter = new Filter(pointsAmount);
	}

	public void addPoint(int newX, int newY, int newZ) {
		points.add(new Point(newX, newY, newZ));
		while (points.size() > MAX_LIST_SIZE) {
			points.remove();
		}
		System.out.println(newX + " " + newY + " " + newZ);
	}
}