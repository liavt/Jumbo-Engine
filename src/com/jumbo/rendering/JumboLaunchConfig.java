package com.jumbo.rendering;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class JumboLaunchConfig {
	public final BufferedImage[] icon;
	public final String fontpath, title;
	public final Dimension dim;

	public boolean fullscreen = false, vsync = false, resizable = false;
	public DisplayMode mode = Display.getDesktopDisplayMode();

	// these 2 getters are here to save on function calls. you can do
	// JumboSettings.launchConfig.width() instead of
	// JumboSettings.launchConfig.mode.getWidth().
	public int width() {
		return dim.width;
	}

	public int height() {
		return dim.height;
	}

	public JumboLaunchConfig(String name, DisplayMode mode, BufferedImage[] icon, String fontpath) {
		this.fontpath = fontpath;
		this.icon = icon;
		this.mode = mode;
		this.title = name;
		this.dim = new Dimension(mode.getWidth(), mode.getHeight());
	}

	public JumboLaunchConfig(String name, Dimension dim, BufferedImage[] icon, String fontpath) {
		this.fontpath = fontpath;
		this.icon = icon;
		this.title = name;
		this.dim = dim;
	}

}
