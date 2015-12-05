package com.jumbo.components.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboRenderer;

public class JumboUIPane extends JumboGraphicsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboUIPane(Rectangle bounds) {
		super(bounds, null);
		setTexture(JumboUIHandler.create(bounds.width, bounds.height));
	}

	@Override
	public void tick() {
		JumboRenderer.render(this);
		TriggeredAction action = getCustomaction();
		if (action != null) {
			action.action();
		}
	}

}
