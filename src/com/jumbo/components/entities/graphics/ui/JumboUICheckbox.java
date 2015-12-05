package com.jumbo.components.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.components.entities.graphics.JumboCheckbox;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;

public class JumboUICheckbox extends JumboCheckbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboUICheckbox(Rectangle rectangle) {
		super(JumboUIHandler.create(rectangle.width, rectangle.height, 1),
				JumboUIHandler.create(rectangle.width, rectangle.height, JumboUIHandler.getHovercolor(), 1), rectangle);
		JumboTextBox t = new JumboTextBox(new Rectangle(0, 0, rectangle.width, rectangle.height),
				new JumboText("", 0, 0));
		this.descriptor = t;
	}

	@Override
	public void tick() {
		super.tick();
		if (boxtriggered) {
			((JumboTextBox) getDescriptor()).setText("<16>X");
		} else {
			((JumboTextBox) getDescriptor()).setText("");
		}
	}

}
