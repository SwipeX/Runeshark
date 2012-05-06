package api.methods;

import input.MouseHandle;

import java.awt.Point;
import java.awt.event.MouseEvent;

import loader.RSLoader;

/**
 * @Author Ramy
 */
public class Mouse extends MethodProvider {

    private static boolean Pressed = false;
    private static int Speed = MouseHandle.DEFAULT_MOUSE_SPEED;

    /**
     * Author - Enfilade Moves the mouse a random distance between 1 and
     * maxDistance from the current position of the mouse by generating a random
     * vector and then multiplying it by a random number between 1 and
     * maxDistance. The maximum distance is cut short if the mouse would go off
     * screen in the direction it chose.
     *
     * @param maxDistance The maximum distance the cursor will move (exclusive)
     */
    public static void moveRandomly(final int maxDistance) {
        moveRandomly(1, maxDistance);
    }

    /**
     * Author - Enfilade Moves the mouse a random distance between minDistance
     * and maxDistance from the current position of the mouse by generating
     * random vector and then multiplying it by a random number between
     * minDistance and maxDistance. The maximum distance is cut short if the
     * mouse would go off screen in the direction it chose.
     *
     * @param minDistance The minimum distance the cursor will move
     * @param maxDistance The maximum distance the cursor will move (exclusive)
     */
    public static void moveRandomly(final int minDistance, final int maxDistance) {
        /* Generate a random vector for the direction the mouse will move in */
        double xvec = Math.random();
        if (random(0, 2) == 1) {
            xvec = -xvec;
        }
        double yvec = Math.sqrt(1 - xvec * xvec);
        if (random(0, 2) == 1) {
            yvec = -yvec;
        }
        /* Start the maximum distance at maxDistance */
        double distance = maxDistance;
        /* Get the current location of the cursor */
        final Point p = getLocation();
        /* Calculate the x coordinate if the mouse moved the maximum distance */
        final int maxX = (int) Math.round(xvec * distance + p.x);
        /*
           * If the maximum x is offscreen, subtract that distance/xvec from the
           * maximum distance so the maximum distance will give a valid X
           * coordinate
           */
        distance -= Math.abs((maxX - Math.max(0, Math.min(Game.getWidth(), maxX))) / xvec);
        /* Do the same thing with the Y coordinate */
        final int maxY = (int) Math.round(yvec * distance + p.y);
        distance -= Math.abs((maxY - Math.max(0, Math.min(Game.getHeight(), maxY))) / yvec);
        /*
           * If the maximum distance in the generated direction is too small,
           * don't move the mouse at all
           */
        if (distance < minDistance) {
            return;
        }
        /*
           * With the calculated maximum distance, pick a random distance to move
           * the mouse between maxDistance and the calculated maximum distance
           */
        distance = random(minDistance, (int) distance);
        /* Generate the point to move the mouse to and move it there */
        move((int) (xvec * distance) + p.x, (int) (yvec * distance) + p.y);
    }

