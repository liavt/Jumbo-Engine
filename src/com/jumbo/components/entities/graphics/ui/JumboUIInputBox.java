package com.jumbo.components.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.components.entities.graphics.JumboImage;
import com.jumbo.components.entities.graphics.JumboInputBox;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.rendering.JumboTexture;

public class JumboUIInputBox extends JumboInputBox {

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

	public JumboUIInputBox(Rectangle bounds, String title) {
		super(new JumboTexture(), bounds);
		if (!title.equals("")) {
			final JumboText Title = new JumboText("<i>" + title);
			setDescriptor(Title);
		}
		create();
	}

	public JumboUIInputBox(Rectangle bounds) {
		this(bounds, "");
	}
}
