package com.jumbo.entities.graphics.text;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.jumbo.components.Position;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboTexture;

public class JumboTextBox extends JumboGraphicsObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected JumboText text;
	private Position offset;
	private boolean centerwidth = true, centerheight = true, topdown = true;

	public JumboTextBox(Rectangle bounds, JumboText t) {
		super(bounds, new JumboTexture());
		text = t;
		offset = t.getPosition();
		calculatePosition();
		t.setRenderable(true);
	}

	/**
	 * @return the text
	 */
	public JumboText getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(JumboText text) {
		this.text = text;
		setUpdaterequired(true);
	}

	/**
	 * @return the centerwidth
	 */
	public boolean isCenterwidth() {
		return centerwidth;
	}

	/**
	 * @param centerwidth
	 *            the centerwidth to set
	 */
	public void setCenterwidth(boolean centerwidth) {
		this.centerwidth = centerwidth;
	}

	/**
	 * @return the centerheight
	 */
	public boolean isCenterheight() {
		return centerheight;
	}

	/**
	 * @param centerheight
	 *            the centerheight to set
	 */
	public void setCenterheight(boolean centerheight) {
		this.centerheight = centerheight;
		setUpdaterequired(true);
	}

	/**
	 * @return the topdown
	 */
	public boolean isTopdown() {
		return topdown;
	}

	/**
	 * @param topdown
	 *            the topdown to set
	 */
	public void setTopdown(boolean topdown) {
		this.topdown = topdown;
	}

	@Override
	public Rectangle additionalCalculations(Rectangle bounds) {
		int x = 0, y = 0;
		final Rectangle tbounds = text.getBounds();
		// JumboConsole.log(bounds + " " + basebounds);
		if (centerwidth) {
			x = (int) ((((bounds.width / 2.0f) - (tbounds.width / 2.0f))));
		}
		if (centerheight) {
			y = (int) ((((bounds.height / 2.0f) - (tbounds.height / 2.0f))));
		}
		if (topdown) {
			y = bounds.height - y - tbounds.height;
		}
		tbounds.x = x + offset.x;
		tbounds.y = y + offset.y;
		if (tbounds.width >= bounds.width) {
			// TODO fix word wrap
			// final int size = (int) (JumboStringHandler.getSize() * ((float)
			// bounds.width / (float) (tbounds.width)))
			// + 1;
			// final ArrayList<JumboEntity> imgs = text.getLetters();
			// for (JumboEntity e : imgs) {
			// final Rectangle newbounds = e.getBounds();
			// final float mod = (float) size / ((JumboLetter) e).getSize();
			// System.out.println(mod);
			// e.setOutbounds(new Rectangle((int) (newbounds.x * mod),
			// newbounds.y, (int) (newbounds.width * mod),
			// (int) (newbounds.height * mod)));
			// }
			// text.setLetters(imgs);
		}
		text.setMaintainingPosition(true);
		text.calculatePosition();
		return bounds;
	}

	public void setText(String s) {
		text.setText(s);
		setUpdaterequired(true);
	}

	@Override
	public void setRenderable(boolean r) {
		text.setRenderable(r);
		setUpdaterequired(true);
	}

	@Override
	public boolean isRenderable() {
		return text.isRenderable();
	}

	@Override
	public void customRender() {
		final ArrayList<JumboEntity> p = text.getParents();
		if (!p.contains(this)) {
			p.add(this);
		}
		text.render();
	}

	@Override
	public void customTick() {
		text.tick();
	}

}
