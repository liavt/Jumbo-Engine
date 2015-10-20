package com.jumbo.rendering;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import com.jumbo.components.audio.JumboAudioPlayer;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.Maths;
import com.jumbo.tools.loaders.StringHandler;

public final class Jumbo {
	// main engine class, gets called to start the engine

	static JumboPaintClass paint;
	static boolean init = false;

	/**
	 * Sets up the display, font render, and renderer by calling
	 * {@link JumboDisplayManager#createDisplay()},
	 * {@link StringHandler#initFont()}, and {@link JumboRenderer#init()}
	 * respectively.
	 * 
	 * @see #start
	 * @throws IOException
	 *             If it fails to register the font
	 */
	public static void init() throws IOException {
		JumboDisplayManager.createDisplay();
		// Mouse.setNativeCursor(c);
		// ShaderProgram.init();
		// Maths.init();
		StringHandler.initFont();
		JumboRenderer.init();
		// JumboAudioPlayer.init();
	}

	public static TriggeredAction getCloseaction() {
		return closeaction;
	}

	public static void setCloseaction(TriggeredAction closeaction) {
		Jumbo.closeaction = closeaction;
	}

	private static TriggeredAction mainaction, closeaction;

	public static TriggeredAction getMainaction() {
		return mainaction;
	}

	public static void setMainaction(TriggeredAction mainaction) {
		Jumbo.mainaction = mainaction;
	}

	public static void start(JumboLaunchConfig c) {
		try {
			JumboSettings.launchConfig = c;
			paint = new JumboPaintClass();
			paint.run();
		} catch (Exception e) {
			ErrorHandler.handle(e);
		}
	}

	public static int getFrameWidth() {
		return JumboRenderer.renderwidth;
	}

	public static int getFrameHeight() {
		return JumboRenderer.renderheight;
	}

	public static void setView(JumboViewport v) {
		JumboPaintClass.setView(v);
	}

	public static void setCustomTickAction(TriggeredAction action) {
		JumboPaintClass.setCustomaction(action);
	}

	public static TriggeredAction getCustomTickAction() {
		return JumboPaintClass.getCustomaction();
	}

	public static JumboViewport getView() {
		return JumboPaintClass.getView();
	}

	public static JumboViewport getPreviousView() {
		return JumboPaintClass.getPreviousView();
	}

	public static void update() {
		JumboPaintClass.update();
	}

	public static void setPreviousView(JumboViewport v) {
		JumboPaintClass.setPreviousView(v);
	}

	/**
	 * Stops everything, closing the display, calling {@link #closeDisplay()},
	 * {@link Jumbo#getCloseaction()}, {@link Maths#destroy()},
	 * {@link JumboAudioPlayer#destroy()},flushing {@link System#out} and
	 * {@link System#err}, and finally calling {@link System#exit} with an error
	 * code of 0.
	 * <P>
	 * This is automatically called from the main loop when
	 * {@link Display#isCloseRequested()} is true.
	 * <p>
	 * This method is to be used at the end of a program's lifetime. If you
	 * simply wish the close the graphical window, use {@link #closeDisplay()}
	 * instead.
	 * 
	 * @see System
	 * @see Display
	 */
	public static void stop() {
		closeDisplay();
		TriggeredAction close = Jumbo.getCloseaction();
		if (close != null) {
			close.action();
		}
		JumboAudioPlayer.destroy();
		Maths.destroy();
		System.out.flush();
		System.err.flush();
		// Display.destroy();
		// frame.dispose();
		System.exit(0);
	}

	public static void closeDisplay() {
		Jumbo.paint.stop();
	}

	/**
	 * Sets a new {@link JumboLaunchConfig} by calling {@link #closeDisplay()}
	 * and {@link #start(JumboLaunchConfig)}.
	 * 
	 * @param c
	 *            the new {@link JumboLaunchConfig}
	 * 
	 * @see JumboLaunchConfig
	 * @see JumboDisplayManager
	 */
	public static void setNewLaunchConfig(JumboLaunchConfig c) {
		closeDisplay();
		start(c);
	}

	public static void setFullscreen(boolean b) {
		JumboDisplayManager.setFullscreen(b);
	}
}
