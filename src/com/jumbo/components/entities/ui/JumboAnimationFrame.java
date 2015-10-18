package com.jumbo.components.entities.ui;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboTexture;

public class JumboAnimationFrame extends JumboGraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int delay;

	public JumboAnimationFrame(int delay, JumboTexture texture) {
		super(new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), texture);
		this.delay = delay;
	}

	public JumboAnimationFrame(int delay, BufferedImage texture) {
		super(new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), new JumboTexture(texture));
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public void tick() {
		JumboRenderer.render(this);
	}
}
