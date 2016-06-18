package com.jumbo.core.modules.presets;

import com.jumbo.core.Jumbo;
import com.jumbo.core.modules.JumboTextureModule;

/**
 * {@link JumboTextureModule} whose methods don't do anything - for a
 * non-graphical mode.
 *
 * @see Jumbo#setGraphicsEnabled(boolean)
 */
public class JumboTextureModuleNG extends JumboTextureModule {

	@Override
	public void unbind() {
	}

	@Override
	public void bind(int id) {
	}

	@Override
	public void unload(int id) {
	}

	@Override
	public int load(int width, int height, int... pixels) {
		return 0;
	}

	@Override
	public int[] getData(int width, int height, int id) {
		return new int[] { 0 };
	}

}
