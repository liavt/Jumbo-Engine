package com.jumbo.rendering;
import com.jubmo.components.interfaces.RenderAction;
import com.jubmo.components.interfaces.TriggeredAction;

/**
*Class that contains various interfaces to be used for renderring by the {@link JumboRenderer}. 
*<p>
*Contains default interfaces that can be overridden to provide a custom OpenGL implementation.
*<p>
*The JumboRenderer can have multiple JumboRenderModes at once, and can switch between them easily.
*<p>
*Additionally, each {@link JumboViewport} has a default JumboRenderMode, for {@link JumboGraphicsObject}s that dont want to override it.
*@see JumboRenderer
*@see RenderAction
*@see TriggeredAction
*@see JumboViewport
**/
public final class JumboRenderMode{
    /**
    *Each render pass, when you bind a new texture, set this {@link LamdbaInteger} to be the new texture ID. This way, you can avoid repeat texture rebindings.
    **/
    //TODO need to move this somewhere else
    public LambdaInteger prevousid=-1;

    private static RenderAction render = (JumboEntity e, int renderwidth, int renderheight)->{	
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
				if (previousid.getNum() != id) {
					tex.bind();
					previousid.setNum(id);
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
	 *<p>
	 *Each time {@link JumboRenderer#render(JumboGraphicsObject en)} is called, the current {@link JumboRenderMode}'s {@link RenderAction} is called.
	 *<p>
	 *When overriding the default {@link RenderAction}, make sure to account for all of a {@link JumboGraphicsObject}'s and {@link JumboTexture} properties, like its dimensions, rotation, and tint.
	 * @return What is used to render.
	 * @see setRenderAction(RenderAction action)
	 * */
	public static final RenderAction getRenderAction(){
		return render;
	}
	
	/**
	 * Set the {@link RenderAction} used to render {@link JumboGraphicsObject}s.
	 *<p>
	 *Each time {@link JumboRenderer#render(JumboGraphicsObject en)} is called, the current {@link JumboRenderMode}'s {@link RenderAction} is called.
	 *<p>
	 *When overriding the default {@link RenderAction}, make sure to account for all of a {@link JumboGraphicsObject}'s and {@link JumboTexture} properties, like its dimensions, rotation, and tint.
	 * @param action the new render action.
	 * @see getRenderAction()
	 * */
	public static final void setRenderAction(RenderAction action){
    if(action==null)throw new NullPointerException("Input is null!");
		render=action;
	}
	
	/**
	 * Get the custom {@link TriggeredAction} called for graphics initialization when this {@link JumboRenderMode} is set.
	 * 
	 * @return TriggeredAction called during Jumbo.start()
	 *@see TriggeredAction
	 * */
	public static final TriggeredAction getCustomInitialization(){
		return init;
	}
	
	/**
	 * Override the default OpenGL graphics initialization called when this {@link JumboRenderMode} is set.
	 * <p>
	 * If you are doing your own OpenGL implemenation, use this method to set static properties for OpenGL.
	 * 
	 * @param action the new initialization action.
	 	 *@see TriggeredAction
	 * */
	public static final void setCustomInitialization(TriggeredAction action){
	if(action==null)throw new NullPointerException("Input is null!");
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
	if(action==null)throw new NullPointerException("Input is null!");
		customprepare=action;
	}
	
	/**
	*Default constructor for {@link JumboRenderMode}.
	*<p>
	*Contains the default rendering implemenations.
	**/
	public JumboRenderMode(){
	}
	
	/**
	*Constructor for {@link JumboRenderMode}.
	*@param prep custom graphics preparation action called before graphics rendering
	*@param init custom graphics initiation called when this JumboRenderMode is used for the first time. 
	*@see TriggeredAction
	*@see RenderAction
	*@see JumboRenderer
	*@see JumboViewport
	**/
	public JumboRenderMode(TriggeredAction prep, TriggeredAction init, RenderAction render){
	if(init==null)throw new NullPointerException("Initiation is null!");
	if(prep==null)throw new NullPointerException("Preperation action is null!");
	if(render==null)throw new NullPointerException("Render action is null!");
	customprepare=prep;
	this.init=init;
	this.render=render;
	}
	
	/**
	*Calls this {@link JumboRenderMode}'s prepare action.
	*@see getCustomPreparationAction()
	**/
	public final void prepare(){
	customprepare.action();}
	
		*Calls this {@link JumboRenderMode}'s initialization action.
	*@see getCustomInitializationAction()
	**/
	public final void init(){
	init.action();
	}
	
		*Calls this {@link JumboRenderMode}'s {@link RenderActoin}.
	*@see getRenderAction()
	**/
	public final void render(JumboGraphicsObject e, int width, int height){
	render.action(e,width,height);
	}
}
