package com.jumbo.entities.graphics;

import com.jumbo.components.Quad;
import java.awt.image.BufferedImage;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.texture.JumboTexture;

public class JumboImage extends JumboGraphicsObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public JumboImage(Quad bounds, JumboTexture texture) {
		super(bounds, texture);
	}

	public JumboImage(JumboTexture texture, Quad bounds) {
		super(bounds, texture);
	}

	public JumboImage(BufferedImage img, Quad bounds) {
		super(bounds, new JumboTexture(img));
	}

	public JumboImage(BufferedImage img, int x, int y, int w, int h) {
		super(new Quad(x, y, w, h), new JumboTexture(img));
	}

	@Override
	public void customRender() {
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {
	}
}
