package com.jumbo.entities.graphics.ui;

import java.awt.Rectangle;

import com.jumbo.entities.graphics.JumboCheckbox;
import com.jumbo.entities.graphics.text.JumboText;
import com.jumbo.entities.graphics.text.JumboTextBox;

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
	public void customRender() {
		super.customRender();
		if (boxtriggered) {
			((JumboTextBox) getDescriptor()).setText("<16>X");
		} else {
			((JumboTextBox) getDescriptor()).setText("");
		}
	}

}
