package com.jumbo.rendering.ng;

import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboGraphicsObject;
import com.jumbo.rendering.JumboRenderMode;

/**
 * {@link JumboRenderMode} whose methods don't do anything - for a non-graphical
 * mode.
 *
 * @see Jumbo#setGraphicsEnabled(boolean)
 */
public class JumboNGRenderMode extends JumboRenderMode {

	public JumboNGRenderMode() {
		setCustomInitialization(() -> {
		});
		setCustomPreparationAction(() -> {
		});
		setRenderAction((JumboGraphicsObject o, int w, int h) -> {
		});
	}

}
