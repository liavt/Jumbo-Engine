package com.jumbo.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.jumbo.components.FloatRectangle;
import com.jumbo.components.Position;
import com.jumbo.components.TripleFloat;
import com.jumbo.tools.ErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.calculations.Dice;
import com.jumbo.tools.calculations.Maths;

/**
 * Class that handles all OpenGL rendering code.
 * */
public final class JumboRenderer {
	/**
	 * 
	 */
	public static boolean wasResized = Display.wasResized();
	static int renderwidth, renderheight;
	private static RenderAction render = (JumboEntity e)->{	
			Rectangle rect = new Rectangle();
			try {
				rect = e.getInheritedOutbounds();
			} catch (NullPointerException ex) {
				System.err.println("Entity " + e + " has a null parent!");
				ErrorHandler.handle(ex);
			}
			e.setRenderposition(new Position(rect.x, rect.y));
			final boolean outofbounds = (rect.x + rect.width <= 0 || rect.x >= renderwidth || rect.y + rect.height <= 0
					|| rect.y >= renderheight);
			e.setOutofbounds(outofbounds);
			if (e.isRenderable() && !outofbounds) {
				// GL11.glLoadIdentity();
				// check to make sure its not offscreen
				// texture binding
				final JumboTexture tex = e.getTexture();
				// to prevent repeat method calls
				int id = tex.getID();
				if (previousid != id) {
					tex.bind();
					previousid = id;
				}
				// color
				FloatRectangle c = tex.getColor();
				final boolean trippy = JumboSettings.trippy;
				if (trippy) {
					Color c2 = Dice.randomColor();
					c = new FloatRectangle(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f,
							c.height);
				}
				GL11.glColor4f(c.x, c.y, c.width, c.height);
				final FloatRectangle texturecoords = tex.getTextureCoords();
				// float texturex = e.getTexture().getTextureCoords().x,
				// texturey =
				// e
				// .getTexture().getTextureCoords().y, texturew = e
				// .getTexture().getTextureCoords().z, textureh = e
				// .getTexture().getTextureCoords().w;
				// GL20.glUseProgram(ShaderProgram.programID);
				// GL20.glVertexAttrib4f(ShaderProgram.position, rect.x, rect.y,
				// rect.width, rect.height);
				// position transformations
				final int shake = JumboSettings.shakeintensity;
				// rendering is here
				final Dimension topleft = e.getVectorTopLeft(), topright = e.getVectorTopRight(),
						botleft = e.getVectorBotLeft(), botright = e.getVectorBotRight();
				final int rotation = tex.getRotation();
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				if (rotation > 0) {
					GL11.glRotatef(rotation, 0, 0, 1);
				}
				GL11.glTranslatef(texturecoords.x, texturecoords.y, 0);
				GL11.glMatrixMode(5888);
				GL11.glBegin(e.getRendertype());
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(rect.x + shake + topleft.width, rect.y + shake + rect.height + topleft.height + shake);
				GL11.glTexCoord2f(texturecoords.width, 0);
				GL11.glVertex2f(rect.x + shake + rect.width + topright.width + shake,
						rect.y + shake + rect.height + topright.height + shake);
				GL11.glTexCoord2f(texturecoords.width, texturecoords.height);
				GL11.glVertex2f(rect.x + shake + rect.width + botright.width + shake, rect.y + shake + botright.height);
				GL11.glTexCoord2f(0, texturecoords.height);
				GL11.glVertex2f(rect.x + shake + botleft.width, rect.y + shake + botleft.height);
				GL11.glEnd();
				// GL20.glUseProgram(0);
				// // e.getTexture().unbind();
			}};
	
	//will eventually be in JumboLaunchConfig.
	private static TriggeredAction init = ()->{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_FILL);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
	
	private static TriggeredAction customprepare = ()->{
		
	}
	
	/**
	 * Returns the {@link RenderAction} used to render {@link JumboGraphicsObject}s.
	 * 
	 * @return What is used to render.
	 * @see setRenderAction(RenderAction action)
	 * */
	public static final RenderAction getRenderAction(){
		return render;
	}
	
	/**
	 * Set the {@link RenderAction} used to render {@link JumboGraphicsObject}s.
	 * <p>
	 * Only use this method if you understand what you are doing.
	 * @param action the new render action.
	 * @see getRenderAction()
	 * */
	public static final void setRenderAction(RenderAction action){
		render=action;
	}
	
	/**
	 * Get the custom {@link TriggeredAction} called for graphics initialization in {@link Jumbo#start()}.
	 * 
	 * @return TriggeredAction called during Jumbo.start()
	 * */
	public static final TriggeredAction getCustomInitation(){
		return init;
	}
	
	/**
	 * Override the default OpenGL graphics initialization called during {@link Jumbo#start()}.
	 * <p>
	 * If you are doing your own OpenGL implemenation, use this method to set static properties for OpenGL.
	 * <p>
	 * This method must be called before Jumbo.start(). Otherwise, it will have no effect.
	 * <p>
	 * ONLY USE THIS IF YOU KNOW WHAT YOU ARE DOING! oTHERWISE, IT MAY LEAD TO DIRE CONSEQUENCES!
	 * 
	 * @param action the new initialization.
	 * */
	public static final void setCustomInitation(TriggeredAction action){
		init=action;
	}
	
