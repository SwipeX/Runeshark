package api.methods;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Ramy
 *
 */

public class CannyEdge {

	
	private final static float GAUSSIAN_CUT_OFF = 0.005f;
	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

	
	private int height;
	private int width;
	private int picsize;
	private int[] data;
	private int[] magnitude;
	private BufferedImage sourceImage;
	private BufferedImage edgesImage;
	
	private float gaussianKernelRadius;
	private float lowThreshold;
	private float highThreshold;
	private int gaussianKernelWidth;
	private boolean contrastNormalized;

	private float[] xConv;
	private float[] yConv;
	private float[] xGradient;
	private float[] yGradient;
	
	
	/**
	 * Constructs a new detector with default parameters.
	 */
	
	public CannyEdge() {
		lowThreshold = 2.5f;
		highThreshold = 7.5f;
		gaussianKernelRadius = 2f;
		gaussianKernelWidth = 16;
		contrastNormalized = false;
	}
	
	/**
	 * Constructs a new detector with BufferedImage
	 */
	
	public CannyEdge(BufferedImage source) {
		sourceImage = source;
		lowThreshold = 2.5f;
		highThreshold = 7.5f;
		gaussianKernelRadius = 2f;
		gaussianKernelWidth = 16;
		contrastNormalized = false;
	}
	
	/**
	 * The image that provides the luminance data used by this detector to
	 * generate edges.
	 * 
	 * @return the source image, or null
	 */
	
	public BufferedImage getSourceImage() {
		return sourceImage;
	}
	
	/**
	 * Specifies the image that will provide the luminance data in which edges
	 * will be detected. A source image must be set before the process method
	 * is called.
	 *  
	 * @param image a source of luminance data
	 */
	
	public void setSourceImage(BufferedImage image) {
		sourceImage = image;
	}

	/**
	 * Obtains an image containing the edges detected during the last call to
	 * the process method. The buffered image is an opaque image of type
	 * BufferedImage.TYPE_INT_ARGB in which edge pixels are white and all other
	 * pixels are black.
	 * 
	 * @return an image containing the detected edges, or null if the process
	 * method has not yet been called.
	 */
	
	public BufferedImage getEdgesImage() {
		return edgesImage;
	}
 
	/**
	 * Sets the edges image. Calling this method will not change the operation
	 * of the edge detector in any way. It is intended to provide a means by
	 * which the memory referenced by the detector object may be reduced.
	 * 
	 * @param edgesImage expected (though not required) to be null
	 */
	
	public void setEdgesImage(BufferedImage edgesImage) {
		this.edgesImage = edgesImage;
	}

	/**
	 * The low threshold for hysteresis. The default value is 2.5.
	 * 
	 * @return the low hysteresis threshold
	 */
	
	public float getLowThreshold() {
		return lowThreshold;
	}
	
	/**
	 * Sets the low threshold for hysteresis. Suitable values for this parameter
	 * must be determined experimentally for each application. It is nonsensical
	 * (though not prohibited) for this value to exceed the high threshold value.
	 * 
	 * @param threshold a low hysteresis threshold
	 */
	
