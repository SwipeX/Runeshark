package bot.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import loader.RSLoader;
import api.methods.ColorUtil;
import bot.Bot;
import bot.interfaces.Paintable;

public class MainDebug implements Paintable {
	Color DebugColor = new Color(250, 40, 0, 190);

	public Color getColor() {

		int c = Bot
				.getCurrent()
				.getScreen()
				.getRGB(api.methods.Mouse.getLocation().x,
						api.methods.Mouse.getLocation().y);
		int red = (c & 0x00ff0000) >> 16;
		int green = (c & 0x0000ff00) >> 8;
		int blue = c & 0x000000ff;
		return new Color(red, green, blue);

	}

	public Point getMousePos() {
		return RSLoader.getCurrent().getCanvas().getMousePosition();
	}

	@Override
	public void paint(Graphics g) {
		Color c;
		try {
			c = getColor();
			for (Point p : ColorUtil.findAllColor(c)) {
				g.setColor(Color.RED);
				g.fillRect(p.x, p.y, 2, 2);
			}
			g.setColor(DebugColor);
			g.drawString("Mouse: " + api.methods.Mouse.getLocation().x + ", "
					+ api.methods.Mouse.getLocation().y, 20, 20);
			g.drawString("Color: " + c.toString(), 20, 30);
			g.setColor(c);
			g.fill3DRect(25, 35, 130, 30, true);
		} catch (Exception e) {

		}

	}
}
