package api.methods;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


import javax.imageio.ImageIO;

import api.util.ZipUtils;

import util.Configuration;

/**
 * @Author Unknown. Converted from Simba
 * @Updater Swipe, Dwarfeh
 */
@SuppressWarnings("static-access")
public class RSText {

	static String d = Configuration.Paths.getCacheDirectory() + "\\Fonts\\Fonts";
	public static AllFonts allfonts = new AllFonts();

	public static boolean hasFonts() {
		File directory = new File(d);
        return !(!directory.exists() || directory.listFiles() == null) && directory.listFiles().length > 0;
	}

	public static void CopyFonts() throws IOException {
		if (!hasFonts()) {
			String LOC = "http://www.runeshark.net/bot/Fonts.zip";
			ZipUtils.unpackArchive(new URL(LOC),
					new File(Configuration.Paths.getCacheDirectory()));
		}
	}

	public static class Font {
		public Point[] goodpts;
		public Point[] badpts;
		public char letter;
		public Rectangle letbox;

		public Font(ArrayList<Point> goodpoints, ArrayList<Point> badpoints,
				char letter, Rectangle letbox) {
			goodpts = new Point[goodpoints.size()];
			goodpts = goodpoints.toArray(goodpts);
			badpts = new Point[badpoints.size()];
			badpts = badpoints.toArray(goodpts);
			this.letbox = letbox;
			this.letter = letter;
		}
	}

	/**
	 * Enumerated types of all the fonts available to use
	 * 
	 */
	public static enum FontTypes {
		BigChars, CharsNPC, FriendChars, LoginChars, SmallChars, StatChars, UpChars, UpCharsEx
	}

	/**
	 * Class that stores every single font typea and is used to load all font
	 * types
	 */
	public static class AllFonts {
		public static String[] fontnames = { "BigChars", "CharsNPC",
				"FriendChars", "LoginChars", "SmallChars", "StatChars",
				"UpChars", "UpCharsEx" };
		public static final Font[][] allLetters = new Font[fontnames.length][62];

		public AllFonts() {
			for (int i = 0; i < allLetters.length; i++) {
				allLetters[i] = grabfontset(allLetters[i], fontnames[i]);
			}
		}
	}

	/**
	 * Load all images for the current font and store the font information to
	 * the Font array
	 * 
	 * @param fontset
	 *            : font array that information will be stored
	 * @param fontname
	 *            : name of the current font
	 * @return : a filled array of font information
	 */
	public static Font[] grabfontset(Font[] fontset, String fontname) {
		int cnt = 0;
		int i;
		try {
			for (i = 48; i < 58; i++) {
				final BufferedImage img = ImageIO.read(new File(d + "/"
						+ fontname + "/" + i + ".bmp"));
				fontset[cnt++] = grabPoints(img, (char) i);
			}
			for (i = 65; i < 91; i++) {
				final BufferedImage img = ImageIO.read(new File(d + "/"
						+ fontname + "/" + i + ".bmp"));
				fontset[cnt++] = grabPoints(img, (char) i);
			}
			for (i = 97; i < 123; i++) {
				final BufferedImage img = ImageIO.read(new File(d + "/"
						+ fontname + "/" + i + ".bmp"));
				fontset[cnt++] = grabPoints(img, (char) i);
			}
		} catch (IOException ignored) {

		}
		return fontset;
	}

