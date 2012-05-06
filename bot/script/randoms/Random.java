package bot.script.randoms;

public abstract class Random {
	public boolean isDone = false;

	public abstract boolean onStart();

	public abstract int loop();

	public void onFinish() {
	}

	public abstract boolean startCondition();
}
