package com.jumbo.entities.graphics;

import com.jumbo.components.Quad;
import java.awt.image.BufferedImage;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.texture.JumboTexture;

public class JumboAnimationFrame extends JumboGraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int delay;

	public JumboAnimationFrame(int delay, JumboTexture texture) {
		super(new Quad(0, 0, texture.getWidth(), texture.getHeight()), texture);
		this.delay = delay;
	}

	public JumboAnimationFrame(int delay, BufferedImage texture) {
		super(new Quad(0, 0, texture.getWidth(), texture.getHeight()), new JumboTexture(texture));
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public void customRender() {
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {
	}
}
