package com.jumbo.rendering;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import com.jumbo.components.FloatRectangle;
import com.jumbo.tools.ImageUtility;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.Dice;
import com.jumbo.tools.calculations.Maths;
import com.jumbo.tools.loaders.ImageHandler;

public class JumboTexture implements java.io.Serializable, java.lang.Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		JumboTexture t = new JumboTexture();
		t.setID(ID);
		FloatRectangle coords = getTextureCoords();
		t.setTextureCoords(new FloatRectangle(coords.x, coords.y, coords.width, coords.height));
		t.setColor(color);
		return t;
	}

	public static final JumboTexture solidcolor = new JumboTexture(ImageUtility.createColoredImage(Color.white));

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

	public void replaceImg(BufferedImage img) {
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
	 * Constructor that copies every value of the argument Texture2D except for
	 * the color value.
	 * 
	 * @param t
	 *            {@link JumboTexture} to be copied
	 * 
	 */
	public JumboTexture(JumboTexture t) {
		this(t, Color.WHITE);
	}

	public JumboTexture(BufferedImage img) {
		replaceImg(img);
	}

	public JumboTexture(int[] array, int width, int height) {
		setData(array, width, height);
	}

	public JumboTexture(String path) {
		this(ImageHandler.getImage(path));
	}

	public JumboTexture(JumboTexture t, Color c) {
		ID = t.getID();
		FloatRectangle coords = t.getTextureCoords();
		textureCoords = new FloatRectangle(coords.x, coords.y, coords.width, coords.height);
		setColor(c);
	}

	public JumboTexture(JumboTexture t, FloatRectangle c) {
		this(t, new Color(c.x, c.y, c.width, c.height));
	}

	public JumboTexture(Color c) {
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

	private int load(int[] inpixels) {

		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		final int[] pixels;
		if (JumboSettings.rectangle) {
			pixels = new int[] { Maths.colorToByte(Dice.randomColor()) };
			width = 1;
			height = 1;
		} else {
			pixels = inpixels;
		}
		IntBuffer buffer = ByteBuffer.allocateDirect(pixels.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(pixels).flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
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

	void bind() {
		glBindTexture(GL_TEXTURE_2D, ID);
	}

	static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void unload() {
		GL11.glDeleteTextures(ID);
	}

	public void setColor(Color col) {
		color.x = col.getRed() / 255.0f;
		color.y = col.getGreen() / 255.0f;
		color.width = col.getBlue() / 255.0f;
		color.height = col.getAlpha() / 255.0f;
	}
}
