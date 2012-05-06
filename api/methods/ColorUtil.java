package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @Author Converted from Simba.
 * @Updater Soviet
 */
public class ColorUtil {

	/**
	 * @return Game Screen as BufferedImage.
	 */
	public static BufferedImage getScreen() {
		return Client.getScreen();
	}

	/**
	 * 
	 * @param c
	 *            first color array.
	 * @param d
	 *            second color array.
	 * @return int of the matches count between the arrays.
	 */
	public static int matchCount(Color[][] c, Color[][] d) {
		int i = 0;
		int j = 0;
		int count = 0;
		for (Color[] e : c) {
			for (Color f : e) {
				if (d[j][i].equals(f)) {
					count++;
				}
				j++;
			}
			i++;
		}
		return count;
	}

	/**
	 * 
	 * @param c1
	 *            first color
	 * @param c2
	 *            second color
	 * @param radius
	 *            radius to search for from the first Color.
	 * @return the Point of the first found location of the specified second color
	 */
	public Point findColorInRadius(Color c1, Color c2, int radius) {
		Point p[];
		Point start, end;
		if ((p = findAllColor(c1)) != null) {
			for (Point p2 : p) {
				start = new Point(p2.x - radius, p2.y - radius);
				end = new Point(p2.x + radius, p2.y + radius);
				for (int i = start.x; i < end.x; i++) {
					for (int j = start.y; j < end.y; j++) {
						// See if point is less than radius
						if (Math.abs(Calc.getDistanceBetween(p2,
								new Point(i, j))) <= radius) {
							if (getColor(i, j).equals(c2)) {
								return new Point(i, j);
							}
						}
					}
				}
			}
		}
		return null;
	}

