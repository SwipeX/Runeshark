package api.action;

public class Task implements ActivateCondition {
	String name;

	public Task() {
	}

	public Task(String name) {
		this.name = name;
	}

	@Override
	public boolean shouldActivate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This will only be activated if the previous method is true
	 */
	public void run() {

	}
}
