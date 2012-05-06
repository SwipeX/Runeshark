package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Dwarfeh
 * @Updater Soviet
 */
public class Inventory {

    public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
    private static final int WIDTH = 36;
    private static final int HEIGHT = 32;



    public static enum Slot {
        SLOT_0(0, getInventorySlot(0, 0)), SLOT_1(1, getInventorySlot(1, 0)), SLOT_2(
                2, getInventorySlot(2, 0)), SLOT_3(3, getInventorySlot(3, 0)), SLOT_4(
                4, getInventorySlot(0, 1)), SLOT_5(5, getInventorySlot(1, 1)), SLOT_6(
                6, getInventorySlot(2, 1)), SLOT_7(7, getInventorySlot(3, 1)), SLOT_8(
                8, getInventorySlot(0, 2)), SLOT_9(9, getInventorySlot(1, 2)), SLOT_10(
                10, getInventorySlot(2, 2)), SLOT_11(11, getInventorySlot(3, 2)), SLOT_12(
                12, getInventorySlot(0, 3)), SLOT_13(13, getInventorySlot(1, 3)), SLOT_14(
                14, getInventorySlot(2, 3)), SLOT_15(15, getInventorySlot(3, 3)), SLOT_16(
                16, getInventorySlot(0, 4)), SLOT_17(17, getInventorySlot(1, 4)), SLOT_18(
                18, getInventorySlot(2, 4)), SLOT_19(19, getInventorySlot(3, 4)), SLOT_20(
                20, getInventorySlot(0, 5)), SLOT_21(21, getInventorySlot(1, 5)), SLOT_22(
                22, getInventorySlot(2, 5)), SLOT_23(23, getInventorySlot(3, 5)), SLOT_24(
                24, getInventorySlot(0, 6)), SLOT_25(25, getInventorySlot(1, 6)), SLOT_26(
                26, getInventorySlot(2, 6)), SLOT_27(27, getInventorySlot(3, 6));

        private final int index;
        private final Rectangle bounds;

        public int getIndex() {
            return index;
        }

        private Slot(int index, Rectangle bounds) {
            this.index = index;
            this.bounds = bounds;
        }

        /*
           * public static int [] getSlotsWithColor(Color color) { ArrayList<Slot>
           * list = new ArrayList<Slot>(); for (final Slot slot : Slot.values()) {
           * for (final Color slot_color : slot.getColors()) { if
           * (slot_color.equals(color)) { list.add(slot); } } } Slot s[] = new
           * Slot[list.size()]; for (int i = 0; i < s.length; i++) { for(int j =
           * 0; j < 18; i++){ if(list.get(i).getIndex() == j){ list.removeAll(i);
           * } } p[i] = list.get(i); } return p; }
           */

        public Rectangle getBounds() {
            return bounds;
        }

        /**
         *
         * @return Returns the center Point of the slot
         */
        public Point getCenter() {
            if (open()) {
                return new Point(bounds.x + (int) (bounds.width / 2), bounds.y
                        + (bounds.height / 2));
            }
            return null;
        }

        /**
         *
         * @return Returns the center color of the slot
         */
        public Color getCenterColor() {
            if (open()) {
                final Point center = getCenter();
                return Game.isPointValid(center) ? Game.getColorAt(center) : null;
            }
            return null;
        }

