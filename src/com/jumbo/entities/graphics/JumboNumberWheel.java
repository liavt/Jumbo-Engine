package com.jumbo.entities.graphics;

import com.jumbo.components.Quad;
import java.awt.image.BufferedImage;

import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.graphics.text.JumboInputBox;

public class JumboNumberWheel extends JumboInputBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboNumberWheel(BufferedImage icon, BufferedImage hovericon, int x, int y, int w, int h) {
		super(icon, hovericon, x, y, w, h);
	}

	public JumboNumberWheel(BufferedImage icon, BufferedImage hovericon, Quad Quad) {
		super(icon, hovericon, Quad);
	}

	public JumboNumberWheel(BufferedImage icon, int x, int y, int w, int h) {
		super(icon, x, y, w, h);
	}

	public JumboNumberWheel(int x, int y, int w, int h, JumboTexture tex) {
		super(x, y, w, h, tex);
	}

	public JumboNumberWheel(JumboImage img, Quad rect) {
		super(img, rect);
	}

	public JumboNumberWheel(JumboTexture tex, JumboTexture hover, Quad Quad) {
		super(tex, hover, Quad);
	}

	public JumboNumberWheel(JumboTexture tex, Quad Quad) {
		super(tex, Quad);
	}

	@Override
	protected boolean filter(char c) {
		return (c >= '0' && c <= '9') && super.filter(c);
	}

	public long getNumber() {
		return text.length() <= 0 ? 0 : Long.parseLong(text.toString());
	}
}
