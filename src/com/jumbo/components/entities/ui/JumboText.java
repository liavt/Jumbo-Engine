package com.jumbo.components.entities.ui;

import java.awt.Rectangle;

import com.jumbo.components.JumboColor;
import com.jumbo.components.entities.JumboGraphicsGroup;
import com.jumbo.rendering.JumboEntity;
import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.loaders.JumboStringHandler;

public class JumboText extends JumboGraphicsObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JumboGraphicsGroup imgs = new JumboGraphicsGroup();;
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		imgs.array = JumboStringHandler.getLetters(text); 
		genText();
	}

	public JumboText(String text) {
		this(text, 0, 0);
	}

	public JumboText(String text, int size) {
		this(text, 0, 0, size);
	}

	public JumboText(String text2, int i, int j, int size, JumboColor c, boolean italics) {
		super(new Rectangle(i, j, 0, 0), new JumboTexture(JumboStringHandler.getBitmap()));
		this.text = text2;
		setRenderable(false);
		imgs.array = JumboStringHandler.getLetters(text2);
		setColor(c);
		setItalics(italics);
		setSize(size);
	}

	public JumboText(String text2, int i, int j, int size) {
		this(text2, i, j, size, JumboColor.WHITE, false);
	}

	public JumboText(String text, int x, int y) {
		this(text, x, y, JumboStringHandler.getSize());
	}

	public JumboText(String text, int x, int y, int size, JumboColor c) {
		this(text, x, y, size, c, false);
	}

	public JumboText(String text, int x, int y, JumboColor c) {
		this(text, x, y, JumboStringHandler.getSize(), c);
	}

	public JumboText(String text, JumboColor c) {
		this(text, 0, 0, JumboStringHandler.getSize(), c, false);
	}

	public JumboText() {
		this("");
	}

	public void setSize(int size) {
		for (JumboEntity e : imgs.array) {
			final JumboLetter l = (JumboLetter) e;
			l.setSize(size);
		}
		genText();
	}

	public void setItalics(boolean b) {
		for (JumboEntity e : imgs.array) {
			final JumboLetter l = (JumboLetter) e;
			l.setItalics(b);
		}
	}

	public void setColor(JumboColor c) {
		for (JumboEntity e : imgs.array) {
			final JumboLetter l = (JumboLetter) e;
			l.setColor(c);
		}
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
		if (imgs.array.size() > 0) {
			final JumboGraphicsGroup temparray = new JumboGraphicsGroup();
			int xoffset = 0, offsetnum = 0, base = JumboStringHandler.getBase();
			final Rectangle bounds = getBounds();
			bounds.height = imgs.array.get(0).getBounds().height;
			int tempoffset = 0, outwidth = 0;
			for (int i = 0; i < imgs.array.size(); i++) {
				final JumboLetter img = (JumboLetter) imgs.array.get(i);
				final Rectangle imgbounds = img.getBounds();
				final float mod = img.getSize() / (float) JumboStringHandler.getSize();
				final int width = (int) (imgbounds.width * mod), height = (int) (imgbounds.height * mod);
				if (img.getId() == 10) {
					offsetnum++;
					tempoffset = 0;
					xoffset = 0;
				} else if (width > 0) {
					img.setBounds(new Rectangle((xoffset), ((base - height) - (offsetnum * base)), (width), (height)));
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
		} else {
			imgs.array.clear();
		}
	}
}
