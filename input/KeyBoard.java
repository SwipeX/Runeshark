package input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import api.methods.MethodProvider;

import loader.RSLoader;

public class KeyBoard extends MethodProvider implements KeyListener {

	public static KeyBoard kb;

	public static Key KeyPressed;
	public static Key KeyTyped;

	public KeyBoard() {
		kb = this;
	}

	public static KeyBoard getCurrent() {
		return kb;
	}

	public void typeKey(char c) {

		Canvas canvas = RSLoader.getCurrent().getCanvas();
		if (canvas != null) {
			KeyEvent keyEvent = new KeyEvent(canvas, KeyEvent.KEY_TYPED,
					System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, c);
			try {
				canvas.dispatchEvent(keyEvent);
			} catch (final Exception ex) {
				return;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			releaseKey(KeyEvent.VK_UNDEFINED, c);
		}

	}

	public void pressKey(int k, int key) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, k,
				(char) key);
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	private char getKeyChar(final char c) {
		if (c >= 36 && c <= 40) {
			return KeyEvent.VK_UNDEFINED;
		} else {
			return c;
		}
	}
	
	public void pressKey(int key) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, key,
				getKeyChar((char) key));
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	public void pressKey(char c) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_UNDEFINED, c);
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	public void releaseKey(char c) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
				KeyEvent.VK_UNDEFINED, c);
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	public void releaseKey(int k, int key) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, k,
				(char) key);
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	public void releaseKey(int key) {
		KeyEvent keyEvent = new KeyEvent(RSLoader.getCurrent().getCanvas(),
				KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, key,
				(char) key);
		RSLoader.getCurrent().getCanvas().dispatchEvent(keyEvent);
	}

	public void pressEnter() {
		pressKey(KeyEvent.VK_ENTER);
		releaseKey(KeyEvent.VK_ENTER);
	}

	public void typeWord(String s, boolean enter) {
		for (char c : s.toCharArray()) {
			try {
				typeKey(c);
			} catch (final Exception ex) {
				continue;
			}
		}
		if (enter) {
			pressEnter();
		}

	}

	public static char getKeyPressed() {
		return KeyPressed.getChar();
	}

	public boolean isKeyPressed(char c) {
		return KeyPressed.Char == c;
	}

	public long getKeyPressedTime() {
		return KeyPressed.getWhen();
	}

	public long getKeyTypedTime() {
		return KeyTyped.getWhen();
	}

	public char getKeyTyped() {
		return KeyTyped.getChar();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0 == null) {
			return;
		}
		KeyPressed.Char = arg0.getKeyChar();
		KeyPressed.Long = arg0.getWhen();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0 == null) {
			return;
		}
		KeyPressed.Long = arg0.getWhen();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (arg0 == null) {
			return;
		}
		KeyTyped.Char = arg0.getKeyChar();
		KeyTyped.Long = arg0.getWhen();
	}

	public class Key {

		char Char;
		long Long;

		Key(char c, long t) {
			Char = c;
			Long = t;
		}

		public long getWhen() {
			return Long;
		}

		public char getChar() {
			return Char;
		}
	}
}
