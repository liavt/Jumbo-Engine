package com.jumbo.entities.graphics.ui;

import com.jumbo.components.Quad;

import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.graphics.JumboSlider;

public class JumboUISlider extends JumboSlider {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JumboTexture slider = JumboUIHandler.create(10, 10, 2);

	public void create() {
		Quad bounds = getBounds();
		setTexture(JumboUIHandler.create(bounds.width, bounds.height));
		overlayarea.getTexture().getColor().height = 0;
	}

	public JumboUISlider(Quad bounds) {
		super(bounds);
		create();
		setValuebox(slider);
		setMaintainingDimensions(false);
	}

}
