package api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class Tabs {
	final static int TAB_WIDTH = 30;
	final static int TAB_HEIGHT = 35;
	final static Rectangle[] TOP_TABS = {
			new Rectangle(522, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(552, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(582, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(612, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(642, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(672, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(702, 170, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(732, 170, TAB_WIDTH, TAB_HEIGHT) };
	final static Rectangle[] BOTTOM_TABS = {
			new Rectangle(522, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(552, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(582, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(612, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(642, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(672, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(702, 470, TAB_WIDTH, TAB_HEIGHT),
			new Rectangle(732, 470, TAB_WIDTH, TAB_HEIGHT) };
	final static String[] TOP_TAB_NAMES = { "Style", "Task List", "Stats",
			"Quests", "Inventory", "Equip", "Prayer", "Magic" };
	final static String[] BOTTOM_TAB_NAMES = { "NONE", "Friends List",
			"Ignore List", "Clan Chat", "Settings", "Emote", "Music", "Notes" };
	static Color CURRENT_TAB = new Color(230, 157, 57);

	public static String getCurrentTab() {
        
        //Banking bug fix
        if(Bank.isOpen()){
            return "Inventory";
        }
		Point p;
		if ((p = ColorUtil.findColor(CURRENT_TAB, new Point(500, 160),
				new Point(765, 200))) != null) {
			for (int i = 0; i < TOP_TABS.length; i++) {
				if (TOP_TABS[i].contains(p)) {
					return TOP_TAB_NAMES[i];
				}
			}
		}
		if ((p = ColorUtil.findColor(CURRENT_TAB, new Point(500, 460),
				new Point(765, 503))) != null) {
			for (int i = 0; i < BOTTOM_TABS.length; i++) {
				if (BOTTOM_TABS[i].contains(p)) {
					return BOTTOM_TAB_NAMES[i];
				}
			}
		}
		return "NONE";
	}

	public static int getTab(String s) {
		for (int i = 0; i < TOP_TAB_NAMES.length; i++) {
			if (s.equals(TOP_TAB_NAMES[i]))
				return i;
		}
		return -1;
	}

	public static boolean setTab(String tab) {
		if (!getCurrentTab().equals(tab)) {
			Mouse.clickMouse((int) TOP_TABS[getTab(tab)].getCenterX(),
					(int) TOP_TABS[getTab(tab)].getCenterY(), true);
        }
        return getCurrentTab().equals(tab);
	}

}