package com.jumbo.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lwjgl.opengl.Display;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.entities.audio.JumboAudioHandler;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.input.JumboInputHandler;
import com.jumbo.tools.input.console.JumboConsole;

final class JumboPaintClass {
	boolean running = false;

	private static TriggeredAction customaction;

	private static JumboScene v = new JumboScene();
	private static JumboScene previousView = new JumboScene();

	/**
	 * @return the customaction
	 */
	static TriggeredAction getCustomaction() {
		return customaction;
	}

	/**
	 * @param customaction
	 *            the customaction to set
	 */
	static void setCustomaction(TriggeredAction customaction) {
		JumboPaintClass.customaction = customaction;
	}

	/**
	 * @param previousView
	 *            the previousView to set
	 */
	public static void setPreviousScene(JumboScene previousView) {
		JumboPaintClass.previousView = previousView;
	}

	/**
	 * @return the v
	 */
	static JumboScene getScene() {
		return v;
	}

	/**
	 * @return the previousView
	 */
	static JumboScene getPreviousScene() {
		return previousView;
	}

	static void setScene(JumboScene ve) {
		previousView = v;
		v = ve;
	}

	void start() {
		if (running) {
			return;
		}
		running = true;
		this.run();
	}

	void stop() {
		e.shutdownNow();
		running = false;
	}

	static void render() {
		// JumboConsole.log(Jumbo.getFrameWidth() + " " +
		// Jumbo.getFrameHeight());
		// JumboAudioPlayer.tick();
		JumboRenderer.prepare();
		v.render();
		Display.update();
		// input is checked after Display.update is called, as that is when
		// input is polled
		JumboInputHandler.refresh();
	}

	static void tick() {
		v.tick();
		final TriggeredAction a = customaction;
		if (a != null) {
			a.action();
		}
		System.runFinalization();
		if (Display.isCloseRequested()) {
			Jumbo.stop();
		}
	}

	static void update() {
		final int time = (int) ((System.currentTimeMillis()) - startTime);
		if (Math.abs(time) >= JumboSettings.tickdelay) {
			startTime = (int) System.currentTimeMillis();
			tick();
		}
		render();
		final int fps = JumboSettings.fps;
		if (fps > 0) {
			Display.sync(fps);
		}
	}

	private static ExecutorService e;

	private static int startTime = (int) System.currentTimeMillis();

	void run() {
		try {
			e = Executors.newFixedThreadPool(2);
			running = true;
			final Runnable input = () -> {
				if (!JumboAudioHandler.isInit()) {
					try {
						JumboAudioHandler.init();
					} catch (Exception e1) {
						JumboErrorHandler.handle(e1);
					}
				}
				JumboAudioHandler.tick();
			} , console = System.console() != null ? JumboConsole::tick : () -> {
			};
			Future<?> inputfuture = e.submit(input);
			Future<?> consolefuture = e.submit(console);
			// console.start();
			// this is to make sure that the audio player is ready before the
			// program starts. threads are not always reliable, and before,
			// sometimes the program will start before the audio player is init,
			// causing a crash.
			while (true) {
				if (JumboAudioHandler.isInit()) {
					break;
				}
			}
			if (!Jumbo.init) {
				Jumbo.init();
				final TriggeredAction action = Jumbo.getLaunchAction();
				if (action != null) {
					action.action();
				}
				Jumbo.init = true;
			}
			startTime = (int) System.currentTimeMillis();
			while (running) {
				if (inputfuture.isDone()) {
					inputfuture = e.submit(input);
				}
				if (consolefuture.isDone()) {
					consolefuture = e.submit(console);
				}
				update();
			}
		} catch (Throwable t) {
			JumboErrorHandler.handle(t);
		}
	}

}