	/**
	 * Get the current {@link TriggeredAction} getting called each frame to prepare graphics rendering.
	 * 
	 * @return Action being called each frame
	 * @see setCustomPreparationAction(TriggeredAction action)
	 * */
	public static final TriggeredAction getCustomPreparationAction(){
		return customprepare;
	}
	
	/**
	 * Set a custom {@link TriggeredAction} to prepare graphics for rendering each frame.
	 * <p>
	 * If you have custom OpenGL code, use this method. It gets called each frame before graphics rendering starts.
	 * @param action a custom graphics preparation action
	 * @see getCustomPreparationAction()
	 * */
	public static final void setCustomPreparationAction(TriggeredAction action){
		customprepare=action;
	}

	private JumboRenderer() {
	}

	public static void setRefreshcolor(float r, float g, float b) {
		refreshcolor = new TripleFloat(r, g, b);
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}

	/**
	 * The 'display width' is the width of the current window being rendered to.
	 * 
	 * @return the current bounds for rendering
	 * @see #getDisplayheight()
	 * @see #getDisplaywidth(int displaywidh)
	 */
	public static int getDisplaywidth() {
		return renderwidth;
	}

	/**
	 * @param displaywidth
	 *            the displaywidth to set
	 */
	public static void setDisplaywidth(int displaywidth) {
		JumboRenderer.renderwidth = displaywidth;
	}

	/**
	 * @return the displayheight
	 */
	public static int getDisplayheight() {
		return renderheight;
	}

	/**
	 * @param displayheight
	 *            the displayheight to set
	 */
	public static void setDisplayheight(int displayheight) {
		JumboRenderer.renderheight = displayheight;
	}

	/**
	 * @param c
	 */
	public static void setRefreshcolor(Color c) {
		refreshcolor.x = c.getRed() / 255.0f;
		refreshcolor.y = c.getGreen() / 255.0f;
		refreshcolor.z = c.getBlue() / 255.0f;
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}

	private static TripleFloat refreshcolor = new TripleFloat(0, 0, 0);

	/**
	 * Specifies all the settings for OpenGL. Called automatically during the
	 * Game.start() method.
	 */
	public static void init() {
		init.action();
		update();
	}

	/**
	 * Prepares the screen for rendering, which includes clearing it.
	 */
	public static void prepare() {
		if (JumboSettings.trippy) {
			if (Dice.rollPercent(42)) {
				if (Dice.rollBool()) {
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
				} else {
					GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
				}
				GL11.glClearColor(Dice.roll(10) / 10, Dice.roll(10) / 10, Dice.roll(10) / 10, 1);
			}
		} else {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		}
		if (wasResized) {
			update();
		}
		wasResized = Display.wasResized();
		customprepare.action();
	}

	/**
	 * 
	 */
	public static void update() {
		GL11.glLoadIdentity();
		JumboLaunchConfig config = JumboSettings.launchConfig;
		int width = Display.getWidth(), height = Display.getHeight();
		Dimension dim = new Dimension(width, height);
		// Dynamic resizing only works if its larger than the launch dimensions.
		// If its not, it gets strectched, which looks a bit ugly. Because of
		// this, developers should make their base window as small as possible.
		// if (width < config.width || height < config.height) {
		// renderwidth = config.width;
		// renderheight = config.height;
		// GL11.glOrtho(0.0f, config.width, 0, config.height, 0.0f, 1.0f);
		// GL11.glViewport(0, 0, width, height);
		// Maths.currentdim = new Dimension(config.width, config.height);
		// } else {
		// for high dpi modes
		float factor = Display.getPixelScaleFactor();
		renderwidth = (int) (dim.width * factor);
		renderheight = (int) (dim.height * factor);
		GL11.glOrtho(0.0f, renderwidth, 0, renderheight, 0.0f, 1.0f);
		GL11.glViewport(0, 0, renderwidth, renderheight);
		Maths.currentdim = dim;
		// }
		Maths.xmod = (renderwidth / ((float) JumboSettings.launchConfig.width));
		Maths.ymod = (renderheight / ((float) JumboSettings.launchConfig.height));
		if (JumboSettings.wireframe) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		JumboPaintClass.getView().update();
		JumboPaintClass.getPreviousView().update();
		wasResized = false;
	}

	private static int previousid = 0;

	/**
	 * Renders the {@link JumboEntity} e as a quad, where the quad's properties
	 * are decided by the Entities properties.
	 * 
	 * @param e
	 *            Entity to be rendered
	 */
	public static void render(JumboGraphicsObject e) {
		try {
			render.action(e);
		} catch (NullPointerException ex) {
			System.err.println("A parameter in the entity " + e + " is null!");
			ErrorHandler.handle(ex);
		}
	}

	public static TripleFloat getRefreshcolor() {
		return refreshcolor;
	}

	public static void setRefreshcolor(TripleFloat c) {
		refreshcolor = c;
		GL11.glClearColor(refreshcolor.x, refreshcolor.y, refreshcolor.z, 1);
	}
}
