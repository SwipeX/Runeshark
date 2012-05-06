package bot.script.randoms;

import java.awt.Color;

import api.methods.ColorUtil;

public class TeleRandom extends Random {
	Color ACTIVE_COLOR = new Color(118, 56, 52);

	@Override
	public boolean onStart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return ColorUtil.findColor(ACTIVE_COLOR) != null;
	}

}
