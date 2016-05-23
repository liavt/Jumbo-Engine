package com.jumbo.entities.graphics.ui;

import java.awt.Dimension;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.core.JumboTexture;

public class JumboUIHandler {
	private static int[] passivecolor = new int[] { 50, 50, 50, 255 }, hovercolor = new int[] { 0, 100, 255, 255 };
	private static int defaultcompression = 3;

	private static JumboUICreator creator = new JumboUICreator();

	/**
	 * @return the passivecolor
	 */
	public static int[] getPassivecolor() {
		return passivecolor;
	}

	/**
	 * @param passivecolor
	 *            the passivecolor to set
	 */
	public static void setPassivecolor(int[] passivecolor) {
		JumboUIHandler.passivecolor = passivecolor;
	}

	/**
	 * @return the hovercolor
	 */
	public static int[] getHovercolor() {
		return hovercolor;
	}

	/**
	 * @param hovercolor
	 *            the hovercolor to set
	 */
	public static void setHovercolor(int[] hovercolor) {
		JumboUIHandler.hovercolor = hovercolor;
	}

	/**
	 * @return the defaultcompression
	 */
	public static int getDefaultcompression() {
		return defaultcompression;
	}

	/**
	 * @param defaultcompression
	 *            the defaultcompression to set
	 */
	public static void setDefaultcompression(int defaultcompression) {
		JumboUIHandler.defaultcompression = defaultcompression;
	}

	/**
	 * @return the creator
	 */
	public static JumboUICreator getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public static void setCreator(JumboUICreator creator) {
		JumboUIHandler.creator = creator;
	}

	private static class BoxData {
		public BoxData() {
		}

		int id = 0, cratio = getDefaultcompression();
		Dimension d;
		JumboColor c;
	}

	private static ArrayList<BoxData> boxes = new ArrayList<>();

	public static JumboTexture create(int width, int height) {
		return create(width, height, passivecolor);
	}

	public static JumboTexture create(int width, int height, int[] pix) {
		return create(width, height, pix, defaultcompression);
	}

	public static JumboTexture create(int width, int height, int compressionratio) {
		return create(width, height, passivecolor, compressionratio);
	}

	public static JumboTexture create(int width, int height, int[] c, int compressionratio) {
		for (BoxData d : boxes) {
			if (d.d.width == width && d.d.height == height
					&& d.c.toByte() == new JumboColor(c[0], c[1], c[2], c[3]).toByte()
					&& d.cratio == compressionratio) {
				return new JumboTexture(d.id);
			}
		}

		final JumboTexture t = creator.create(width, height, c, compressionratio);

		final BoxData d = new BoxData();
		d.d = new Dimension(width, height);
		d.id = t.getID();
		d.c = new JumboColor(c[0], c[1], c[2], c[3]);
		d.cratio = compressionratio;
		boxes.add(d);
		return t;
	}
}
