package loader;

import java.io.IOException;

import bot.Updater;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteAquaSkin;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import ui.BotFrame;
import ui.Login;

public class Boot {
	static BotFrame botframe;

	public static void main(String args[]) throws IOException {
		Thread.currentThread().setName("Runeshark");
		if (Updater.isLatest()) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							SubstanceLookAndFeel
									.setSkin(new GraphiteAquaSkin());
							try {
								UIManager
										.setLookAndFeel(new SubstanceGraphiteAquaLookAndFeel());
							} catch (UnsupportedLookAndFeelException e) {
								e.printStackTrace();
							}

						}
					});
					new Login().setVisible(true);
				}
			});
		} else {
			Updater.downloadNewUpdate();
		}

	}

	public static void log(final String s) {

		BotFrame.log(s);
	}

}
