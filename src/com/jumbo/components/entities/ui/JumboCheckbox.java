package com.jumbo.components.entities.ui;

import java.awt.Rectangle;

import com.jumbo.rendering.JumboTexture;

public class JumboCheckbox extends JumboButton {

	public JumboCheckbox(JumboTexture tex, JumboTexture hover, Rectangle rectangle) {
		super(tex, hover, rectangle);
	}

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	protected boolean boxtriggered = false;

	/**
	 * @return the boxtriggered
	 */
	public boolean isCheckboxTriggered() {
		return boxtriggered;
	}

	/**
	 * @param boxtriggered
	 *            the boxtriggered to set
	 */
	public void setCheckboxTriggered(boolean boxtriggered) {
		this.boxtriggered = boxtriggered;
	}

	@Override
	public void tick() {
		super.tick();
		if (isTriggered()) {
			boxtriggered = !boxtriggered;
		}
	}

}
