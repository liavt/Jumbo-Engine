package com.jumbo.core.ng;

import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboTextureBinder;

/**
 * {@link JumboTextureBinder} whose methods don't do anything - for a
 * non-graphical mode.
 *
 * @see Jumbo#setGraphicsEnabled(boolean)
 */
public class JumboNGTextureBinder extends JumboTextureBinder {

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
