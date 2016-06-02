package com.jumbo.core;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.jumbo.components.JumboException;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.ng.JumboNGRenderMode;
import com.jumbo.core.ng.JumboNGTextureBinder;
import com.jumbo.entities.audio.JumboAudioHandler;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.input.console.JumboConsole;
import com.jumbo.tools.loaders.JumboStringHandler;

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
		JumboDisplayManager.createDisplay();
		// Mouse.setNativeCursor(c);
		// ShaderProgram.init();
		// Maths.init();
		JumboRenderer.init();
		JumboStringHandler.initFont();
		// JumboAudioPlayer.init();
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
			JumboSettings.launchConfig = c;
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

	public static void update() {
		JumboPaintClass.update();
	}

	public static void setPreviousScene(JumboScene v) {
		JumboPaintClass.setPreviousScene(v);
	}

	/**
	 * Stops everything, closing the display, calling {@link #closeDisplay()},
	 * {@link Jumbo#getCloseListener()}, {@link JumboAudioHandler#destroy()}
	 * ,flushing {@link System#out} and {@link System#err}, and finally calling
	 * {@link System#exit} with the specified exit code.
	 * <P>
	 * This is automatically called from the main loop when
	 * {@link Display#isCloseRequested()} is true.
	 * <p>
	 * This method is to be used at the end of a program's lifetime. If you
	 * simply wish the close the graphical window, use {@link #closeDisplay()}
	 * instead.
	 * 
	 * @param id
	 *            exit code given to {@link System#exit(int)}
	 * 
	 * @see System
	 * @see Display
	 */
	public static void stop(int id) {
		try {
			if (Display.isCreated() && Display.isCurrent()) {
				final TriggeredAction close = Jumbo.getCloseListener();
				if (close != null) {
					close.action();
				}
				closeDisplay();
			}
		} catch (LWJGLException e) {
			JumboConsole.log(e, 2);
			// just continue with the forced shutdown
		}
		if (JumboAudioHandler.isInit()) {
			JumboAudioHandler.destroy();
		}
		System.out.flush();
		System.err.flush();
		// Display.destroy();
		// frame.dispose();
		System.exit(id);
	}

	public static void stop() {
		stop(0);
	}

	public static void closeDisplay() throws LWJGLException {
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
		final TriggeredAction prev = getLaunchAction();
		JumboDisplayManager.closeInput();
		init = false;
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

	/**
	 * <b>Be aware that this changes the current {@link JumboRenderMode} and
	 * {@link JumboTextureBinder}</b>
	 * <p>
	 * Set whether graphics should be enabled.
	 * <p>
	 * Disabled graphics allows for {@linkplain Jumbo#start(JumboLaunchConfig)}
	 * to be called, but no window will appear. Other features in the Jumbo
	 * Engine will still work, like audio, and entities will still be ticked and
	 * updated.
	 * 
	 * @param b
	 *            Whether graphical rendering should occur
	 * @see JumboNGRenderMode
	 * @see JumboNGTextureBinder
	 */
	public static void setGraphicsEnabled(boolean b) {
		if (b) {
			JumboRenderer.setCurrentRenderMode(new JumboRenderMode());
			JumboTexture.setBinder(new JumboTextureBinder());
		} else {
			JumboRenderer.setCurrentRenderMode(new JumboNGRenderMode());
			JumboTexture.setBinder(new JumboNGTextureBinder());
		}
	}
}
