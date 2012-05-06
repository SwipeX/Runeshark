package ui;

import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import bot.Bot;

public class TabbedBotPanel {
	JTabbedPane botTabs;

	public TabbedBotPanel(Applet a) {
		try {
			init(a);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(Applet a) throws MalformedURLException, IOException {
		botTabs = new JTabbedPane();
		botTabs.addTab("Bot 1", (Icon)ImageIO.read(new URL("")), a);
		botTabs.setSize(756, 503);
		botTabs.setVisible(true);

	}
}
