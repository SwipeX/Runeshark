package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import api.wrapper.ColorPoint;

import loader.Boot;

/**
 * @Author Ramy, Soviet
 */
public class Bank {
	/* Various variables used throughout class */
	static Rectangle SEARCH_BTN = new Rectangle(60, 300, 35, 20);
	static Rectangle CLOSE = new Rectangle(481, 27, 16, 15);
	final static int BANK_SLOT_WIDTH = 30;
	final static int BANK_SLOT_HEIGHT = 35;
	
	/* Here lie the methods of the Bank class */
	/**
	 * Checks if the bank is open.
	 * 
	 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return ColorUtil.getColor(new Point(360, 314)).equals(
				new Color(64, 219, 69));
	}

    /**
     * @param p
     *          The point at which you would like to click to open the bank
     *
     * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
     */
    public static boolean open(Point p) {
        Mouse.click(p,false);
        p = Mouse.getLocation();
        Mouse.click(new Point((int)p.getX(),(int)p.getY()+45),5,3);
        return Bank.isOpen();
    }
    
	public static boolean close() {
		Mouse.clickMouse((int) CLOSE.getCenterX(),(int) CLOSE.getCenterY(), 4, 4, true);
	    return !Bank.isOpen();
    }
    
	public static Slot getSlotWithinColorTolerance(Color color, Color tolerance) {
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.areColorsWithinTolerance(slot_color,color,tolerance)) {
					return slot;
				}
			}
		}
		return null;
	}

    /**
    * @param item
    *            The item which you would like to search your bank for
    * @return The slot that contains the item you wish to find, returns null if
    *         not found
    */
    public static Slot getSlotOCR(String item) {
        if (open())  {
            for (final Slot slot : Slot.values()) {
                Mouse.move((int) slot.getCenter().getX(), (int) slot.getCenter()
                        .getY(), 3, 3);
                if (OCR.getUpText().contains(item)) {
                    return slot;
                }
            }
        }
        return null;
    }
	
	public static void withdrawColor(Color color, int amount) {
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (slot_color.equals(color)) {
					withdrawSlot(slot.getIndex(), amount);
				}
			}
		}
	}
	
	public static void withdrawColorWithTolerance(Color color, int amount, int t) {
		Color tolerance = new Color(t,t,t);
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.areColorsWithinTolerance(slot_color,color,tolerance)) {
					withdrawSlot(slot.getIndex(), amount);
				}
			}
		}
	}
	
	public static void withdrawColorWithTolerance(Color color, int amount, Color tolerance) {
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.areColorsWithinTolerance(slot_color,color,tolerance)) {
					withdrawSlot(slot.getIndex(), amount);
				}
			}
		}
	}


	public static void withdrawSlot(int index, int amount) {
		Slot BANK_SLOT = getSlot(index);
		Point p = BANK_SLOT.getCenter();
		Mouse.click(p, false);
		int x = (int) p.getX();
		int y = (int) p.getY();

		switch (amount) {
        case 0:
            Mouse.click(new Point(x, (y + 76)), 5, 3);
            break;
		case 1:
			Mouse.click(new Point(x, (y + 28)), 5, 3);
			break;
		case 5:
			Mouse.click(new Point(x, (y + 44)), 5, 3);
			break;
		case 10:
			Mouse.click(new Point(x, (y + 60)), 5, 3);
			break;
		case 28:
			Mouse.click(new Point(x, (y + 108)), 5, 3);
			break;
		case 27:
			Mouse.click(new Point(x, (y + 124)), 5, 3);
			break;
        default:
            Mouse.click(new Point(x, (y + 92)), 5, 3);
            KeyBoard.sendString(String.valueOf(amount),true);
            break;
		}
	}
	public static Slot getSlot(final int index) {
		for (final Slot slot : Slot.values()) {
			if (slot.getIndex() == index) {
				return slot;
			}
		}
		return null;
	}

	// The top bank slots represented as rectangles.
	public static enum Slot {
		SLOT_0(0, new Rectangle(40, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_1(
				1, new Rectangle(80, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_2(
				2, new Rectangle(120, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_3(
				3, new Rectangle(160, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_4(
				4, new Rectangle(200, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_5(
				5, new Rectangle(240, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_6(
				6, new Rectangle(280, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT)), SLOT_7(
				7, new Rectangle(320, 90, BANK_SLOT_WIDTH, BANK_SLOT_HEIGHT));

		private final int index;
		private final Rectangle bounds;

		public int getIndex() {
			return index;
		}
		
		private Slot(int index,Rectangle bounds) {
			this.index = index;
			this.bounds = bounds;
		}

		public static Slot getSlot(final int index) {
			for (final Slot slot : Slot.values()) {
				if (slot.getIndex() == index) {
					return slot;
				}
			}
			return null;
		}
		
		public static Slot getSlotWithColor(Color color) {
			for (final Slot slot : Slot.values()) {
				for (final Color slot_color : slot.getColors()) {
					if (slot_color.equals(color)) {
						return slot;
					}
				}
			}
			return null;
		}
		
	

		public Color[] getColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						final Color color = Game.getColorAt(p);
						colors.add(color);
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}

		public Color getCenterColor() {
			final Point center = getCenter();
			return Game.isPointValid(center) ? Game.getColorAt(center) : null;
		}

		public  Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y
					+ (int) (bounds.height / 2));
		}
	}
    
        public static boolean open(){
            return Bank.isOpen();
        }

		public static void setSearch() {
			Mouse.clickMouse((int) SEARCH_BTN.getCenterX(),
					(int) SEARCH_BTN.getCenterY(), 2, 2, true);
		}

		public static void Search(String s) {
			setSearch();
			KeyBoard.sendString(s, false);
		}

		public static final ColorPattern OPEN_PATTERN = new ColorPattern(
				new ColorPoint[] { new ColorPoint(64, 33, 73, 66, 50),
						new ColorPoint(21, 265, 85, 79, 63),
						new ColorPoint(266, 319, 89, 76, 63),
						new ColorPoint(489, 285, 35, 30, 23) });
		public static final ColorPattern EQUIP_OPEN_PATTERN = new ColorPattern(
				new ColorPoint[] { new ColorPoint(24, 14, 73, 66, 50),
						new ColorPoint(268, 21, 255, 152, 31),
						new ColorPoint(474, 51, 206, 205, 62),
						new ColorPoint(352, 322, 73, 64, 52) });
		public static final ColorPattern NOTED_PATTERN = new ColorPattern(
				new ColorPoint[] { new ColorPoint(223, 303, 165, 161, 123),
						new ColorPoint(234, 403, 46, 36, 27),
						new ColorPoint(227, 314, 118, 115, 85),
						new ColorPoint(232, 313, 195, 192, 165) });

	
		/**
		 * An enum of bank buttons.
		 */
		public enum BankButton {
			EQUIPMENT_STATS("Equipment Stats", new Rectangle(460, 47, 34, 33)), BANK(
					"Bank", new Rectangle(458, 36, 34, 35)), SEARCH("Search",
					new Rectangle(62, 296, 34, 24)), WITHDRAW_MODE(
					"Withdraw Mode", new Rectangle(212, 296, 34, 24)), DEPOSIT_ALL_INVENTORY(
					"Deposit All Inventory", new Rectangle(352, 296, 34, 24)), DEPOSIT_ALL_EQUIPMENT(
					"Deposit All Equipment", new Rectangle(388, 296, 34, 24)), DEPOSIT_ALL_BOB(
					"Deposit All BoB", new Rectangle(424, 296, 34, 24)), DEPOSIT_MONEY_POUCH(
					"Deposit Money Pouch", new Rectangle(460, 296, 34, 24)), CLOSE(
					"Close Bank", new Rectangle(481, 27, 16, 15));

			private final String name;
			private final Rectangle bounds;

			private BankButton(final String name, final Rectangle bounds) {
				this.name = name;
				this.bounds = bounds;
			}

			/**
			 * Gets the name of the bank button.
			 * 
			 * @return The button's name.
			 */
			public String getName() {
				return name;
			}

			/**
			 * Gets the interactive bounds of the button.
			 * 
			 * @return The button's bounds.
			 */
			public Rectangle getBounds() {
				return bounds;
			}

			/**
			 * Gets the center point of the button.
			 * 
			 * @return The button's center.
			 */
			public Point getCenter() {
				return new Point((int) (bounds.x + (bounds.width / 2)),
						(int) (bounds.y + (bounds.height / 2)));
			}

			/**
			 * Gets the color array of the button's display.
			 * 
			 * @return The button's colors.
			 */
			public Color[][] getColors() {
				final Color[][] colors = new Color[bounds.width][bounds.height];
				for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
					for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
						colors[x][y] = Game.getColors()[x][y];
					}
				}
				return colors;
			}

			/**
			 * Clicks the button.
			 */
			public void click() {
				Mouse.click(getCenter(), 3,3);
			}

		}

		/**
		 * Opens the equipment stats view.
		 * 
		 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
		 */
		public static boolean openEquipmentView() {
			BankButton.EQUIPMENT_STATS.click();
			MethodProvider.sleep(1500);
			return isEquipmentOpen();
		}

		/**
		 * Opens the bank view.
		 * 
		 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
		 */
		public static boolean openBankView() {
			BankButton.BANK.click();
			MethodProvider.sleep(1500);
			return isOpen() && !isEquipmentOpen();
		}

		/**
		 * Checks if the equipment window is open.
		 * 
		 * @return <tt>true</tt> if equipment window is open; otherwise
		 *         <tt>false</tt>.
		 */
		public static boolean isEquipmentOpen() {
			return EQUIP_OPEN_PATTERN.isPresent(Game.getImage());
		}

		/**
		 * Searches for a given string in the bank's search.
		 * 
		 * @param search
		 *            The string to search for.
		 */
		public static void search(final String search) {
			BankButton.SEARCH.click();
			MethodProvider.sleep(1000);
			KeyBoard.typeWord(search);
			MethodProvider.sleep(1000);
		}

		/**
		 * Checks if the search is open.
		 * 
		 * @return <tt>true</tt> if search is open; otherwise <tt>false</tt>.
		 */
		public static boolean isSearchOpen() {
			// TODO
			Boot.log("Bank => isSearchOpen() not implemented!");
			return false;
		}

		/**
		 * Sets the withdrawal mode.
		 * 
		 * @param noted
		 *            <tt>true</tt> for noted; <tt>false</tt> for unnoted.
		 * @return <tt>true</tt> if successful; otherwise <tt>false</tt>.
		 */
		public static boolean setWithdrawalMode(final boolean noted) {
			if (isWithdrawalModeNoted() != noted) {
				BankButton.WITHDRAW_MODE.click();
				MethodProvider.sleep(1200);
				return isWithdrawalModeNoted() == noted;
			}
			return isWithdrawalModeNoted();
		}

		/**
		 * Checks if the current withdrawal mode is set to noted.
		 * 
		 * @return <tt>true</tt> if noted; otherwise <tt>false</tt>.
		 */
		public static boolean isWithdrawalModeNoted() {
			return NOTED_PATTERN.isPresent(Game.getImage());
		}

		/**
		 * Clicks the deposit all button.
		 */
		public static void depositAll() {
			BankButton.DEPOSIT_ALL_INVENTORY.click();
		}

		/**
		 * Clicks the deposit all equipment button.
		 */
		public static void depositEquipment() {
			BankButton.DEPOSIT_ALL_EQUIPMENT.click();
		}

		/**
		 * Clicks the deposit beast of burden inventory button.
		 */
		public static void depositBoB() {
			BankButton.DEPOSIT_ALL_BOB.click();
		}

		/**
		 * Clicks the deposit money pouch button.
		 */
		public static void depositMoneyPouch() {
			BankButton.DEPOSIT_MONEY_POUCH.click();
		}	
}
