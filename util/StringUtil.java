package util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {
	private static final String[] COLOURS_STR = new String[] { "red", "green",
			"cyan", "purple", "white" };
	private static final Map<String, Color> COLOR_MAP = new HashMap<String, Color>();

	public static String stripHtml(final String s) {
		return s.replaceAll("\\<.*?\\>", "");
	}

	public static String formatVersion(final int version) {
		final String v = Integer.toString(version), x;
		final char d = '.';
		final StringBuilder s = new StringBuilder(6);
		s.append(v, 0, 1);
		s.append(d);
		switch (v.length()) {
		case 1:
			s.append('0');
			break;
		case 2:
			s.append(v, 1, 2);
			s.append('0');
			break;
		case 3:
			x = v.substring(1, 3);
			s.append(x.equals("00") ? "0" : x);
			break;
		default:
			s.append(v, 1, 2);
			s.append(d);
			x = v.substring(2, 4);
			s.append(x.equals("00") ? "0" : x);
			break;
		}
		return s.toString();
	}

	/**
	 * Draws a line on the screen at the specified index. Default is green.
	 * <p/>
	 * Available colours: red, green, cyan, purple, white.
	 * 
	 * @param render
	 *            The Graphics object to be used.
	 * @param row
	 *            The index where you want the text.
	 * @param text
	 *            The text you want to render. Colours can be set like [red].
	 */
	public static void drawLine(final Graphics render, final int row,
			final String text) {
		final FontMetrics metrics = render.getFontMetrics();
		final int height = metrics.getHeight() + 4; // height + gap
		final int y = row * height + 15 + 19;
		final String[] texts = text.split("\\[");
		int xIdx = 7;
		Color cur = Color.GREEN;
		for (String t : texts) {
			for (@SuppressWarnings("unused")
			final String element : COLOURS_STR) {
				// String element = COLOURS_STR[i];
				// Don't search for a starting '[' cause it they don't exists.
				// we split on that.
				final int endIdx = t.indexOf(']');
				if (endIdx != -1) {
					final String colorName = t.substring(0, endIdx);
					if (COLOR_MAP.containsKey(colorName)) {
						cur = COLOR_MAP.get(colorName);
					} else {
						try {
							final Field f = Color.class.getField(colorName);
							final int mods = f.getModifiers();
							if (Modifier.isPublic(mods)
									&& Modifier.isStatic(mods)
									&& Modifier.isFinal(mods)) {
								cur = (Color) f.get(null);
								COLOR_MAP.put(colorName, cur);
							}
						} catch (final Exception ignored) {
						}
					}
					t = t.replace(colorName + "]", "");
				}
			}
			render.setColor(Color.BLACK);
			render.drawString(t, xIdx, y + 1);
			render.setColor(cur);
			render.drawString(t, xIdx, y);
			xIdx += metrics.stringWidth(t);
		}
	}

	public static String unescapeXmlEntities(String text) {
		text = text.replaceAll("&amp;", "&");
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("&gt;", ">");
		text = text.replaceAll("&quot;", "\"");
		text = text.replaceAll("&apos;", "'");
		return text;
	}

	public static String urlEncode(final String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (final UnsupportedEncodingException ignored) {
			return text;
		}
	}

	public static String fileNameWithoutExtension(String path) {
		int z = path.lastIndexOf('/');
		if (z != -1) {
			if (++z == path.length()) {
				return "";
			} else {
				path = path.substring(z);
			}
		}
		z = path.indexOf('.');
		if (z != -1) {
			path = path.substring(0, z);
		}
		return path;
	}

	public static String throwableToString(final Throwable t) {
		if (t != null) {
			final Writer exception = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(exception);
			t.printStackTrace(printWriter);
			return exception.toString();
		}
		return "";
	}

	public static byte[] getBytesUtf8(final String string) {
		try {
			return string.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static String newStringUtf8(final byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			return new String(bytes, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static String byteArrayToHexString(byte[] b) {
		final StringBuilder s = new StringBuilder(b.length * 2);
		for (byte aB : b) {
			s.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
		}
		return s.toString();
	}

	public static byte[] hexStringToByteArray(final String s) {
		byte[] data = new byte[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
