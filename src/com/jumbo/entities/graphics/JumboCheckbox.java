package com.jumbo.entities.graphics;

import java.awt.Rectangle;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboTexture;

public class JumboCheckbox extends JumboButton {

	public JumboCheckbox(JumboTexture tex, JumboTexture hover, Rectangle rectangle) {
		super(tex, hover, rectangle);
		setClickAction(() -> {
		});
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
		this.boxtriggered = !boxtriggered;
		if (clickaction != null) {
			clickaction.action();
		}
	}

	@Override
	public void setClickAction(TriggeredAction ac) {
		super.setClickAction(() -> {
			boxtriggered = !boxtriggered;
			ac.action();
		});
	}

}
