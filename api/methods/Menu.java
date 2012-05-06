package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class Menu {
	public final static Color MENU = new Color(166, 166, 166);

	public static boolean isOpen() {
		return ColorUtil.findAllColorWithinTolerance(MENU, new Color(1, 1,
				1)).length > 0;
	}

	public static int getIndex(String action) {
		if (!isOpen())
			return -1;
		try {
			for (int x = 0; x < getHeight(getBounds().height); x++) {
				Rectangle rect1 = getRect(x + 1);
				String text = RSText.findString(new Color(160, 152, 123), rect1, RSText.FontTypes.UpCharsEx);
				if (text.contains(action))
					return x + 1;
			}
		} catch (Exception ignored) {
		}
		return -1;
	}

	public static void clickAction(String action) {
		clickIndex(getIndex(action));
	}

	public static void clickIndex(int index) {
		if (!isOpen()) {
			return;
		}
		Point[] arr = ColorUtil.findAllColorWithinTolerance(MENU,
				new Color(1, 1, 1));
		if (arr.length < 0) {
			return;
		}
		int menuY = 8;
		for (int x = 0; x < index; x++) {
			menuY += 16;
		}
		Point left = Calc.getLeftMax(arr);
		Point right = Calc.getRightMax(arr);
		Point high = Calc.getNorthern(arr);
		Mouse.click(left.x + (right.x - left.x) / 2, high.y + menuY, true);
	}

	public static int getHeight(int height) {
		height -= 20;
		return height / 17;
	}

	public static Rectangle getBounds() {
		Point[] arr = ColorUtil.findAllColorWithinTolerance(MENU,
				new Color(1, 1, 1));
		try {
			Point left = Calc.getLeftMax(arr);
			Point right = Calc.getRightMax(arr);
			Point high = Calc.getNorthern(arr);
			Point low = Calc.getSouthern(arr);
			return new Rectangle(left.x, high.y, right.x - left.x, low.y
					- high.y);
		} catch (Exception ignored) {
		}
		return null;
	}

	public static Rectangle getRect(int index) {
		Rectangle bounds = getBounds();
		int y = bounds.y;
		y += 17 * index;
		return new Rectangle(bounds.x, y, bounds.width, 17);
	}

	public static String getText(int index, Color color) {
		Rectangle rect = getRect(index);
		return RSText.findString(color,	rect, RSText.FontTypes.UpCharsEx);
	}
}