    /**
     *
     * @param c1
     *            first color
     * @param c2
     *            second color
     * @param radius
     *            radius to search for from the first Color.
     * @param tolerance
     *            integer that represents color tolerance, for instance 5 = new color(5,5,5)
     * @return the Point of the first found location of the specified second color
     */
    public Point findColorInRadiusWithinTolerance(Color c1, Color c2, int radius,int tolerance) {
        Point p[];
        Point start, end;
        if ((p = findAllColor(c1)) != null) {
            for (Point p2 : p) {
                start = new Point(p2.x - radius, p2.y - radius);
                end = new Point(p2.x + radius, p2.y + radius);
                for (int i = start.x; i < end.x; i++) {
                    for (int j = start.y; j < end.y; j++) {
                        // See if point is less than radius
                        if (Math.abs(Calc.getDistanceBetween(p2,
                                new Point(i, j))) <= radius) {
                            if (areColorsWithinTolerance(getColor(i, j),c2, tolerance)){
                                return new Point(i, j);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param c1
     *            first color
     * @param c2
     *            second color
     * @param radius
     *            radius to search for from the first Color.
     * @param tolerance
     *            color object that represents tolerance
     * @return the Point of the first found location of the specified second color
     */
    public Point findColorInRadiusWithinTolerance(Color c1, Color c2, int radius,Color tolerance) {
        Point p[];
        Point start, end;
        if ((p = findAllColor(c1)) != null) {
            for (Point p2 : p) {
                start = new Point(p2.x - radius, p2.y - radius);
                end = new Point(p2.x + radius, p2.y + radius);
                for (int i = start.x; i < end.x; i++) {
                    for (int j = start.y; j < end.y; j++) {
                        // See if point is less than radius
                        if (Math.abs(Calc.getDistanceBetween(p2,
                                new Point(i, j))) <= radius) {
                            if (areColorsWithinTolerance(getColor(i, j),c2, tolerance)){
                                return new Point(i, j);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
	/**
	 * 
	 * @param c
	 *            - the color to search for.
	 * @return the location of Color on screen.
	 */
	public static Point findColor(Color c) {
		BufferedImage b = getScreen();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, Color tolerance) {
		BufferedImage b = getScreen();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getWidth(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param t
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as t = 10 it could return anywhere from (190,190,190) -
	 *            (210,210,210)
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, int t) {
		BufferedImage b = getScreen();
		Color tolerance = new Color(t, t, t);
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param b
	 *            the buffered imagine in which you would like to search for the
	 *            color specified.
	 * @return Point location
	 */
	public static Point findColor(Color c, BufferedImage b) {
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * @param b
	 *            - the buffered image in which you would like to search for the
	 *            color specified within tolerance
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, Color tolerance,
			BufferedImage b) {
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param t
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as t = 10 it could return anywhere from (190,190,190) -
	 *            (210,210,210)
	 * @param b
	 *            - the buffered image in which you would like to search for the
	 *            color specified within tolerance
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, int t, BufferedImage b) {
		Color tolerance = new Color(t, t, t);
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param r
	 *            the rectangle area on the screen in which you would like to
	 *            search for the color specified
	 * @return Point location
	 */
	public static Point findColor(Color c, Rectangle r) {
		BufferedImage b = getScreenPart(r);
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * @param r
	 *            - the rectangle on the screen in which you would like to
	 *            search for the color specified within tolerance
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, Color tolerance,
			Rectangle r) {
		BufferedImage b = getScreenPart(r);
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param t
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as t = 10 it could return anywhere from (190,190,190) -
	 *            (210,210,210)
	 * @param r
	 *            - the rectangle on the screen in which you would like to
	 *            search for the color specified within tolerance
	 * @return Point location
	 */
	public static Point findColorWithinTolerance(Color c, int t, Rectangle r) {
		Color tolerance = new Color(t, t, t);
		BufferedImage b = getScreenPart(r);
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    return new Point(i,j);
                }
			}
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 *            - desired Color
	 * @return - Array of point locations for colors = c
	 */
	public static Point[] findAllColor(Color c) {
		ArrayList<Point> list = new ArrayList<Point>();
		BufferedImage b = getScreen();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					list.add(new Point(i, j));
				}
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            the desired color.
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * 
	 * @return Point location array
	 */
	public static Point[] findAllColorWithinTolerance(Color c, Color tolerance) {
		ArrayList<Point> list = new ArrayList<Point>();
		BufferedImage b = getScreen();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    list.add(new Point(i,j));
                }
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            - desired Color
	 * @param b
	 *            - the buffered image in which to search for the desired color
	 * @return - Array of point locations for colors = c
	 */
	public static Point[] findAllColor(Color c, BufferedImage b) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					list.add(new Point(i, j));
				}
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            desired Color
	 * @param b
	 *            the buffered image in which you would like to search
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * 
	 * @return - Array of point locations for the color within the specified
	 *         tolerance within the buffered image
	 */
	public static Point[] findAllColorWithinTolerance(Color c, BufferedImage b,
			Color tolerance) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    list.add(new Point(i,j));
                }
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            desired Color
	 * @param b
	 *            the buffered image in which you would like to search
	 * @param t
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as t = 10 it could return anywhere from (190,190,190) -
	 *            (210,210,210)
	 * 
	 * @return - Array of point locations for the color within the specified
	 *         tolerance within the buffered image
	 */
	public static Point[] findAllColorWithinTolerance(Color c, BufferedImage b,
			int t) {
		Color tolerance = new Color(t, t, t);
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    list.add(new Point(i,j));
				}
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            - desired Color
	 * @param r
	 *            - the rectangle in which to search for the desired color
	 * @return - Array of point locations for colors = c
	 */
	public static Point[] findAllColor(Color c, Rectangle r) {
		BufferedImage b = getScreenPart(r);
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (new Color(b.getRGB(i, j)).equals(c)) {
					list.add(new Point(i, j));
				}
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            desired Color
	 * @param r
	 *            the rectangle in which you would like to search for the
	 *            desired color within the specified tolerance.
	 * @param tolerance
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as tolerance = new Color(11,3,4) it could return anywhere
	 *            from (189,197,196) - (211,203,204)
	 * 
	 * @return - Array of point locations for the color within the specified
	 *         tolerance within the buffered image
	 */
	public static Point[] findAllColorWithinTolerance(Color c, Rectangle r,
			Color tolerance) {
		BufferedImage b = getScreenPart(r);
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    list.add(new Point(i,j));
                }
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * 
	 * @param c
	 *            desired Color
	 * @param r
	 *            the rectangle in which you would like to search for the
	 *            desired color within the specified tolerance.
	 * @param t
	 *            - the rgb values which can vary. If c = new Color(200,200,200)
	 *            Such as t = 10 it could return anywhere from (190,190,190) -
	 *            (210,210,210)
	 * 
	 * @return - Array of point locations for the color within the specified
	 *         tolerance within the buffered image
	 */
	public static Point[] findAllColorWithinTolerance(Color c, Rectangle r,
			int t) {
		BufferedImage b = getScreenPart(r);
		Color tolerance = new Color(t, t, t);
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
                if(areColorsWithinTolerance(getColor(i,j),c,tolerance)){
                    list.add(new Point(i,j));
                }
			}
		}
		Point p[] = new Point[list.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = list.get(i);
		}
		return p;
	}

	/**
	 * Determines whether the difference between two integers falls within the
	 * range.
	 * 
	 * @param val
	 *            - the first integer you would like to compare
	 * @param des
	 *            - the second integer you would like to compare
	 * @param range
	 *            - the range in which to search for
	 * 
	 * @return True if the difference of the integers fall under the range
	 */
	private static boolean inRange(int val, int des, int range) {
		return des >= val - range && des <= val + range;
	}

	/**
	 * Gets the color at the specified point.
	 * 
	 * @param p
	 *            - The point at which you wish desire a color.
	 * @return The color at the point specified.
	 */
	public static Color getColor(Point p) {
		BufferedImage b = getScreen();
		return new java.awt.Color(b.getRGB(p.x, p.y));
	}

	/**
	 * Gets the color at the specified coordinates.
	 * 
	 * @param x
	 *            The x coordinate of the point at which you desire a color.
	 * @param y
	 *            The y coordinate of the point at which you desire a color.
	 * @return The color at the coordinates specified.
	 */
	public static Color getColor(int x, int y) {
		BufferedImage b = getScreen();
		return new java.awt.Color(b.getRGB(x, y));
	}

	/**
	 * 
	 * @param c
	 *            the given color to search for.
	 * @param start
	 *            the start point.
	 * @param end
	 *            the end point.
	 * @return the point of the given color between the start and end points.
	 */
	public static Point findColor(java.awt.Color c, Point start, Point end) {
		BufferedImage b = getScreen();
		if (b != null) {
			for (int i = start.x; i < end.x; i++) {
				for (int j = start.y; j < end.y; j++) {

					if (new java.awt.Color(b.getRGB(i, j)).equals(c)) {
						return new Point(i, j);
					}

				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param area
	 *            the area's rectangle.
	 * @return BufferedImage of the given rectangle.
	 */
	public static BufferedImage getScreenPart(Rectangle area) {
		if (area.x > 0 && area.x < 756 && area.x + area.width > 0
				&& area.x + area.width < 756) {
			if (area.y > 0 && area.y < 756 && area.y + area.height > 0
					&& area.y + area.height < 756) {
				return getScreen().getSubimage(area.x, area.y, area.width,
						area.height);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param b
	 *            the BufferedImage to search it.
	 * @return the color of the most common in the given BufferedImage.
	 */
	public static Color getAverage(BufferedImage b) {
		Color c[][] = ImageUtil.getColors(b);
		int[] tot = new int[3];
		int x = 0;
		for (Color[] d : c) {
			for (Color e : d) {
				x++;
				tot[0] += e.getRed();
				tot[1] += e.getGreen();
				tot[2] += e.getBlue();
			}
		}
		return new Color(tot[0] / x, tot[1] / x, tot[2] / x);
	}

	/**
	 * 
	 * @param color1
	 *            the first color
	 * @param color2
	 *            the second color param t - the rgb values which can vary. If c
	 *            = new Color(200,200,200) Such as tolerance = 10) it could
	 *            return anywhere from (190,190,190) - (210,210,210)
	 * @param t tolerance
     * @return true if the colors are within the tolerance
	 */
	public static boolean areColorsWithinTolerance(Color color1, Color color2,
			int t) {
		Color tolerance = new Color(t, t, t);
		return (color1.getRed() - color2.getRed() < tolerance.getRed() && color1
				.getRed() - color2.getRed() > -tolerance.getRed())
				&& (color1.getBlue() - color2.getBlue() < tolerance.getBlue() && color1
						.getBlue() - color2.getBlue() > -tolerance.getBlue())
				&& (color1.getGreen() - color2.getGreen() < tolerance
						.getGreen() && color1.getGreen() - color2.getGreen() > -tolerance
						.getGreen());
	}

	/**
	 * 
	 * @param color1
	 *            the first color
	 * @param color2
	 *            the second color param tolerance - the rgb values which can
	 *            vary. If c = new Color(200,200,200) Such as tolerance = new
	 *            Color(11,3,4) it could return anywhere from (189,197,196) -
	 *            (211,203,204)
	 * @return true if the colors are within the tolerance
	 */
	public static boolean areColorsWithinTolerance(Color color1, Color color2,
			Color tolerance) {
		return (color1.getRed() - color2.getRed() < tolerance.getRed() && color1
				.getRed() - color2.getRed() > -tolerance.getRed())
				&& (color1.getBlue() - color2.getBlue() < tolerance.getBlue() && color1
						.getBlue() - color2.getBlue() > -tolerance.getBlue())
				&& (color1.getGreen() - color2.getGreen() < tolerance
						.getGreen() && color1.getGreen() - color2.getGreen() > -tolerance
						.getGreen());
	}

	/**
	 * Gets the "distance" between two colors, their components assumed to be
	 * points in 3D space ranging from 0.0 to 1.0.
	 * 
	 * @param r1
	 *            Red value of the first color.
	 * @param g1
	 *            Green value of the first color.
	 * @param b1
	 *            Blue value of the first color.
	 * @param r2
	 *            Red value of the second color.
	 * @param g2
	 *            Green value of the second color.
	 * @param b2
	 *            Blue value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(double r1, double g1, double b1,
			double r2, double g2, double b2) {
		double red = r2 - r1;
		double green = g2 - g1;
		double blue = b2 - b1;
		return Math.sqrt(red * red + green * green + blue * blue);
	}

	/**
	 * Gets the "distance" between two colors, their components assumed to be
	 * points in 3D space ranging from 0.0 to 1.0.
	 * 
	 * @param c1
	 *            The first color.
	 * @param c2
	 *            The second Color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final Color c1, final Color c2) {
		float rgb1[] = new float[3];
		float rgb2[] = new float[3];
		c1.getColorComponents(rgb1);
		c2.getColorComponents(rgb2);
		return getDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	public static int getDistanceSquare(int c1, int c2) {
		int rd = ((c1 >> 16) & 0xFF) - ((c2 >> 16) & 0xFF);
		int gd = ((c1 >> 8) & 0xFF) - ((c2 >> 8) & 0xFF);
		int bd = (c1 & 0xFF) - (c2 & 0xFF);
		return rd * rd + gd * gd + bd * bd;
	}

	/**
	 * Gets the "distance" between two colors, their components assumed to be
	 * points in 3D space ranging from 0.0 to 1.0.
	 * 
	 * @param rgb1
	 *            The RGB values of the first color.
	 * @param rgb2
	 *            The RGB values of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final double[] rgb1, final double[] rgb2) {
		return getDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	/**
	 * Gets the "distance" between two colors, their components assumed to be
	 * points in 3D space ranging from 0.0 to 1.0.
	 * 
	 * @param rgb1
	 *            The RGB value of the first color.
	 * @param rgb2
	 *            The RGB value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final int rgb1, final int rgb2) {
		return getDistance(new Color(rgb1), new Color(rgb2));
	}
}
