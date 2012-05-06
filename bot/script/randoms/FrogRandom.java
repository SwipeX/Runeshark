package bot.script.randoms;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import ui.BotFrame;

import api.methods.ColorUtil;
import api.methods.Mouse;
import api.methods.OCR;

/***
 * 
 * @author Swipe Might work, might not....who knows...
 * 
 */
public class FrogRandom extends Random {
	Color FROG_CROWN_COLOR = new Color(238, 210, 73);

	@Override
	public int loop() {
		if (OCR.getTextAt(new Rectangle(182, 341, 149, 29))
				.contains("Princess")) {
			Mouse.clickMouse(261, 465, true);
			// sleep(500);
			Mouse.clickMouse(261, 465, true);
			// sleep(500);
			Mouse.clickMouse(261, 465, true);
			// sleep(5000);
			Mouse.clickMouse(261, 465, true);
			// check to see if we are done
			isDone = true;
		} else {
			Point p;
			if ((p = ColorUtil.findColor(FROG_CROWN_COLOR)) != null) {
				Mouse.clickMouse(p.x, p.y, true);
			}
		}
		return 250;
	}

	@Override
	public boolean onStart() {
		BotFrame.log("Found Frog Random!");
		return true;
	}

	@Override
	public boolean startCondition() {
		return (OCR.getTextAt(new Rectangle(182, 341, 149, 29))
				.contains("Frog"));

	}

}
