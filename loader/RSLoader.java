package loader;

import input.KeyBoard;
import input.Mouse;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Configuration;

public final class RSLoader implements AppletStub {
	Applet target;
	public Applet loader;
	public static final HashMap<String, String> params = new HashMap<String, String>();
	public final String baseLink = "http://world6.runescape.com/";
	String HTML = null;
	String URL = null;
	static RSLoader r;

	public static Mouse m;
	public static KeyBoard kb;

	public RSLoader() {
		r = this;
		parseParams();
		m = new Mouse();
		kb = new KeyBoard();
	}

	public static boolean hasLibs() {
		File directory = new File(Configuration.Paths.getCacheDirectory());
		if (directory == null || directory.listFiles() == null) {
			System.out.println("Making new directory...");
			directory.mkdirs();
			return false;
		} else {
			return true;
		}
	}

	public static void renewMouse() {
		m = new Mouse();
	}

	public static KeyBoard getKeyBoard() {
		return kb;
	}

	public static Mouse getCurrentMouse() {
		return m;
	}

	public static RSLoader getCurrent() {
		return r;
	}

	public java.awt.Canvas getCanvas() {
		return (java.awt.Canvas) loader.getComponent(0);
	}

	public Applet getClient() {
		return loader;
	}

	public Applet loadClient() {
		try {
			downloadFile(URL);
			loader = (Applet) new URLClassLoader(new java.net.URL[] { new File(
					Configuration.Paths.getCacheDirectory() + "/runescape.jar")
					.toURI().toURL() }).loadClass("Rs2Applet").newInstance();
			loader.setStub(this);
			loader.init();
			loader.start();
			return loader;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void appletResize(int width, int height) {
	}

	@Override
	public final java.net.URL getCodeBase() {
		try {
			return new java.net.URL(baseLink);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public final java.net.URL getDocumentBase() {
		try {
			return new java.net.URL(baseLink);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public final String getParameter(String name) {
		return params.get(name);
	}

	@Override
	public final AppletContext getAppletContext() {
		return null;
	}

	String getContent(String link) {
		try {
			java.net.URL url = new java.net.URL(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String myparams = null;
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				myparams += inputLine;
			}
			in.close();
			return myparams;
		} catch (Exception e) {
		}
		return null;
	}

	String getUrl() throws Exception {
		return baseLink + ext("archive=", " ", HTML);
	}

	void downloadFile(final String url) {
		try {
			BufferedInputStream in = new BufferedInputStream(new java.net.URL(
					url).openStream());
			if (hasLibs()) {
			}
			FileOutputStream fos = new FileOutputStream(
					Configuration.Paths.getCacheDirectory() + "/runescape.jar");
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int x = 0;
			while ((x = in.read(data, 0, 1024)) >= 0) {
				bout.write(data, 0, x);
			}
			bout.close();
			in.close();
		} catch (Exception e) {
		}
	}

	String ext(String from, String to, String str1) {
		int p = 0;
		p = str1.indexOf(from, p) + from.length();
		return str1.substring(p, str1.indexOf(to, p));
	}

	void parseParams() {
		try {
			HTML = getContent(baseLink);
			Pattern regex = Pattern.compile(
					"<param name=\"?([^\\s]+)\"?\\s+value=\"?([^>]*)\"?>",
					Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			Matcher regexMatcher = regex.matcher(HTML);
			while (regexMatcher.find()) {
				if (!params.containsKey(regexMatcher.group(1))) {
					params.put(remove(regexMatcher.group(1)),
							remove(regexMatcher.group(2)));
					// System.out.println(remove(regexMatcher.group(1))+", "+
					// remove(regexMatcher.group(2)));
				}
			}
			// System.out.println("--------------------------------------------------------------------------------");
			// System.out.println("----------------------Succesfully parsed parameters.----------------------------");
			URL = getUrl();
		} catch (Exception e) {
		}
	}

	String remove(String str) {
		return str.replaceAll("\"", "");
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
