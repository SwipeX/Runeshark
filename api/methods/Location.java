package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import ui.BotFrame;
import util.Configuration;

import bot.Bot;

/**
 * @Author Unknown. Converted from Simba
 */
public class Location {
	public static BufferedImage FULL_MAP = null;
	public static Point PLAYER_DOT = new Point(625, 85);
	public static Color Flag = new Color(255, 40, 0);
	private static final Logger log = Logger.getLogger("Location");
	private static final Rectangle INNER_COMPASS_BOUNDS = new Rectangle(529,
			10, 29, 29);

	public Location() {
		loadMap();
	}

	public static void loadMap() {
		try {
			log.info(new File(Configuration.Paths.getCacheDirectory()
					+ File.separator + "runescape_surface.png")
					.getAbsolutePath());
			FULL_MAP = ImageIO.read(new File(new File(Configuration.Paths
					.getCacheDirectory()
					+ File.separator
					+ "runescape_surface.png").getAbsolutePath()));
			if (FULL_MAP != null) {
				log.info("loaded map");
			}
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public static Rectangle MINIMAP_LOC = new Rectangle(580, 40, 90, 90);
	public static final Rectangle COMPASS = new Rectangle(529, 557, 9, 39);

	public static BufferedImage getMiniMap() {
		return ColorUtil.getScreenPart(MINIMAP_LOC);
	}

	public static BufferedImage getCompass() {
		return ColorUtil.getScreenPart(INNER_COMPASS_BOUNDS);
	}

	/**
	 * Gets the current angle of the compass relative to true north. Examples: -
	 * If facing north, returns 0 degrees. - If facing east, returns 90 degrees.
	 * - If facing south, returns 180 degrees. - If facing west, returns 270
	 * degrees.
	 * 
	 * 
	 * @return The angle of the compass, or -1 if unable to read compass.
	 */
	public static double getCompassAngle() {
		BufferedImage game = Bot.getCurrent().getScreen();
		final double radius = (INNER_COMPASS_BOUNDS.width / 2.0) - 1.0;
		final double centerX = INNER_COMPASS_BOUNDS.x + radius;
		final double centerY = INNER_COMPASS_BOUNDS.y + radius;
		final List<Integer> angles = getCompassBlackPoints(game, centerX + 1.0,
				centerY + 1.0, radius);
		if (angles == null) {
			return -1;
		}

		// Fixes the bug where the "N" reaches the 360 | 0 degrees border and
		// the points are read in the opposite order, which results in
		// inaccurate readings of +/- 10 when the compass is at ~90 degrees.
		double angle = angles.get(0);
		if ((angles.get(1) - angle) > 100) {
			angle = angles.get(1) + 10.0;
		} else if (angles.get(0) < 20 && angles.get(1) > 300) {
			angle = angles.get(1) - 10.0;
		} else {
			angle += 10.0;
		}
		angle *= Math.PI / 180;
		final double curX = (centerX + radius * Math.cos(angle));
		final double curY = (centerY + radius * Math.sin(angle));
		return 360 - (Math.abs((Math.atan2((centerY - radius) - curY, centerX
				- curX) * 2)
				* 180 / Math.PI));
	}

	/**
	 * Gets the two black pixels from the "N" letter that touch the outer edge
	 * of the compass. The center between those two points is a very accurate
	 * reading of the compass's pointing direction.
	 * 
	 * @param game
	 *            The game client image.
	 * @param x
	 *            The x coordinate of the compass.
	 * @param y
	 *            The y coordinate of the compass.
	 * @param radius
	 *            The compass's radius.
	 * 
	 * @return A list containing the two points, or null if not found.
	 */
	private static List<Integer> getCompassBlackPoints(
			final BufferedImage game, final double x, final double y,
			final double radius) {

		final ArrayList<Integer> angles = new ArrayList<Integer>();
		circleLoop: for (double i = 0.0; i < 360.0; i += 1.0) {
			final double angle = i * Math.PI / 180;
			final int curX = (int) (x + radius * Math.cos(angle));
			final int curY = (int) (y + radius * Math.sin(angle));
			for (final int a : angles) {
				if ((i - a) < 8) {
					continue circleLoop;
				}
			}
			final int rgb = game.getRGB(curX, curY);
			if (ColorUtil.getDistance(rgb, Color.BLACK.getRGB()) < 0.01) {
				angles.add((int) i);
				if (angles.size() == 2) {
					return angles;
				}
			}
		}
		return null;
	}

	public static Point[] getAllDots(Color c) {
		return ColorUtil.findAllColorWithinTolerance(c, getMiniMap(),
				new Color(2, 2, 2));
	}

	public static Point[] getAllPlayers() {
		return getAllDots(new Color(255, 238, 251)); // 55,45,52 - white
	}

	public static Point[] getAllNPCs() {
		return getAllDots(new Color(253, 255, 5));
	}

	public static Point[] getAllDrops() {
		return getAllDots(new Color(255, 43, 22));
	}

	public static BufferedImage rotateImage(BufferedImage b, int deg) {
		return ImageUtil.rotate(b, deg);
	}

	public static BufferedImage rotateMiniMap(int degrees) {
		return rotateImage(getMiniMap(), degrees);
	}

	public static BufferedImage getMiniMapRotated() {
		return rotateMiniMap(360 - (int) getCompassAngle());
	}

	public static Point getFullLoc() {
		Point[] p = { new Point(622, 80), new Point(625, 80),
				new Point(628, 80), new Point(622, 90), new Point(625, 90),
				new Point(628, 90) };
		Color[] c = new Color[p.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = ColorUtil.getColor(p[i]); // get Colors from minimap
		}
		// load map
		if (FULL_MAP == null) {
			loadMap();
		}
		// compare Colors and locations...
		return PLAYER_DOT;

	}

	@SuppressWarnings("unused")
	public static Point getMiniMapLocOnFull() {
		ArrayList<Integer> totals = new ArrayList<Integer>();
		ArrayList<Point> locs = new ArrayList<Point>();
		// load map
		if (FULL_MAP == null) {
			loadMap();
		}
		// get minimap
		BufferedImage minmap = getMiniMapRotated();
		List<Point> p = null;
		for (double d = 0; d < 1.0; d += .1) {
			p = ImageUtil.findMatchLocations(FULL_MAP, minmap, d);
			for (Point p2 : p) {
				BotFrame.log("Estimated - " + p2.x + ", " + p2.y);
			}
		}

		return new Point(0, 0);

	}

	public static Point getMiniMapLocOnFull2() {
		ArrayList<Point> tempc1 = new ArrayList<Point>();
		ArrayList<Point> tempc2 = new ArrayList<Point>();
		// load map
		if (FULL_MAP == null) {
			loadMap();
		}
		// get minimap
		BufferedImage minmap = getMiniMapRotated();
		Color c1 = ImageUtil.getColorAt(minmap, 20, 50);
		Color c2 = ImageUtil.getColorAt(minmap, 80, 50);
		int distance = Calc.getDistanceBetween(new Point(20, 50), new Point(80,
				50));
		Color t;
		for (int i = 0; i < FULL_MAP.getHeight(); i++) {
			for (int j = 0; j < FULL_MAP.getWidth(); j++) {
				if (ColorUtil.areColorsWithinTolerance(
						(t = ImageUtil.getColorAt(FULL_MAP, new Point(j, i))),
						c1, new Color(2, 2, 2))) {
					BotFrame.log("Found c1: " + j + ", " + i);
					tempc1.add(new Point(j, i));
				}
				if (t.equals(c2)) {
					BotFrame.log("Found c2: " + j + ", " + i);
					tempc2.add(new Point(j, i));
				}
			}

		}
        for (Point aTempc1 : tempc1) {
            for (Point aTempc2 : tempc2) {
                if (Calc.getDistanceBetween(aTempc1, aTempc2) == distance) {
                    log.info("Found match at :" + aTempc1.x + ", "
                            + aTempc1.y);
                }
            }
        }
		return null;
	}

}
