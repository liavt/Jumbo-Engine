package com.jumbo.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import com.jumbo.components.FloatRectangle;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.JumboAnimationFrame;

public class ImageUtility {
	private static AffineTransform tx = null;
	private static AffineTransformOp op = null;
	private static BufferedImage img2 = null;

	private ImageUtility() {
	}

	// gives img2 a tint of color
	public static BufferedImage addColor(BufferedImage img2, Color color) {
		BufferedImage tempimg = img2;
		WritableRaster data = img2.getRaster();
		BufferedImage img = img2;
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				int[] pixel = data.getPixel(x, y, new int[4]);
				if (pixel[3] > 0) {
					pixel[0] = (int) (pixel[0] * .55f) + (int) (color.getRed() * .45f);
					pixel[1] = (int) (pixel[1] * .55f) + (int) (color.getGreen() * .45f);
					pixel[2] = (int) (pixel[2] * .55f) + (int) (color.getBlue() * .45f);
					data.setPixel(x, y, pixel);
				}
			}
		}
		img = tempimg;
		img.setData(data);
		return img;
	}

	// same to addColor(), but alternates between the specified color and a
	// slightly modified version of it, giving a checkerboard look
	public static BufferedImage addCheckeredColor(BufferedImage img2, Color color) {
		float tintOpacity = 0.45f;
		boolean first = true;
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(img2, null, 0, 0);
		Raster data = img.getData();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			first = !first;
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				int[] pixel = data.getPixel(x, y, new int[4]);
				first = !first;
				if (first) {
					g2d.setColor(new Color((color.getRed() / 255f) * 0.5f, (color.getGreen() / 255f) * 0.5f,
							(color.getBlue() / 255f) * 0.5f, tintOpacity));
				} else {
					g2d.setColor(new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,
							tintOpacity));
				}
				if (pixel[3] > 0) {
					g2d.fillRect(x, y, 1, 1);
				}
			}
		}
		g2d.dispose();
		return img;
	}

	// converts all pixels with color to transparent
	public static BufferedImage removeColor(BufferedImage img2, Color color) {
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(img2, null, 0, 0);
		Raster data = img.getData();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				for (int i = 0; i < 10; i++) {
					if (img.getRGB(x, y) + i == color.getRGB() || img.getRGB(x, y) - i == color.getRGB()) {
						g2d.setBackground(new Color(0, 0, 0, 0));
						g2d.clearRect(x, y, 1, 1);
					}
				}
			}
		}
		g2d.dispose();
		return img;
	}

	// replaces all pixels that have a specific color with newcolor
	public static BufferedImage replaceColor(BufferedImage img2, Color color, Color newcolor) {
		BufferedImage img = img2;
		WritableRaster data = img.getRaster();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				float[] pix = data.getPixel(x, y, new float[4]);
				if (pix[0] == color.getRed() && pix[1] == color.getGreen() && pix[2] == color.getBlue()
						&& pix[3] == color.getAlpha()) {
					pix[0] = newcolor.getRed();
					pix[1] = newcolor.getGreen();
					pix[2] = newcolor.getBlue();
					pix[3] = newcolor.getAlpha();
				}
			}
		}
		img.setData(data);
		return img;
	}

	// gives how much (non transparent) pixels there are in the picture

	public static int countPixels(BufferedImage img2) {
		int pixels = 0;
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(img2, null, 0, 0);
		Raster data = img.getData();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				if (img.getRGB(x, y) != new Color(0, 0, 0, 0).getRGB()) {
					pixels++;
				}
			}
		}
		g2d.dispose();
		return pixels;
	}

	// returns a bufferedimage with the pixels that are different
	public static BufferedImage findDifference(BufferedImage img2, BufferedImage img1) {
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
		Graphics2D g2d = img.createGraphics();
		Raster data = img.getData();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				if ((img2.getRGB(x, y) >> 24) == 0x00 && (img1.getRGB(x, y) >> 24) != 0x00) {
					g2d.setColor(new Color(img1.getRGB(x, y)));
					g2d.drawRect(x, y, 1, 1);
				}
			}
		}
		g2d.dispose();
		return img;
	}

	// gives a 1 pixel wide black border to the img
	public static BufferedImage addBorder(BufferedImage img2) {
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(img2, null, 0, 0);
		Raster data = img.getData();
		for (int x = data.getMinX(); x < data.getWidth(); x++) {
			for (int y = data.getMinY(); y < data.getHeight(); y++) {
				g2d.setColor(Color.BLACK);
				if (x + 1 < img.getWidth() && img.getRGB(x + 1, y) != new Color(0, 0, 0, 0).getRGB()
						&& img.getRGB(x + 1, y) != g2d.getColor().getRGB()
						&& img.getRGB(x, y) == new Color(0, 0, 0, 0).getRGB()
						|| y + 1 < img.getWidth() && img.getRGB(x, y + 1) != new Color(0, 0, 0, 0).getRGB()
								&& img.getRGB(x, y + 1) != g2d.getColor().getRGB()
								&& img.getRGB(x, y) == new Color(0, 0, 0, 0).getRGB()
						|| y - 1 > 0 && img.getRGB(x, y - 1) != new Color(0, 0, 0, 0).getRGB()
								&& img.getRGB(x, y - 1) != g2d.getColor().getRGB()
								&& img.getRGB(x, y) == new Color(0, 0, 0, 0).getRGB()
						|| x - 1 > 0 && img.getRGB(x - 1, y) != new Color(0, 0, 0, 0).getRGB()
								&& img.getRGB(x - 1, y) != g2d.getColor().getRGB()
								&& img.getRGB(x, y) == new Color(0, 0, 0, 0).getRGB()) {
					g2d.fillRect(x, y, 1, 1);
				}
			}
		}
		g2d.dispose();
		return img;
	}

	public static BufferedImage rotate(BufferedImage img, int angle) {
		double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
		int w = img.getWidth(null), h = img.getHeight(null);
		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
		BufferedImage bimg = new BufferedImage(neww, newh, img.getType());
		Graphics2D g = bimg.createGraphics();
		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawRenderedImage(img, null);
		g.dispose();
		return bimg;
	}

	public static BufferedImage invertHorizontal(BufferedImage img) {
		tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img2 = op.filter(img, null);
		return img2;
	}

	public static BufferedImage invertVertical(BufferedImage img) {
		tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -img.getHeight(null));
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img2 = op.filter(img, null);
		return img2;
	}

	// converts a byte array to an image
	public static BufferedImage convertToImage(byte[] array, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		byte[] newarray = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(array, 0, newarray, 0, array.length);
		BufferedImage newimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newimage;
	}

	public static JumboAnimationFrame[] parse(BufferedImage img, int framewidth, int delay) {
		int framenum = img.getWidth() / framewidth;
		JumboAnimationFrame[] frames2 = new JumboAnimationFrame[framenum];
		JumboTexture tex = new JumboTexture(img);
		for (int i = 0; i < framenum; i++) {
			JumboTexture frame = new JumboTexture();
			frame.setID(tex.getID());
			frame.setTextureCoords(new FloatRectangle(((float) i / (float) framenum), 0,
					(float) framewidth / (float) img.getWidth(), 1));
			frames2[i] = new JumboAnimationFrame(delay, frame);
			frames2[i].getBounds().width = framewidth;
		}
		return frames2;
	}

	public static JumboAnimationFrame[] parseVertical(BufferedImage img, int frameheight, int delay) {
		int framenum = img.getHeight() / frameheight;
		JumboAnimationFrame[] frames2 = new JumboAnimationFrame[framenum];
		JumboTexture tex = new JumboTexture(img);
		for (int i = 0; i < framenum; i++) {
			JumboTexture frame = new JumboTexture();
			frame.setID(tex.getID());
			frame.setTextureCoords(new FloatRectangle(0, ((float) i / (float) framenum), 1,
					(float) frameheight / (float) img.getHeight()));
			frames2[i] = new JumboAnimationFrame(delay, frame);
			frames2[i].getBounds().height = frameheight;
		}
		return frames2;
	}

	public static JumboAnimationFrame[] parse(JumboTexture img, int framewidth, int delay) {
		int framenum = img.getWidth() / framewidth;
		JumboAnimationFrame[] frames2 = new JumboAnimationFrame[framenum];
		for (int i = 0; i < framenum; i++) {
			JumboTexture frame = new JumboTexture();
			frame.setID(img.getID());
			frame.setTextureCoords(new FloatRectangle(((float) i / (float) framenum), 0,
					(float) framewidth / (float) img.getWidth(), 1));
			frames2[i] = new JumboAnimationFrame(delay, frame);
			frames2[i].getBounds().width = framewidth;
		}
		return frames2;
	}

	public static JumboAnimationFrame[] parseVertical(JumboTexture img, int frameheight, int delay) {
		int framenum = img.getHeight() / frameheight;
		JumboAnimationFrame[] frames2 = new JumboAnimationFrame[framenum];
		JumboTexture tex = img;
		for (int i = 0; i < framenum; i++) {
			JumboTexture frame = new JumboTexture();
			frame.setID(tex.getID());
			frame.setTextureCoords(new FloatRectangle(0, ((float) i / (float) framenum), 1,
					(float) frameheight / (float) img.getHeight()));
			frames2[i] = new JumboAnimationFrame(delay, frame);
			frames2[i].getBounds().height = frameheight;
		}
		return frames2;
	}

	// returns an image with dimensions width x height that is completely the
	// color specified
	public static BufferedImage createColoredImage(Color color, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster data = img.getRaster();
		int[] pixel = new int[4];
		pixel[3] = color.getAlpha();
		pixel[0] = color.getRed();
		pixel[1] = color.getGreen();
		pixel[2] = color.getBlue();
		;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				data.setPixel(x, y, pixel);
			}
		}
		img.setData(data);
		return img;
	}

	public static BufferedImage createColoredImage(Color color) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		WritableRaster data = img.getRaster();
		int[] pixel = data.getPixel(0, 0, new int[4]);
		pixel[3] = color.getAlpha();
		pixel[0] = color.getRed();
		pixel[1] = color.getGreen();
		pixel[2] = color.getBlue();
		;
		data.setPixel(0, 0, pixel);
		img.setData(data);
		return img;
	}
}
