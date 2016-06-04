package com.jumbo.core;

import java.awt.image.BufferedImage;

import com.jumbo.components.FloatRectangle;
import com.jumbo.components.JumboColor;
import com.jumbo.components.LambdaInteger;
import com.jumbo.tools.loaders.JumboImageHandler;

public class JumboTexture implements java.io.Serializable, java.lang.Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JumboTextureBinder b = new JumboTextureBinder();
	private final static LambdaInteger previousid = new LambdaInteger(-1);

	public static final short FADE_WIDTH = 100;

	static {
		solidcolor = new JumboTexture(new int[] { JumboColor.WHITE.toByte() }, 1, 1);
		final int[] pixels = new int[(FADE_WIDTH * 2)];
		for (int i = 0; i < FADE_WIDTH; i++) {
			pixels[i] = new JumboColor(1.0f, 1.0f, 1.0f, i / (float) FADE_WIDTH).toByte();
		}
		for (int i = 0; i < FADE_WIDTH; i++) {
			pixels[i + FADE_WIDTH] = new JumboColor(1.0f, 1.0f, 1.0f, 1.0f - ((i) / (float) FADE_WIDTH)).toByte();
		}
		fade = new JumboTexture(pixels, FADE_WIDTH * 2, 1);
	}

	public static JumboTextureBinder getBinder() {
		return b;
	}

	public static void setBinder(JumboTextureBinder t) {
		b = t;
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
		result = prime * result + ID;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + rot;
		result = prime * result + ((textureCoords == null) ? 0 : textureCoords.hashCode());
		return result;
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
		JumboTexture other = (JumboTexture) obj;
		if (ID != other.ID) {
			return false;
		}
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (rot != other.rot) {
			return false;
		}
		if (textureCoords == null) {
			if (other.textureCoords != null) {
				return false;
			}
		} else if (!textureCoords.equals(other.textureCoords)) {
			return false;
		}
		return true;
	}

	@Override
	public Object clone() {
		final JumboTexture t = new JumboTexture();
		t.setID(ID);
		final FloatRectangle coords = getTextureCoords();
		t.setTextureCoords(new FloatRectangle(coords.x, coords.y, coords.width, coords.height));
		t.setColor(color);
		return t;
	}

	public static JumboTexture solidcolor, fade;

	private int ID = -1;

	private FloatRectangle textureCoords = new FloatRectangle(0, 0, 1, 1);
	private FloatRectangle color = new FloatRectangle(1, 1, 1, 1);

	private int width = 0, height = 0;

	protected int rot;// rotation values

	/**
	 * Returns the Entity's current rotation
	 * 
	 * @return Entity's rotation
	 */
	public int getRotation() {
		return rot;
	}

	/**
	 * Sets the Entity's current rotation
	 * 
	 * @param rot
	 *            The entity's new rotation
	 */
	public void setRotation(int rot) {
		this.rot = rot;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void fromBufferedImage(BufferedImage img) {
		unload();
		load(img);
	}

	public FloatRectangle getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(FloatRectangle textureCoords) {
		this.textureCoords = textureCoords;
	}

	public FloatRectangle getColor() {
		return color;
	}

	public void setColor(FloatRectangle color) {
		this.color = color;
	}

	public JumboTexture() {
		ID = -1;
	}

	/**
	 * Constructor that copies every value of the argument {@link JumboTexture}
	 * except for the color value.
	 * 
	 * @param t
	 *            {@link JumboTexture} to be copied
	 * 
	 */
	public JumboTexture(JumboTexture t) {
		this(t, JumboColor.WHITE);
	}

	public JumboTexture(BufferedImage img) {
		fromBufferedImage(img);
	}

	public JumboTexture(int[] array, int width, int height) {
		setData(array, width, height);
	}

	public JumboTexture(String path) {
		this(JumboImageHandler.getImage(path));
	}

	public JumboTexture(JumboTexture t, JumboColor c) {
		ID = t.getID();
		final FloatRectangle coords = t.getTextureCoords();
		textureCoords = new FloatRectangle(coords.x, coords.y, coords.width, coords.height);
		width = t.getWidth();
		height = t.getHeight();
		setColor(c);
	}

	public JumboTexture(JumboTexture t, FloatRectangle c) {
		this(t, new JumboColor(c.x, c.y, c.width, c.height));
	}

	public JumboTexture(JumboColor c) {
		this(solidcolor);
		setColor(c);
	}

	public JumboTexture(int id) {
		ID = id;
	}

	public void setData(int[] pix, int width, int height) {
		unload();
		this.width = width;
		this.height = height;
		setID(load(pix));
	}

	/**
	 * @return the data
	 */
	public int[] getData() {
		return b.getData(width, height, ID);
	}

	private int load(int[] inpixels) {
		final int[] pixels;

		pixels = inpixels;
		return b.load(width, height, pixels);
	}

	private void load(BufferedImage img) {
		int size = 0;
		int[] pixels = null;
		try {
			final BufferedImage image = img;
			width = image.getWidth();
			height = image.getHeight();
			size = (width * height);
			pixels = new int[size];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			for (int i = 0; i < size; i++) {
				final int pix = pixels[i];
				pixels[i] = ((pix & 0xff000000) >> 24) << 24 | (pix & 0xff) << 16 | ((pix & 0xff00) >> 8) << 8
						| ((pix & 0xff0000) >> 16);
				// pixels[i] = ((Alpha Value in RGB)) << 24 | (Green Value in
				// RGB) << 16 | ((Blue Value in RGB)) << 8 | ((Red Value in
				// RGB));
			}
			setData(pixels, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public void bind() {
		if (previousid.getNum() != ID) {
			b.bind(ID);
			previousid.setNum(ID);
		}
	}

	public static void unbind() {
		b.unbind();
	}

	public void unload() {
		b.unload(ID);
	}

	public void setColor(JumboColor col) {
		color.x = col.getRed() / 255.0f;
		color.y = col.getGreen() / 255.0f;
		color.width = col.getBlue() / 255.0f;
		color.height = col.getAlpha() / 255.0f;
	}
}
