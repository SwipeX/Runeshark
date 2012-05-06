package util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPInputStream;

import util.StringUtil;

/**
 * @author Paris
 */
public class IOHelper {
	public static final int BUFFER_SIZE = 4096;

	public static byte[] read(final InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			final byte[] temp = new byte[BUFFER_SIZE];
			int read;
			while ((read = is.read(temp)) != -1) {
				buffer.write(temp, 0, read);
			}
		} catch (final IOException ignored) {
			try {
				buffer.close();
			} catch (final IOException ignored2) {
			}
			buffer = null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (final IOException ignored) {
			}
		}
		return buffer == null ? null : buffer.toByteArray();
	}

	public static byte[] read(final URL in) {
		try {
			return read(in.openStream());
		} catch (final IOException ignored) {
			return null;
		}
	}

	public static byte[] read(final File in) {
		try {
			return read(new FileInputStream(in));
		} catch (final FileNotFoundException ignored) {
			return null;
		}
	}

	public static String readString(final URL in) {
		return StringUtil.newStringUtf8(read(in));
	}

	public static String readString(final File in) {
		return StringUtil.newStringUtf8(read(in));
	}

	public static void write(final InputStream in, final OutputStream out) {
		try {
			final byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} catch (final IOException ignored) {
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException ignored) {
			}
		}
	}

	public static void write(final InputStream in, final File out) {
		try {
			write(in, new FileOutputStream(out));
		} catch (final FileNotFoundException ignored) {
		}
	}

	public static void write(final String s, final File out) {
		final ByteArrayInputStream in = new ByteArrayInputStream(
				StringUtil.getBytesUtf8(s));
		write(in, out);
	}

	public static long crc32(final InputStream in) throws IOException {
		final CheckedInputStream cis = new CheckedInputStream(in, new CRC32());
		final byte[] buf = new byte[BUFFER_SIZE];
		while (cis.read(buf) != -1) {
		}
		return cis.getChecksum().getValue();
	}

	public static long crc32(final byte[] data) throws IOException {
		return crc32(new ByteArrayInputStream(data));
	}

	public static long crc32(final File path) throws IOException {
		return crc32(new FileInputStream(path));
	}

	public static byte[] ungzip(final byte[] data) {
		if (data.length < 2) {
			return data;
		}

		final int header = (data[0] | data[1] << 8) ^ 0xffff0000;
		if (header != GZIPInputStream.GZIP_MAGIC) {
			return data;
		}

		try {
			final ByteArrayInputStream b = new ByteArrayInputStream(data);
			final GZIPInputStream gzin = new GZIPInputStream(b);
			final ByteArrayOutputStream out = new ByteArrayOutputStream(
					data.length);
			for (int c = gzin.read(); c != -1; c = gzin.read()) {
				out.write(c);
			}
			return out.toByteArray();
		} catch (final IOException e) {
			e.printStackTrace();
			return data;
		}
	}

	public static boolean isZip(final File file) {
		final String name = file.getName().toLowerCase();
		if (name.endsWith(".jar") || name.endsWith(".zip")) {
			return true;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			final byte[] m = new byte[4];
			fis.read(m);
			fis.close();
			return (m[0] << 24 | m[1] << 16 | m[2] << 8 | m[3]) == 0x504b0304;
		} catch (final IOException ignored) {
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (final IOException ignored) {
				}
			}
		}
		return false;
	}
}
