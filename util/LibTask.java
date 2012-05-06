package util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import util.io.HttpClient;

public class LibTask {

	public static boolean hasLibs() {
		File directory = new File(Configuration.Paths.getLibDirectory());
		if (directory == null || directory.listFiles() == null) {
			System.out.println("Making new directory...");
			directory.mkdirs();
			return false;
		} else {
			return true;
		}
	}

	public static void getLibs() {
		try {
			HttpClient.download(new URL(
					"http://www.runeshark.net/bot/trident.jar"), new File(
					Configuration.Paths.getLibDirectory() + File.separatorChar
							+ "trident.jar"));
			HttpClient.download(new URL(
					"http://www.runeshark.net/bot/CanvasHack.jar"), new File(
					Configuration.Paths.getLibDirectory() + File.separatorChar
							+ "CanvasHack.jar"));
			HttpClient.download(new URL(
					"http://www.runeshark.net/bot/substance.jar"), new File(
					Configuration.Paths.getLibDirectory() + File.separatorChar
							+ "substance.jar"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
