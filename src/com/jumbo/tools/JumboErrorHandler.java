package com.jumbo.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.jumbo.core.Jumbo;
import com.jumbo.tools.input.console.JumboConsole;
import com.jumbo.tools.loaders.JumboStringHandler;

public class JumboErrorHandler {

	private static JFrame errorframe;
	private static JLabel errorheader1, errorheader2;

	public static void handle(Throwable t) {
		handle(t, "");
	}

	public static void handle(Throwable t, String errmessage) {
		try {
			if (JumboSettings.logerrors) {
				errorframe = new JFrame(JumboSettings.launchConfig.title + " Error Handler");
				errorframe.setSize(500, 500);
				errorframe.setVisible(false);
				// JumboDisplayManager.getFrame().dispose();
				errorheader1 = new JLabel("An unexpected error has occured. Please send the error log ");
				errorheader1.setBounds(0, 0, 500, 20);
				errorframe.add(errorheader1);
				errorheader2 = new JLabel("which has been created in the program directory to the developers.");
				errorheader2.setBounds(0, 20, 500, 20);
				errorframe.add(errorheader2);
				String message = "<ERROR REPORT START> %n <ENGINE PROPERTIES> %n";
				message += "Fullscreen: " + JumboSettings.launchConfig.fullscreen + "%n";
				message += "VSync: " + JumboSettings.launchConfig.vsync + "%n";
				message += "Game Title: " + JumboSettings.launchConfig.title + "%n";
				message += "Font Path: " + JumboSettings.launchConfig.fontpath + "%n";
				message += "FPS: " + JumboSettings.fps + "%n";
				message += "Start Screen Width: " + JumboSettings.launchConfig.width() + "%n";
				message += "Start Screen Height: " + JumboSettings.launchConfig.height() + "%n";
				message += "Current Screen Width: " + Jumbo.getFrameWidth() + "%n";
				message += "Current Screen Height: " + Jumbo.getFrameHeight() + "%n";
				message += "%n <SYSTEM PROPERTIES> %n";
				final Properties prop = System.getProperties();
				final Enumeration<Object> keys = prop.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					String value = (String) prop.get(key);
					message += key + ": " + value + "%n";
				}
				message += "%n <ERROR INFO>%n";
				message += t + "%n";
				if (!errmessage.equals("")) {
					message += errmessage + "%n";
				}
				final Throwable cause = t.getCause();
				message += "Error Message: " + t.getMessage() + "%n";
				for (int i = 0; i < t.getStackTrace().length; i++) {
					message += t.getStackTrace()[i] + "%n";
				}
				if (cause != null) {
					message += "Caused by: " + cause + "%n";
					message += cause.getMessage() + "%n";
					for (int i = 0; i < cause.getStackTrace().length; i++) {
						message += cause.getStackTrace()[i] + "%n";
					}
				}
				message += "<ERROR REPORT END>";
				JumboConsole.log(message, 2);
				final String filename = "error-log " + new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new Date());
				JumboConsole.log("An error was found, and was logged under the name of " + filename + ".txt", 2);
				JumboStringHandler.writeString(filename + ".txt", String.format(message));
				// JButton button = new JButton("OK");
				// button.setBounds(new Rectangle(0, 250, 50, 50));
				// button.setAction(new Action() {
				//
				// @Override
				// public void actionPerformed(ActionEvent e) {
				// System.exit(-1);
				// }
				//
				// @Override
				// public void addPropertyChangeListener(
				// PropertyChangeListener arg0) {
				// }
				//
				// @Override
				// public Object getValue(String arg0) {
				// return null;
				// }
				//
				// @Override
				// public boolean isEnabled() {
				// return false;
				// }
				//
				// @Override
				// public void putValue(String arg0, Object arg1) {
				// }
				//
				// @Override
				// public void removePropertyChangeListener(
				// PropertyChangeListener arg0) {
				// }
				//
				// @Override
				// public void setEnabled(boolean arg0) {
				// }
				//
				// });
				// button.setEnabled(true);
				// errorframe.add(button);
				errorframe.setVisible(true);
				errorframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			} else {
				JumboConsole.log("An error was found, but not logged.", 2);
			}
			if (!errmessage.equals("")) {
				JumboConsole.log(errmessage, 2);
			}
			t.printStackTrace();
		} catch (Throwable throwable) {
			JumboConsole.log("An error was found, and was unable to be logged due to the following error: ", 2);
			throwable.printStackTrace();
			JumboConsole.log(("This was caused by trying to log the following error: "), 2);
			if (!errmessage.equals("")) {
				JumboConsole.log(errmessage, 2);
			}
			t.printStackTrace();
		}
		Jumbo.stop(-1);
	}
}
