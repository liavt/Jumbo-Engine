package com.jumbo.core;

import java.awt.Canvas;
import java.nio.ByteBuffer;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.ImageIOImageData;

import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;

class JumboDisplayManager {
	// handles the frame
	private static Canvas canvas = new Canvas();

	public static int getWidth() {
		return JumboSettings.launchConfig.width();
	}

	public static int getHeight() {
		return JumboSettings.launchConfig.height();
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

	static void closeInput() {
		if (Keyboard.isCreated()) {
			Keyboard.destroy();
		}
		if (Mouse.isCreated()) {
			Mouse.destroy();
		}
	}

	static void closeDisplay() throws LWJGLException {
		if (Display.isCreated() && Display.isCurrent()) {
			Display.destroy();
		}
		closeInput();
	}

	private static void createLWJGL() {
		try {
			final JumboLaunchConfig c = JumboSettings.launchConfig;
			// Dimension size = frame.getSize();
			// frame.add(canvas);
			// canvas.setSize(size);
			// Display.setParent(canvas);
			// canvas.setMinimumSize(size);
			// canvas.setVisible(true);
			if (c.fullscreen) {
				Display.setFullscreen(true);
				Display.setDisplayMode(c.mode);
			} else {
				Display.setDisplayMode(c.mode);
				Display.setFullscreen(false);
			}
			Display.setTitle(c.title);
			Display.setResizable(c.resizable);
			if (c.icon != null) {
				final int iconnum = c.icon.length;
				final ByteBuffer[] list = new ByteBuffer[iconnum];
				for (int i = 0; i < iconnum; i++) {
					list[i] = new ImageIOImageData().imageToByteBuffer(c.icon[i], false, false, null);
				}
				Display.setIcon(list);
			}
			if (c.fullscreen && c.vsync) {
				Display.setVSyncEnabled(true);
			}
			if (!Display.isCreated()) {
				final PixelFormat pixelFormat = new PixelFormat();
				final ContextAttribs contextAtrributes = new ContextAttribs(3, 3).withProfileCompatibility(true);
				Display.create(pixelFormat, contextAtrributes);
			}
			if (!Mouse.isCreated()) {
				Mouse.create();
			}
			if (!Keyboard.isCreated()) {
				Keyboard.create();
			}
		} catch (LWJGLException e) {
			JumboErrorHandler.handle(e);
		}

	}

	static void createDisplay() {
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
			JumboErrorHandler.handle(e);
		}
	}

}
