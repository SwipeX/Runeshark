package bot;

public class InputManager {
	public static InputManager i;

	public InputManager() {
		i = this;
	}

	public static InputManager getCurrent() {
		return i;
	}

	public boolean input = true;

	public boolean getInput() {
		return input;
	}

	public void setInput(boolean on) {
		input = on;
	}
}
