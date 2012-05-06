package api.methods;

/**
 * @Author Soviet
 */

// Todo
//Errthang? - Soviet

import java.awt.*;
import java.util.*;
import java.util.List;

public class Magic {

    public static final Rectangle BOUNDS = new Rectangle(560, 206, 145, 207);
    private static final int WIDTH = 24;
    private static final int HEIGHT = 23;

    public static enum Slot {
       SLOT_0(0, getMagicSlot(0, 0)), SLOT_1(1, getMagicSlot(1, 0)), SLOT_2(
                2, getMagicSlot(2, 0)), SLOT_3(3, getMagicSlot(3, 0)), SLOT_4(
                4, getMagicSlot(4, 0)), SLOT_5(5, getMagicSlot(5, 0)), SLOT_6(
                6, getMagicSlot(0, 1)), SLOT_7(7, getMagicSlot(1, 1)), SLOT_8(
                8, getMagicSlot(2, 1)), SLOT_9(9, getMagicSlot(3, 1)), SLOT_10(
                10, getMagicSlot(4, 1)), SLOT_11(11, getMagicSlot(5, 1)), SLOT_12(
                12, getMagicSlot(0, 2)), SLOT_13(13, getMagicSlot(1, 2)), SLOT_14(
                14, getMagicSlot(2, 2)), SLOT_15(15, getMagicSlot(3, 2)), SLOT_16(
                16, getMagicSlot(4, 2)), SLOT_17(17, getMagicSlot(5, 2)), SLOT_18(
                18, getMagicSlot(0, 3)), SLOT_19(19, getMagicSlot(1, 3)), SLOT_20(
                20, getMagicSlot(2, 3)), SLOT_21(21, getMagicSlot(3, 3)), SLOT_22(
                22, getMagicSlot(4, 3)), SLOT_23(23, getMagicSlot(5, 3)), SLOT_24(
                24, getMagicSlot(0, 4)), SLOT_25(25, getMagicSlot(1, 4)), SLOT_26(
                26, getMagicSlot(2, 4)), SLOT_27(27, getMagicSlot(3, 4)), SLOT_28(
                28, getMagicSlot(4, 4)), SLOT_29(29, getMagicSlot(5, 4)), SLOT_30(
                30, getMagicSlot(0, 5)), SLOT_31(31, getMagicSlot(1, 5)), SLOT_32(
                32, getMagicSlot(2, 5)), SLOT_33(33, getMagicSlot(3, 5)), SLOT_34(
                34, getMagicSlot(4, 5)), SLOT_35(35, getMagicSlot(5, 5)), SLOT_36(
                36, getMagicSlot(0, 6)), SLOT_37(37, getMagicSlot(1, 6)), SLOT_38(
                38, getMagicSlot(2, 6)), SLOT_39(39, getMagicSlot(3, 6)), SLOT_40(
                40, getMagicSlot(4, 6)), SLOT_41(41, getMagicSlot(5, 6)), SLOT_42(
                42, getMagicSlot(0, 7)), SLOT_43(43, getMagicSlot(1, 7)), SLOT_44(
                44, getMagicSlot(2, 7)), SLOT_45(45, getMagicSlot(3, 7)), SLOT_46(
                46, getMagicSlot(4, 7)), SLOT_47(47, getMagicSlot(5, 7)), SLOT_48(
                48, getMagicSlot(0, 8)), SLOT_49(49, getMagicSlot(1, 8)), SLOT_50(
                50, getMagicSlot(2, 8)), SLOT_51(51, getMagicSlot(3, 8)), SLOT_52(
                52, getMagicSlot(4, 8)), SLOT_53(53, getMagicSlot(5, 8)), SLOT_54(
                54, getMagicSlot(0, 9)), SLOT_55(55, getMagicSlot(1, 9)), SLOT_56(
                56, getMagicSlot(2, 9)), SLOT_57(57, getMagicSlot(3, 9)), SLOT_58(
                58, getMagicSlot(4, 9)), SLOT_59(59, getMagicSlot(5, 9));

        private final int index;
        private final Rectangle bounds;

        public int getIndex() {
            return index;
        }

        private Slot(int index, Rectangle bounds) {
            this.index = index;
            this.bounds = bounds;
        }


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

    /*  Needs to be modified for Magic class
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

    /**
     *
     * @return random point in the said model
     */
//    public Point getPointInModel() {
//        Point[] b = getModel();
//        return b[MethodProvider.random(0, b.length)];
//    }

        /**
         *
         * @return the model of the inventory item
         */
//        public Point[] getModel() {
//            if (Inventory.open()) {
//                ArrayList<Point> list = new ArrayList<Point>();
//                Rectangle slotRect = this.bounds;
//                for (int i = 0; i < slotRect.getWidth(); i++) {
//                    for (int j = 0; j < slotRect.getHeight(); j++) {
//                        if(!isReady(new Point(slotRect.x + i, slotRect.y + j))) {
//                            list.add(new Point(slotRect.x + i, slotRect.y + j));
//                        }
//                    }
//                }
//                Point p[] = new Point[list.size()];
//                for (int i = 0; i < p.length; i++) {
//                    p[i] = list.get(i);
//                }
//                return p;
//            }
//            return null;
//        }

        /**
         *
         * @return Returns <tt>true</tt> if the inventory is empty, otherwise
         *         <tt>false</tt>
         */
        public boolean isReady() {
            if (open()) {
                final Color center_color = getCenterColor();

                final int min_red = 0;
                final int max_red = 102;
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
//    public void clickModel(){
//        if (open()){
//            Mouse.click(getPointInModel());
//        }
//    }
//
//    public void clickModel(final boolean left){
//        if (open()){
//            Mouse.click(getPointInModel(), left);
//        }
//    }

}

   /**
    *
   * @return Returns <tt>true</tt> if the tab is open, otherwise returns
     */
   public static boolean isOpen() {
       return Tabs.getCurrentTab().equals("Magic");
   }

    /**
     *
     * @return Returns <tt>true</tt> if the tab is open, if it is not open, it
     *         will attempt to open the tab
     */
    public static boolean open() {
        return Tabs.setTab("Magic");
    }

    /**
     *
     * @param columnNumber
     *            Starts at 0 for columns
     * @return Correct X Value for the inventory column
     */
    private static int getXValue(int columnNumber) {
        return 560 + columnNumber * WIDTH;
    }

    /**
     *
     * @param rowNumber
     *            Starts at 0 for Rows
     * @return correct Y value for inventory row
     */
    private static int getYValue(int rowNumber) {
        return 206 + rowNumber * HEIGHT;
    }

    /**
     *
     * @param columnNumber
     *            Starts at 0 for column Number. 0-6
     * @param rowNumber
     *            Starts at 0 for row Number. 0-9
     * @return Makes a rectangle from desired row and column number
     */
    private static Rectangle getMagicSlot(int columnNumber, int rowNumber) {
        return new Rectangle(getXValue(columnNumber), getYValue(rowNumber),
                WIDTH, HEIGHT);
    }
}
