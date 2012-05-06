package bot.script;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ui.BotFrame;
import util.Configuration;
import util.io.HttpClient;

public class ScriptLoader {

	ArrayList<ScriptData> scripts = new ArrayList<ScriptData>();
	ArrayList<Script> classes = new ArrayList<Script>();
	ClassLoader cl;
	private Class<?> toLoad;

	@SuppressWarnings("unused")
	private byte[] loadClassData(String className) throws IOException {
		File f;
		f = new File(className);
		int size = (int) f.length();
		byte buff[] = new byte[size];
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(buff);
		dis.close();

		return buff;
	}

	public Script[] getScripts() {
		Script[] temp = new Script[classes.size()];
		for (int i = 0; i < classes.size(); i++) {
			temp[i] = classes.get(i);
		}
		return temp;
	}

	@SuppressWarnings("deprecation")
	public boolean loadScripts() {
		this.scripts.clear();
		this.classes.clear();

		File directory = new File(Configuration.Paths.getScriptsDirectory());
		if (directory == null || directory.listFiles() == null) {
			System.out.println("Making new directory...");
			directory.mkdirs();
			return false;
		}

		for (File f : directory.listFiles()) {
			if (f.getName().contains(".class") && !f.getName().contains("$")) {
				String className = f.getName().replace(".class", "");
				File file = directory;

				try {
					// Convert File to a URL
					URL url = file.toURL(); // file:/c:/myclasses/
					URL[] urls = new URL[] { url };

					// Create a new class loader with the directory
					ClassLoader cl = new URLClassLoader(urls);

					// Load in the class; MyClass.class should be located in
					// the directory file:/c:/myclasses/com/mycompany
					toLoad = cl.loadClass(className);
					if (toLoad.newInstance() instanceof Script) {
						classes.add((Script) (toLoad.newInstance()));
					}
				} catch (MalformedURLException e) {
				} catch (ClassNotFoundException e) {
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(toLoad.getSimpleName() + " is loaded");

				if (!isScriptClass(toLoad)
						|| !toLoad.isAnnotationPresent(ScriptManifest.class)) {
					System.out.println("not script, or no annotation");
					return false;
				}

				ScriptManifest manifest = toLoad
						.getAnnotation(ScriptManifest.class);

				if (manifest == null || manifest.name() == null
						|| manifest.name().length() < 1) {
					return false;
				}

				this.scripts.add(new ScriptData(toLoad, manifest.name(),
						manifest.authors(), manifest.version(), manifest
								.category(), manifest.description()));
			} else if (f.getName().contains(".jar")) {
				BotFrame.println("JAR!");
				loadLocalScriptJar(f);
			}
		}
		for (String str : loadScriptJarURLs()) {
			try {
				if (str != null)
					loadLocalScriptJar(new File(str));
			} catch (Exception e) {
				System.out.println("Error Loading Script Archieve: " + str
						+ "!");
			}
		}
		return true;
	}

	private String[] loadScriptJarURLs() {
		ArrayList<String> result = new ArrayList<String>();

		File file = new File(
				Configuration.Paths.getScriptsPrecompiledDirectory());
		if (!file.exists() && !file.mkdirs()) {
			return result.toArray(new String[result.size()]);
		} else {
			for (File f : file.listFiles()) {
				if (f.getName().contains(".jar"))
					try {
						result.add(f.getCanonicalPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return result.toArray(new String[result.size()]);
	}

	@SuppressWarnings("unused")
	private void loadOnlineScriptJar(String urlStr) throws IOException,
			InterruptedException {
		URL url = new URL(urlStr.replace(" ", "%20"));

		ByteArrayInputStream byteIS = new ByteArrayInputStream(
				HttpClient.downloadBinary(url));
		final JarInputStream jarIS = new JarInputStream(byteIS);

		Enumeration<JarEntry> entries = new Enumeration<JarEntry>() {

			JarEntry entry;

			@Override
			public JarEntry nextElement() {
				try {
					return (this.entry = jarIS.getNextJarEntry());
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			public boolean hasMoreElements() {
				return this.entry != null;
			}
		};

		loadScriptJar(
				entries,
				URLClassLoader.newInstance(new URL[] { new URL("jar:"
						+ url.getPath() + "!/") }));
	}

	private void loadLocalScriptJar(File f) {
		try {
			ClassLoader loader = URLClassLoader
					.newInstance(new URL[] { new URL("jar:file:"
							+ f.getAbsolutePath() + "!/") });

			ZipFile zip = new ZipFile(f.getAbsolutePath());
			Enumeration<? extends ZipEntry> entries = zip.entries();

			loadScriptJar(entries, loader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadScriptJar(Enumeration<? extends ZipEntry> entries,
			ClassLoader loader) {
		try {
			if (!entries.hasMoreElements())
				return;

			for (ZipEntry entry = entries.nextElement(); entries
					.hasMoreElements(); entry = entries.nextElement()) {
				if (entry == null)
					continue;

				String name = entry.getName();
				if (name == null || !name.contains(".class"))
					continue;

				Class<?> classFile = null;

				try {
					classFile = loader.loadClass(name.replace(".class", ""));
				} catch (Exception e) {

					continue;
				}

				if (classFile == null)
					continue;

				if (!isScriptClass(classFile)
						|| !classFile.isAnnotationPresent(ScriptManifest.class))
					continue;

				ScriptManifest manifest = classFile
						.getAnnotation(ScriptManifest.class);

				if (manifest == null || manifest.name() == null
						|| manifest.name().length() < 1)
					continue;

				this.scripts.add(new ScriptData(classFile, manifest.name(),
						manifest.authors(), manifest.version(), manifest
								.category(), manifest.description()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isScriptClass(Class<?> theClass) {
		if (theClass == null)
			return false;

		return theClass.getSuperclass().getSimpleName().equals("Script");
	}

	public String[] getScriptNames() {
		String[] names = new String[scripts.size()];
		int i = 0;
		for (ScriptData s : scripts) {
			names[i] = s.name;
			i++;
		}
		return names;
	}

	public String[] getScriptCategories() {
		LinkedList<String> names = new LinkedList<String>();

		for (ScriptData s : this.scripts) {
			Boolean b = false;
			for (int i = 0; i < names.size(); i++)
				if (names.get(i).equalsIgnoreCase((String) s.category))
					b = true;
			if (!b)
				names.add((String) s.category);
		}

		String[] result = names.toArray(new String[0]);
		return result;
	}

	public String getDescByName(String name) {
		for (ScriptData s : this.scripts)
			if (s.name.equals(name))
				return s.description;

		return "Error";
	}

	public String getCategoryByName(String name) {
		for (ScriptData s : this.scripts)
			if (s.name.equals(name))
				return s.category;

		return "Error";
	}

	public ArrayList<ScriptData> getScriptsByCat(String category) {
		ArrayList<ScriptData> sL = new ArrayList<ScriptData>();
		for (ScriptData s : scripts)
			if (s.category.equalsIgnoreCase(category))
				sL.add(s);

		return sL;
	}

	public ScriptData getScript(String name) {
		for (ScriptData s : scripts)
			if (s.name.equalsIgnoreCase(name))
				return s;

		return null;
	}
}
