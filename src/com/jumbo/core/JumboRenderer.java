package com.jumbo.core;

import java.awt.Dimension;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.jumbo.components.JumboColor;
import com.jumbo.components.TripleFloat;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.JumboMathHandler;

/**
 * Class that handles all OpenGL rendering code.
 * <p>
 * Contains an internal buffer of {@link JumboRenderMode}s, and can switch
 * between them. By default, index 0 has the built-in implemenation, and the
 * <code>JumboRenderer<code> will use that unless you specify otherwise.
 */
public final class JumboRenderer {
	/**
	 * 
	 */
	public static boolean wasResized = Display.wasResized();
	static int renderwidth, renderheight;
	private static final ArrayList<JumboRenderMode> modes = new ArrayList<>();
	private static JumboRenderMode current;
	private static int currentmode = 0;

	private JumboRenderer() {
	}

	/**
	 * Adds a new {@link JumboRenderMode} to the internal buffer.
	 * 
	 * @param m
	 *            JumboRenderMode to be added
	 * @return index of the added JumboRenderMode
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	public static int addRenderMode(JumboRenderMode m) {
		if (m == null) {
			throw new NullPointerException("Inputted JumboRenderMode is null!");
		}
		modes.add(m);
		return modes.size() - 1;
	}

	/**
	 * Removes a {@link JumboRenderMode} from the internal buffer.
	 * 
	 * @param m
	 *            index of the JumboRenderMode to be removed
	 * @throws IndexOutOfBoundsException
	 *             if the parameter is less than 0
	 * @throws IllegalArgumentException
	 *             if the paramater is larger than the internal buffer size
	 */
	public static void removeRenderMode(int m) {
		if (m < 0) {
			throw new IllegalArgumentException("Input can't be less than 0!");
		}
		if (modes.size() < m) {
			throw new IndexOutOfBoundsException(m + " is larger than the internal buffer size!");
		}
		if (currentmode == m) {
			throw new IllegalArgumentException("Can't remove the current JumboRenderMode!");
		}
		modes.remove(m);
	}

	/**
	 * Removes a {@link JumboRenderMode} from the internal buffer.
	 * 
	 * @param m
	 *            JumboRenderMode to be removed
	 * @throws NullPointerException
	 *             if the parameter is null
	 * @throws IllegalArgumentException
	 *             if the paramater was never added or it is the only
	 *             JumboRenderMode in the bufer.
	 */
	public static void removeRenderMode(JumboRenderMode m) {
		if (m == null) {
			throw new NullPointerException("Inputted JumboRenderMode is null!");
		}
		if (!modes.contains(m)) {
			throw new IllegalArgumentException("JumboRenderMode " + m + " was never added!");
		}
		if (modes.size() <= 1) {
			throw new IllegalArgumentException("Can't remove the only existing JumboRenderMode!");
		}
		if (currentmode == modes.indexOf(m)) {
			throw new IllegalArgumentException("Can't remove the current JumboRenderMode!");
		}
		modes.remove(m);
	}

