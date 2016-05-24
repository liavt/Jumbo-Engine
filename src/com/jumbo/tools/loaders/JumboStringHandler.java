package com.jumbo.tools.loaders;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jumbo.components.FloatRectangle;
import com.jumbo.components.JumboColor;
import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.text.JumboLetter;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.input.console.JumboConsole;

/**
 * Static class that provides various methods concerning Strings. Also provides
 * IO actions for Strings, and the underlying framework for bitmap text
 * rendering.
 */
public final class JumboStringHandler {

	public enum vowels {
		A, E, I, O, U, Y;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	;

	public enum consonants {
		B, C, D, F, G, H, J, K, L, M, N, P, Q, R, S, T, V, W, X, Z;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	/**
	 * Returns the textual suffix for the number given.
	 * <p>
	 * For example:
	 * <ul>
	 * <li>1 will return 1st
	 * <li>2 will return 2nd
	 * <li>3 will return 3rd
	 * </ul>
	 * The rest of the the numbers return with -th, with the exception of 0.
	 *
	 * @param num
	 *            Number to be converted
	 * @return Number with a suffix
	 */
	public static final String getNumberSuffix(int num) {
		if (num == 1 || num == -1) {
			return num + "st";
		} else if (num == 2 || num == -2) {
			return num + "nd";
		} else if (num == 3 || num == -3) {
			return num + "rd";
		} else if (num == 0) {
			return num + "";
		} else {
			return num + "th";
		}
	}

