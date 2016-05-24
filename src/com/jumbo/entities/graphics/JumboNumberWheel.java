package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.text.JumboInputBox;

public class JumboNumberWheel extends JumboInputBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboNumberWheel(BufferedImage icon, BufferedImage hovericon, int x, int y, int w, int h) {
		super(icon, hovericon, x, y, w, h);
	}

	public JumboNumberWheel(BufferedImage icon, BufferedImage hovericon, Rectangle rectangle) {
		super(icon, hovericon, rectangle);
	}

	public JumboNumberWheel(BufferedImage icon, int x, int y, int w, int h) {
		super(icon, x, y, w, h);
	}

	public JumboNumberWheel(int x, int y, int w, int h, JumboTexture tex) {
		super(x, y, w, h, tex);
	}

	public JumboNumberWheel(JumboImage img, Rectangle rect) {
		super(img, rect);
	}

	public JumboNumberWheel(JumboTexture tex, JumboTexture hover, Rectangle rectangle) {
		super(tex, hover, rectangle);
	}

	public JumboNumberWheel(JumboTexture tex, Rectangle rectangle) {
		super(tex, rectangle);
	}

	@Override
	protected boolean filter(char c) {
		return (c >= '0' && c <= '9') && super.filter(c);
	}

	public long getNumber() {
		return text.length() <= 0 ? 0 : Long.parseLong(text.toString());
	}
}
