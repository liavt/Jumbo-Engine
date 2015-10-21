package com.jumbo.components.interfaces;

/**
 * Used in {@link JumboRenderMode}. Called by {@link JumboRenderer} when {@link JumboRenderer#render(JumboGraphicsObject)} is called.
 * @see TriggeredAction
 * */
public interface RenderAction extends java.io.Serializable{
  /**
   * Used to render entities by {@link JumboRenderer}. Stored in a {@link JumboRenderMode}.
   * @param e {@link JumboGraphicsObject} to be rendererd
   * @param width the render width, or the bounds that it is allowed to clip.
   * @param height the render height, or the bounds that it is allowed to clip
   * @see JumboRenderer#getDisplaywidth()
   * @see JumboRenderer#getDisplayheight()
   * */
  public void action(JumboGraphicsObject e,int width, int height);
}