	/**
	 * Get the {@link JumboRenderMode} located at an index.
	 * 
	 * @param index
	 *            location of desired JumboRenderMode
	 * @return The JumboRenderMode located at the specified index
	 * @see #locationOfMode(JumboRenderMode m)
	 */
	public static JumboRenderMode getMode(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Input is less than 0!");
		}
		if (index >= modes.size()) {
			throw new IndexOutOfBoundsException("Input is larger than the internal buffer size!");
		}
		return modes.get(index);
	}

	/**
	 * Returns the location of a {@link JumboRenderMode} in the internal buffer.
	 * 
	 * @param m
	 *            the JumboRenderMode to look up
	 * @return the index of parameter <i>m</i>
	 */
	public static int locationOfMode(JumboRenderMode m) {
		if (m == null) {
			throw new NullPointerException("Input is null!");
		}
		final int loc = modes.indexOf(m);
		if (loc < 0) {
			throw new IllegalArgumentException("Render mode " + m + " is not in the internal buffer!");
		}
		return loc;
	}

	/**
	 * Sets a new {@JumboRenderMode} to be used for rendering.
	 * <p>
	 * If the input is not in the internal buffer, it will get added
	 * automatically
	 * 
	 * @param m
	 *            the JumboRenderMode to be used
	 * @see #render(JumboGraphicsObject obj)
	 * @see #addRenderMode(JumboRenderMode m)
	 * @see #setCurrentRenderMode(int index)
	 */
	public static void setCurrentRenderMode(JumboRenderMode m) {
		if (m == null) {
			throw new NullPointerException("Input is null!");
		}
		final int loc;
		if (!modes.contains(m)) {
			loc = addRenderMode(m);
		} else {
			loc = modes.indexOf(m);
		}
		currentmode = loc;
		current = m;
		current.init();
	}

	/**
	 * Sets the current {@link JumboRenderMode} to be used for rendering.
	 * 
	 * @param index
	 *            the location of the desired mode in the internal buffer
	 * @see #render(JumboGraphicsObject obj)
	 * @see #addRenderMode(JumboRenderMode m)
	 * @see #setCurrentRenderMode(JumboRenderMode m)
	 */
	public static void setCurrentRenderMode(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Input is less than 0!");
		}
		if (index >= modes.size()) {
			throw new IndexOutOfBoundsException("Input is larger than the internal buffer size!");
		}
		setCurrentRenderMode(modes.get(index));
	}

	/**
	 * Set the JumboColor that the screen is cleared to every frame.
	 * 
	 * @param r
	 *            red value represented by 0.0 to 1.0
	 * @param g
	 *            green value represented by 0.0 to 1.0
	 * @param b
	 *            blue value represented by 0.0 to 1.0
	 */
	public static void setRefreshcolor(float r, float g, float b) {
		if (r > 1) {
			throw new IllegalArgumentException("Red cannot be greater than 1!");
		}
		if (g > 1) {
			throw new IllegalArgumentException("Green cannot be greater than 1!");
		}
		if (b > 1) {
			throw new IllegalArgumentException("Blue cannot be greater than 1!");
		}
		if (r < 0) {
			throw new IllegalArgumentException("Red cannot be less than 1!");
		}
		if (g < 0) {
			throw new IllegalArgumentException("Green cannot be less than 1!");
		}
		if (b < 0) {
			throw new IllegalArgumentException("Blue cannot be less than 1!");
		}
		refreshcolor = new TripleFloat(r, g, b);
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}

	/**
	 * The 'display width' is the width of the current area being rendered to.
	 * 
	 * @return the current bounds for rendering
	 * @see #getDisplayheight()
	 * @see #setDisplayheight(int displayheight)
	 * @see #setDisplaywidth(int displaywidth)
	 */
	public static int getDisplaywidth() {
		return renderwidth;
	}

	/**
	 * Set the width of the area to render to. Pixels outside this width will
	 * not be rendered.
	 * <p>
	 * <b>NOTE: DOESN'T ACTUALLY CHANGE THE SIZE OF THE WINDOW</b>
	 * 
	 * @param displaywidth
	 *            the new width
	 * @see #getDisplaywidth()
	 * @see #setDisplayheight(int displayheight)
	 */
	public static void setDisplaywidth(int displaywidth) {
		JumboRenderer.renderwidth = displaywidth;
	}

	/**
	 * Returns the current height of the area being rendered to.
	 * 
	 * @return the display height
	 * @see #setDisplayheight(int height)
	 */
	public static int getDisplayheight() {
		return renderheight;
	}

	/**
	 * Set the height of the area to render to. Pixels outside this height will
	 * not be rendered.
	 * <p>
	 * <b>NOTE: DOESN'T ACTUALLY CHANGE THE SIZE OF THE WINDOW</b>
	 * 
	 * @param displayheight
	 *            the new height
	 * @see #getDisplayheight()
	 * @see #setDisplaywidth(int width)
	 */
	public static void setDisplayheight(int displayheight) {
		JumboRenderer.renderheight = displayheight;
	}

	/**
	 * Set the JumboColor OpenGL will clear to screen to.
	 * 
	 * @param c
	 *            the new JumboColor
	 */
	public static void setRefreshcolor(JumboColor c) {
		if (c == null) {
			throw new NullPointerException("Input is null!");
		}
		refreshcolor.x = c.getRed() / 255.0f;
		refreshcolor.y = c.getGreen() / 255.0f;
		refreshcolor.z = c.getBlue() / 255.0f;
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}

	private static TripleFloat refreshcolor = new TripleFloat(0, 0, 0);

	public static void init() {
		// the default mode
		addRenderMode(new JumboRenderMode());
		setCurrentRenderMode(0);
		update();
	}

	/**
	 * Prepares the screen for rendering, which includes clearing it.
	 * <p>
	 * Also does the current {@link JumboRenderMode}'s custom preparation
	 * action.
	 */
	public static void prepare() {
		if (wasResized) {
			update();
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		wasResized = Display.wasResized();
		current.prepare();
	}

	/**
	 * Called when the screen is resized, and sets some variables, like
	 * {@link JumboMathHandler#xmod} and {@link JumboMathHandler#ymod}.
	 */
	public static void update() {
		GL11.glLoadIdentity();
		// JumboLaunchConfig config = JumboSettings.launchConfig;
		final int width, height;
		final float factor;
		if (JumboSettings.launchConfig.fullscreen) {
			final DisplayMode current = Display.getDisplayMode();
			factor = Display.getPixelScaleFactor();
			width = current.getWidth();
			height = current.getHeight();
		} else {
			width = Display.getWidth();
			height = Display.getHeight();
			factor = Display.getPixelScaleFactor();
		}
		// Dynamic resizing only works if its larger than the launch
		// dimensions.
		// If its not, it gets strectched, which looks a bit ugly. Because
		// of
		// this, developers should make their base window as small as
		// possible.
		// if (width < config.width || height < config.height) {
		// renderwidth = config.width;
		// renderheight = config.height;
		// GL11.glOrtho(0.0f, config.width, 0, config.height, 0.0f, 1.0f);
		// GL11.glViewport(0, 0, width, height);
		// Maths.currentdim = new Dimension(config.width, config.height);
		// } else {
		// for high dpi modes
		renderwidth = (int) (width * factor);
		renderheight = (int) (height * factor);
		JumboMathHandler.currentdim = new Dimension(renderwidth, renderheight);
		GL11.glOrtho(0.0f, renderwidth, 0, renderheight, 0.0f, 1.0f);
		GL11.glViewport(0, 0, renderwidth, renderheight);
		// }
		JumboMathHandler.xmod = (renderwidth / ((float) JumboSettings.launchConfig.width()));
		JumboMathHandler.ymod = (renderheight / ((float) JumboSettings.launchConfig.height()));
		JumboPaintClass.getScene().onWindowUpdate();
		JumboPaintClass.getPreviousScene().onWindowUpdate();
		wasResized = false;
	}

	/**
	 * Renders the {@link JumboGraphicsObject} paramater using the specified
	 * {@link JumboRenderMode}, calling its initiation action, preparation
	 * action, and finally the render action. Afterwards, reverts everything to
	 * the default render mode.
	 * <p>
	 * This method is slower than the standard
	 * {@link #render(JumboGraphicsObject e)}, as it has to switch render modes.
	 * 
	 * @param e
	 *            GraphicsObject to be rendered
	 * @param index
	 *            location of the JumboRenderMode in the internal buffer to be
	 *            used
	 * @see #render(JumboGraphicsObject e)
	 */
	public static void render(JumboGraphicsObject e, int index) {
		if (index != currentmode) {
			render(e, getMode(index));
		} else {
			render(e);
		}
	}

	/**
	 * Renders the {@link JumboGraphicsObject} parameter using the specified
	 * {@link JumboRenderMode}, calling its initiation action, preparation
	 * action, and finally the render action. Afterwards, reverts everything to
	 * the default render mode.
	 * <p>
	 * This method is slower than the standard
	 * {@link #render(JumboGraphicsObject e)}, as it has to switch render modes
	 * twice.
	 * 
	 * @param e
	 *            GraphicsObject to be rendered
	 * @param m
	 *            the JumboRenderMode to use for rendering
	 * @see #render(JumboGraphicsObject e)
	 */
	public static void render(JumboGraphicsObject e, JumboRenderMode m) {
		try {
			m.init();
			m.prepare();
			m.render(e, renderwidth, renderheight);
			current.init();
			current.prepare();
		} catch (NullPointerException ex) {
			JumboErrorHandler.handle(ex, "A parameter in the entity " + e + " is null!");
		} catch (Exception exc) {
			JumboErrorHandler.handle(exc, "Error rendering " + exc);
		}
	}

	/**
	 * Renders the {@link JumboGraphicsObject} parameter using the current
	 * {@link JumboRenderMode}.
	 * 
	 * @param e
	 *            GraphicsObject to be rendered
	 * @see #render(JumboGraphicsObject e, JumboRenderMode m)
	 * @see #setCurrentRenderMode(int index)
	 */
	public static void render(JumboGraphicsObject e) {
		try {
			current.render(e, renderwidth, renderheight);
		} catch (NullPointerException ex) {
			JumboErrorHandler.handle(ex, "A parameter in the entity " + e + " is null!");
		} catch (Exception exc) {
			JumboErrorHandler.handle(exc, "Error rendering " + exc);
		}
	}

	/**
	 * Returns the current JumboColor that is being refreshed to as a
	 * {@link TripleFloat}.
	 * 
	 * @return current refresh JumboColor
	 * @see JumboColor
	 * @see #setRefreshcolor(TripleFloat c)
	 */
	public static TripleFloat getRefreshcolor() {
		return refreshcolor;
	}

	/**
	 * Set the current JumboColor that the screen will be cleared to.
	 * 
	 * @param c
	 *            new JumboColor, as a {@link TripleFloat}
	 * @see #getRefreshcolor()
	 * @see #setRefreshcolor(JumboColor c)
	 * @see #setRefreshcolor(float r, float g, float b)
	 */
	public static void setRefreshcolor(TripleFloat c) {
		refreshcolor = c;
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}
}
