package api.methods;

/**
 * @Author Ramy
 */
public class KeyBoard {
	public static void sendKey(char key) {
		input.KeyBoard.getCurrent().typeKey(key);
	}

	public static void sendString(String s, boolean enter) {
		input.KeyBoard.getCurrent().typeWord(s, enter);
	}

	public static void releaseKey(int key) {
		input.KeyBoard.getCurrent().releaseKey(key);
	}

	public static void releaseKey(char key) {
		input.KeyBoard.getCurrent().releaseKey(key);
	}

	public static void pressKey(int key) {
		input.KeyBoard.getCurrent().pressKey(key);
	}

	public static void pressKey(char key) {
		input.KeyBoard.getCurrent().pressKey(key);
	}

	public static void typeWord(String word) {
		try {
			input.KeyBoard.getCurrent().typeWord(word, true);
		} catch (final Exception ex) {
			return;
		}
	}

	public static void typeWord(String word, boolean enter) {
		input.KeyBoard.getCurrent().typeWord(word, enter);
	}

	public static void pressEnter() {
		input.KeyBoard.getCurrent().pressEnter();
	}

	public static char getKeyPressed() {
		return input.KeyBoard.KeyPressed.getChar();
	}

	public boolean isKeyPressed(char c) {
		return input.KeyBoard.KeyPressed.getChar() == c;
	}

	public long getKeyPressedTime() {
		return input.KeyBoard.KeyPressed.getWhen();
	}

	public long getKeyTypedTime() {
		return input.KeyBoard.KeyTyped.getWhen();
	}

	public char getKeyTyped() {
		return input.KeyBoard.KeyTyped.getChar();
	}
}
