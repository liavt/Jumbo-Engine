package com.jumbo.rendering;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.tools.JumboSettings;

import java.io.File;

public final class Jumbo {
	// main engine class, gets called to start the engine

	static JumboPaintClass paint;

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
		System.setProperty("org.lwjgl.librarypath", new File("lib" + File.separator + "natives-win").getAbsolutePath());
		JumboSettings.launchConfig = c;
		paint = new JumboPaintClass();
		paint.run();
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

	public static void stop() {
		JumboDisplayManager.closeDisplay();
	}

	public static void setFullscreen(boolean b) {
		JumboDisplayManager.setFullscreen(b);
	}
}
