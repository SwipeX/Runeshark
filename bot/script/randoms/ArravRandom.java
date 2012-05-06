package bot.script.randoms;

import java.awt.Color;

import ui.BotFrame;

import api.methods.ColorUtil;

/**
 * 
 * @author Swipe
 * 
 */
public class ArravRandom extends Random {
	Color PORTAL = new Color(191, 233, 145);
	Color TREE = new Color(109, 121, 93);
	Color ARRAV = new Color(60, 61, 52);

	@Override
	public boolean onStart() {
		BotFrame.log("Found Cap'n Arrav Random!");
		return true;
	}

	@Override
	public int loop() {
		// Click Arrav
		// Click continue * 6
		// sleep 3500
		// Click continue
		// Click 257, 430
		// Click continue
		// COMPLETE PUZZLE
		// Click continue * 6
		// Click Portal
		// Check to see if we are done
		isDone = true;
		return 20;
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return ColorUtil.findAllColor(PORTAL) != null
				&& ColorUtil.findAllColor(TREE) != null;
	}

}
