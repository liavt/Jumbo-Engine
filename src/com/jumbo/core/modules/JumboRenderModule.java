package com.jumbo.core.modules;

import com.jumbo.components.interfaces.RenderAction;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboLayer;
import com.jumbo.core.JumboRenderer;

/**
 * Class that contains various interfaces to be used for rendering by the
 * {@link JumboRenderer}.
 * <p>
 * Contains default interfaces that can be overridden to provide a custom OpenGL
 * implementation.
 * <p>
 * The JumboRenderer can have multiple JumboRenderModes at once, and can switch
 * between them easily.
 * 
 * @see JumboRenderer
 * @see RenderAction
 * @see TriggeredAction
 * @see JumboLayer
 **/
public abstract class JumboRenderModule {

	public abstract void init();

	public abstract void prepare();

	public abstract void render(JumboGraphicsObject e, int renderwidth, int renderheight);

	public abstract void resize(int newWidth, int newHeight);

}
