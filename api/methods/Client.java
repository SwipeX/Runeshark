package api.methods;

import java.applet.Applet;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import loader.RSLoader;

import bot.Bot;
import bot.InputManager;

public class Client {

	/**
	 * Allows input to the bot
	 * 
	 * @param on
	 *            - input on/off
	 */
	public static void setInput(boolean on) {
		InputManager.getCurrent().setInput(on);
	}

	/**
	 * Gets Screen as a BufferedImage
     * @return returns the current game screen
     */
	public static BufferedImage getScreen() {
		return Bot.getCurrent().getScreen();
	}

	/**
	 * @param p point that is in question
     * @return true if point is on screen
	 */
	public static boolean isPointValid(Point p) {
		return new Rectangle(0, 0, 756, 503).contains(p);
	}

	/**
	 * @return Game applet
	 */
	public static Applet getApplet() {
		return RSLoader.getCurrent().loader;
	}
}
