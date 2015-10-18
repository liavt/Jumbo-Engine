package com.jumbo.rendering;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public final class JumboLaunchConfig {
	public final int width, height;
	public final BufferedImage icon;
	public final String fontpath, title;

	public JumboLaunchConfig(String name, Dimension dimensions, BufferedImage icon, String fontpath) {
		this.fontpath = fontpath;
		this.icon = icon;
		this.width = dimensions.width;
		this.height = dimensions.height;
		this.title = name;
	}

}
