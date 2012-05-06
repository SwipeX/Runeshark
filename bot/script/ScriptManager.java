package bot.script;

import java.util.Arrays;

import bot.InputManager;

import api.action.Task;

import ui.BotFrame;

public class ScriptManager implements Runnable {

	private Script currentScript;
	public static ScriptManager sm;
	private static Thread s;

	public ScriptManager() {
		sm = this;
		Thread.currentThread().setName("Script Manager");
	}

	public static ScriptManager getCurrent() {
		return sm;
	}

	public void setScript(Script script) {
		try {
			InputManager.getCurrent().setInput(false);
			if (script.onStart()) {
				currentScript = script;
				currentScript.isRunning = true;
				BotFrame.log("Starting Script: "
						+ currentScript.getClass().getName());
				s = new Thread(this);
				s.start();
			}
		} catch (Exception e) {
			String[] a = Arrays.toString(e.getStackTrace()).split(",");
			for (String z : a) {
				BotFrame.error(z);
			}

		}
	}

	@SuppressWarnings("deprecation")
	public void stopScript() {
		BotFrame.log(currentScript.getClass().getName() + " Stopped.");
		BotFrame.getCurrent().stascript.setEnabled(true);
		currentScript.isRunning = false;
		try {
			currentScript.onFinish();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		currentScript = null;
		s.stop();
	}

	public void pauseScript() {
		currentScript.isPaused = true;
	}

	public void unPauseScript() {
		currentScript.isPaused = false;
	}

	public Script getCurrentScript() {
		return currentScript;
	}

	public void sleep(int tosleep) {
		try {
			Thread.sleep(tosleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected boolean runOnce() {
		if (currentScript.isPaused) {
			sleep(50);
		} else {
			int timeOut = -1;
			try {
				// Task system
				if (currentScript.Tasks.size() > 0) {
					for (Task t : currentScript.Tasks) {
						if (t != null) {
							if (t.shouldActivate()) {
								t.run();
								break;
							}
							timeOut = 25; // Screen delay
						}
					}
				} else {
					timeOut = currentScript.loop();
				}
				// Task system end
			} catch (final ThreadDeath td) {
			} catch (final Exception ex) {
				BotFrame.log("Uncaught exception from task: " + ex);
			}
			if (timeOut < 0) {
				return false;
			}
			try {
				sleep(timeOut);
			} catch (final ThreadDeath td) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		if (this.currentScript != null)
			try {
				while (this.currentScript.isRunning) {
					if (!runOnce()) {
						break;
					}
				}
				stopScript();
			} catch (final Throwable t) {
				stopScript();
			}
	}

}