	/**
	 * Grab all points in the image that match the given color
	 * 
	 * @param img
	 *            : image to search through
	 * @param letter
	 *            : grab points with color c
	 * @return : an array of the points that matched color c
	 */
	public static Font grabPoints(BufferedImage img, char letter) {
		ArrayList<Point> goodpts = new ArrayList<Point>();
		ArrayList<Point> badpts = new ArrayList<Point>();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				final Color c2 = new Color(img.getRGB(x, y));
				if (c2.equals(Color.WHITE))
					goodpts.add(new Point(x, y));
				else
					badpts.add(new Point(x, y));
			}
		}
		return new Font(goodpts, badpts, letter, new Rectangle(img.getWidth(),
				img.getHeight()));
	}

	public static boolean checkColor(final Color c, final Color c2,
			final int Tol) {
		return (checkColor(c.getRed(), c2.getRed())
				+ checkColor(c.getGreen(), c2.getGreen()) + checkColor(
				c.getBlue(), c2.getBlue())) < Tol;
	}

	public static int checkColor(final int RGB, final int Val) {
		return Math.abs(RGB - Val);
	}

	public static String sortLetters(ArrayList<Letter> letters) {
		String text = "";
		Letter oldLetter = null;
		while (!letters.isEmpty()) {
			Letter curLetter = new Letter('X', 800, 800);
			for (Letter letter : letters) {
				if (letter.x0 < curLetter.x0) {
					curLetter = letter;
				}
			}
			if (oldLetter != null && curLetter.x0 - oldLetter.x1 > 1)
				text += " ";
			oldLetter = curLetter;
			text += curLetter.letter;
			letters.remove(curLetter);
		}
		return text;
	}

	/**
	 * Find text within box rectangle using specified font
	 *
	 * @param rec
	 *            : rectangle to search for text
	 * @return : String of text that was found inside of rectangle
	 */
	public static String findString(Rectangle rec) {
		return findString(rec, null);
	}

    /**
     * Find text within box rectangle using specified font
     *
     * @param fontC
     *            : color of the text inside of the rectangle, can be left null
     *            to search for any color
     * @param rec
     *            : rectangle to search for text
     * @param curfont
     *            : font type for the letters you will be searching for
     * @return : String of text that was found inside of rectangle
     */
    public static String findString(Color fontC, Rectangle rec, FontTypes curfont) {
        return findString(rec, null);
    }

	/**
	 * Find text within box rectangle using all font types
	 * 
	 * @param rec
	 *            : rectangle to search for text
	 * @param fontC
	 *            : color of the text inside of the rectangle, can be left null
	 *            to search for any color
	 * @return : String of text that was found inside of rectangle
	 */
	public static String findString(Rectangle rec, Color fontC) {
		return findString(rec, fontC, null);
	}

	/**
	 * Find text within box rectangle
	 * 
	 * @param rec
	 *            : rectangle to search for text
	 * @param fontC
	 *            : color of the text inside of the rectangle, can be left null
	 *            to search for any color
	 * @param font
	 *            : font type for the letters you will be searching for, this
	 *            can be left null to search for all font types
	 * @return : String of text that was found inside of rectangle
	 */
	public static String findString(Rectangle rec, Color fontC, Font[] font) {
		ArrayList<Letter> nums = new ArrayList<Letter>();
		Font[] foundFont = font;

		for (int y = rec.y; y < rec.y + rec.height; y++) {
			M1: for (int x = rec.x; x < rec.x + rec.width; x++) {
				Color c = Game.getColorAt(x, y);
				if (fontC != null && !fontC.equals(c))
					continue;
				for (int i = 0; i < AllFonts.allLetters.length; i++) {
					Font[] curFont = AllFonts.allLetters[i];
					if (foundFont != null) {
						curFont = foundFont;
						i = AllFonts.allLetters.length;
					}
					M2: for (Font aCurFont : curFont) {
						final Rectangle loc = new Rectangle(x
								- aCurFont.goodpts[0].x, y
								- aCurFont.goodpts[0].y, aCurFont.letbox.width,
								aCurFont.letbox.height);
						if (!rec.contains(loc))
							continue;
						for (int k = 0; k < aCurFont.goodpts.length; k++) {
							if (!checkColor(
									Game.getColorAt(loc.x
											+ aCurFont.goodpts[k].x, loc.y
											+ aCurFont.goodpts[k].y), c, 40))
								continue M2;
						}
						for (int k = 0; k < aCurFont.badpts.length; k++) {
							if (checkColor(
									Game.getColorAt(loc.x
											+ aCurFont.badpts[k].x, loc.y
											+ aCurFont.badpts[k].y), c, 40))
								continue M2;
						}

						nums.add(new Letter(aCurFont.letter, loc.x, loc.x
								+ aCurFont.letbox.width));
						foundFont = curFont;
						fontC = c;
						continue M1;
					}
				}
			}
		}
		return sortLetters(nums);
	}

	/**
	 * Class used to what letter was found and its start location and ending
	 * location
	 */
	public static class Letter {
		public int x0, x1;
		public char letter;

		public Letter(char letter, int x0, int x1) {
			this.letter = letter;
			this.x0 = x0;
			this.x1 = x1;
		}
	}

	private static class Character {
		private char character;
		private int[] fontPointsX, fontPointsY, shadowPointsX, shadowPointsY;
		private int height, width;

		private Character(char character, int[] x, int[] y) {
			this.character = character;
			this.fontPointsX = x;
			this.fontPointsY = y;

			ArrayList<Point> shadowPoints = new ArrayList<Point>();

			main: for (int i = 0; i < fontPointsX.length; i++) {

				for (int j = 0; j < fontPointsX.length; j++) {
					if (fontPointsX[i] + 1 == fontPointsX[j]
							&& fontPointsY[i] + 1 == fontPointsY[j]) {
						continue main;
					}
				}

				shadowPoints.add(new Point(fontPointsX[i] + 1,
						fontPointsY[i] + 1));

				if (fontPointsX[i] + 1 > width) {
					width = fontPointsX[i] + 1;
				}
				if (fontPointsY[i] + 1 > height) {
					height = fontPointsY[i] + 1;
				}
			}

			shadowPointsX = new int[shadowPoints.size()];
			shadowPointsY = new int[shadowPoints.size()];

			for (int i = 0; i < shadowPointsX.length; i++) {
				shadowPointsX[i] = shadowPoints.get(i).x;
				shadowPointsY[i] = shadowPoints.get(i).y;
			}
		}

		private char getCharacter() {
			return character;
		}

		private int getHeight() {
			return height;
		}

		private int getWidth() {
			return width;
		}

	}

	private static Character[] optionCharacters = {
			new Character('a', new int[] { 1, 2, 3, 4, 1, 2, 3, 4, 0, 4, 0, 4,
					1, 2, 3, 4 }, new int[] { 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 4,
					4, 5, 5, 5, 5 }),
			new Character('b', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
					1, 2, 3, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7,
					8, 3, 3, 3, 8, 8, 8, 4, 5, 6, 7 }),
			new Character('c',
					new int[] { 1, 2, 3, 0, 0, 0, 0, 4, 4, 1, 2, 3 },
					new int[] { 0, 0, 0, 1, 2, 3, 4, 1, 4, 5, 5, 5 }),
			new Character('d', new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 2, 3,
					0, 0, 0, 0, 1, 2, 3 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7,
					8, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8 }),
			new Character('e', new int[] { 1, 2, 3, 0, 4, 0, 1, 2, 3, 4, 0, 0,
					4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 3,
					4, 4, 5, 5, 5 }),
			new Character('f', new int[] { 2, 3, 1, 1, 0, 1, 2, 3, 1, 1, 1, 1,
					1 }, new int[] { 0, 0, 1, 2, 3, 3, 3, 3, 4, 5, 6, 7, 8 }),
			new Character('g', new int[] { 1, 2, 3, 4, 0, 0, 0, 0, 4, 4, 4, 4,
					1, 2, 3, 4, 4, 3, 2, 1 }, new int[] { 0, 0, 0, 0, 1, 2, 3,
					4, 1, 2, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7 }),
			new Character('h', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
					4, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 3,
					3, 3, 4, 5, 6, 7, 8 }),
			new Character('i', new int[] { 0, 0, 0, 0, 0, 0, 0 }, new int[] {
					0, 3, 4, 5, 6, 7, 8 }),
			new Character('j', new int[] { 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2 },
					new int[] { 10, 3, 10, 0, 3, 4, 5, 6, 7, 8, 9 }),
			new Character('k', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2,
					3, 3, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 6, 5,
					6, 4, 7, 3, 8 }),
			new Character('l', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }),
			new Character('m', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4,
					4, 4, 5, 6, 7, 8, 8, 8, 8, 8 }, new int[] { 0, 1, 2, 3, 4,
					5, 0, 0, 0, 1, 2, 3, 4, 5, 0, 0, 0, 1, 2, 3, 4, 5 }),
			new Character('n', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4,
					4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 0, 0, 0, 1, 2, 3, 4,
					5 }),
			new Character('o', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 4, 1,
					2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5,
					5 }),
			new Character('p', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 1,
					2, 3, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 0,
					0, 0, 5, 5, 5, 1, 2, 3, 4 }),
			new Character('q', new int[] { 1, 2, 3, 4, 0, 4, 0, 4, 0, 4, 0, 4,
					1, 2, 3, 4, 4, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 2, 3,
					3, 4, 4, 5, 5, 5, 5, 6, 7 }),
			new Character('r', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3 },
					new int[] { 0, 1, 2, 3, 4, 5, 1, 0, 0 }),
			new Character('s',
					new int[] { 1, 2, 3, 0, 0, 1, 2, 3, 3, 0, 1, 2 },
					new int[] { 0, 0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 5 }),
			new Character('t',
					new int[] { 1, 1, 0, 1, 2, 3, 1, 1, 1, 1, 2, 3 },
					new int[] { 0, 1, 2, 2, 2, 2, 3, 4, 5, 6, 7, 7 }),
			new Character('u', new int[] { 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 1, 2,
					3, 4 }, new int[] { 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 5, 5, 5,
					5 }),
			new Character('v', new int[] { 0, 4, 0, 4, 1, 3, 1, 3, 2, 2 },
					new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 }),
			new Character('w', new int[] { 0, 3, 6, 0, 3, 6, 0, 2, 4, 6, 0, 2,
					4, 6, 1, 5, 1, 5 }, new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2,
					2, 3, 3, 3, 3, 4, 4, 5, 5 }),
			new Character('x', new int[] { 0, 4, 1, 3, 2, 2, 1, 3, 0, 4 },
					new int[] { 0, 0, 1, 1, 2, 3, 4, 4, 5, 5 }),
			new Character('y',
					new int[] { 0, 4, 1, 1, 1, 3, 3, 3, 2, 2, 2, 1 },
					new int[] { 0, 0, 1, 2, 3, 1, 2, 3, 4, 5, 6, 7 }),
			new Character('z',
					new int[] { 0, 1, 2, 3, 3, 2, 1, 0, 0, 1, 2, 3 },
					new int[] { 0, 0, 0, 0, 1, 2, 3, 4, 5, 5, 5, 5 }),

			new Character('A', new int[] { 2, 3, 2, 3, 1, 4, 1, 4, 1, 4, 0, 1,
					2, 3, 4, 5, 0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3,
					3, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7 }),
			new Character('B', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 1, 2, 3,
					4, 0, 5, 0, 5, 0, 5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0,
					0, 1, 1, 2, 2, 3, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7,
					7 }),
			new Character('C', new int[] { 2, 3, 4, 5, 1, 6, 0, 0, 0, 0, 1, 6,
					2, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 3, 4, 5, 6,
					6, 7, 7, 7, 7 }),
			new Character('D', new int[] { 0, 1, 2, 3, 4, 0, 5, 0, 6, 0, 6, 0,
					6, 0, 6, 0, 5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 0,
					1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7 }),
			new Character('E', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 4,
					0, 0, 0, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 0, 1, 2,
					3, 3, 3, 3, 3, 4, 5, 6, 7, 7, 7, 7, 7 }),
			new Character('F', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 0,
					0, 0, 0 }, new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4,
					5, 6, 7 }),
			new Character('G', new int[] { 2, 3, 4, 5, 1, 6, 0, 0, 0, 0, 1, 2,
					3, 4, 5, 6, 6, 6, 5, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2,
					3, 4, 5, 6, 7, 7, 7, 7, 6, 5, 4, 4, 4 }),
			new Character('H', new int[] { 0, 5, 0, 5, 0, 5, 0, 1, 2, 3, 4, 5,
					0, 5, 0, 5, 0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3,
					3, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 }),
			new Character('I',
					new int[] { 0, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 2 },
					new int[] { 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7 }),
			new Character('J',
					new int[] { 1, 2, 3, 3, 3, 3, 3, 3, 3, 0, 1, 2 },
					new int[] { 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7 }),
			new Character('K', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3,
					3, 4, 4, 5, 5 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 4, 3,
					4, 2, 5, 1, 6, 0, 7 }),
			new Character('L',
					new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4 },
					new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7 }),
			new Character('M', new int[] { 0, 1, 5, 6, 0, 1, 5, 6, 0, 2, 4, 6,
					0, 2, 4, 6, 0, 3, 6, 0, 3, 6, 0, 6, 0, 6 }, new int[] { 0,
					0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5,
					5, 6, 6, 7, 7 }),
			new Character('N', new int[] { 0, 1, 5, 0, 1, 5, 0, 2, 5, 0, 2, 5,
					0, 3, 5, 0, 3, 5, 0, 4, 5, 0, 4, 5 }, new int[] { 0, 0, 0,
					1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7,
					7 }),
			new Character('O', new int[] { 2, 3, 4, 1, 5, 0, 6, 0, 6, 0, 6, 0,
					6, 1, 5, 2, 3, 4 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3,
					4, 4, 5, 5, 6, 6, 7, 7, 7 }),
			new Character('P', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 1,
					2, 3, 0, 0, 0 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3,
					4, 4, 4, 4, 5, 6, 7 }),
			new Character('Q', new int[] { 2, 3, 4, 1, 5, 0, 6, 0, 6, 0, 6, 0,
					6, 1, 5, 2, 3, 4, 4, 5, 6 }, new int[] { 0, 0, 0, 1, 1, 2,
					2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 8, 9, 9 }),
			new Character('R', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 1,
					2, 3, 0, 0, 0, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 1, 1, 2,
					2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 5, 6, 7 }),
			new Character('S', new int[] { 1, 2, 3, 4, 0, 5, 0, 1, 2, 3, 4, 5,
					5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 3, 3,
					4, 4, 5, 6, 6, 7, 7, 7, 7 }),
			new Character('T', new int[] { 0, 1, 2, 3, 4, 5, 6, 3, 3, 3, 3, 3,
					3, 3 }, new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6,
					7 }),
			new Character('U', new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5,
					5, 5, 5, 5, 5, 5 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 7,
					7, 7, 6, 5, 4, 3, 2, 1, 0 }),
			new Character('V', new int[] { 0, 5, 0, 5, 0, 5, 1, 4, 1, 4, 1, 4,
					2, 3, 2, 3 }, new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
					5, 6, 6, 7, 7 }),
			new Character('W', new int[] { 0, 4, 8, 0, 4, 8, 1, 3, 5, 7, 1, 3,
					5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 2, 6, 2, 6 }, new int[] { 0,
					0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5,
					5, 6, 6, 7, 7 }),
			new Character('X', new int[] { 0, 5, 0, 5, 1, 4, 2, 3, 2, 3, 1, 4,
					0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
					5, 6, 6, 7, 7 }),
			new Character('Y', new int[] { 0, 6, 1, 5, 2, 4, 3, 3, 3, 3, 3 },
					new int[] { 0, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7 }),
			new Character('Z', new int[] { 0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 0,
					0, 1, 2, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3,
					4, 5, 6, 7, 7, 7, 7, 7, 7 }),

			new Character('0', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 4, 0,
					4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3,
					4, 4, 5, 5, 6, 6, 7, 7, 7 }),
			new Character('1', new int[] { 2, 0, 1, 2, 2, 2, 2, 2, 2, 0, 1, 2,
					3, 4 }, new int[] { 0, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7,
					7 }),
			new Character('2', new int[] { 1, 2, 3, 0, 4, 4, 3, 2, 1, 0, 0, 1,
					2, 3, 4 }, new int[] { 0, 0, 0, 1, 1, 2, 3, 4, 5, 6, 7, 7,
					7, 7, 7 }),
			new Character('3', new int[] { 1, 2, 3, 0, 4, 4, 3, 2, 4, 4, 4, 0,
					1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 3, 3, 4, 5, 6, 6,
					7, 7, 7 }),
			new Character('4', new int[] { 4, 4, 3, 4, 2, 4, 1, 4, 0, 0, 1, 2,
					3, 4, 5, 4, 4 }, new int[] { 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
					5, 5, 5, 5, 5, 6, 7 }),
			new Character('5', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 4,
					4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 3,
					3, 3, 4, 5, 6, 6, 7, 7, 7 }),
			new Character('6', new int[] { 2, 3, 1, 0, 0, 1, 2, 3, 0, 4, 0, 4,
					0, 4, 1, 2, 3 }, new int[] { 0, 0, 1, 2, 3, 3, 3, 3, 4, 4,
					5, 5, 6, 6, 7, 7, 7 }),
			new Character('7',
					new int[] { 0, 1, 2, 3, 4, 4, 3, 3, 2, 2, 1, 1 },
					new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7 }),
			new Character('8', new int[] { 1, 2, 3, 0, 4, 0, 4, 1, 2, 3, 0, 4,
					0, 4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3,
					3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7 }),
			new Character('9', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 1, 2, 3,
					4, 4, 3, 2, 1 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4,
					4, 4, 4, 5, 6, 7, 7 }),

			new Character('-', new int[] { 0, 1, 2 }, new int[] { 0, 0, 0 }),
			new Character('/', new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 },
					new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 }),
			new Character('(', new int[] { 2, 1, 1, 0, 0, 0, 0, 0, 1, 1, 2 },
					new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }),
			new Character(')', new int[] { 0, 1, 1, 2, 2, 2, 2, 2, 1, 1, 0 },
					new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }) };

	static {
		Arrays.sort(optionCharacters, new Comparator<Character>() {
			public int compare(Character arg0, Character arg1) {
				if (arg0.width < arg1.width) {
					return 1;
				}
				if (arg0.width > arg1.width) {
					return -1;
				}

				if (arg0.fontPointsX.length < arg1.fontPointsX.length) {
					return 1;
				}
				if (arg0.fontPointsX.length > arg1.fontPointsX.length) {
					return -1;
				}

				return 0;
			}
		});
	}

	/**
	 * Gets the text when hovering over something with mouse
	 * 
	 * @return The text, if any, from the mouse hovering over
	 */
    /*
    *Don't change this with findString. It sometimes presents text
    *that findString does not find; mainly for xp gain
    */
	public static String getOptionsText() {
		StringBuilder builder = new StringBuilder();

		BufferedImage gameImage = Game.getImage();

		int leftUpperX = 5, leftUpperY = 5, width = Game.VIEWPORT.width - 10, height = 20;

		boolean[][] ocrImage = new boolean[width][height];

		for (int x = leftUpperX; x < leftUpperX + width; x++) {
			for (int y = leftUpperY; y < leftUpperY + height; y++) {
				int color = gameImage.getRGB(x, y) & 0xFFFFFF;

				ocrImage[x - leftUpperX][y - leftUpperY] = getDistanceSquare(
						color, 14474460) < 12500 // WHITE
						|| getDistanceSquare(color, 56540) < 12500 // CYAN
						|| getDistanceSquare(color, 14474240) < 12500 // YELLOW
						|| getDistanceSquare(color, 15106620) < 12500;

			}
		}

		// First, find a capital letter in the area ((0,0),(50,height))

		int posX = 0;

		x: for (int x = 0; x < 50; x++) {
			for (int y = 0; y < height; y++) {

				c: for (Character c : optionCharacters) {

					if (c.getCharacter() < 'A' || c.getCharacter() > 'Z') {
						continue;
					}

					if (y + c.getHeight() >= 20) {
						continue;
					}

					if (x + c.getWidth() >= 25) {
						continue;
					}

					for (int j = 0; j < c.fontPointsX.length; j++) {
						if (!ocrImage[x + c.fontPointsX[j]][y
								+ c.fontPointsY[j]]) {
							continue c;
						}
					}

					for (int j = 0; j < c.shadowPointsX.length; j++) {
						if (ocrImage[x + c.shadowPointsX[j]][y
								+ c.shadowPointsY[j]]) {
							continue c;
						}
					}

					builder.append(c.getCharacter());
					posX = x + c.getWidth();

					break x;

				}

			}
		}

		// now read the rest of the characters

		int lastPosX = 0;

		for (; posX < Game.VIEWPORT.width - 10; posX++) {
			y: for (int y = 0; y < height; y++) {

				c: for (Character c : optionCharacters) {

					if (y + c.getHeight() >= height) {
						continue;
					}

					if (posX + c.getWidth() >= Game.VIEWPORT.width - 10) {
						continue;
					}

					for (int j = 0; j < c.fontPointsX.length; j++) {
						if (!ocrImage[posX + c.fontPointsX[j]][y
								+ c.fontPointsY[j]]) {
							continue c;
						}
					}

					for (int j = 0; j < c.shadowPointsX.length; j++) {
						if (ocrImage[posX + c.shadowPointsX[j]][y
								+ c.shadowPointsY[j]]) {
							continue c;
						}
					}

					if (lastPosX != 0 && posX - lastPosX > 5) {
						builder.append(' ');
					}

					builder.append(c.getCharacter());
					posX += c.getWidth() - 1;
					lastPosX = posX + 1;

					break y;
				}
			}
		}

		return builder.toString();
	}

	public static int getDistanceSquare(int c1, int c2) {
		int rd = ((c1 >> 16) & 0xFF) - ((c2 >> 16) & 0xFF);
		int gd = ((c1 >> 8) & 0xFF) - ((c2 >> 8) & 0xFF);
		int bd = (c1 & 0xFF) - (c2 & 0xFF);
		return rd * rd + gd * gd + bd * bd;
	}

	/**
	 * This method cleans input image by replacing all non black pixels with
	 * white pixels
	 * 
	 * @param image
	 *            - input image that will be cleaned
	 * @return - cleaned input image as BufferedImage
	 */
	public static BufferedImage blackAndWhiteCleaning(BufferedImage image) {
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				if (image.getRGB(i, j) != -16777216) {
					image.setRGB(i, j, -1);
				}
			}
		}
		return image;
	}

	/**
	 * This method cleans input image by replacing all pixels with RGB values
	 * from -4473925 (gray) to -1 (white) with white pixels and from -4473925
	 * (gray) to -16777216 (black) with black pixels
	 * 
	 * @param image
	 *            - input image that will be cleaned
	 * @return - cleaned input image as BufferedImage
	 */
	public static BufferedImage blackAndGrayCleaning(BufferedImage image) {
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				if (image.getRGB(i, j) > -4473925) {
					image.setRGB(i, j, -1);
				} else {
					image.setRGB(i, j, -16777216);
				}
			}
		}
		return image;
	}

	/**
	 * This method cleans input image by replacing all pixels with RGB values
	 * from -3092272 (light gray) to -1 (white) with white pixels and from
	 * -3092272 (light gray) to -16777216 (black) with black pixels
	 * 
	 * @param image
	 *            - input image that will be cleaned
	 * @return - cleaned input image as BufferedImage
	 */
	public static BufferedImage blackAndLightGrayCleaning(BufferedImage image) {
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				if (image.getRGB(i, j) > -3092272) {
					image.setRGB(i, j, -1);
				} else {
					image.setRGB(i, j, -16777216);
				}
			}
		}
		return image;
	}

	/**
	 * This method cleans input image by replacing all pixels with RGB values
	 * from RGBcolor input (the input color) to -1 (white) with white pixels and
	 * from RGBcolor input (the input color) to -16777216 (black) with black
	 * pixels
	 * 
	 * @param image
	 *            - input image that will be cleaned
	 * @param RGBcolor
	 *            - input RGB value of wanted color as reference for celaning
	 * @return - cleaned input image as BufferedImage
	 */
	public static BufferedImage colorCleaning(BufferedImage image, int RGBcolor) {
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				if (image.getRGB(i, j) == RGBcolor) {
					image.setRGB(i, j, -16777216);
				} else {
					image.setRGB(i, j, -1);
				}
			}
		}
		return image;
	}

	/**
	 * This method loads the input Image and returns the cleaned version
	 * 
	 * @param f
	 *            - input file that will be loaded as image
	 * @return - return cleaned loaded image as BufferedImage
	 * @throws IOException
	 *             - if error occurs during loading
	 */
	public static BufferedImage loadAndCleanImage(File f) throws IOException {
		BufferedImage image = ImageIO.read(f);
		return blackAndLightGrayCleaning(image);

	}

	/**
	 * Loads image from the file.
	 * 
	 * @param file
	 *            image file
	 * @return loaded image
	 * @throws IOException
	 */
	public static BufferedImage loadImage(File file) throws IOException {
        return ImageIO.read(file);
	}

	/**
	 * This method reads the image pixels until it reads the first black pixel
	 * by height and then returns that value
	 * 
	 * @param Img
	 *            - input image that will be read
	 * @return - returns the value of height when conditions are true
	 */
	private static int trimLockup(BufferedImage Img) {

		for (int j = 0; j < Img.getHeight(); j++) {
			for (int i = 0; i < Img.getWidth(); i++) {
				if (Img.getRGB(i, j) == -16777216) {
					return j;
				}
			}
		}
		return 0;
	}

	/**
	 * This method reads the input image from the input from start pixel height
	 * (y1) until it reads the first next row where all pixel are white by
	 * height and return that value
	 * 
	 * @param Img
	 *            - input image that will be read
	 * @param y1
	 *            - input start height pixel of image
	 * @return - returns the value of height when conditions are true
	 */
	private static int trimLockdown(BufferedImage Img, int y1) {

		for (int j = y1 + 1; j < Img.getHeight(); j++) {
			int counterWhite = 0;
			for (int i = 0; i < Img.getWidth(); i++) {
				if (Img.getRGB(i, j) == -1) {
					counterWhite++;
				}
			}
			if (counterWhite == Img.getWidth()) {
				// this is a check for dots over the letters i and j
				// so they wont be misread as dots
				if (j > (Img.getHeight() / 2)) {
					return j;
				}
			}
			if (j == Img.getHeight() - 1) {
				return j + 1;
			}
		}
		return 0;
	}

	/**
	 * This method trims the input image and returns it as a BufferedImage
	 * 
	 * @param imageToTrim
	 *            input image that will be trimed
	 * @return return trimed input image as BufferedImage
	 */
	public static BufferedImage trimImage(BufferedImage imageToTrim) {

		int y1 = trimLockup(imageToTrim);
		int y2 = trimLockdown(imageToTrim, y1);
		int x1 = 0;
		int x2 = imageToTrim.getWidth();
		return imageToTrim.getSubimage(x1, y1, x2 - x1, y2 - y1);
	}

	/**
	 * Resize image to specified dimensions
	 * 
	 * @param image
	 *            image to resize
	 * @param width
	 *            new image width
	 * @param height
	 *            new image height
	 * @return resized image
	 */
	public static BufferedImage resizeImage(BufferedImage image, int width,
			int height) {
		BufferedImage resizedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	/**
	 * Crops (returns subimage) of specified input image at specified points.
	 * 
	 * @param image
	 *            image to crop
	 * @param x1
	 *            top left x coordinate
	 * @param y1
	 *            top left y coordinate
	 * @param x2
	 *            bottom right x coordinate
	 * @param y2
	 *            bottom right y coordinate
	 * 
	 * @return image croped at specified points
	 */
	public static BufferedImage cropImage(BufferedImage image, int x1, int y1,
			int x2, int y2) {
		return image.getSubimage(x1, y1, x2 - x1, y2 - y1);
	}

	/**
	 * Creates and returns image from the given text.
	 * 
	 * @param text
	 *            input text
	 * @param font
	 *            text font
	 * @return image with input text
	 */
	public static BufferedImage createImageFromText(String text,
			java.awt.Font font) {
		// You may want to change these setting, or make them parameters
		boolean isAntiAliased = true;
		boolean usesFractionalMetrics = false;
		FontRenderContext frc = new FontRenderContext(null, isAntiAliased,
				usesFractionalMetrics);
		TextLayout layout = new TextLayout(text, font, frc);
		Rectangle2D bounds = layout.getBounds();
		int w = (int) Math.ceil(bounds.getWidth());
		int h = (int) Math.ceil(bounds.getHeight()) + 2;
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB); // for example;
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		g.setFont(font);
		Object antiAliased = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAliased);
		Object fractionalMetrics = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				fractionalMetrics);
		g.drawString(text, (float) -bounds.getX(), (float) -bounds.getY());
		g.dispose();

		return image;
	}
}
