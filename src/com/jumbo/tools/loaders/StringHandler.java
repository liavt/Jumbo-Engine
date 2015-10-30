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
import com.jumbo.components.entities.ui.JumboImage;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.JumboSettings;

/**
 * Static class that provides various methods concerning Strings. Also provides
 * IO actions for Strings, and the underlying framework for bitmap text
 * rendering.
 *
 * @author Liav
 */
public final class StringHandler {
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
	 * Returns the textual suffi for the number given.
	 * <p>
	 * For example:
	 * <p>
	 * 1 will return 1st
	 * <p>
	 * 2 will return 2nd
	 * <p>
	 * 3 will return 3rd
	 * <p>
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
			ErrorHandler.handle(e);
		}
		return result.toString().intern();
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return "";
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
			ErrorHandler.handle(e);
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
		return tex;
	}

	public static JumboTexture getUnknownchar() {
		return unknownchar;
	}

	public static void setUnknownchar(JumboTexture unknownchar) {
		StringHandler.unknownchar = unknownchar;
	}

	private static int base, size;

	private static CharInfo[] chars;

	/**
	 * Creates a bitmap texture for the specified font file. Called via the
	 * {@link Jumbo#start} method automatically.
	 *
	 * @throws IOException
	 */
	@SuppressWarnings("boxing")
	public final static void initFont() throws IOException {
		unknownchar = new JumboTexture();
		List<String> lines = new ArrayList<>();
		try {
			for (String s : Files.readAllLines(Paths.get(JumboSettings.launchConfig.fontpath + ".fnt"))) {
				lines.add(s);
			}
		} catch (IOException e) {
			System.err.println("ERROR READING FONT FILE!");
			ErrorHandler.handle(e);
		}
		base = Integer.valueOf(
				lines.get(1).substring(lines.get(1).lastIndexOf("base=") + 5, lines.get(1).lastIndexOf(" scaleW=")));
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
		tex = new JumboTexture(ImageHandler.getImage(JumboSettings.launchConfig.fontpath + "_0.png"));
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

	public final static JumboImage[] getLetters(String s) {
		final int length = s.length();
		final JumboImage[] result = new JumboImage[length];
		final JumboTexture[] tex = new JumboTexture[length];
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
				Rectangle outbounds = out.rect;
				JumboTexture texture = StringHandler.tex;
				float width = texture.getWidth(), height = texture.getHeight();
				FloatRectangle outpos = new FloatRectangle(outbounds.x / width, outbounds.y / height,
						outbounds.width / width, outbounds.height / height);
				tex[i] = new JumboTexture();
				tex[i].setID(texture.getID());
				tex[i].setTextureCoords(outpos);
				if (id == 32) {
					outbounds.width = 9;
				} else if (id == 10) {
					outbounds.height = -1;
				}
				result[i] = new JumboImage(new Rectangle(0, 0, outbounds.width, outbounds.height), tex[i]);
			} else {
				System.err.println("UNICODE ID " + id + " NOT FOUND IN FONT FILE");
				result[i] = new JumboImage(new Rectangle(0, 0, 0, 0), StringHandler.unknownchar);
			}
		}
		return result;
	}

	public final static String capitalize(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public final static void deleteStringIfExists(String path) {
		File f = new File(path);
		if (!f.exists()) {
			ErrorHandler.handle(new IllegalArgumentException(path + " leads to a file that doesn't exist!"));
		}
		f.delete();
	}

	public final static void deleteString(String path) {
		File f = new File(path);
		f.delete();
	}
}
