package com.jumbo.components.entities.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import com.jumbo.components.FloatRectangle;
import com.jumbo.components.entities.JumboGraphicsGroup;
import com.jumbo.rendering.JumboEntity;
import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.loaders.StringHandler;

public class JumboText extends JumboGraphicsObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JumboGraphicsGroup imgs = new JumboGraphicsGroup();;
	private Color color = Color.white;
	private int size = StringHandler.getSize();
	private String text;
	private boolean italics = false;

	public boolean isItalics() {
		return italics;
	}

	public void setItalics(boolean italics) {
		this.italics = italics;
		if (italics) {
			for (JumboEntity entity : imgs.array) {
				JumboGraphicsObject e = (JumboGraphicsObject) entity;
				e.setVectorTopRight(new Dimension(4, 0));
				e.setVectorTopLeft(new Dimension(4, 0));
			}
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		genText();
	}

	public int getSize() {
		return size;
	}

	public JumboText(String text, Color c) {
		this(text, 0, 0, c);
	}

	public JumboText(String text, int size) {
		this(text, 0, 0, size);
	}

	public JumboText(String text, int size, Color c) {
		this(text, 0, 0, c, size);
	}

	public JumboText(String text) {
		this(text, 0, 0);
	}

	;

	public JumboText(String text2, int i, int j) {
		this(text2, i, j, 32);
	}

	public JumboText(String text2, int i, int j, int size) {
		this(text2, i, j, Color.WHITE, size);
	}

	public JumboText(String text2, int i, int j, Color c) {
		this(text2, i, j, c, 32);
	}

	public JumboText(String text2, int i, int j, Color c, int size) {
		super(new Rectangle(i, j, 0, 0), new JumboTexture(StringHandler.getBitmap()));
		this.text = text2;
		setRenderable(false);
		this.color = c;
		this.size = size;
		genText();
	}

	public JumboText() {
		this("");
	}

	@Override
	public void tick() {
		if (!imgs.getParents().contains(this)) {
			imgs.addParent(this);
		}
		imgs.tick();
		if (active) {
			if (customaction != null) {
				customaction.action();
			}
		}
	}

	@Override
	public void setRenderable(boolean render) {
		for (JumboEntity e : imgs.array) {
			((JumboGraphicsObject) e).setRenderable(render);
		}
	}

	private void genText() {
		final JumboImage[] tempimg = StringHandler.getLetters(text);
		if (tempimg.length > 0) {
			final JumboGraphicsGroup temparray = new JumboGraphicsGroup();
			int xoffset = 0, offsetnum = 0, base = StringHandler.getBase();
			final Rectangle bounds = getBounds();
			bounds.height = tempimg[0].getBounds().height;
			int tempoffset = 0, outwidth = 0;
			for (int i = 0; i < text.length(); i++) {
				JumboImage img = tempimg[i];
				Rectangle imgbounds = img.getBounds();
				int width = imgbounds.width, height = imgbounds.height;
				if (width == -10) {
					offsetnum++;
					tempoffset = 0;
					xoffset = 0;
				} else if (width > 0) {
					img.setBounds(new Rectangle((xoffset), (base - height) - (offsetnum * base), (width), (height)));
					tempoffset += width;
					xoffset += width;
					if (tempoffset > outwidth) {
						outwidth = tempoffset;
					}
					img.setMaintainingPosition(true);
					temparray.array.add(img);
				}
			}
			bounds.height -= offsetnum * base;
			temparray.setBounds(new Rectangle(0, 0, 1, 1));
			bounds.width = outwidth;
			imgs = temparray;
			setColor(color);
			setSize(size);
			setItalics(italics);
		} else {
			imgs.array.clear();
		}
	}

	public void setColor(Color c) {
		this.color = c;
		for (JumboEntity i : imgs.array) {
			((JumboGraphicsObject) i).getTexture().setColor(new FloatRectangle(c.getRed() / 255.0f,
					c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f));
			;
		}
	}

	public Color getColor() {
		return color;
	}

	public void setSize(int size) {
		this.size = size;
		float mod = size / (float) StringHandler.getSize();
		getBounds().width *= mod;
		getBounds().height *= mod;
		for (JumboEntity i : imgs.array) {
			Rectangle bounds = i.getBounds();
			i.setBounds(new Rectangle((int) (bounds.x * mod), bounds.y, (int) (bounds.width * mod),
					(int) (bounds.height * mod)));
		}
	}
}
