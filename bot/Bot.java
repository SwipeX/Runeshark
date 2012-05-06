package bot;

import java.awt.image.BufferedImage;

import loader.RSLoader;

public class Bot {

	public static BufferedImage gameScreen = null;
	public java.awt.Canvas gameCanvas = null;
	static Bot b;

	public Bot() {
		b = this;
		gameScreen = new BufferedImage(756, 503, 1);
	}

	/**
	 * Sets the local Canvas to the Current Canvas.
	 */
	public void setCanvas() {
		gameCanvas = RSLoader.getCurrent().getCanvas();
	}

	/**
	 * Gets the current Canvas
	 */
	public java.awt.Canvas getCanvas() {
		return RSLoader.getCurrent().getCanvas();
	}

	/**
	 * Get the game screen as a bufferedimage
	 */
	public BufferedImage getScreen() {
		return getCanvas().getGameScreen();
	}

	/**
	 * Sleeps for x amount of milliseconds
	 * 
	 * @param z
	 *            is the amount of ms
	 */
	public static void sleep(int z) {
		try {
			Thread.sleep(z);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns static context
	 */
	public static Bot getCurrent() {
		return b;
	}

}
