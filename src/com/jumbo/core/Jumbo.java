package com.jumbo.core;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.entities.audio.JumboAudioHandler;
import com.jumbo.util.JumboErrorHandler;
import com.jumbo.util.JumboSettings;
import com.jumbo.util.input.console.JumboConsole;
import com.jumbo.util.input.console.JumboMessageType;
import com.jumbo.util.loaders.JumboStringHandler;

public final class Jumbo {
	// main engine class, gets called to start the engine

	static JumboPaintClass paint;
	static boolean init = false;

	/**
	 * Sets up the display, font render, and renderer by calling
	 * {@link JumboDisplayManager#createDisplay()},
	 * {@link JumboStringHandler#initFont()}, and {@link JumboRenderer#init()}
	 * respectively.
	 * 
	 * @see #start
	 * @throws IOException
	 *             If it fails to register the font
	 */
	public static void init() throws IOException {
		JumboConsole.log("Initializing Jumbo Engine...", JumboMessageType.ENGINE);

		JumboDisplayManager.createDisplay();
		// Mouse.setNativeCursor(c);
		// ShaderProgram.init();
		// Maths.init();
		JumboRenderer.init();
		JumboStringHandler.initFont();
		// JumboAudioPlayer.init();
	}

	public static boolean isRunning() {
		return paint.running;
	}

	public static TriggeredAction getCloseListener() {
		return closeaction;
	}

	public static void setCloseListener(TriggeredAction closeaction) {
		Jumbo.closeaction = closeaction;
	}

	private static TriggeredAction mainaction, closeaction;

	public static TriggeredAction getLaunchAction() {
		return mainaction;
	}

	public static void setLaunchAction(TriggeredAction mainaction) {
		Jumbo.mainaction = mainaction;
	}

	public static void start(JumboLaunchConfig c) {
		start(c, new JumboScene(), new JumboScene());
	}

	static void start(JumboLaunchConfig c, JumboScene s, JumboScene prev) {
		try {
			JumboConsole.log("Launching display...", JumboMessageType.ENGINE);
			JumboSettings.launchConfig = c;
			JumboPaintClass.closeRequested = false;
			paint = new JumboPaintClass();
			setScene(s);
			setPreviousScene(prev);
			paint.run();
		} catch (Exception e) {
			JumboErrorHandler.handle(e);
		}
	}

	public static int getFrameWidth() {
		return JumboRenderer.renderwidth;
	}

	public static int getFrameHeight() {
		return JumboRenderer.renderheight;
	}

	public static void setScene(JumboScene v) {
		JumboPaintClass.setScene(v);
	}

	public static void setCustomTickAction(TriggeredAction action) {
		JumboPaintClass.setCustomaction(action);
	}

	public static TriggeredAction getCustomTickAction() {
		return JumboPaintClass.getCustomaction();
	}

	public static JumboScene getScene() {
		return JumboPaintClass.getScene();
	}

	public static JumboScene getPreviousScene() {
		return JumboPaintClass.getPreviousScene();
	}

	public static void update() throws LWJGLException {
		JumboPaintClass.update();
	}

	public static void setPreviousScene(JumboScene v) {
		JumboPaintClass.setPreviousScene(v);
	}

	/**
	 * Stops everything, closing the display, calling {@link #requestClose()},
	 * {@link Jumbo#getCloseListener()}, {@link JumboAudioHandler#destroy()}
	 * ,flushing {@link System#out} and {@link System#err}, and finally calling
	 * {@link System#exit} with the specified exit code.
	 * <P>
	 * This is automatically called from the main loop when
	 * {@link Display#isCloseRequested()} is true.
	 * <p>
	 * This method is to be used at the end of a program's lifetime. If you
	 * simply wish the close the graphical window, use {@link #requestClose()}
	 * instead.
	 * 
	 * 
	 * @see System
	 * @see Display
	 */
	public static void stop() {
		final TriggeredAction close = Jumbo.getCloseListener();
		if (close != null) {
			close.action();
		}
		requestClose();
		if (JumboAudioHandler.isInit() && AL.isCreated()) {
			JumboAudioHandler.destroy();
		}

		// Display.destroy();
		// frame.dispose();
	}

	static void shutdownDisplay() throws LWJGLException {
		JumboConsole.log("Closing display...", JumboMessageType.ENGINE);

		init = false;
		Jumbo.paint.stop();
		JumboDisplayManager.closeInput();
		JumboDisplayManager.closeDisplay();

	}

	public static void requestClose() {
		try {
			if (Display.isCurrent()) {

				shutdownDisplay();

			} else {
				JumboPaintClass.closeRequested = true;
				while (paint != null && paint.running) {
				}
			}
		} catch (LWJGLException e) {
			JumboErrorHandler.handle(e);
		}
	}

	/**
	 * Sets a new {@link JumboLaunchConfig} by calling {@link #requestClose()}
	 * and {@link #start(JumboLaunchConfig)}.
	 * 
	 * @param c
	 *            the new {@link JumboLaunchConfig}
	 * 
	 * @see JumboLaunchConfig
	 * @see JumboDisplayManager
	 */
	public static void setNewLaunchConfig(JumboLaunchConfig c) {
		requestClose();
		final TriggeredAction prev = getLaunchAction();
		// to make sure that the mainaction doesn't get called twice, making for
		// some weird effects.
		setLaunchAction(() -> {
			Jumbo.setLaunchAction(prev);
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

	/**
	 * 
	 * Vertical sync locks the FPS at the monitor's refresh rate, so no screen
	 * tearing will occur. However, this increases input lag.
	 * 
	 * @param b
	 *            true for vertical sync; false for not.
	 * @throws JumboException
	 *             If {@linkplain JumboLaunchConfig#fullscreen} is false
	 */
	public static void setVSync(boolean b) throws JumboException {
		if (!JumboSettings.launchConfig.fullscreen) {
			throw new JumboException("Can't turn on VSync when fullscreen isn't enabled!");
		}
		JumboLaunchConfig c = JumboSettings.launchConfig;
		c.vsync = b;
		setNewLaunchConfig(c);
	}

}
