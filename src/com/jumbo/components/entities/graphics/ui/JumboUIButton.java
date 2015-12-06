package com.jumbo.components.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.components.entities.graphics.JumboButton;
import com.jumbo.components.entities.graphics.JumboImage;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.rendering.JumboTexture;

public class JumboUIButton extends JumboButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void create() {
		Rectangle bounds = getBounds();
		icon = new JumboImage(new Rectangle(0, 0, bounds.width, bounds.height),
				JumboUIHandler.create(bounds.width, bounds.height));
		hovericon = new JumboImage(new Rectangle(0, 0, bounds.width, bounds.height),
				JumboUIHandler.create(bounds.width, bounds.height, JumboUIHandler.getHovercolor()));
	}

	public JumboUIButton(Rectangle bounds) {
		super(new JumboTexture(), bounds);
		create();
	}

	public JumboUIButton(Rectangle rectangle, JumboText jumboText) {
		super(new JumboTexture(), rectangle);
		create();
		setDescriptor(jumboText);
	}
}
