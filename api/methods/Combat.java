package api.methods;

import java.awt.*;
import java.util.ArrayList;

import api.wrapper.ColorPoint;

/**
 * @Author Swipe
 * @Updater Dwarfeh
 */
public class Combat {
	private static Point MIDSCREEN = new Point(259, 162);
	public static final Rectangle HP_TEXT_BOUNDS = new Rectangle(719, 19, 27,
			25);

	public enum AttackStyle {

		STRENGTH(0, null),
        DEFENCE(1, null),
        ATTACK(2, null),
        RANGE(3, null),
        MAGIC(4, null);

		private int ID;
		private ArrayList<ColorPoint> XPColorPoints;

		AttackStyle(int ID, ArrayList<ColorPoint> XPColorPoints) {
			this.ID = ID;
			this.XPColorPoints = XPColorPoints;
		}

		public int getID() {
			return ID;
		}

		public ColorPoint[] getXPColorPointsArray() {
			return XPColorPoints.toArray(new ColorPoint[XPColorPoints.size()]);
		}

		public ArrayList<ColorPoint> getXPColorPoints() {
			return XPColorPoints;
		}

		public AttackStyle getStyle() {
			for (AttackStyle as : AttackStyle.values()) {
				int count = 0;
				for (ColorPoint cp : as.getXPColorPoints()) {
					if (Game.getColorAt(cp.getPoint()).equals(cp.getColor())) {
						count++;
					}
				}
				if (count == as.getXPColorPoints().size()) {
					return as;
				}
			}
			return null;
		}

		public AttackStyle getStyle(ArrayList<ColorPoint> XPColorPoints) {
			for (AttackStyle as : AttackStyle.values()) {
				if (as.getXPColorPoints().equals(XPColorPoints)) {
					return as;
				}
			}
			return null;
		}

		public AttackStyle getStyle(int ID) {
			for (AttackStyle as : AttackStyle.values()) {
				if (as.getID() == ID) {
					return as;
				}
			}
			return null;
		}
	}

	/**
	 * efficient way to check low health
	 * 
	 * @return true if health is below 35%
	 */
	public static boolean isHealthLow() {
		return ColorUtil.getColor(714, 30).equals(new Color(27, 0, 0));
	}

	/**
	 * Gets the current hp amount
	 * 
	 * @return The current amount
	 */
	public static int getCurrentHP() {
		return Integer.parseInt(RSText.findString(HP_TEXT_BOUNDS, null, null));
	}

	/**
	 * @return true if player is in combat.
	 */
	public static boolean isInCombat() {
		Color hpBar = new Color(155, 202, 0);
		java.util.List<Point> hpBarLoc = ImageUtil.getPointsWithColor(
				Game.getImage(), hpBar, 0.05D);
		Point nearest = null;
		double dist = 0;
		double maxDist = 45;
		for (Point POINT : hpBarLoc) {
			double distTmp = Calc.getDistanceBetween(POINT, MIDSCREEN);
			if (distTmp < maxDist) {
				if (nearest == null) {
                    dist = distTmp;
                    nearest = POINT;
				} else if (distTmp < dist) {
                    nearest = POINT;
                    dist = distTmp;
				}
			}
		}
        return nearest != null;
    }
}
