package api.methods;

import java.awt.Color;

public class Player {
	/**
	 * 
	 * @return if a player is moving or not
	 */
	public static boolean isWalking() {
		Color c = ColorUtil.getColor(628, 87);
		MethodProvider.sleep(350);
		return c.equals(ColorUtil.getColor(628, 87));
	}
}
