package bot.script;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import api.action.Task;
import api.methods.MethodProvider;
import ui.BotFrame;

public abstract class Script {
	public boolean isRunning = false;
	public boolean isPaused = false;
	public static ArrayList<Task> Tasks = new ArrayList<Task>();
	public static final Logger log = Logger.getLogger("Script");

	public static void registerTasks(Task... t) {
		for (Task task : t) {
			Tasks.add(task);
		}
	}

	public abstract boolean onStart();

	public abstract int loop();

	public abstract Graphics doPaint(Graphics g);

	public static void log(final Object message) {
		log.info(message.toString());
	}

	public void log(final Color color, final Object message) {
		final Object[] parameters = { color };
		log.log(Level.INFO, message.toString(), parameters);
	}

	/**
	 * @deprecated
	 * @param s
	 */
	public static void println(String s) {
		BotFrame.log(s);
	}

	public void onFinish() {
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception ignored) {
		}
	}

	public static void sleep(long millis, long milli) {
		try {
			Thread.sleep(random((int) millis, (int) milli));
		} catch (Exception ignored) {
		}
	}

	public static int random(int low, int high) {
		return MethodProvider.random(low, high);
	}
}