package api.methods;

import java.awt.event.KeyEvent;

import api.wrapper.*;

/**
 * Game camera control methods.
 * 
 * @Author Ramy
 */
public class Camera extends MethodProvider {
	public static void setNorth() {
		Mouse.clickMouse(545, 23, 14, 7, true);
	}

	/**
	 * Rotates the camera right for a given time.
	 * 
	 * @param millis
	 *            The time in milliseconds to rotate the camera.
	 */
	public static void rotateRight(final int millis) {
		rotate(KeyEvent.VK_RIGHT, millis);
	}

	public static double getRotation() {
		return Location.getCompassAngle();
	}

	public static void rotateToAngle(double degrees) {
		double curDeg = getRotation();
        if (curDeg == -1) {
            return;
        }
        boolean left = (360 - curDeg) > 180;
			while (getRotation() < degrees) {
				if (left) {
					rotateLeft(200);
				} else {
					rotateRight(200);
				}
			}
	}

	/**
	 * Rotates the camera left for a given time.
	 * 
	 * @param millis
	 *            The time in milliseconds to rotate the camera.
	 */
	public static void rotateLeft(final int millis) {
		rotate(KeyEvent.VK_LEFT, millis);
	}

	/**
	 * Moves the camera up for a given time.
	 * 
	 * @param millis
	 *            The time in milliseconds to move the camera.
	 */
	public static void moveUp(final int millis) {
		rotate(KeyEvent.VK_UP, millis);
	}

	/**
	 * Set the camera to a certain percentage of the maximum rotaion. Don't rely
	 * on the return value too much - it should return whether the camera was
	 * successfully set, but it isn't very accurate near the very extremes of
	 * the height.
	 * <p/>
	 * <p/>
	 * This also depends on the maximum camera angle in a region, as it changes
	 * depending on situation and surroundings. So in some areas, 68% might be
	 * the maximum altitude. This method will do the best it can to switch the
	 * camera altitude to what you want, but if it hits the maximum or stops
	 * moving for any reason, it will return.
	 * <p/>
	 * <p/>
	 * <p/>
	 * Mess around a little to find the altitude percentage you like. In later
	 * versions, there will be easier-to-work-with methods regarding altitude.
	 * 
	 * @param rotaion
	 *            The rotaion of the camera to set.
	 * @return true if the camera was successfully moved; otherwise false.
	 */
	public boolean setRotaion(final int rotaion) {
		int curAlt = (int) getRotation();
		int lastAlt = 0;
		if (curAlt == rotaion) {
			return true;
		} else if (curAlt < rotaion) {
			KeyBoard.pressKey((char) KeyEvent.VK_UP);
			long start = System.currentTimeMillis();
			while (curAlt < rotaion
					&& System.currentTimeMillis() - start < random(50, 100)) {
				if (lastAlt != curAlt) {
					start = System.currentTimeMillis();
				}
				lastAlt = curAlt;
				sleep(random(5, 10));
				curAlt = (int) getRotation();
			}
			KeyBoard.releaseKey((char) KeyEvent.VK_UP);
			return true;
		} else {
			KeyBoard.pressKey((char) KeyEvent.VK_DOWN);
			long start = System.currentTimeMillis();
			while (curAlt > rotaion
					&& System.currentTimeMillis() - start < random(50, 100)) {
				if (lastAlt != curAlt) {
					start = System.currentTimeMillis();
				}
				lastAlt = curAlt;
				sleep(random(5, 10));
				curAlt = (int) getRotation();
			}
			KeyBoard.releaseKey((char) KeyEvent.VK_DOWN);
			return true;
		}
	}

	/**
	 * Moves the camera in a random direction for a given time.
	 * 
	 * @param timeOut
	 *            The maximum time in milliseconds to move the camera for.
	 */
	public void moveRandomly(final int timeOut) {
		final Timer timeToHold = new Timer(timeOut);
		final int highest = random(75, 100);
		final int lowest = random(0, 25);
		final int vertical = Math.random() < Math.random() ? KeyEvent.VK_UP
				: KeyEvent.VK_DOWN;
		final int horizontal = Math.random() < Math.random() ? KeyEvent.VK_LEFT
				: KeyEvent.VK_RIGHT;
		boolean verticalKeyDown = false;
		boolean horizontalKeyDown = false;
		if (random(0, 10) < 8) {
			KeyBoard.pressKey((char) vertical);
			verticalKeyDown = true;
		}
		if (random(0, 10) < 8) {
			KeyBoard.pressKey((char) horizontal);
			horizontalKeyDown = true;
		}
		while (timeToHold.isRunning() && (verticalKeyDown || horizontalKeyDown)) {
			if (getRotation() >= highest && vertical == KeyEvent.VK_UP
					|| getRotation() <= lowest && vertical == KeyEvent.VK_DOWN) {
				KeyBoard.releaseKey((char) vertical);
				verticalKeyDown = false;
			}
			sleep(10);
		}
		if (verticalKeyDown)
			KeyBoard.releaseKey((char) vertical);
		if (horizontalKeyDown)
			KeyBoard.releaseKey((char) horizontal);
	}

	/**
	 * Moves the camera down for a given time.
	 * 
	 * @param millis
	 *            The time in milliseconds to move the camera.
	 */
	public static void moveDown(final int millis) {
		rotate(KeyEvent.VK_DOWN, millis);
	}

	private static void rotate(final int key, final int millis) {
		KeyBoard.pressKey(key);
		sleep(millis);
		KeyBoard.releaseKey(key);
	}

}