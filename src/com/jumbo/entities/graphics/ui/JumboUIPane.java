package com.jumbo.entities.graphics.ui;

import com.jumbo.components.Quad;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;

public class JumboUIPane extends JumboGraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboUIPane(Quad bounds) {
		super(bounds, null);
		setTexture(JumboUIHandler.create(bounds.width, bounds.height));
	}

	@Override
	public void customRender() {
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {
	}

}
