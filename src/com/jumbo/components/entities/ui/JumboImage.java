package com.jumbo.components.entities.ui;

import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboTexture;

import java.awt.*;
import java.awt.image.BufferedImage;

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
	public void tick() {
		JumboRenderer.render(this);
		if (active) {
			if (customaction != null)
				customaction.action();
		}
	}
}
