package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import util.Configuration;

public class Updater {
	static String VERSIONURL = "http://www.runeshark.net/bot/version.txt";

	public static boolean isLatest() throws IOException {
		double latestVersion = 0.0;
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				new URL(VERSIONURL).openStream()));
		String input;
		if ((input = br.readLine()) != null) {
			latestVersion = Double.parseDouble(input);
		}
		return getLocalVersion() >= latestVersion;
	}

	public static double getLocalVersion() {
		return Configuration.version;
	}

	public static void downloadNewUpdate() {
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			desktop.browse(new URI("http://www.runeshark.net/download"));
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		}
	}
}
