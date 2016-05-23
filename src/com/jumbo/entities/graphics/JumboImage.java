package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.JumboTexture;

public class JumboImage extends JumboGraphicsObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public JumboImage(Rectangle bounds, JumboTexture texture) {
		super(bounds, texture);
	}

	public JumboImage(JumboTexture texture, Rectangle bounds) {
		super(bounds, texture);
	}

	public JumboImage(BufferedImage img, Rectangle bounds) {
		super(bounds, new JumboTexture(img));
	}

	public JumboImage(BufferedImage img, int x, int y, int w, int h) {
		super(new Rectangle(x, y, w, h), new JumboTexture(img));
	}

	@Override
	public void customRender() {
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {
	}
}
