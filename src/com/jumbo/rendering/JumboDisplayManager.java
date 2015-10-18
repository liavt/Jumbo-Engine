package com.jumbo.rendering;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.ImageIOImageData;

import com.jumbo.components.audio.JumboAudioPlayer;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.Maths;

class JumboDisplayManager {
	// handles the frame
	private static Canvas canvas = new Canvas();

	public static void setFullscreen(boolean full) {
		JumboSettings.fullscreen = full;
		if (full) {
			JumboRenderer.wasResized = true;
		}
		createDisplay();
	}

	public static int getWidth() {
		return JumboSettings.launchConfig.width;
	}

	public static int getHeight() {
		return JumboSettings.launchConfig.height;
	}

	public static Canvas getCanvas() {
		return canvas;
	}

	public static void setCanvas(Canvas canvas) {
		JumboDisplayManager.canvas = canvas;
	}

	public static String getTitle() {
		return JumboSettings.launchConfig.title;
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static JFrame frame;

	private static void createLWJGL() {
		try {
			// Dimension size = frame.getSize();
			// frame.add(canvas);
			// canvas.setSize(size);
			// Display.setParent(canvas);
			// canvas.setMinimumSize(size);
			// canvas.setVisible(true);
			if (JumboSettings.fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
			} else {
				Display.setDisplayMode(
						new DisplayMode(JumboSettings.launchConfig.width, JumboSettings.launchConfig.height));
			}
			Display.setTitle(JumboSettings.launchConfig.title);
			Display.setResizable(true);
			BufferedImage img = JumboSettings.launchConfig.icon;
			if (img != null) {
				ByteBuffer[] list = {
						new ImageIOImageData().imageToByteBuffer(JumboSettings.launchConfig.icon, false, false, null) };
				Display.setIcon(list);
			}
			if (JumboSettings.fullscreen && JumboSettings.vsync) {
				Display.setVSyncEnabled(true);
			}
			if (!Display.isCreated()) {
				Display.create();
			}
			if (!Mouse.isCreated()) {
				Mouse.create();
			}
			if (!Keyboard.isCreated()) {
				Keyboard.create();
			}
		} catch (LWJGLException e) {
			ErrorHandler.handle(e);
		}

	}

	public static void createDisplay() {
		// ContextAttribs attribs = new ContextAttribs(4, 5)
		// .withForwardCompatible(true);;
		// // attribs.withProfileCore(true)
		try {
			// JFrame frame = new JFrame();
			// frame.setVisible(false);
			//
			// // frame.setUndecorated(true);
			// // frame.setResizable(true);
			// frame.setResizable(true);
			// GraphicsDevice d =
			// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			// if (JumboSettings.fullscreen) {
			// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			// frame.setUndecorated(true);
			// if (d.isFullScreenSupported()) {
			// d.setFullScreenWindow(frame);
			// frame.setMinimumSize(d.getFullScreenWindow().getSize());
			// }
			// } else {
			// d.setFullScreenWindow(null);
			// frame.setPreferredSize(
			// new Dimension(JumboSettings.launchConfig.width,
			// JumboSettings.launchConfig.height));
			// // added those values to factor in 'window decorations''
			// frame.setMinimumSize(
			// new Dimension(JumboSettings.launchConfig.width,
			// JumboSettings.launchConfig.height));
			// }
			// frame.pack();
			// frame.setVisible(true);
			// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			//
			// frame.setTitle(JumboSettings.launchConfig.title);
			// frame.setIconImage(JumboSettings.launchConfig.icon);
			// frame.setResizable(true);
			// frame.addWindowListener(new java.awt.event.WindowAdapter() {
			// @Override
			// public void windowClosing(java.awt.event.WindowEvent windowEvent)
			// {
			// closeDisplay();
			// System.exit(0);
			// }
			// });
			// if (JumboDisplayManager.frame != null) {
			// JumboDisplayManager.frame.dispose();
			// }
			// JumboDisplayManager.frame = frame;
			createLWJGL();
		} catch (Exception e) {
			ErrorHandler.handle(e);
		}
	}

	public static void closeDisplay() {
		TriggeredAction close = Jumbo.getCloseaction();
		if (close != null) {
			close.action();
		}
		Jumbo.paint.stop();
		JumboAudioPlayer.destroy();
		Maths.destroy();
		System.out.flush();
		System.err.flush();
		// Display.destroy();
		// frame.dispose();
		System.exit(0);
	}

}
