package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import bot.Bot;

/**
 * @Author Converted from Simba.
 */
public class Calc {
	/**
	 * Gets the distance between two points.
	 * 
	 * @param p1
	 *            The first point.
	 * @param p2
	 *            The second point.
	 * @return The distance.
	 */
	public static int getDistanceBetween(final Point p1, final Point p2) {
		if (p1 == null || p2 == null) {
			return -1;
		}
		final int xDiff = p2.x - p1.x;
		final int yDiff = p2.y - p1.y;
		return (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	/**
	 * 
	 * @param x
	 *            minimap loc-x
	 * @param y
	 *            minimap loc-y
	 * @return conversion
	 */
	/*public Point minimapToScreen(int x, int y) {
		Point base = Location.PLAYER_DOT;

		return new Point(-1, -1);

	}*/

	/**
	 * Gets the largest distance between all points.<br>
	 * Useful to check if a bunch of points is close to each other.
	 * 
	 * @param points
	 *            The points to include.
	 * @return The largest distance in pixels between all points.
	 */
	public static int getLargestDistanceBetween(final Point... points) {
		if (points == null) {
			return -1;
		}
		int distance = 0;
		int largestDistance = -1;
		top: for (final Point main : points) {
			for (final Point sub : points) {
				if (main == null || sub == null || main.equals(sub)) {
					continue top;
				} else if ((distance = getDistanceBetween(main, sub)) > largestDistance) {
					largestDistance = distance;
				}
			}
		}
		return largestDistance;
	}

	/**
	 * Gets the most left point of an array of points
	 * 
	 * @param in array
	 * @return most left point
	 */
	public static Point getLeftMax(Point[] in) {
		int x = -1;
		Point a = null;
		for (Point p : in) {
			if (x == -1 || p.x < x) {
				x = p.x;
				a = p;
			}
		}
		return a;
	}

	/**
	 * Gets the most right point of an array of points
	 * 
	 * @param in array
	 * @return most right point
	 */
	public static Point getRightMax(Point[] in) {
		int x = -1;
		Point a = null;
		for (Point p : in) {
			if (x == -1 || p.x > x) {
				x = p.x;
				a = p;
			}
		}
		return a;
	}

	/**
	 * Gets the highest point of an array of points
	 * 
	 * @param in array
	 * @return most left point
	 */
	public static Point getNorthern(Point[] in) {
		int y = -1;
		Point a = null;
		for (Point p : in) {
			if (y == -1 || p.y < y) {
				y = p.y;
				a = p;
			}
		}
		return a;
	}

	/**
	 * Gets the lowest point of an array of points
	 * 
	 * @param in array
	 * @return lowest point
	 */
	public static Point getSouthern(Point[] in) {
		int y = -1;
		Point a = null;
		for (Point p : in) {
			if (y == -1 || p.y > y) {
				y = p.y;
				a = p;
			}
		}
		return a;
	}

	/**
	 * Returns nearest point of given array of points
	 * 
	 * @param p
	 * @param in
	 * @return nearest point of given array of points
	 */
	public static Point getClosest(Point p, Point[] in) {
		Point ret = null;
		for (Point a : in) {
			if (ret == null) {
				ret = a;
			} else {
				if (getDistanceBetween(ret, p) > getDistanceBetween(a, p)) {
					ret = a;
				}
			}
		}
		return ret;
	}

	public static double getDistance(double d, double d1, double d2, double d3,
			double d4, double d5) {
		double d6 = d3 - d;
		double d7 = d4 - d1;
		double d8 = d5 - d2;
		return Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
	}

	public static double getDistance(Color color, Color color1) {
		float af[] = new float[3];
		float af1[] = new float[3];
		color.getColorComponents(af);
		color1.getColorComponents(af1);
		return getDistance(af[0], af[1], af[2], af1[0], af1[1], af1[2]);
	}

	public static double getDistance(double ad[], double ad1[]) {
		return getDistance(ad[0], ad[1], ad[2], ad1[0], ad1[1], ad1[2]);
	}

	public static double getDistance(int i, int j) {
		return getDistance(new Color(i), new Color(j));
	}

	public static boolean isDark(double d, double d1, double d2) {
		return getDistance(d, d1, d2, 0.0D, 0.0D, 0.0D) > getDistance(d, d1,
				d2, 1.0D, 1.0D, 1.0D);
	}

	public static boolean isDark(Color color) {
		float af[] = new float[3];
		color.getColorComponents(af);
		return isDark(af[0], af[1], af[2]);
	}

	public static double getColorConcentration(final Rectangle rectangle,
			final Color color, final double threshold) {
		final List<Point> points = ImageUtil.getPointsWithColor(Bot
				.getCurrent().getScreen(), rectangle, color, threshold);
		if (points == null || points.isEmpty()) {
			return 0;
		}
		final double pixels = (double) (rectangle.getWidth()
				* rectangle.getHeight() * 100);
		return Math.min((double) (points.size() / pixels), 100.00);
	}

}
