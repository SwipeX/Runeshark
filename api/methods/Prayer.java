package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Dwarfeh
 */


public class Prayer {

    public static final Color ACTIVATED = new Color(171, 154, 108);
    public static final Color QUICKPRAYER_ACTIVATED = new Color(155, 186, 150);
    public static final Rectangle QUICKPRAYER_BOUNDS = new Rectangle(705, 53,34, 33);
    public static final Rectangle QUICKPRAYER_TEXT_BOUNDS = new Rectangle(740,62, 22, 23);
    public static final Rectangle PRAYER_TEXT_BOUNDS = new Rectangle(733, 58,28, 26);

    public static boolean isOpen() {
        return Tabs.getCurrentTab().equals("Prayer");
    }

    public static boolean open() {
        return Tabs.setTab("Prayer");
    }

    /**
     * Gets the current prayer amount
     *
     * @return The current prayer amount
     */
    public static int getCurrentPrayerPoints() {
        return Integer.parseInt(RSText.findString(PRAYER_TEXT_BOUNDS, null,null));
    }

    public static Point getIconCenter() {
        return new Point((QUICKPRAYER_BOUNDS.x + (QUICKPRAYER_BOUNDS.width / 2)), (QUICKPRAYER_BOUNDS.y + (QUICKPRAYER_BOUNDS.height / 2)));
    }

    public static Color[] getButtonColors() {
        if (open()) {
            final List<Color> colors = new LinkedList<Color>();
            for (int x = QUICKPRAYER_BOUNDS.x; x < QUICKPRAYER_BOUNDS.x + QUICKPRAYER_BOUNDS.width; x++) {
                for (int y = QUICKPRAYER_BOUNDS.y; y < QUICKPRAYER_BOUNDS.y + QUICKPRAYER_BOUNDS.height; y++) {
                    final Point p = new Point(x, y);
                    if (Client.isPointValid(p)) {
                        colors.add(ColorUtil.getColor(p));
                    }
                }
            }
            return colors.toArray(new Color[colors.size()]);
        }
        return null;
    }

    public static boolean isQuickPrayerActivated() {
        final List<Color> colors = Arrays.asList(getButtonColors());
        return colors.contains(QUICKPRAYER_ACTIVATED);
    }

    public static boolean setQuickPrayerEnabled(final boolean activated) {
        if (isQuickPrayerActivated() != activated) {
            Mouse.click(getIconCenter());
        }
        return isQuickPrayerActivated();
    }

    public Modern getCurrentModernEffect(){
        if (open()) {
            for(Modern m: Modern.values()){
                if(m.isActivated()){
                    return m;
                }
            }
        }
        return null;
    }

    public enum Modern implements Effect {
        THICK_SKIN("Thick Skin", 1, getPrayerSlot(0, 0)),
        BURST_STRENGTH("Burst Strength", 4, getPrayerSlot(1, 0)),
        CLARITY_THOUGHT("Clarity Thought", 7, getPrayerSlot(2, 0)),
        SHARP_EYE("Sharp Eye", 8, getPrayerSlot(3, 0)),
        MYSTIC_WILL("Mystic Will", 9, getPrayerSlot(4, 0)),
        ROCK_SKIN("Rock Skin", 10, getPrayerSlot(0, 1)),
        SUPERHUMAN_STRENGTH("Superhuman Strength", 13, getPrayerSlot(1, 1)),
        IMPROVED_REFLEXES("Improved Reflexes", 16, getPrayerSlot(2, 1)),
        RAPID_RESTORE("Rapid Restore", 19, getPrayerSlot(3, 1)),
        RAPID_HEAL("Rapid Heal", 22, getPrayerSlot(4, 1)),
        PROTECT_ITEM("Protect Item", 25, getPrayerSlot(0, 2)),
        HAWK_EYE("Hawk Eye", 26, getPrayerSlot(1, 2)),
        MYSTIC_LORE("Mystic Lore", 27, getPrayerSlot(2, 2)),
        STEEL_SKIN("Steel Skin", 28, getPrayerSlot(3, 2)),
        ULTIMATE_STRENGTH("Ultimate Strength", 31, getPrayerSlot(4, 2)),
        INCREDIBLE_REFLEXES("Incredible Reflexes", 34, getPrayerSlot(0, 3)),
        PROTECT_SUMMONING("Protect from Summoning", 35, getPrayerSlot(1, 3)),
        PROTECT_MAGIC("Protect from Magic", 37, getPrayerSlot(2, 3)),
        PROTECT_MISSILES("Protect from Missiles", 40, getPrayerSlot(3, 3)),
        PROTECT_MELEE("Protect from Melee", 43, getPrayerSlot(4, 3)),
        EAGLE_EYE("Eagle Eye", 45, getPrayerSlot(0, 4)),
        MYSTIC_MIGHT("Mystic Might", 46, getPrayerSlot(1, 4)),
        RETRIBUTION("Retribution", 47, getPrayerSlot(2, 4)),
        REDEMPTION("Redemption", 49, getPrayerSlot(3, 4)),
        SMITE("Smite", 52, getPrayerSlot(4, 4)),
        CHIVALRY("Chivalry", 60, getPrayerSlot(0, 5)),
        RAPID_RENEWAL("Rapid Renewal", 65, getPrayerSlot(1, 5)),
        PIETY("Piety", 70, getPrayerSlot(2, 5)),
        RIGOUR("Rigour",74, getPrayerSlot(3, 5)),
        AUGURY("Augury", 77,getPrayerSlot(4, 5));

