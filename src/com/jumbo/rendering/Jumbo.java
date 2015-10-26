package com.jumbo.rendering;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import com.jumbo.components.JumboException;
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
		start(c, new JumboScene(), new JumboScene());
	}

	static void start(JumboLaunchConfig c, JumboScene s, JumboScene prev) {
		try {
			JumboSettings.launchConfig = c;
			paint = new JumboPaintClass();
			setScene(s);
			setPreviousScene(prev);
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

	public static void setScene(JumboScene v) {
		JumboPaintClass.setView(v);
	}

	public static void setCustomTickAction(TriggeredAction action) {
		JumboPaintClass.setCustomaction(action);
	}

	public static TriggeredAction getCustomTickAction() {
		return JumboPaintClass.getCustomaction();
	}

	public static JumboScene getScene() {
		return JumboPaintClass.getView();
	}

	public static JumboScene getPreviousScene() {
		return JumboPaintClass.getPreviousView();
	}

	public static void update() {
		JumboPaintClass.update();
	}

	public static void setPreviousScene(JumboScene v) {
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
		TriggeredAction close = Jumbo.getCloseaction();
		if (close != null) {
			close.action();
		}
		closeDisplay();
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
		JumboDisplayManager.closeInput();
		JumboDisplayManager.closeDisplay();
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
		final TriggeredAction prev = getMainaction();
		JumboDisplayManager.closeInput();
		init = false;
		// to make sure that the mainaction doesn't get called twice, making for
		// some weird effects.
		setMainaction(() -> {
			Jumbo.setMainaction(prev);
		});
		start(c, Jumbo.getScene(), Jumbo.getPreviousScene());
	}

	public static void restartWindow() {
		setNewLaunchConfig(JumboSettings.launchConfig);
	}

	public static void setFullscreen(boolean b) {
		JumboLaunchConfig c = JumboSettings.launchConfig;
		c.fullscreen = b;
		if (b) {
			JumboRenderer.wasResized = true;
		}
		setNewLaunchConfig(c);
	}

	public static void setVSync(boolean b) throws JumboException {
		if (!JumboSettings.launchConfig.fullscreen) {
			throw new JumboException("Can't turn on VSync when fullscreen isn't enabled!");
		}
		JumboLaunchConfig c = JumboSettings.launchConfig;
		c.vsync = b;
		setNewLaunchConfig(c);
	}
}
