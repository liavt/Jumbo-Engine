package com.jumbo.entities.graphics.ui;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.jumbo.core.JumboTexture;

public class JumboUICreator {

	@SuppressWarnings("static-method")
	public JumboTexture create(int width, int height, int[] color, int compressionratio) {
		BufferedImage img = new BufferedImage(width / compressionratio, height / compressionratio,
				BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster data = img.getRaster();
		int datawidth = data.getWidth(), dataheight = data.getHeight();
		int[] pix = new int[4];
		pix[0] = 100;
		pix[1] = pix[0];
		pix[2] = pix[1];
		pix[3] = 255;
		for (int x = data.getMinX(); x < datawidth; x++) {
			for (int y = data.getMinY(); y < dataheight; y++) {
				data.setPixel(x, y, pix);
			}
		}
		pix[0] = color[0];
		pix[1] = color[1];
		pix[2] = color[2];
		pix[3] = color[3];
		for (int x = 1; x < datawidth - 1; x++) {
			for (int y = 1; y < dataheight - 1; y++) {
				data.setPixel(x, y, pix);
			}
		}
		pix[0] = 0;
		pix[1] = pix[0];
		pix[2] = pix[1];
		pix[3] = 100;
		for (int x = 2; x < datawidth - 2; x++) {
			for (int y = 2; y < dataheight - 2; y++) {
				data.setPixel(x, y, pix);
			}
		}
		img.setData(data);
		return new JumboTexture(img);
	}
}
