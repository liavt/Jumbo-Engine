package com.jumbo.core.ng;

import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderMode;

/**
 * {@link JumboRenderMode} whose methods don't do anything - for a non-graphical
 * mode.
 *
 * @see Jumbo#setGraphicsEnabled(boolean)
 */
public class JumboNGRenderMode extends JumboRenderMode {

	@Override
	public void render(JumboGraphicsObject e, int renderwidth, int renderheight) {
	}

	@Override
	public void init() {
	}

	@Override
	public void prepare() {
	}

}
