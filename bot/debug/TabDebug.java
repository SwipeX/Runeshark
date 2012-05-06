package bot.debug;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import api.methods.Game;
import api.methods.Tabs;
import bot.interfaces.Paintable;

public class TabDebug implements Paintable {

	private final Color DebugColor = new Color(0, 255, 51, 115);
	private final BasicStroke DebugStroke = new BasicStroke(4);

	@Override
	public void paint(Graphics g1) {

	}

}
