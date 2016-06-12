package com.jumbo.entities.graphics.ui;

import com.jumbo.components.Quad;

import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.graphics.JumboButton;
import com.jumbo.entities.graphics.JumboImage;
import com.jumbo.entities.graphics.text.JumboText;

public class JumboUIButton extends JumboButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void create() {
		Quad bounds = getBounds();
		icon = new JumboImage(new Quad(0, 0, bounds.width, bounds.height),
				JumboUIHandler.create(bounds.width, bounds.height));
		hovericon = new JumboImage(new Quad(0, 0, bounds.width, bounds.height),
				JumboUIHandler.create(bounds.width, bounds.height, JumboUIHandler.getHovercolor()));
	}

	public JumboUIButton(Quad bounds) {
		super(new JumboTexture(), bounds);
		create();
	}

	public JumboUIButton(Quad Quad, JumboText jumboText) {
		super(new JumboTexture(), Quad);
		create();
		setDescriptor(jumboText);
	}
}
