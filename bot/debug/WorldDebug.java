package bot.debug;

import java.awt.Color;
import java.awt.Graphics;

import api.methods.Location;
import bot.interfaces.Paintable;

public class WorldDebug implements Paintable {
	String ocr;
	Color DebugColor = new Color(250, 250, 250, 190);

	@Override
	public void paint(Graphics g) {
		g.setColor(DebugColor);
		g.drawString("Compass Rotation: " + Location.getCompassAngle(), 20, 40);
	}

}