package com.jumbo.components;

import java.awt.Color;
import java.io.Serializable;

public class JumboColor implements java.lang.Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private short r = 255, g = 255, b = 255, a = 255;

	public static final JumboColor RED = new JumboColor(255, 0, 0), GREEN = new JumboColor(0, 255, 0),
			DARK_BLUE = new JumboColor(0, 0, 255), CYAN = new JumboColor(0, 255, 255),
			LIGHT_BLUE = new JumboColor(50, 200, 255), DARK_RED = new JumboColor(150, 0, 0),
			DARK_GREEN = new JumboColor(0, 150, 0), PURPLE = new JumboColor(100, 0, 255),
			MAGENTA = new JumboColor(255, 0, 255), WHITE = new JumboColor(255, 255, 255),
			BLACK = new JumboColor(0, 0, 0), YELLOW = new JumboColor(255, 255, 0),
			DARK_GREY = new JumboColor(100, 100, 100), LIGHT_GREY = new JumboColor(175, 175, 175),
			DARK_ORANGE = new JumboColor(255, 100, 0), LIGHT_ORANGE = new JumboColor(255, 150, 0);

	public JumboColor darker() {
		return new JumboColor((short) (r * 0.9f), (short) (g * 0.9f), (short) (b * 0.9f));
	}

	public JumboColor brighter() {
		return new JumboColor((short) (r * 1.1f), (short) (g * 1.1f), (short) (b * 1.1f));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JumboColor [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}

	public int getRGB() {
		return new Color(r, g, b, a).getRGB();
	}

	public String getHexcode() {
		return String.format("#%02x%02x%02x", r, g, b);
	}

	@Override
	public Object clone() {
		final short r = this.r, g = this.g, b = this.b, a = this.a;
		return new JumboColor(r, g, b, a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JumboColor other = (JumboColor) obj;
		if (a != other.a) {
			return false;
		}
		if (b != other.b) {
			return false;
		}
		if (g != other.g) {
			return false;
		}
		if (r != other.r) {
			return false;
		}
		return true;
	}

	public static JumboColor fromHex(String hex) {
		if (hex.startsWith("#")) {
			return fromHex(Integer.parseInt(hex.substring(1), 16));
		} else if (hex.startsWith("0x")) {
			return fromHex(Integer.parseInt(hex.substring(2), 16));
		} else if (hex.startsWith("#")) {
			return fromHex(Integer.parseInt(hex.substring(1), 16));
		}
		return fromHex(Integer.parseInt(hex, 16));
	}

	public static JumboColor fromHex(int hex) {
		final int r = (hex & 0xFF0000) >> 16;
		final int g = (hex & 0xFF00) >> 8;
		final int b = (hex & 0xFF);
		return new JumboColor(r, g, b);
	}

	public JumboColor(short r, short g, short b, short a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public JumboColor(short r, short g, short b) {
		this(r, g, b, 255);
	}

	public JumboColor(float r, float g, float b, float a) {
		this((short) (r * 255.0f), (short) (g * 255.0f), (short) (b * 255.0f), (short) (a * 255.0f));
	}

	public JumboColor(float r, float g, float b) {
		this(r, g, b, 1);
	}

	public JumboColor() {
		this(255, 255, 255, 255);
	}

	public JumboColor(JumboColor c) {
		this(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public JumboColor(short[] color) {
		this(color[0], color[1], color[2], color[3]);
	}

	/**
	 * @return the r
	 */
	public short getRed() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setRed(short r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public short getGreen() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setGreen(short g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public short getBlue() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setBlue(short b) {
		this.b = b;
	}

	/**
	 * @return the a
	 */
	public short getAlpha() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setAlpha(short a) {
		this.a = a;
	}

	public int toByte() {
		return ((a)) << 24 | (g) << 16 | ((b)) << 8 | ((r));
	}

	public float getAlphaAsFloat() {
		return a / 255.0f;
	}

	public float getRedAsFloat() {
		return r / 255.0f;
	}

	public float getGreenAsFloat() {
		return g / 255.0f;
	}

	public float getBlueAsFloat() {
		return b / 255.0f;
	}

	public void setAlpha(float a) {
		this.a = (short) (a * 255.0f);
	}

	public void setRed(float r) {
		this.r = (short) (r * 255.0f);
	}

	public void setGreen(float g) {
		this.g = (short) (g * 255.0f);
	}

	public void setBlue(float b) {
		this.b = (short) (b * 255.0f);
	}
}