	public void setLowThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		lowThreshold = threshold;
	}
 
	/**
	 * The high threshold for hysteresis. The default value is 7.5.
	 * 
	 * @return the high hysteresis threshold
	 */
	
	public float getHighThreshold() {
		return highThreshold;
	}
	
	/**
	 * Sets the high threshold for hysteresis. Suitable values for this
	 * parameter must be determined experimentally for each application. It is
	 * nonsensical (though not prohibited) for this value to be less than the
	 * low threshold value.
	 * 
	 * @param threshold a high hysteresis threshold
	 */
	
	public void setHighThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		highThreshold = threshold;
	}

	/**
	 * The number of pixels across which the Gaussian kernel is applied.
	 * The default value is 16.
	 * 
	 * @return the radius of the convolution operation in pixels
	 */
	
	public int getGaussianKernelWidth() {
		return gaussianKernelWidth;
	}
	
	/**
	 * The number of pixels across which the Gaussian kernel is applied.
	 * This implementation will reduce the radius if the contribution of pixel
	 * values is deemed negligable, so this is actually a maximum radius.
	 * 
	 * @param gaussianKernelWidth a radius for the convolution operation in
	 * pixels, at least 2.
	 */
	
	public void setGaussianKernelWidth(int gaussianKernelWidth) {
		if (gaussianKernelWidth < 2) throw new IllegalArgumentException();
		this.gaussianKernelWidth = gaussianKernelWidth;
	}

	/**
	 * The radius of the Gaussian convolution kernel used to smooth the source
	 * image prior to gradient calculation. The default value is 16.
	 * 
	 * @return the Gaussian kernel radius in pixels
	 */
	
	public float getGaussianKernelRadius() {
		return gaussianKernelRadius;
	}
	
	/**
	 * Sets the radius of the Gaussian convolution kernel used to smooth the
	 * source image prior to gradient calculation.
	 * 
	 * @return a Gaussian kernel radius in pixels, must exceed 0.1f.
	 */
	
	public void setGaussianKernelRadius(float gaussianKernelRadius) {
		if (gaussianKernelRadius < 0.1f) throw new IllegalArgumentException();
		this.gaussianKernelRadius = gaussianKernelRadius;
	}
	
	/**
	 * Whether the luminance data extracted from the source image is normalized
	 * by linearizing its histogram prior to edge extraction. The default value
	 * is false.
	 * 
	 * @return whether the contrast is normalized
	 */
	
	public boolean isContrastNormalized() {
		return contrastNormalized;
	}
	
	/**
	 * Sets whether the contrast is normalized
	 * @param contrastNormalized true if the contrast should be normalized,
	 * false otherwise
	 */
	
	public void setContrastNormalized(boolean contrastNormalized) {
		this.contrastNormalized = contrastNormalized;
	}
	
	
	public void process() {
		width = sourceImage.getWidth();
		height = sourceImage.getHeight();
		picsize = width * height;
		initArrays();
		readLuminance();
		if (contrastNormalized) normalizeContrast();
		computeGradients(gaussianKernelRadius, gaussianKernelWidth);
		int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		int high = Math.round( highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		thresholdEdges();
		writeEdges(data);
	}
 
	
	private void initArrays() {
		if (data == null || picsize != data.length) {
			data = new int[picsize];
			magnitude = new int[picsize];

			xConv = new float[picsize];
			yConv = new float[picsize];
			xGradient = new float[picsize];
			yGradient = new float[picsize];
		}
	}
	
	private void computeGradients(float kernelRadius, int kernelWidth) {
		
		float kernel[] = new float[kernelWidth];
		float diffKernel[] = new float[kernelWidth];
		int kwidth;
		for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
			float g1 = gaussian(kwidth, kernelRadius);
			if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2) break;
			float g2 = gaussian(kwidth - 0.5f, kernelRadius);
			float g3 = gaussian(kwidth + 0.5f, kernelRadius);
			kernel[kwidth] = (g1 + g2 + g3) / 3f / (2f * (float) Math.PI * kernelRadius * kernelRadius);
			diffKernel[kwidth] = g3 - g2;
		}

		int initX = kwidth - 1;
		int maxX = width - (kwidth - 1);
		int initY = width * (kwidth - 1);
		int maxY = width * (height - (kwidth - 1));
		
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				float sumX = data[index] * kernel[0];
				float sumY = sumX;
				int xOffset = 1;
				int yOffset = width;
				for(; xOffset < kwidth ;) {
					sumY += kernel[xOffset] * (data[index - yOffset] + data[index + yOffset]);
					sumX += kernel[xOffset] * (data[index - xOffset] + data[index + xOffset]);
					yOffset += width;
					xOffset++;
				}
				
				yConv[index] = sumY;
				xConv[index] = sumX;
			}
 
		}
 
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0f;
				int index = x + y;
				for (int i = 1; i < kwidth; i++)
					sum += diffKernel[i] * (yConv[index - i] - yConv[index + i]);
 
				xGradient[index] = sum;
			}
 
		}

		for (int x = kwidth; x < width - kwidth; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0.0f;
				int index = x + y;
				int yOffset = width;
				for (int i = 1; i < kwidth; i++) {
					sum += diffKernel[i] * (xConv[index - yOffset] - xConv[index + yOffset]);
					yOffset += width;
				}
 
				yGradient[index] = sum;
			}
 
		}
 
		initX = kwidth;
		maxX = width - kwidth;
		initY = width * kwidth;
		maxY = width * (height - kwidth);
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				int indexN = index - width;
				int indexS = index + width;
				int indexW = index - 1;
				int indexE = index + 1;
				int indexNW = indexN - 1;
				int indexNE = indexN + 1;
				int indexSW = indexS - 1;
				int indexSE = indexS + 1;
				
				float xGrad = xGradient[index];
				float yGrad = yGradient[index];
				float gradMag = hypot(xGrad, yGrad);

				float nMag = hypot(xGradient[indexN], yGradient[indexN]);
				float sMag = hypot(xGradient[indexS], yGradient[indexS]);
				float wMag = hypot(xGradient[indexW], yGradient[indexW]);
				float eMag = hypot(xGradient[indexE], yGradient[indexE]);
				float neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
				float seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
				float swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
				float nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
				float tmp;

				if (xGrad * yGrad <= (float) 0 /*(1)*/
					? Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) /*(3)*/
							&& tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag) /*(4)*/
					: Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) /*(3)*/
							&& tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag) /*(4)*/
					) {
					magnitude[index] = gradMag >= MAGNITUDE_LIMIT ? MAGNITUDE_MAX : (int) (MAGNITUDE_SCALE * gradMag);
				} else {
					magnitude[index] = 0;
				}
			}
		}
	}
 

	private float hypot(float x, float y) {
		return (float) Math.hypot(x, y);
	}
 
	private float gaussian(float x, float sigma) {
		return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
	}
 
	private void performHysteresis(int low, int high) {
		Arrays.fill(data, 0);
 
		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (data[offset] == 0 && magnitude[offset] >= high) {
					follow(x, y, offset, low);
				}
				offset++;
			}
		}
 	}
 
	private void follow(int x1, int y1, int i1, int threshold) {
		int x0 = x1 == 0 ? x1 : x1 - 1;
		int x2 = x1 == width - 1 ? x1 : x1 + 1;
		int y0 = y1 == 0 ? y1 : y1 - 1;
		int y2 = y1 == height -1 ? y1 : y1 + 1;
		
		data[i1] = magnitude[i1];
		for (int x = x0; x <= x2; x++) {
			for (int y = y0; y <= y2; y++) {
				int i2 = x + y * width;
				if ((y != y1 || x != x1)
					&& data[i2] == 0 
					&& magnitude[i2] >= threshold) {
					follow(x, y, i2, threshold);
					return;
				}
			}
		}
	}

	private void thresholdEdges() {
		for (int i = 0; i < picsize; i++) {
			data[i] = data[i] > 0 ? -1 : 0xff000000;
		}
	}
	
	private int luminance(float r, float g, float b) {
		return Math.round(0.299f * r + 0.587f * g + 0.114f * b);
	}
	
	private void readLuminance() {
		int type = sourceImage.getType();
		if (type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB) {
			int[] pixels = (int[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				int p = pixels[i];
				int r = (p & 0xff0000) >> 16;
				int g = (p & 0xff00) >> 8;
				int b = p & 0xff;
				data[i] = luminance(r, g, b);
			}
		} else if (type == BufferedImage.TYPE_BYTE_GRAY) {
			byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xff);
			}
		} else if (type == BufferedImage.TYPE_USHORT_GRAY) {
			short[] pixels = (short[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xffff) / 256;
			}
		} else if (type == BufferedImage.TYPE_3BYTE_BGR) {
            byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            int offset = 0;
            for (int i = 0; i < picsize; i++) {
                int b = pixels[offset++] & 0xff;
                int g = pixels[offset++] & 0xff;
                int r = pixels[offset++] & 0xff;
                data[i] = luminance(r, g, b);
            }
        } else {
			throw new IllegalArgumentException("Unsupported image type: " + type);
		}
	}
 
	private void normalizeContrast() {
		int[] histogram = new int[256];
		for (int i = 0; i < data.length; i++) {
			histogram[data[i]]++;
		}
		int[] remap = new int[256];
		int sum = 0;
		int j = 0;
		for (int i = 0; i < histogram.length; i++) {
			sum += histogram[i];
			int target = sum*255/picsize;
			for (int k = j+1; k <=target; k++) {
				remap[k] = i;
			}
			j = target;
		}
		
		for (int i = 0; i < data.length; i++) {
			data[i] = remap[data[i]];
		}
	}
	
	private void writeEdges(int pixels[]) {
		if (edgesImage == null) {
			edgesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, pixels);
	}
 
}