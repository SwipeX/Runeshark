package util.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import util.Configuration;
import bot.Bot;

public class ScreenshotUtil {

	private static final Logger log = Logger.getLogger(ScreenshotUtil.class
			.getName());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMdd-hhmmss");

	public static void saveScreenshot(final boolean hideUsername) {
		final String name = ScreenshotUtil.dateFormat.format(new Date())
				+ ".png";
		final File dir = new File(Configuration.Paths.getScreenshotsDirectory());
		if (dir.isDirectory() || dir.mkdirs()) {
			ScreenshotUtil.saveAScreenshot(Bot.getCurrent().getScreen(),
					new File(dir, name), "png", hideUsername);
		}
	}

	public static void saveScreenshot(final boolean hideUsername,
			String filename) {
		if (!filename.endsWith(".png")) {
			filename = filename.concat(".png");
		}

		final File dir = new File(Configuration.Paths.getScreenshotsDirectory());
		if (dir.isDirectory() || dir.mkdirs()) {
			ScreenshotUtil.saveAScreenshot(Bot.getCurrent().getScreen(),
					new File(dir, filename), "png", hideUsername);
		}
	}

	private static void saveAScreenshot(final BufferedImage bot,
			final File file, final String type, final boolean hideUsername) {
		try {
			final BufferedImage image = bot;
			if (hideUsername) {
				final Graphics graphics = image.createGraphics();
				graphics.setColor(Color.black);
				graphics.fillRect(7, 459, 100, 15);
				graphics.dispose();
			}
			ImageIO.write(image, type, file);
			ScreenshotUtil.log.info("Screenshot saved to: " + file.getPath());
		} catch (final Exception e) {
			ScreenshotUtil.log.log(Level.SEVERE, "Could not take screenshot.",
					e);
		}
	}

}
