package com.jumbo.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.JumboButton;
import com.jumbo.entities.graphics.JumboImage;
import com.jumbo.entities.graphics.text.JumboText;

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
