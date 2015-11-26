package com.jumbo.tools.loaders;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.jumbo.tools.JumboErrorHandler;

public final class JumboImageHandler {
	private JumboImageHandler() {
	}

	public static void saveImage(BufferedImage img, String path, String format) {
		try {
			ImageIO.write(img, format, new File(path));
		} catch (Exception e) {
			JumboErrorHandler.handle(e);
		}
	}

	public static BufferedImage getImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			System.err.println("Error reading image path '" + path + "'");
			e.printStackTrace();
		}

		return image;
	}

	@Deprecated
	public static boolean exists(String path) {
		try {
			ImageIO.read(new File(path));
			return true;
		} catch (@SuppressWarnings("unused") Exception e) {
			return false;
		}
	}
}
