
package com.jumbo.components.interfaces;

import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.modules.JumboRenderModule;

/**
 * Used in {@link JumboRenderModule}. Called by {@link JumboRenderer} when
 * {@link JumboRenderer#render(JumboGraphicsObject)} is called.
 * 
 * @see TriggeredAction
 */
@FunctionalInterface
public interface RenderAction extends java.io.Serializable {
	/**
	 * Used to render entities by {@link JumboRenderer}. Stored in a
	 * {@link JumboRenderModule}.
	 * 
	 * @param e
	 *            {@link JumboGraphicsObject} to be rendererd
	 * @param width
	 *            the render width, or the bounds that it is allowed to clip.
	 * @param height
	 *            the render height, or the bounds that it is allowed to clip
	 * @see JumboRenderer#getDisplaywidth()
	 * @see JumboRenderer#getDisplayheight()
	 */
	public abstract void action(JumboGraphicsObject e, int width, int height);
}
