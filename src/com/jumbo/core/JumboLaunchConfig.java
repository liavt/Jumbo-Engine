package com.jumbo.core;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jumbo.tools.JumboSettings;

public final class JumboLaunchConfig {
	public final BufferedImage[] icon;
	public final String fontpath, title;
	public final int width, height;
	private static final String defaultName = "LWJGL", defaultFont = "";

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

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, DisplayMode, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(DisplayMode mode, BufferedImage[] icon, String fontpath) {
		this(defaultName, mode, icon, fontpath);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, DisplayMode, BufferedImage[], String)}
	 * 
	 * @param name
	 * @param mode
	 * @param fontpath
	 */
	public JumboLaunchConfig(String name, DisplayMode mode, String fontpath) {
		this(name, mode, null, fontpath);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, DisplayMode, BufferedImage[], String)}
	 * 
	 * @param mode
	 */
	public JumboLaunchConfig(DisplayMode mode) {
		this(mode, null, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, DisplayMode, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(String name, DisplayMode mode) {
		this(name, mode, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, DisplayMode, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(String name, DisplayMode mode, BufferedImage[] icon) {
		this(name, mode, icon, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(Dimension mode, BufferedImage[] icon, String fontpath) {
		this(defaultName, mode, icon, fontpath);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(String name, Dimension mode, String fontpath) {
		this(name, mode, null, fontpath);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(Dimension mode) {
		this(mode, null, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(String name, Dimension mode) {
		this(name, mode, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(String name, Dimension mode, BufferedImage[] icon) {
		this(name, mode, icon, defaultFont);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(DisplayMode mode, String font) {
		this(defaultName, mode, font);
	}

	/**
	 * Overloaded constructor for
	 * {@link #JumboLaunchConfig(String, Dimension, BufferedImage[], String)}
	 */
	public JumboLaunchConfig(Dimension mode, String font) {
		this(defaultName, mode, font);
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

	public static JumboLaunchConfig getCurrent() {
		return JumboSettings.launchConfig;
	}

}
