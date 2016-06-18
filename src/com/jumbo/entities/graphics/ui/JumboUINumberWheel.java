package com.jumbo.entities.graphics.ui;

import com.jumbo.components.Quad;

import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.graphics.JumboImage;
import com.jumbo.entities.graphics.JumboNumberWheel;
import com.jumbo.entities.graphics.text.JumboText;

public class JumboUINumberWheel extends JumboNumberWheel {

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

	public JumboUINumberWheel(Quad bounds, String title) {
		super(new JumboTexture(), bounds);
		if (!title.equals("")) {
			final JumboText Title = new JumboText("<i>" + title);
			setDescriptor(Title);
		}
		create();
	}

	public JumboUINumberWheel(Quad bounds) {
		this(bounds, "");
	}
}
