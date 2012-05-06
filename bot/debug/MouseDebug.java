package bot.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import api.methods.Mouse;
import bot.interfaces.Paintable;

public class MouseDebug implements Paintable {
	Color DebugColor = new Color(250, 40, 0, 190);

	@Override
	public void paint(Graphics g) {
		Point a = Mouse.getLocation();
		if (a != null) {
			int mouse_x = a.x;
			int mouse_y = a.y;
			g.setColor(DebugColor);
			g.drawLine(0, mouse_y, mouse_x, mouse_y);
			g.drawLine(756, mouse_y, mouse_x, mouse_y);
			g.drawLine(mouse_x, 0, mouse_x, mouse_y);
			g.drawLine(mouse_x, 503, mouse_x, mouse_y);
		}
	}
}
