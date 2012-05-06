package api.wrapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import ui.BotFrame;

import api.methods.Game;

/**
 * 
 * @author Unknown. Converted from Simba
 * @Updater  Swipe
 * 
 */
public class DTM {
	private final Color[] rgb;
	private final Point[] loc;
	private Rectangle box;
	private final String name;
	private ArrayList<Rectangle> dtmLocations = new ArrayList<Rectangle>();

	public DTM(final Point[] loc, final Color[] color, final String name) {
		this.loc = loc;
		this.rgb = color;
		this.name = name;
		box = createRectangleFromPoints(loc);
	}

	public DTM(final Point[] loc, final Color[] color) {
		this(loc, color, "DTM");
	}

	/**
	 * Given a set of points create a rectangle that encases all of those points
	 * 
	 * @param points
	 *            : set of points to create a rectangle with
	 * @return : A rectangle that encloses all points
	 */
	private Rectangle createRectangleFromPoints(final Point[] points) {
		Point minPoint = new Point(800, 600), maxPoint = new Point(0, 0);
		for (int i = 0; i < loc.length; i++) {
			if (points[i].x < minPoint.x)
				minPoint.x = points[i].x;
			if (points[i].x > maxPoint.x)
				maxPoint.x = points[i].x;
			if (points[i].y < minPoint.y)
				minPoint.y = points[i].y;
			if (points[i].y > maxPoint.y)
				maxPoint.y = points[i].y;
		}
		return new Rectangle(minPoint.x, minPoint.y,
				(maxPoint.x - minPoint.x) + 1, (maxPoint.y - minPoint.y) + 1);
	}

	/**
	 * Looks for the DTM in the original spot specified
	 * 
	 * @param tol
	 *            : tolerance between each RGB value when checking if colors are
	 *            the same
	 * @return <tt>true</tt> if a DTM was found; otherwise <tt>false</tt>
	 */
	public boolean findOriginalDTM(final int tol) {
		return checkAllPoints(loc[0], tol);
	}

	/**
	 * Finds all DTMs on the game screen stores and stores there location to the
	 * dtmLocations ArrayList.
	 * 
	 * @param tol
	 *            : tolerance between each RGB value when checking if colors are
	 *            the same
	 * @return <tt>true</tt> if a DTM was found; otherwise <tt>false</tt>
	 */
	public boolean findDTMS(final int tol) {
		return findDTMS(tol, new Rectangle(0, 0, Game.getCanvasSize().width,
				Game.getCanvasSize().height));
	}

	/**
	 * Searches for DTMs in in the rectangle passed and stores all DTMs found to
	 * the dtmLocations ArrayList.
	 * 
	 * @param tol
	 *            : tolerance between each RGB value when checking if colors are
	 *            the same
	 * @param rec
	 *            : Rectangle to search for the DTM to be in.
	 * @return <tt>true</tt> if a DTM was found; otherwise <tt>false</tt>
	 */
	public boolean findDTMS(final int tol, final Rectangle rec) {
		boolean foundDTM = false;
		dtmLocations.clear();

		for (int y = rec.y; y < rec.y + rec.height; y++) {
			for (int x = rec.x; x < rec.x + rec.width; x++) {
				if (checkColor(Game.getColorAt(x, y), rgb[0], tol)) {
					final Rectangle curbox = new Rectangle(x
							- (loc[0].x - box.x), y - (loc[0].y - box.y),
							box.width, box.height);
					if (checkAllPoints(new Point(x, y), tol)) {
						dtmLocations.add(curbox);
						foundDTM = true;
					}
				}
			}
		}
		return foundDTM;
	}

	/**
	 * Grabs the DTM specified by index in the dtms found array
	 * 
	 * @param i
	 *            : index in the dtm array to grab
	 * @return : the center point of the rectangle for the DTM or null if not
	 *         found
	 */
	public Point getDTM(final int i) {
		return dtmLocations.isEmpty() ? null : new Point(dtmLocations.get(i).x
				+ (dtmLocations.get(i).width / 2), dtmLocations.get(i).y
				+ (dtmLocations.get(i).height / 2));
	}

