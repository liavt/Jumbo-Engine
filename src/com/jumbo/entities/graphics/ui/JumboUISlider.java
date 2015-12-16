package com.jumbo.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.JumboSlider;

public class JumboUISlider extends JumboSlider {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JumboTexture slider = JumboUIHandler.create(10, 10, 2);

	public void create() {
		Rectangle bounds = getBounds();
		setTexture(JumboUIHandler.create(bounds.width, bounds.height));
		overlayarea.getTexture().getColor().height = 0;
	}

	public JumboUISlider(Rectangle bounds) {
		super(bounds);
		create();
		setValuebox(slider);
		setMaintainingDimensions(false);
	}

}
