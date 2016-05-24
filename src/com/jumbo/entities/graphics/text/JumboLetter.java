package com.jumbo.entities.graphics.text;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.jumbo.components.JumboColor;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.JumboTexture;

public class JumboLetter extends JumboGraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0, size = 0;
	private boolean italics;

	public void setColor(JumboColor c) {
		getTexture().setColor(c);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the italics
	 */
	public boolean isItalics() {
		return italics;
	}

	/**
	 * @param italics
	 *            the italics to set
	 */
	public void setItalics(boolean italics) {
		this.italics = italics;
		if (italics) {
			setVectorTopRight(new Dimension(4, 0));
			setVectorTopLeft(new Dimension(4, 0));
		} else {
			setVectorTopRight(new Dimension(0, 0));
			setVectorTopLeft(new Dimension(0, 0));
		}
	}

	// @Override
	// public Rectangle additionalCalculations(Rectangle bounds) {
	// return bounds;
	// }

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		setUpdaterequired(true);
		this.size = size;
	}

	public JumboLetter(Rectangle bounds, JumboTexture texture) {
		super(bounds, texture);
	}

	@Override
	public void customRender() {
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {
	}

}