    /**
     * Moves the mouse off the screen in a random direction.
     */
    public static void moveOffScreen() {
        if (isPresent()) {
            switch (random(0, 4)) {
                case 0: // up
                    move(random(-10, Game.getWidth() + 10), random(-100, -10));
                    break;
                case 1: // down
                    move(random(-10, Game.getWidth() + 10), Game.getHeight() + random(10, 100));
                    break;
                case 2: // left
                    move(random(-100, -10), random(-10, Game.getHeight() + 10));
                    break;
                case 3: // right
                    move(random(10, 100) + Game.getWidth(), random(-10, Game.getHeight() + 10));
                    break;
            }
        }
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param x The x coordinate to drag to.
     * @param y The y coordinate to drag to.
     */
    public static void drag(final int x, final int y, int randX, int randY) {
        drag(random(x - randX, x + randX), random(y - randY, y + randY));
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param p The point to drag to.
     * @see #drag(int, int)
     */
    public static void drag(final Point p) {
        drag(p.x, p.y);
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param p The point to drag to.
     * @see #drag(int, int)
     */
    public static void drag(final Point p, int randX, int randY) {
        drag(random(p.x - randX, p.x + randX), random(p.y - randY, p.y + randY));
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param x the x coordinate to drag to
     * @param y the y coordinate to drag to
     */
    public static void drag(final int x, final int y) {
        input.MouseHandle.dragMouse(x,y);
    }

    public static void move(final int x, final int y){
        move(new Point(x,y));
    }

    public static void move(Point p){
        move(Speed,p);
    }

    public static void move(Point p, final int randX, final int randY){
        move(Speed,new Point(random(p.x - randX, p.x + randX), random(p.y - randY, p.y + randY)));
    }

    public static void move(final int speed, final Point p) {
        move(speed, p.x, p.y, 0, 0, 0);
    }

    /**
     * Moves the mouse slightly depending on where it currently is.
     */
    public static void moveSlightly() {
        final Point p = new Point(
                (int) (getLocation().getX() + (Math.random() * 50 > 25 ? 1 : -1) * (30 + Math.random() * 90)),
                (int) (getLocation().getY() + (Math.random() * 50 > 25 ? 1 : -1) * (30 + Math.random() * 90)));
        if (p.getX() < 1 || p.getY() < 1 || p.getX() > 761 || p.getY() > 499) {
            moveSlightly();
            return;
        }
        move(p);
    }

    /**
     * @param maxDistance The maximum distance outwards.
     * @return A random x value between the current client location and the max
     *         distance outwards.
     */
    public static int getRandomX(final int maxDistance) {
        final Point p = getLocation();
        if (p.x < 0 || maxDistance <= 0) {
            return -1;
        }
        if (random(0, 2) == 0) {
            return p.x - random(0, p.x < maxDistance ? p.x : maxDistance);
        } else {
            final int dist = Game.getWidth() - p.x;
            return p.x + random(1, dist < maxDistance && dist > 0 ? dist : maxDistance);
        }
    }

    /**
     * @param maxDistance The maximum distance outwards.
     * @return A random y value between the current client location and the max
     *         distance outwards.
     */
    public static int getRandomY(final int maxDistance) {
        final Point p = getLocation();
        if (p.y < 0 || maxDistance <= 0) {
            return -1;
        }
        if (random(0, 2) == 0) {
            return p.y - random(0, p.y < maxDistance ? p.y : maxDistance);
        } else {
            final int dist = Game.getHeight() - p.y;
            return p.y + random(1, dist < maxDistance && dist > 0 ? dist : maxDistance);
        }
    }

    /**
     * Moves the mouse to a given location with given randomness.
     *
     * @param p the Point
     * @param randX The maximum randomness in the x coordinate.
     * @param randY The maximum randomness in the y coordinate.
     */
    public static void click(final Point p, final int randX, final int randY) {
        final Point a = new Point(random(p.x - randX, p.x + randX), random(p.y - randY, p.y + randY));
        if (Game.isPointValid(a)) {
            while (!getLocation().equals(a)) {
                move(Speed, a);
            }
            click();
        }
    }

    public synchronized static void move(final int speed, final int x,
                                         final int y, final int randX, final int randY, final int afterOffset) {
        if (x != -1 || y != -1) {
            MouseHandle.moveMouse(speed, x, y, randX, randY);
            if (afterOffset > 0) {
                sleep(random(60, 300));
                final Point pos = getLocation();
                move(pos.x - afterOffset, pos.y - afterOffset, afterOffset * 2,
                        afterOffset * 2);
            }
        }
    }

    /**
     * Moves the mouse to the specified point at default speed.
     *
     * @param x
     *            The x destination.
     * @param y
     *            The y destination.
     * @param randX
     *            x-axis randomness (added to x).
     * @param randY
     *            y-axis randomness (added to y).
     * @see #move(int, int, int, int, int, int)
     * @see #setSpeed(int)
     */
    public static void move(final int x, final int y, final int randX,
                            final int randY) {
        move(getSpeed(), x, y, randX, randY, 0);
    }


    public static void Press(final Point p, final boolean left){
        Press(p.x,p.y,left);
    }
    public static void Press(final int x, final int y, final boolean left) {
        if (!isPressed()) {
            Pressed = true;
            final MouseEvent me = new MouseEvent(RSLoader.getCurrent()
                    .getCanvas(), MouseEvent.MOUSE_PRESSED,
                    System.currentTimeMillis(), 0, x, y, 1, false,
                    left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
            RSLoader.getCurrent().getCanvas().dispatchEvent(me);
        }
    }

    /**
     * Click at a Point
     *
     * @param p
     *            the point to click
     */
    public static void click(Point p) {
        click(p.x, p.y, true);
    }

    /**
     * Moves the mouse slightly depending on where it currently is and clicks.
     */
    public static void clickSlightly() {
        final Point p = new Point((int) (getLocation().getX() + (Math.random() * 50 > 25 ? 1 : -1) * (30 + Math.random() * 90)),
                (int) (getLocation().getY() + (Math.random() * 50 > 25 ? 1 : -1) * (30 + Math.random() * 90)));
        if (p.getX() < 1 || p.getY() < 1 || p.getX() > 761 || p.getY() > 499) {
            clickSlightly();
            return;
        }
        click(p, true);
    }

    public static void click(int x, int y) {
        click(new Point(x, y));
    }

    public static void click(Point p, boolean left) {
        Mouse.clickMouse(p.x, p.y, left);
    }

    public static void click(int x, int y, boolean left) {
        Mouse.clickMouse(x, y, left);
    }

    public static boolean isPressed() {
        return Pressed;
    }

    public static void click() {
        click(getLocation());
    }

    /**
     * Set the mouse speed
     *
     * @param speed
     *            The speed to move the mouse at. 4-10 is advised, 1 being the
     *            fastest.
     */
    public static void setSpeed(int speed) {
        Speed = speed;
    }

    /**
     * Gets the current mouse speed
     *
     * @return current mouse speed
     */
    public static int getSpeed() {
        return Speed;
    }

    public static void clickMouse(int x, int y, boolean left) {
        input.Mouse.click(x, y, left);
    }

    public static void clickMouse(int x, int y, int randx, int randy,
                                  boolean left) {
        input.Mouse.click(
                x + MethodProvider.random(randx * -1, randx),
                y + MethodProvider.random(randy * -1, randy), left);
    }

    public static Point getLocation() {
        return input.Mouse.getLocation();
    }

    /**
     * @return The <tt>Point</tt> at which the bot's mouse was last clicked.
     */
    public static Point getPressLocation() {
        return new Point(input.Mouse.LastClickX, input.Mouse.LastClickY);
    }

    /**
     * @return The system time when the bot's mouse was last pressed.
     */
    public static long getPressTime() {
        return input.Mouse.LastClickTime;
    }

    public static boolean isPresent(){
        return input.Mouse.isPresent();
    }
}
