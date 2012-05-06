import java.io.File;
import java.io.IOException;

import util.Configuration;
import util.LibTask;

public class RuneShark {

	public static void main(String args[]) throws IOException {

		boolean sh = false;

		final String location = loader.Boot.class.getCanonicalName();
		final StringBuilder Parm = new StringBuilder(64);
		final char q = '"', s = ' ', d = ';';

		try {
			if (!LibTask.hasLibs()) {
				LibTask.getLibs();
			}

			String[] libs = new String[3];
			libs[0] = new File(Configuration.Paths.getLibDirectory()
					+ File.separatorChar + "substance.jar").getCanonicalPath();
			libs[1] = new File(Configuration.Paths.getLibDirectory()
					+ File.separatorChar + "trident.jar").getCanonicalPath();
			libs[2] = new File(Configuration.Paths.getLibDirectory()
					+ File.separatorChar + "CanvasHack.jar").getCanonicalPath();

			final String flags = "-Xmx1024m -cp "
					+ RuneShark.class.getProtectionDomain().getCodeSource()
							.getLocation().getPath() + d + libs[2] + d
					+ libs[0] + d + libs[1] + d;
			System.out.println(RuneShark.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			switch (Configuration.getCurrentOperatingSystem()) {
			case WINDOWS:
				Parm.append("javaw");
				Parm.append(s);
				Parm.append(flags);
				break;
			case MAC:// NO BREAK!
			case LINUX:
				sh = true;
				Parm.append("java");
				Parm.append(s);
				Parm.append(flags);
				Parm.append(s);
				Parm.append("-Xdock:name=");
				Parm.append(q);
				Parm.append(Configuration.NAME);
				Parm.append(q);
				Parm.append(s);
				Parm.append("-Xdock:icon=");
				Parm.append(q);
				Parm.append(Configuration.Paths.Resources.ICON);
				Parm.append(q);
				Parm.append(s);
				Parm.append("-Dapple.laf.useScreenMenuBar=true");
				break;
			default:
				Parm.append("javaw");
				Parm.append(s);
				Parm.append(flags);
				break;
			}

			Parm.append(s);
			Parm.append("-Xbootclasspath/p:");
			Parm.append(libs[2]);
			Parm.append(s);
			Parm.append(location);
			System.out.print(Parm.toString());
			final Runtime run = Runtime.getRuntime();
			if (sh) {
				run.exec(new String[] { "/bin/sh", "-c", Parm.toString() });
			} else {
				run.exec(Parm.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * if (Configuration.getCurrentOperatingSystem() ==
		 * Configuration.OperatingSystem.WINDOWS) { Runtime.getRuntime().exec(
		 * "javaw -Xmx1024m -cp " + RuneShark.class.getProtectionDomain()
		 * .getCodeSource().getLocation() .getPath() + ";" + libs[2] + ";" +
		 * libs[0] + ";" + libs[1] + " -Xbootclasspath/p:" + libs[2] + " " +
		 * Boot.class.getCanonicalName()); } else { String[] cmd = new String[]
		 * { "sh", "-c", "java -Xmx1024m -cp " +
		 * RuneShark.class.getProtectionDomain() .getCodeSource().getLocation()
		 * .getPath() + ";" + libs[2] + ";" + libs[0] + ";" + libs[1] +
		 * " -Xbootclasspath/p:" + libs[2] + " " + Boot.class.getCanonicalName()
		 * + "\" loader.Boot" }; Runtime.getRuntime().exec(cmd); } } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
	}
}
