package com.jumbo.tools.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.jumbo.rendering.Jumbo;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.JumboSettings;

public final class ConsoleCommands {
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final Command[] engine = { new Command("shutdown",
			"Stops the Jumbo Engine, closing the window, and shutting down the JVM. Same as calling Jumbo.stop().", "",
			(String[] args) -> {
				Jumbo.stop();
			}),
			new Command("help",
					"Shows all available commands. Put in a specific command as an argument to learn about that "
							+ "command.",
					"[Command you wish to learn about]", (String[] args) -> {
						if (args.length > 1) {
							help(args[1]);
						} else {
							help("");
						}
					}),
			new Command("trip", "Set trip mode on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.trippy = Boolean.parseBoolean(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("shake", "Set shaky mode on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.shaky = Boolean.parseBoolean(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("fullscreen", "Set fullscreen on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					Jumbo.setFullscreen(Boolean.parseBoolean(args[1]));
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("vysnc", "Set vsync on or off.", "[true/false]", (String[] args) -> {
				if (args.length > 1) {
					try {
						Jumbo.setVSync(Boolean.parseBoolean(args[1]));
					} catch (Exception e) {
						ErrorHandler.handle(e);
					}
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("shakiness", "Set the intensity of shakiness.", "[intensity]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.shakeintensity = Integer.parseInt(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("volume", "Set the master volume.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.totalvolume = Integer.parseInt(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("fxvolume", "Set the volume of sound effects.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.fxvolume = Integer.parseInt(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}), new Command("musicvolume", "Set the volume of music.", "[volume]", (String[] args) -> {
				if (args.length > 1) {
					JumboSettings.musicvolume = Integer.parseInt(args[1]);
				} else {
					System.err.println("Invalid usage! Refer to /help for proper usage");
				}
			}) };
	private static final ArrayList<Command> custom = new ArrayList<>();

	public static void execute(String s) {
		short spaceloc = (short) s.indexOf(" ");
		if (spaceloc == -1) {
			spaceloc = (short) (s.length());
		}
		final String cmd = s.substring(0, spaceloc);
		final String[] args = s.substring(spaceloc).split(" ");
		for (Command c : engine) {
			if (c.getInput().equals(cmd)) {
				c.getAction().action(args);
			}
		}
	}

	private static void help(String s) {
		if (s.equals("")) {
			final StringBuffer out = new StringBuffer("All commands: " + System.lineSeparator());
			for (Command c : engine) {
				out.append("/").append(c.getInput()).append(" ");
			}
			out.append(System.lineSeparator());
			for (Command c : custom) {
				out.append("/").append(c.getInput()).append(" ");
			}
			System.out.println(out);
		} else {
			Command match = null;
			for (Command c : engine) {
				if (c.getInput().equals(s)) {
					match = c;
				}
			}
			for (Command c : custom) {
				if (c.getInput().equals(s)) {
					match = c;
				}
			}
			if (match == null) {
				System.err.println(s + " is not recognized as an existing command.");
			} else {
				System.out.println("/" + match.getInput());
				System.out.println(match.getDesc());
				System.out.println("USAGE: /" + match.getInput() + " " + match.getUsage());
			}
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
			ErrorHandler.handle(e);
		}
	}
}
