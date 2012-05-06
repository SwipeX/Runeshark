package api.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BitMapUtil {
	public static String BitmapToString(BufferedImage dwBit) {
		try {
			ByteArrayOutputStream a2 = new ByteArrayOutputStream();
			ImageIO.write(dwBit, "jpg", a2);
			a2.flush();
			byte[] a3 = a2.toByteArray();
			a2.close();
			String a1 = null;
			a1 = new sun.misc.BASE64Encoder().encode(a3);
			return a1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage StringToBitmap(String img) {
		try {
			sun.misc.BASE64Decoder b = new sun.misc.BASE64Decoder();
			byte[] b1 = b.decodeBuffer(img);
			InputStream in = new ByteArrayInputStream(b1);
            return ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