        // BROKEN!
        Point[][] getOutline() {
            Point[][] result = new Point[bounds.width][bounds.height];
            for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                    final Point p = new Point(x, y);
                    if (Game.isPointValid(p)) {
                        final Color color = Game.getColorAt(p);
                        if (color.equals(new Color(0, 0, 2))) {
                            result[x][y] = new Point(x, y);
                        }
                    }
                }
            }
            return result;
        }

        public Color[] getColors() {
            final List<Color> colors = new LinkedList<Color>();
            if (open()) {

                for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                    for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                        final Point p = new Point(x, y);
                        if (Game.isPointValid(p)) {
                            final Color color = Game.getColorAt(p);
                            colors.add(color);
                        }
                    }
                }
            }
            return colors.toArray(new Color[colors.size()]);
        }

        /**
         *
         * @return the model of the inventory item
         */
        public Point[] getModel() {
            if (Inventory.open()) {
                ArrayList<Point> list = new ArrayList<Point>();
                Rectangle slotRect = this.bounds;
                for (int i = 0; i < slotRect.getWidth(); i++) {
                    for (int j = 0; j < slotRect.getHeight(); j++) {
                        if(!isEmpty(new Point(slotRect.x + i, slotRect.y + j))) {
                            list.add(new Point(slotRect.x + i, slotRect.y + j));
                        }
                    }
                }
                Point p[] = new Point[list.size()];
                for (int i = 0; i < p.length; i++) {
                    p[i] = list.get(i);
                }
                return p;
            }
            return null;
        }

        /*
         * People don't need this
         */
        private boolean isEmpty(Point p) {
            if (Inventory.open()) {
                final Color pointColor = ColorUtil.getColor(p);

                final int min_red = 63;
                final int max_red = 74;
                final int min_green = 53;
                final int max_green = 68;
                final int min_blue = 44;
                final int max_blue = 58;
                if (pointColor != null) {
                    final int center_red = pointColor.getRed();
                    final int center_green = pointColor.getGreen();
                    final int center_blue = pointColor.getBlue();
                    if (center_red >= min_red && center_red <= max_red) {
                        if (center_green >= min_green && center_green <= max_green) {
                            if (center_blue >= min_blue && center_blue <= max_blue) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        /**
         *
         * @return random point in the said model
         */
        public Point getPointInModel() {
            Point[] b = getModel();
            return b[MethodProvider.random(0, b.length)];
        }


        /**
         *
         * @return Returns <tt>true</tt> if the inventory is empty, otherwise
         *         <tt>false</tt>
         */
        public boolean isEmpty() {
            if (open()) {
                final Color center_color = getCenterColor();

                final int min_red = 63;
                final int max_red = 74;
                final int min_green = 53;
                final int max_green = 68;
                final int min_blue = 44;
                final int max_blue = 58;
                if (center_color != null) {
                    final int center_red = center_color.getRed();
                    final int center_green = center_color.getGreen();
                    final int center_blue = center_color.getBlue();
                    if (center_red >= min_red && center_red <= max_red) {
                        if (center_green >= min_green && center_green <= max_green) {
                            if (center_blue >= min_blue && center_blue <= max_blue) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        public void click() {
            if (open()) {
                Mouse.click(getCenter());
            }
        }

        public void click(final boolean left) {
            if (open()) {
                Mouse.click(getCenter(), left);
            }
        }

        // Implements randomization to replace click method in the future. -Soviet
        public void clickModel(){
            if (open()){
                Mouse.click(getPointInModel());
            }
        }

        public void clickModel(final boolean left){
            if (open()){
                Mouse.click(getPointInModel(), left);
            }
        }

    }

    /**
     * @param index
     *            The inventory index of the Slot you want [0-27]
     * @return The slot at the specified index
     */
    public static Slot getSlot(int index) {
        for (final Slot slot : Slot.values()) {
            if (slot.getIndex() == index) {
                return slot;
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

    /**
     * This allows more complex algorithms to be developed in search and
     * exclusion methods
     *
     * @return All of the slot bounds
     */
    public static Rectangle[] getAllSlotBounds() {
        Rectangle[] a = new Rectangle[28];
        int i = 0;
        for (int column = 0; column < 4; column++) {
            for (int row = 0; row < 7; row++) {
                a[i] = getInventorySlot(column, row);
                i++;
            }
        }
        return a;
    }

    /**
     *
     * @param slotNumber
     *            Drops all except the given slot number. 0-27
     */
    public static void dropAllExcept(int slotNumber) {
        if (open()) {
            for (int i = 0; i < 28; i++) {
                if (i != slotNumber && !Inventory.getSlotAt(i).isEmpty()) {
                    Inventory.doAction(i, i <= 23 ? 2 : 1);
                }
            }
        }
    }

    /**
     *
     * @param slotNumber
     *            Drops all except the given slot numbers. 0-27
     */
    public static void dropAllExcept(int... slotNumber) {
        if (open()) {
            for (int i = 0; i < 28; i++) {
                for (int a : slotNumber) {
                    if (i != a && !Inventory.getSlotAt(i).isEmpty()) {
                        Inventory.doAction(i, i <= 23 ? 2 : 1);
                    }
                }
            }
        }
    }

    /**
     * Drops all inventory items.
     */
    public static void dropAll() {
        if (open()) {
            for (int i = 0; i < 28; i++) {
                if (!Inventory.getSlotAt(i).isEmpty()) {
                    Inventory.doAction(i, i <= 23 ? 2 : 1);
                }
            }
        }
    }

    /**
     *
     * @param centerColor
     *            Drops all except the given center color.
     * @param tolerance
     *            amount to choose between colors to drop
     */
    public static void dropAllExcept(Color centerColor, int tolerance) {
        if (open()) {
            for (int i = 0; i < 28; i++) {
                if (!api.methods.Inventory.getSlotAt(i).isEmpty()
                        && ColorUtil.areColorsWithinTolerance(
                        api.methods.Inventory.getSlotAt(i)
                                .getCenterColor(), centerColor,
                        tolerance)) {
                    api.methods.Inventory.doAction(i, i <= 23 ? 2 : 1);
                }
            }
        }
    }

    /**
     *
     * @param tolerance
     *            amount to choose between colors to drop
     * @param colors
     *            Drops all except the given center colors.
     */
    public static void dropAllExcept(int tolerance, Color... colors) {
        if (open()) {
            for (int i = 0; i < 28; i++) {
                for (Color a : colors) {
                    if (!api.methods.Inventory.getSlotAt(i).isEmpty()
                            && ColorUtil.areColorsWithinTolerance(
                            api.methods.Inventory.getSlotAt(i)
                                    .getCenterColor(), a, tolerance)) {
                        api.methods.Inventory.doAction(i, i <= 23 ? 2 : 1);
                    }
                }
            }
        }
    }

    /**
     * @param color
     *            The color of the item you would like to find the slot of
     *
     * @return Returns the first slot that contains the color specified
     */
    public static Slot getSlotWithColor(Color color) {
        if (open()) {
            for (final Slot slot : Slot.values()) {
                for (final Color slot_color : slot.getColors()) {
                    if (slot_color.equals(color)) {
                        return slot;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param color
     *            The center color of the item you would like to find the slot
     *            of
     * @return Returns the first slot that contains the desired color; if any.
     */
    public static Slot getSlotWithCenterColor(Color color) {
        if (open()) {
            for (Inventory.Slot a : Inventory.Slot.values()) {
                if (a.getCenterColor().equals(color)) {
                    return a;
                }
            }
        }
        return null;
    }

    /**
     * @param color
     *            The color of the item you would like to find the slot of
     * @param tolerance
     *            The tolerance threshold for for colors within the color
     *            specified
     *
     * @return Returns the first slot that contains the color within tolerance
     *         specified
     */
    public static Slot getSlotWithinColorTolerance(Color color, Color tolerance) {
        if (open()) {
            for (final Slot slot : Slot.values()) {
                for (final Color slot_color : slot.getColors()) {
                    if (ColorUtil.areColorsWithinTolerance(slot_color, color,
                            tolerance)) {
                        return slot;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @return Returns <tt>true</tt> if the tab is open, otherwise returns
     *         <tt>false</tt>
     */
    public static boolean isOpen() {
        return Tabs.getCurrentTab().equals("Inventory");
    }

    /**
     *
     * @return Returns <tt>true</tt> if the tab is open, if it is not open, it
     *         will attempt to open the tab
     */
    public static boolean open() {
        return Tabs.setTab("Inventory");
    }

    /**
     *
     * @return Returns the amount items currently in the inventory
     */
    public static int getCount() {
        int count = 0;
        if (open()) {
            for (final Slot slot : Slot.values()) {
                if (!slot.isEmpty()) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * @param colors
     *
     * @return Returns the amount items currently in the inventory that match
     *         the colors passed
     */
    public static int getCount(final Color... colors) {
        int count = 0;
        if (open()) {

            for (final Slot slot : Slot.values()) {
                if (!slot.isEmpty()) {
                    for (final Color slot_color : slot.getColors()) {
                        for (final Color c : colors) {
                            if (slot_color.equals(c)) {
                                count++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     *
     * @return Returns <tt>true</tt> if the inventory is empty, otherwise
     *         returns <tt>false</tt>
     */
    public static boolean isEmpty() {
        return getCount() == 0;
    }

    /**
     * @return Returns <tt>true</tt> if the inventory is full, otherwise returns
     *         <tt>false</tt>
     */
    public static boolean isFull() {
        return getCount() == 28;
    }

    /**
     * @param index
     *            Index of Inventory slot, 0-27
     *
     * @return Returns the slot at the given index in the inventory
     */
    public static Slot getSlotAt(final int index) {
        for (final Slot slot : Slot.values()) {
            if (slot.getIndex() == index) {
                return slot;
            }
        }
        return null;
    }

    /**
     * @param slot
     *            The slot index that you wish to act upon
     * @param action
     *            The action you would like to do, integer that represents menu
     *            items once the item is right clicked
     */
    public static void doAction(int slot, int action) {
        doAction(getSlotAt(slot), action);
    }

    /**
     * @param theSlot
     *            The slot that you wish to act upon
     * @param action
     *            The action you would like to do, integer that represents menu
     *            items once the item is right clicked
     */
    public static void doAction(Slot theSlot, int action) {
        if (open()) {
            Mouse.clickMouse(theSlot.getCenter().x, theSlot.getCenter().y, 2, 2,
                    false);
            MethodProvider.sleep(200);
            Mouse.clickMouse(Mouse.getLocation().x, Mouse.getLocation().y
                    + (20 * action), 2, 2, true);
        }
    }

    /**
     *
     * @param columnNumber
     *            Starts at 0 for columns
     * @return Correct X Value for the inventory column
     */
    private static int getXValue(int columnNumber) {
        return 561 + columnNumber * 42;
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
    private static Rectangle getInventorySlot(int columnNumber, int rowNumber) {
        return new Rectangle(getXValue(columnNumber), getYValue(rowNumber),
                WIDTH, HEIGHT);
    }

}