package com.jumbo.core;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class JumboLaunchConfig {
	public final BufferedImage[] icon;
	public final String fontpath, title;
	public final int width, height;

	public boolean fullscreen = false, vsync = false, resizable = false;
	public DisplayMode mode = Display.getDesktopDisplayMode();

	// these 2 getters are here to save on function calls. you can do
	// JumboSettings.launchConfig.width() instead of
	// JumboSettings.launchConfig.mode.getWidth().
	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public JumboLaunchConfig(String name, DisplayMode mode, BufferedImage[] icon, String fontpath) {
		this.fontpath = fontpath;
		this.icon = icon;
		this.mode = mode;
		this.title = name;
		this.width = mode.getWidth();
		this.height = mode.getHeight();
	}

	public JumboLaunchConfig(String name, Dimension dim, BufferedImage[] icon, String fontpath) {
		this.fontpath = fontpath;
		this.icon = icon;
		this.title = name;
		this.mode = new DisplayMode(dim.width, dim.height);
		this.width = mode.getWidth();
		this.height = mode.getHeight();
	}

}
