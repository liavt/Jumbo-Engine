package com.jumbo.entities.graphics.ui;

import com.jumbo.components.Quad;

import com.jumbo.entities.graphics.JumboCheckbox;
import com.jumbo.entities.graphics.text.JumboText;
import com.jumbo.entities.graphics.text.JumboTextBox;

public class JumboUICheckbox extends JumboCheckbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboUICheckbox(Quad Quad) {
		super(JumboUIHandler.create(Quad.width, Quad.height, 1),
				JumboUIHandler.create(Quad.width, Quad.height, JumboUIHandler.getHovercolor(), 1), Quad);
		JumboTextBox t = new JumboTextBox(new Quad(0, 0, Quad.width, Quad.height),
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
