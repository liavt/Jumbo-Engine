package com.jumbo.tools;

import com.jumbo.rendering.JumboLaunchConfig;

public final class JumboSettings {
	private JumboSettings() {
	}

	public static JumboLaunchConfig launchConfig;

	public static final boolean wireframe = false;// makes everything become a
													// wireframe, so it allows
													// you to see whether
													// objects are being
													// rendered, and if they are
													// behind other objects.
	public static final boolean rectangle = false;// makes all the textures in
													// memory to a solid random
													// color. this allows you to
													// find out which objects
													// share textures, as they
													// will both be the same
													// color.
	public static final boolean logerrors = false;// whether to write a log file
													// to the disk explaining
													// the error. turn this on
													// for production, and off
													// for when you are testing
													// it. DONT FORGET ABOUT IT!
													// IF YOU DO, YOU WILL
													// PROBABLY HAVE LIKE 1000S
													// OF LOGS
	public static boolean trippy = false;// smoke weed everyday!
	public static boolean shaky = false;// like the drug mode, but less trippy
	public static int shakeintensity = 0; // how much the screen shakes,
											// measured
											// in pixels
	public static boolean inputEnabled = true;// for user input
	public static boolean fullscreen = false, vsync = false;// for
	public static int fps = 0;
	// graphics

	public static float musicvolume = 0.0f, fxvolume = -6.0f, totalvolume = 8.0f;// guess
																					// what
																					// these
																					// are
																					// for?

}
