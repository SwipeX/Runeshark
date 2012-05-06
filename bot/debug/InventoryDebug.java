package bot.debug;

import java.awt.Color;
import java.awt.Graphics;

import bot.interfaces.Paintable;
import api.methods.Inventory;
import api.methods.Tabs;

public class InventoryDebug implements Paintable {

	public Color defaultcolor = Color.black;

	/*
	 * public Point getCenter(Slot slot){ switch(slot.getColumn()){ case 1:
	 * return new Point(slot.getCenter().x + 6,slot.getCenter().y); case 2:
	 * return new Point(slot.getCenter().x + 3,slot.getCenter().y); case 3:
	 * return new Point(slot.getCenter().x ,slot.getCenter().y); case 4: return
	 * new Point(slot.getCenter().x - 4,slot.getCenter().y); default: return new
	 * Point(0,0); } }
	 */

	@Override
	public void paint(Graphics g) {
		if (Tabs.getCurrentTab() == "Inventory") {
			for (Inventory.Slot slot : Inventory.Slot.values()) {
				Color c = slot.getCenterColor();
				// c.getRed() + c.getGreen() + c.getBlue()
				if (!slot.isEmpty()) {
					g.setColor(Color.green);
					// c.getRed() + c.getGreen() + c.getBlue()
					// Integer.toHexString(new Integer( new String(""+
					// c.getRed() + c.getGreen() + c.getBlue())))
					g.drawString("" + Integer.toHexString(c.getRGB()),
							slot.getCenter().x, slot.getCenter().y);
				}
			}
		}
	}

}
