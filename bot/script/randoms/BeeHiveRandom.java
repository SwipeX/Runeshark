package bot.script.randoms;

import java.awt.Color;

import ui.BotFrame;

import api.methods.ColorUtil;

/**
 * 
 * @author Swipe
 * 
 */
public class BeeHiveRandom extends Random {
	Color CHAT_KEEPER = new Color(237, 227, 226);

	@Override
	public boolean onStart() {
		BotFrame.log("BeeHive Random Found!");
		return true;
	}

	@Override
	public int loop() {
		// click continue * 4
		// build
		// click mouse 225,275
		// clcik continue *2
		return 0;
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return ColorUtil.getColor(75, 375).equals(CHAT_KEEPER);
	}

}
