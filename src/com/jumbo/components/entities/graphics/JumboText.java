package com.jumbo.components.entities.graphics;

import java.awt.Rectangle;
import java.util.ArrayList;

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
		generateText();
	}

	public JumboText(String text) {
		this(text, 0, 0);
	}

	public JumboText(String text2, int i, int j) {
		super(new Rectangle(i, j, 0, 0), new JumboTexture(JumboStringHandler.getBitmap()));
		this.text = text2;
		setRenderable(false);
		generateText();
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

	public ArrayList<JumboEntity> getLetters() {
		return imgs.array;
	}

	public void setLetters(ArrayList<JumboEntity> letters) {
		imgs.array = letters;
	}

	public void generateText() {
		imgs.array = JumboStringHandler.getLetters(text);
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
