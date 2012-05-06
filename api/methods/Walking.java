package api.methods;

public class    Walking extends MethodProvider {

	/**
	 * Will walk x tiles across and y tiles down from current location.
	 * 
	 * @param north
	 *            - set the compass to north?
	 */
	public static void walkShort(int x, int y, boolean north) {
		if (north)
			Game.clickCompass();
		Mouse.click(Location.PLAYER_DOT.x + (x * 8), Location.PLAYER_DOT.y
				+ (y * 8));

		sleep((int) Math.sqrt((x + 1) * (1 + y)) * 500);
	}

}
