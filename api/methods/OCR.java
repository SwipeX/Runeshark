package api.methods;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;

public class OCR {

	public static String getUpText() {
		 if (RSText.hasFonts()) {
		 return RSText.findString(new Rectangle(0, 0, 700, 500), null);
		 } else {
		 try {
		 RSText.CopyFonts();
		 return getUpText();
		 } catch (MalformedURLException e) {
		 e.printStackTrace();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }
		return RSText.findString(new Rectangle(0, 0, 700, 500), null);
	}

    public static String getOptionText(){
        return RSText.getOptionsText();
    }
    
	public static String getTextAt(Rectangle loc) {
		if (RSText.hasFonts()) {
			return RSText.findString(loc, null);
		} else {
			try {
				RSText.CopyFonts();
				return getUpText();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return "";
	}

	public static String getTextFromColor(Rectangle loc, Color c) {
		if (RSText.hasFonts()) {
			return RSText.findString(loc, c);
		} else {
			try {
				RSText.CopyFonts();
				return getUpText();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}