        private final String name;
        private final int requiredLevel;
        private final Rectangle bounds;

        private Modern(final String name, final int required_level, final Rectangle bounds) {
            this.name = name;
            this.requiredLevel = required_level;
            this.bounds = bounds;
        }

        public String getName() {
            return name;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public Point getCenter() {
            return new Point((bounds.x + (bounds.width / 2)), (bounds.y + (bounds.height / 2)));
        }

        public Color[] getColors() {
            if (open()) {
                final List<Color> colors = new LinkedList<Color>();
                for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                    for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                        final Point p = new Point(x, y);
                        if (Client.isPointValid(p)) {
                            colors.add(ColorUtil.getColor(p));
                        }
                    }
                }
                return colors.toArray(new Color[colors.size()]);
            }
            return null;
        }

        public boolean isActivated() {
            if (open()) {
                final List<Color> colors = Arrays.asList(getColors());
                return colors.contains(ACTIVATED);
            }
            return false;
        }

        public boolean setEnabled(final boolean activated) {
            if (open()) {
                if (isActivated() != activated) {
                    Mouse.click(getCenter(), 6, 6);
                }
                return isActivated();
            }
            return false;
        }
    }

    //TODO NOT MEMBER
    public enum Ancient implements Effect {
        ;

        private final String name;
        private final int requiredLevel;
        private final Rectangle bounds;

        private Ancient(final String name, final int required_level,
                        final Rectangle bounds) {
            this.name = name;
            this.requiredLevel = required_level;
            this.bounds = bounds;
        }

        public String getName() {
            return name;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public Point getCenter() {
            return new Point((bounds.x + (bounds.width / 2)), (bounds.y + (bounds.height / 2)));
        }

        public Color[] getColors() {
            if (open()) {
                final List<Color> colors = new LinkedList<Color>();
                for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                    for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                        final Point p = new Point(x, y);
                        if (Client.isPointValid(p)) {
                            colors.add(ColorUtil.getColor(p));
                        }
                    }
                }
                return colors.toArray(new Color[colors.size()]);
            }
            return null;
        }

        public boolean isActivated() {
            if (open()) {
                final List<Color> colors = Arrays.asList(getColors());
                return colors.contains(ACTIVATED);
            }
            return false;
        }

        public boolean setEnabled(final boolean activated) {
            if (open()) {
                if (isActivated() != activated) {
                    Mouse.click(getCenter(), 6, 6);
                }
                return isActivated();
            }
            return false;
        }
    }

    public interface Effect {

        public String getName();

        public int getRequiredLevel();

        public Rectangle getBounds();

        public Point getCenter();

        public Color[] getColors();

        public boolean isActivated();

        public boolean setEnabled(final boolean activated);

    }

    /**
     *
     * @param columnNumber
     *            Starts at 0 for columns
     * @return Correct X Value for the inventory column
     */
    private static int getXValue(int columnNumber) {
        return 555 + columnNumber * 37;
    }

    /**
     *
     * @param rowNumber
     *            Starts at 0 for Rows
     * @return correct Y value for inventory row
     */
    private static int getYValue(int rowNumber) {
        return 212 + rowNumber * 36;
    }

    /**
     *
     * @param columnNumber
     *            Starts at 0 for column Number. 0-3
     * @param rowNumber
     *            Starts at 0 for row Number. 0-6
     * @return Makes a rectangle from desired row and column number
     */
    private static Rectangle getPrayerSlot(int columnNumber, int rowNumber) {
        return new Rectangle(getXValue(columnNumber), getYValue(rowNumber), 30, 32);
    }

}