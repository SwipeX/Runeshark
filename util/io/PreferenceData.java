package util.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.Configuration;

public class PreferenceData {
	private final File file;

	public PreferenceData(final int type) {
		file = new File(Configuration.Paths.getSettingsDirectory()
				+ File.separator + "pref_new-" + type + ".dat");
	}

	public byte[] get() {
		try {
			final RandomAccessFile raf = new RandomAccessFile(file, "rw");
			final byte[] b = new byte[(int) raf.length()];
			raf.readFully(b);
			return b;
		} catch (final IOException ioe) {
			return new byte[0];
		}
	}

	public void set(byte[] data) {
		try {
			final RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.write(data);
		} catch (final IOException ignored) {
		}
	}
}
