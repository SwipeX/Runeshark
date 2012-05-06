package api.methods;

import java.util.Random;

import ui.BotFrame;

public class MethodProvider {

	public static final Random random = new Random();

	/**
	 * Returns a linearly distributed pseudorandom integer.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random integer min <= n < max.
	 */
	public static int random(final int min, final int max) {
		return min + (max == min ? 0 : random.nextInt(max - min));
	}

	/**
	 * Returns a normally distributed pseudorandom integer about a mean centered
	 * between min and max with a provided standard deviation.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @param sd
	 *            The standard deviation. A higher value will increase the
	 *            probability of numbers further from the mean being returned.
	 * @return Random integer min <= n < max from the normal distribution
	 *         described by the parameters.
	 */
	public static int random(final int min, final int max, final int sd) {
		final int mean = min + (max - min) / 2;
		int rand;
		do {
			rand = (int) (random.nextGaussian() * sd + mean);
		} while (rand < min || rand >= max);
		return rand;
	}

	/**
	 * Returns a normally distributed pseudorandom integer with a provided
	 * standard deviation about a provided mean.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @param mean
	 *            The mean (>= min and < max).
	 * @param sd
	 *            The standard deviation. A higher value will increase the
	 *            probability of numbers further from the mean being returned.
	 * @return Random integer min <= n < max from the normal distribution
	 *         described by the parameters.
	 */
	public static int random(final int min, final int max, final int mean,
			final int sd) {
		int rand;
		do {
			rand = (int) (random.nextGaussian() * sd + mean);
		} while (rand < min || rand >= max);
		return rand;
	}

	/**
	 * Returns a linearly distributed pseudorandom <code>double</code>.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random min <= n < max.
	 */
	public static double random(final double min, final double max) {
		return min + random.nextDouble() * (max - min);
	}

	/**
	 * @param toSleep
	 *            The time to sleep in milliseconds.
	 */
	public static void sleep(final int toSleep) {
		try {
			Thread.sleep(toSleep);
		} catch (final InterruptedException ignored) {
			log(ignored.getMessage());
		}
	}

	@Deprecated
	public static void println(String s) {
		BotFrame.log(s);
	}

	public static void log(String s) {
		BotFrame.log(s);
	}
}
