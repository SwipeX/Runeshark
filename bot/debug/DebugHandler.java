package bot.debug;

import java.awt.Graphics;
import java.util.ArrayList;

import bot.interfaces.Paintable;
import bot.script.ScriptManager;

public class DebugHandler implements Paintable {

	static DebugHandler dh;
	public ArrayList<Paintable> paintList = new ArrayList<Paintable>();

	boolean enable = true;

	public DebugHandler() {
		dh = this;
	}

	public boolean contains(Paintable p) {
		return paintList.contains(p);
	}

	public void enableDebug(Paintable p) {
		if (!enable) {
			enable = true;
		}
		paintList.add((Paintable) p);
	}

	public void disableDebug(Paintable p) {
		paintList.remove(p);
	}

	public void stop() {
		enable = false;
	}

	public static DebugHandler getCurrent() {
		return dh;
	}

	@Override
	public void paint(Graphics g) {
		if (ScriptManager.getCurrent() == null) {
			new ScriptManager();
		} else {
			if (ScriptManager.getCurrent().getCurrentScript() != null
					&& ScriptManager.getCurrent().getCurrentScript().isRunning) {
				ScriptManager.getCurrent().getCurrentScript().doPaint(g);
			}
		}
		for (Paintable p : paintList) {
			p.paint(g);
		}

	}
}
