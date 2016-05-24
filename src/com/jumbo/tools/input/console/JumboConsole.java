package com.jumbo.tools.input.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jumbo.core.Jumbo;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;

public final class JumboConsole {
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final JumboCommand[] engine = { new JumboCommand("shutdown",
			"Stops the Jumbo Engine, closing the window, and shutting down the JVM. Same as calling Jumbo.stop().", "",
			(String[] args) -> {
				Jumbo.stop();
			}),
			new JumboCommand("help",
					"Shows all available commands. Put in a specific command as an argument to learn about that "
							+ "command.",
					"[Command you wish to learn about]", (String[] args) -> {
						if (args.length > 1) {
							help(args[1]);
						} else {
							help("");
						}
					}),
			new JumboCommand("fullscreen", "Set fullscreen on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					Jumbo.setFullscreen(Boolean.parseBoolean(args[1]));
				} else {
					log("Invalid usage! Refer to /help for proper usage", 1);
				}
			}), new JumboCommand("vysnc", "Set vsync on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					try {
						Jumbo.setVSync(Boolean.parseBoolean(args[1]));
					} catch (Exception e) {
						JumboErrorHandler.handle(e);
					}
				} else {
					log("Invalid usage! Refer to /help for proper usage", 1);
				}
			}), new JumboCommand("volume", "Set the master volume.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.totalvolume = Integer.parseInt(args[1]);
				} else {
					log("Invalid usage! Refer to /help for proper usage", 1);
				}
			}), new JumboCommand("fxvolume", "Set the volume of sound effects.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.fxvolume = Integer.parseInt(args[1]);
				} else {
					log("Invalid usage! Refer to /help for proper usage", 1);
				}
			}), new JumboCommand("mvolume", "Set the volume of music.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.musicvolume = Integer.parseInt(args[1]);
				} else {
					log("Invalid usage! Refer to /help for proper usage", 1);
				}
			}) };
	private static final ArrayList<JumboCommand> custom = new ArrayList<>();

	public static void execute(String s) {
		short spaceloc = (short) s.indexOf(" ");
		if (spaceloc == -1) {
			spaceloc = (short) (s.length());
		}
		final String cmd = s.substring(0, spaceloc);
		final String[] args = s.substring(spaceloc).split(" ");
		for (JumboCommand c : engine) {
			if (c.getInput().equals(cmd)) {
				c.getAction().action(args);
			}
		}
	}

	private static void help(String s) {
		if (s.equals("")) {
			final StringBuffer out = new StringBuffer("All commands: " + System.lineSeparator());
			for (JumboCommand c : engine) {
				out.append("/").append(c.getInput()).append(" ");
			}
			out.append(System.lineSeparator());
			for (JumboCommand c : custom) {
				out.append("/").append(c.getInput()).append(" ");
			}
			log(out);
		} else {
			JumboCommand match = null;
			for (JumboCommand c : engine) {
				if (c.getInput().equals(s)) {
					match = c;
				}
			}
			for (JumboCommand c : custom) {
				if (c.getInput().equals(s)) {
					match = c;
				}
			}
			if (match == null) {
				log(s + " is not recognized as an existing command.", 1);
			} else {
				log("/" + match.getInput());
				log(match.getDesc());
				log("USAGE: /" + match.getInput() + " " + match.getUsage());
			}
		}
	}

	public static void log(Object s) {
		log(s, 0);
	}

	public static void log(Object s, int type) {
		final JumboMessageType t = JumboMessageType.getType(type);
		final StringBuilder b = new StringBuilder("[").append(new SimpleDateFormat("HH:mm:ss:S").format(new Date()))
				.append("][").append(t.getName()).append("] ").append(s);
		if (!t.isError()) {
			System.out.println(b);
		} else {
			System.err.println(b);
		}
	}

	public static void tick() {
		try {
			final String s = in.readLine();
			if (s != null) {
				if (s.startsWith("/")) {
					execute(s.substring(1));
				}
			}
		} catch (IOException e) {
			JumboErrorHandler.handle(e);
		}
	}
}
