package com.jumbo.core.modules.presets;

import org.lwjgl.opengl.GL11;

public class JumboRenderModuleWireframe extends JumboRenderModuleGL11 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jumbo.core.modules.JumboRenderModuleGL11#prepare()
	 */
	@Override
	public void init() {
		super.init();
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

}
