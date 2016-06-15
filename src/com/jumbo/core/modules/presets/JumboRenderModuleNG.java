package com.jumbo.core.modules.presets;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.modules.JumboRenderModule;

/**
 * {@link JumboRenderModule} whose methods don't do anything - for a
 * non-graphical mode.
 *
 */
public class JumboRenderModuleNG extends JumboRenderModule {

	@Override
	public void render(JumboGraphicsObject e, int renderwidth, int renderheight) {
	}

	@Override
	public void init() {
	}

	@Override
	public void prepare() {
	}

	@Override
	public void resize(int newWidth, int newHeight) {

	}

}
