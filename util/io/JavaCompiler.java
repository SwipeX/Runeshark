package util.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.Callable;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import util.Configuration;
import util.Configuration.OperatingSystem;

/**
 * @author Paris
 */
public class JavaCompiler implements Callable<Boolean> {
	private final static String JAVACARGS = "-g:none";
	private final File source;
	private final String classPath;

	public JavaCompiler(final File source, final String classPath) {
		this.source = source;
		this.classPath = classPath;
	}

	public Boolean call() {
		try {
			return compileNative() || compileSystem();
		} catch (final IOException ignored) {
			return false;
		}
	}

	public static boolean isAvailable() {
		return ToolProvider.getSystemJavaCompiler() != null
				|| findJavac() != null;
	}

	private boolean compileNative() {
		final javax.tools.JavaCompiler compiler = ToolProvider
				.getSystemJavaCompiler();
		if (compiler == null) {
			return false;
		}
		final DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<JavaFileObject>();
		final StandardJavaFileManager fileManager = compiler
				.getStandardFileManager(diagnosticsCollector, null, null);
		Iterable<? extends JavaFileObject> fileObjects = fileManager
				.getJavaFileObjects(source);
		try {
			fileManager.setLocation(StandardLocation.CLASS_PATH,
					Arrays.asList(new File(classPath)));
		} catch (final IOException ignored) {
		}
		return compiler.getTask(null, fileManager, diagnosticsCollector, null,
				null, fileObjects).call();
	}

	private boolean compileSystem() throws IOException {
		String javac = findJavac();
		if (javac == null) {
			throw new IOException();
		}
		final Process process = Runtime.getRuntime().exec(
				new String[] { javac, JAVACARGS, "-cp", classPath,
						source.getAbsolutePath() });
		try {
			process.waitFor();
		} catch (final InterruptedException ignored) {
		}
		final int result = process.exitValue();
		return result == 0;
	}

	private static String findJavac() {
		try {
			if (Configuration.getCurrentOperatingSystem() == OperatingSystem.WINDOWS) {
				String currentVersion = readProcess("REG QUERY \"HKLM\\SOFTWARE\\JavaSoft\\Java Development Kit\" /v CurrentVersion");
				currentVersion = currentVersion.substring(
						currentVersion.indexOf("REG_SZ") + 6).trim();
				String binPath = readProcess("REG QUERY \"HKLM\\SOFTWARE\\JavaSoft\\Java Development Kit\\"
						+ currentVersion + "\" /v JavaHome");
				binPath = binPath.substring(binPath.indexOf("REG_SZ") + 6)
						.trim() + "\\bin\\javac.exe";
				return new File(binPath).exists() ? binPath : null;
			} else {
				String whichQuery = readProcess("which javac");
				return whichQuery == null || whichQuery.length() == 0 ? null
						: whichQuery.trim();
			}
		} catch (Exception ignored) {
			return null;
		}
	}

	private static String readProcess(final String exec) throws IOException {
		final Process compiler = Runtime.getRuntime().exec(exec);
		final InputStream is = compiler.getInputStream();
		try {
			compiler.waitFor();
		} catch (final InterruptedException ignored) {
			return null;
		}
		final StringBuilder result = new StringBuilder(256);
		int r;
		while ((r = is.read()) != -1) {
			result.append((char) r);
		}
		return result.toString();
	}
}