	public static String loadAsString(String file) {
		final File test = new File(file);

		if (!test.exists() || !test.isFile() || !test.canRead()) {
			return null;
		}

		final StringBuffer result = new StringBuffer();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer).append(System.lineSeparator());
			}
		} catch (Exception e) {
			JumboErrorHandler.handle(e);
		}
		return result.toString().intern();
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return null;
	}

	public static void writeString(String path, String text) {
		writeString(path, text, "UTF-16");
	}

	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			final File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	public static void writeString(String path, String text, String charset) {
		File file = new File(path);
		try {
			File par = file.getParentFile();
			if (par != null) {
				par.mkdirs();
			}
			// file.mkdirs();
			file.createNewFile();
			try (final PrintWriter writer = new PrintWriter(path, charset)) {
				writer.print(text);
			}
		} catch (Exception e) {
			JumboErrorHandler.handle(e);
		}
	}

	public static class CharInfo {
		public Rectangle rect = new Rectangle(0, 0, 0, 0);
		public int id = 0;

		public CharInfo(int id, Rectangle rect) {
			this.rect = rect;
			this.id = id;
		}
	}

	private static JumboTexture tex, unknownchar;

	public static JumboTexture getBitmap() {
		if (tex == null) {
			throw new IllegalStateException("Font was not initialiazed! Call JumboStringHandler.init()!");
		}
		return tex;
	}

	public static JumboTexture getUnknownchar() {
		return unknownchar;
	}

	public static void setUnknownchar(JumboTexture unknownchar) {
		JumboStringHandler.unknownchar = unknownchar;
	}

	private static int base, size;

	private static CharInfo[] chars;

	// private static void createTruetypeFont() {
	// // tex, size, base, chardata(x,y,id,width,height)
	// try {
	// final GraphicsEnvironment ge =
	// GraphicsEnvironment.getLocalGraphicsEnvironment();
	// final Font font = Font.createFont(Font.TRUETYPE_FONT,
	// new File(JumboSettings.launchConfig.fontpath + ".ttf"));
	// ge.registerFont(font);
	//
	// size = font.getSize();
	// } catch (Exception e) {
	// System.err.println("ERROR CREATING TRUETYPE FONT!");
	// JumboErrorHandler.handle(e);
	// }
	// }

	/**
	 * Creates a bitmap texture for the specified font file. Called via the
	 * {@link Jumbo#start} method automatically.
	 *
	 * @throws IOException
	 */
	public static void initFont() throws IOException {
		unknownchar = new JumboTexture();
		final List<String> lines = new ArrayList<>();
		if (JumboSettings.launchConfig != null && JumboSettings.launchConfig.fontpath != ""
				&& JumboSettings.launchConfig.fontpath != null) {
			try {
				for (String s : Files.readAllLines(Paths.get(JumboSettings.launchConfig.fontpath + ".fnt"))) {
					lines.add(s);
				}
			} catch (Exception e) {
				System.err.println("ERROR READING FONT FILE!");
				JumboErrorHandler.handle(e);
			}
			base = Integer.valueOf(lines.get(1).substring(lines.get(1).lastIndexOf("base=") + 5,
					lines.get(1).lastIndexOf(" scaleW=")));
			size = Integer.valueOf(
					lines.get(0).substring(lines.get(0).lastIndexOf("size=") + 5, lines.get(0).lastIndexOf(" bold=")));
			if (Integer.valueOf(lines.get(1).substring(lines.get(1).lastIndexOf("pages=") + 6,
					lines.get(1).lastIndexOf(" packed="))) != 1) {
				throw new IOException("FONT HAS MORE THAN 1 TEXTURE PAGE, CAN'T LOAD!",
						new IllegalArgumentException("FONT PATH LEADS TO INVALID FONT!"));
			}
			if (Integer.valueOf(lines.get(0).substring(lines.get(0).lastIndexOf("unicode=") + 8,
					lines.get(0).lastIndexOf(" stretchH="))) != 1) {
				throw new IOException("FONT IS NOT UNICODE, CAN'T LOAD!",
						new IllegalArgumentException("FONT PATH LEADS TO INVALID FONT!"));
			}
			if (!lines.get(0).contains("bold=0 italic=0")) {
				throw new IOException("FONT IS BE BOLD OR ITALICS, CAN'T LOAD!",
						new IllegalArgumentException("FONT PATH LEADS TO INVALID FONT!"));
			}
			chars = new CharInfo[Integer.valueOf(lines.get(3).replace("chars count=", ""))];
			for (int i = 0; i < chars.length; i++) {
				String line = lines.get(3 + i);
				int id = 0;
				Rectangle rect = new Rectangle(0, 0, 0, 0);
				for (String part : line.split(" ")) {
					if (part.contains("id=")) {
						id = Integer.valueOf(part.substring(3));
					} else if (part.contains("x=")) {
						rect.x = Integer.valueOf(part.substring(2));
					} else if (part.contains("y=")) {
						rect.y = Integer.valueOf(part.substring(2));
					} else if (part.contains("width=")) {
						rect.width = Integer.valueOf(part.substring(6));
					} else if (part.contains("height=")) {
						rect.height = Integer.valueOf(part.substring(7));
					}
				}
				chars[i] = new CharInfo(id, rect);
			}
			tex = new JumboTexture(JumboImageHandler.getImage(JumboSettings.launchConfig.fontpath + "_0.png"));

			resetSettings();
		} else {
			JumboConsole.log("No font was initialized;  text-based classes will raise errors", 1);
		}
	}

	public static int getSize() {
		return size;
	}

	public static int getBase() {
		return base;
	}

	public static CharInfo[] getChars() {
		return chars;
	}

	private enum CommandType {
		FALSE, TRUE, IGNORED
	}

	private enum ParseType {
		PROBING, COLOR, SIZE, ITALICS
	}

	// for letter by letter stuff
	private static boolean italics = false;
	private static int currentsize = JumboStringHandler.size;
	private static JumboColor col = JumboColor.BLACK;
	private static ParseType type = ParseType.PROBING;

	private static void resetSettings() {
		currentsize = size;
		col = JumboColor.BLACK;
		type = ParseType.PROBING;
	}

	private static void parseCommand(char[] letters) {
		type = ParseType.PROBING;
		for (int i = 0; i < letters.length; i++) {
			final char c = letters[i];
			if (type == ParseType.COLOR) {
				// this is the only way i could think of doing this
				final StringBuffer hex = new StringBuffer();
				while (type == ParseType.COLOR) {
					if (i < letters.length) {
						final char character = letters[i];
						checkParseType(character);
						if (type == ParseType.COLOR) {
							i++;
							hex.append(character);
						} else {
							break;
						}
					} else {
						type = ParseType.PROBING;
					}

				}
				col = JumboColor.fromHex(hex.toString());
			} else if (type == ParseType.SIZE) {
				// this is the only way i could think of doing this
				final StringBuffer size = new StringBuffer();
				while (type == ParseType.SIZE) {
					if (i < letters.length) {
						final char character = letters[i];
						checkParseType(character);
						if (type == ParseType.SIZE) {
							i++;
							size.append(character);
						} else {
							break;
						}
					} else {
						type = ParseType.PROBING;
					}

				}
				JumboStringHandler.currentsize = Integer.parseUnsignedInt((size.toString()));
			} else if (type == ParseType.ITALICS) {
				// this is the only way i could think of doing this
				final StringBuffer bool = new StringBuffer();
				while (type == ParseType.ITALICS) {
					if (i < letters.length) {
						final char character = letters[i];
						checkParseType(character);
						if (type == ParseType.ITALICS) {
							i++;
							bool.append(character);
						} else {
							break;
						}
					} else {
						type = ParseType.PROBING;
					}

				}
				final String out = bool.toString();
				if (out != null) {
					if (out.equals("")) {
						italics = !italics;
					} else {
						final int outbool = Integer.parseInt(bool.toString());
						italics = outbool > 0;
					}
				}
			}
			checkParseType(c);
		}
	}

	private static void checkParseType(char c) {
		if (c == 'i') {
			type = ParseType.ITALICS;
		} else if (c == '#') {
			type = ParseType.COLOR;
		} else if (c == 's') {
			type = ParseType.SIZE;
		}
	}

	private static String defaultprefix = "";

	public final static ArrayList<JumboEntity> getLetters(String string) {
		resetSettings();
		final String s = defaultprefix + string;
		final int length = s.length();
		final ArrayList<JumboEntity> results = new ArrayList<>(length);
		CommandType commandtype = CommandType.FALSE;
		final StringBuilder command = new StringBuilder();
		for (int i = 0; i < length; i++) {
			final int id = s.codePointAt(i);
			CharInfo out = null;
			for (CharInfo c : chars) {
				if (c.id == id) {
					out = c;
				}
			}
			if (id < 21) {
				out = new CharInfo(id, new Rectangle(0, 0, -id, 0));
			}
			if (out != null) {

				// check for commands
				if (commandtype == CommandType.TRUE) {
					if (id == 62) {
						commandtype = CommandType.FALSE;
						// parsing the commands
						parseCommand(command.toString().toCharArray());
						command.delete(0, command.length());
					} else {
						command.append((char) id);
					}
					continue;
				}

				final Rectangle outbounds = out.rect;
				final JumboTexture texture = JumboStringHandler.tex;
				final float width = texture.getWidth(), height = texture.getHeight();
				final FloatRectangle outpos = new FloatRectangle(outbounds.x / width, outbounds.y / height,
						outbounds.width / width, outbounds.height / height);

				// check for special characters
				if (id == 32) {
					outbounds.width = 9;
				} else if (id == 36) {
					commandtype = CommandType.IGNORED;
				} else if (id == 60) {
					if (commandtype == CommandType.IGNORED) {
						commandtype = CommandType.FALSE;
						results.remove(results.size() - 1);
					} else {
						commandtype = CommandType.TRUE;
						continue;
					}
				} else if (commandtype == CommandType.IGNORED) {
					commandtype = CommandType.FALSE;
				}

				final JumboTexture tex = new JumboTexture();
				tex.setID(texture.getID());
				tex.setTextureCoords(outpos);
				tex.setColor(col);

				final JumboLetter let = new JumboLetter(new Rectangle(0, 0, (outbounds.width), (outbounds.height)),
						tex);
				let.setId(id);
				let.setSize(currentsize);
				let.setItalics(italics);
				results.add(let);
			} else {
				JumboConsole.log("UNICODE ID " + id + " NOT FOUND IN FONT FILE", 1);
				results.add(new JumboLetter(new Rectangle(0, 0, 0, 0), JumboStringHandler.unknownchar));
			}
		}
		return results;
	}

	/**
	 * @return the defaultprefix
	 */
	public static String getDefaultPrefix() {
		return defaultprefix;
	}

	/**
	 * @param defaultprefix
	 *            the defaultprefix to set
	 */
	public static void setDefaultPrefix(String defaultprefix) {
		JumboStringHandler.defaultprefix = defaultprefix;
	}

	public final static String capitalize(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public final static void deleteStringIfExists(String path) {
		File f = new File(path);
		if (!f.exists()) {
			JumboErrorHandler.handle(new IllegalArgumentException(path + " leads to a file that doesn't exist!"));
		}
		f.delete();
	}

	public final static void deleteString(String path) {
		File f = new File(path);
		f.delete();
	}
}
