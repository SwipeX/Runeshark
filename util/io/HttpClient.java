package util.io;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import util.Configuration;
import util.StringUtil;

/**
 * @author Paris
 */
public class HttpClient {
	private static final Logger log = Logger.getLogger(HttpClient.class
			.getName());
	public static final String HTTP_USERAGENT_FAKE;
	public static final Map<URL, URL> http301 = new HashMap<URL, URL>();

	static {
		final boolean x64 = System.getProperty("sun.arch.data.model").equals(
				"64");
		final StringBuilder s = new StringBuilder(70);
		s.append("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; ");
		if (x64) {
			s.append("WOW64; ");
		}
		s.append("Trident/5.0)");
		HTTP_USERAGENT_FAKE = s.toString();
		s.setLength(0);
		s.append(Configuration.NAME);
	}

	public static String getHttpUserAgent(final URL url) {
		return HTTP_USERAGENT_FAKE;
	}

	public static HttpURLConnection getHttpConnection(final URL url)
			throws IOException {
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.addRequestProperty("Accept-Charset",
				"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		con.addRequestProperty("Accept-Encoding", "gzip, deflate");
		con.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		con.addRequestProperty("Host", url.getHost());
		con.addRequestProperty("User-Agent", getHttpUserAgent(url));
		con.setConnectTimeout(10000);
		return con;
	}

	private static HttpURLConnection getConnection(final URL url)
			throws IOException {
		final HttpURLConnection con = getHttpConnection(url);
		con.setUseCaches(true);
		return con;
	}

	public static long getLastModified(final URL url) throws IOException {
		final HttpURLConnection con = getConnection(url);
		long modified = con.getLastModified();
		if (modified != 0L) {
			modified -= TimeZone.getDefault().getOffset(modified);
		}
		return modified;
	}

	public static HttpURLConnection download(final URL url, final File file)
			throws IOException {
		final HttpURLConnection con = getConnection(url);
		long modified = con.getLastModified();

		if (file.exists() && modified != 0L) {
			modified -= TimeZone.getDefault().getOffset(modified);
			if (file.lastModified() < modified) {
				log.fine("Using " + file.getName() + " from cache");
				return con;
			}
		}

		log.fine("Downloading new " + file.getName());
		IOHelper.write(getInputStream(con), file);

		con.disconnect();
		return con;
	}

	public static String downloadAsString(final URL url) throws IOException {
		return downloadAsString(getConnection(url));
	}

	public static String downloadAsString(final HttpURLConnection con)
			throws IOException {
		final byte[] buffer = downloadBinary(con);
		return StringUtil.newStringUtf8(buffer);
	}

	public static byte[] downloadBinary(final URL url) throws IOException {
		return downloadBinary(getConnection(url));
	}

	private static byte[] downloadBinary(final URLConnection con)
			throws IOException {
		final DataInputStream in = new DataInputStream(getInputStream(con));
		final byte[] buffer;
		final int len = con.getContentLength();
		if (len == -1) {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			IOHelper.write(in, out);
			buffer = out.toByteArray();
		} else {
			buffer = new byte[len];
			in.readFully(buffer);
		}
		in.close();
		return buffer;
	}

	public static InputStream openStream(final URL url) throws IOException {
		return getInputStream(getConnection(url));
	}

	private static InputStream getInputStream(final URLConnection con)
			throws IOException {
		final InputStream in = con.getInputStream();
		final String encoding = con.getHeaderField("Content-Encoding");
		if (encoding != null) {
			if (encoding.equalsIgnoreCase("gzip")) {
				return new GZIPInputStream(in);
			} else if (encoding.equalsIgnoreCase("deflate")) {
				return new InflaterInputStream(in);
			}
		}
		return in;
	}
}
