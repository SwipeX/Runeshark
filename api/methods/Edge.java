package api.methods;

import java.awt.image.BufferedImage;

public class Edge {

	public enum DetectMethod {
		Sobel(new int[][] { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } },
				new int[][] { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } }), Cross(
				new int[][] { { 1, 0 }, { 0, -1 } }, new int[][] { { 0, 1 },
						{ -1, 0 } }), Prewitt(new int[][] { { -1, 0, 1 },
				{ -1, 0, 1 }, { -1, 0, 1 } }, new int[][] { { 1, 1, 1 },
				{ 0, 0, 0 }, { -1, -1, -1 } });
		private int[][] X;
		private int[][] Y;

		DetectMethod(int[][] X, int[][] Y) {
			this.X = X;
			this.Y = Y;
		}

		public int[][] getX() {
			return X;
		}

		public int[][] getY() {
			return Y;
		}

		public DetectMethod getFastest() {
			return DetectMethod.Prewitt;
		}
	}

	public int lum(int r, int g, int b) {
		return (r + r + r + b + g + g + g + g) >> 3;
	}

	private int rgdToLum(int rgb) {
		int r = (rgb & 0xff0000) >> 16;
		int g = (rgb & 0xff00) >> 8;
		int b = (rgb & 0xff);
		return this.lum(r, g, b);
	}

	private int lvlToGS(int level) {
		return (level << 16) | (level << 8) | level;
	}

	private BufferedImage cloneImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null),
				image.isAlphaPremultiplied(), null);
	}

	public BufferedImage detectEdges(BufferedImage image, DetectMethod method) {
		BufferedImage ret = cloneImage(image);
		int width = image.getWidth();
		int height = image.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int level = 255;
				if ((x > 0) && (x < (width - 1)) && (y > 0)
						&& (y < (height - 1))) {
					int sumX = 0;
					int sumY = 0;
					for (int i = -1; i < (method.getX().length - 1); i++) {
						for (int j = -1; j < (method.getY().length - 1); j++) {
							sumX += this.rgdToLum(image.getRGB(x + i, y + j))
									* method.getX()[i + 1][j + 1];
							sumY += this.rgdToLum(image.getRGB(x + i, y + j))
									* method.getY()[i + 1][j + 1];
						}
					}
					level = Math.abs(sumX) + Math.abs(sumY);
					if (level < 0) {
						level = 0;
					} else if (level > 255) {
						level = 255;
					}
					level = 255 - level;
				}
				ret.setRGB(x, y, this.lvlToGS(level));
			}
		}
		return ret;
	}

}
