package bot.script.randoms;

import java.util.ArrayList;

public class RandomChecker implements Runnable {
	public final static int DELAY = 500;
	public static Thread t;
	ArrayList<Random> RandomScripts = new ArrayList<Random>();

	public RandomChecker() {
		RandomScripts.add(new ArravRandom());
		RandomScripts.add(new FrogRandom());
		RandomScripts.add(new BeeHiveRandom());
		RandomScripts.add(new TeleRandom());
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		for (Random r : RandomScripts) {
			if (r.startCondition()) {
				r.onStart();
				while (!r.isDone) {
					int s = r.loop();
					try {
						Thread.sleep(s);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
