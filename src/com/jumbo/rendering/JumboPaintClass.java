package com.jumbo.rendering;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lwjgl.opengl.Display;

import com.jumbo.components.audio.JumboAudioPlayer;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.InputHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.Maths;
import com.jumbo.tools.loaders.ConsoleCommands;

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
	public static void setPreviousView(JumboScene previousView) {
		JumboPaintClass.previousView = previousView;
	}

	/**
	 * @return the v
	 */
	static JumboScene getView() {
		return v;
	}

	/**
	 * @return the previousView
	 */
	static JumboScene getPreviousView() {
		return previousView;
	}

	static void setView(JumboScene ve) {
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

	static void update() {
		// System.out.println(Jumbo.getFrameWidth() + " " +
		// Jumbo.getFrameHeight());
		InputHandler.refresh();
		// JumboAudioPlayer.tick();
		JumboRenderer.prepare();
		Maths.refresh();
		v.tick();
		final TriggeredAction a = customaction;
		if (a != null) {
			a.action();
		}
		System.runFinalization();
		Display.update();
	}

	private static ExecutorService e;

	void run() {
		try {
			e = Executors.newFixedThreadPool(2);
			running = true;
			final Runnable input = () -> {
				if (!JumboAudioPlayer.isInit()) {
					try {
						JumboAudioPlayer.init();
					} catch (Exception e1) {
						ErrorHandler.handle(e1);
					}
				}
				JumboAudioPlayer.tick();
			} , console = System.console() != null ? ConsoleCommands::tick : () -> {
			};
			Future<?> inputfuture = e.submit(input);
			Future<?> consolefuture = e.submit(console);
			// console.start();
			// this is to make sure that the audio player is ready before the
			// program starts. threads are not always reliable, and before,
			// sometimes the program will start before the audio player is init,
			// causing a crash.
			while (true) {
				if (JumboAudioPlayer.isInit()) {
					break;
				}
			}
			if (!Jumbo.init) {
				Jumbo.init();
				final TriggeredAction action = Jumbo.getMainaction();
				if (action != null) {
					action.action();
				}
				Jumbo.init = true;
			}
			while (running) {
				if (inputfuture.isDone()) {
					inputfuture = e.submit(input);
				}
				if (consolefuture.isDone()) {
					consolefuture = e.submit(console);
				}
				update();
				final int fps = JumboSettings.fps;
				if (fps > 0) {
					Display.sync(fps);
				}
				if (Display.isCloseRequested()) {
					Jumbo.stop();
				}
			}
		} catch (Throwable t) {
			ErrorHandler.handle(t);
		}
	}

}