	/**
	 *
	 * @param locM
	 *            location of point that matches the first color in the array
	 *            list colorB
	 * @param tol tolerance
     * @return true if all colors match
	 */
	private boolean checkAllPoints(Point locM, final int tol) {
		try {
			final Point locI = loc[0];
			for (int i = 0; i < loc.length; i++) {
				// we can come back and shorten this, but for now this is to
				// make sure we make no mistakes
				final Color initialColor = rgb[i];
				final Point initialColorPoint = loc[i];
				final int xshift = initialColorPoint.x - locI.x;
				final int yshift = initialColorPoint.y - locI.y;
				final Point pointToCheck = new Point(locM.x + xshift, locM.y
						+ yshift);
				final Color colorToCheck = Game.getColorAt(pointToCheck);

				if (!checkColor(initialColor, colorToCheck, tol))
					return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks to see if the 2 colors passed match
	 * 
	 * @param c
	 *            : Color 1
	 * @param c2
	 *            : Color 2
	 * @param tol
	 *            : tolerance between each RGB value when checking if colors are
	 *            the same
	 * @return <tt>true</tt> if colors match; otherwise <tt>false</tt>
	 */
	private boolean checkColor(final Color c, final Color c2, final int tol) {
		return (checkColor(c.getRed(), c2.getRed(), tol)
				&& checkColor(c.getGreen(), c2.getGreen(), tol) && checkColor(
				c.getBlue(), c2.getBlue(), tol));
	}

	/**
	 * Checks to see if the 2 RGB values match
	 * 
	 * @param RGB1
	 *            : RGB value 1
	 * @param RGB2
	 *            : RGB value 2
	 * @param tol
	 *            : tolerance between the RGB values when checking if they are
	 *            the same
	 * @return <tt>true</tt> if RGB values match; otherwise <tt>false</tt>
	 */
	private boolean checkColor(final int RGB1, final int RGB2, final int tol) {
		return Math.abs(RGB1 - RGB2) < tol;
	}

	public void drawDTMs(Graphics g) {
		g.setColor(Color.RED);
        for (Rectangle dtmLocation : dtmLocations) {
            drawX(g, dtmLocation);
        }
	}

	private void drawX(Graphics g, Rectangle rec) {
		g.setColor(Color.RED);
		g.drawLine(rec.x, rec.y, rec.x + rec.width, rec.y + rec.height);
		g.drawLine(rec.x, rec.y + rec.height, rec.x + rec.width, rec.y);
	}

	public void drawDTMSquares(Graphics g, final Color c1, boolean addname)
			throws Exception {
		for (int i = 0; i < dtmLocations.size(); i++) {
			Rectangle dtm = dtmLocations.get(i);
			g.setColor(c1);
			g.drawRect(dtm.x, dtm.y, dtm.width, dtm.height);
			if (!addname)
				return;
			g.setColor(Color.BLACK);
			g.fill3DRect(dtm.x + dtm.width - 33, dtm.y + dtm.height + 4, 41,
					11, true);
			g.setColor(Color.WHITE);
			g.drawString(name + " " + i, dtm.x + dtm.width - 30, dtm.y
					+ dtm.height + 13);
		}
	}

	public void fancydraw(Graphics g, Color recColor, boolean addname,
			boolean addX) {
		try {
			if (dtmLocations.isEmpty())
				return;
			g.setFont(g.getFont().deriveFont((float) 9));
            for (Rectangle dtmLocation : dtmLocations) {
                drawDTMSquares(g, recColor, addname);
            }
			if (addX)
				drawX(g, dtmLocations.get(0));
		} catch (Exception e) {
			BotFrame.log("Exception occured in paint");
		}
	}
}