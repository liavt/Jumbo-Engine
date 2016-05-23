package com.jumbo.core;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.jumbo.components.Position;

public abstract class JumboGraphicsObject extends JumboEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JumboTexture tex = new JumboTexture();
	protected boolean outofbounds = false;
	protected int rendertype = 7;

	public int getRendertype() {
		return rendertype;
	}

	public void setRendertype(int rendertype) {
		this.rendertype = rendertype;
	}

	public Position getRenderposition() {
		return renderpos;
	}

	public void setRenderposition(Position renderpos) {
		this.renderpos = renderpos;
	}

	protected Position renderpos = new Position();

	public boolean isOutofbounds() {
		return outofbounds;
	}

	public void setOutofbounds(boolean outofbounds) {
		this.outofbounds = outofbounds;
	}

	public JumboGraphicsObject(Rectangle bounds, JumboTexture texture) {
		super(bounds);
		this.tex = texture;
	}

	public Dimension getVectorTopRight() {
		return vectorTopRight;
	}

	public void setVectorTopRight(Dimension vectorTopRight) {
		this.vectorTopRight = vectorTopRight;
	}

	public Dimension getVectorTopLeft() {
		return vectorTopLeft;
	}

	public void setVectorTopLeft(Dimension vectorTopLeft) {
		this.vectorTopLeft = vectorTopLeft;
	}

	public Dimension getVectorBotLeft() {
		return vectorBotLeft;
	}

	public void setVectorBotLeft(Dimension vectorBotLeft) {
		this.vectorBotLeft = vectorBotLeft;
	}

	public Dimension getVectorBotRight() {
		return vectorBotRight;
	}

	public void setVectorBotRight(Dimension vectorBotRight) {
		this.vectorBotRight = vectorBotRight;
	}

	public Dimension vectorTopRight = new Dimension(0, 0), vectorTopLeft = new Dimension(0, 0),
			vectorBotLeft = new Dimension(0, 0), vectorBotRight = new Dimension(0, 0);

	private boolean renderable = true;// whether the entity should be rendered

	// on screen or not (for stuff like
	// audio, graphicsgroup, etc)

	/**
	 * Returns if the entity is currently being rendered during runtime.
	 * 
	 * @return Whether the entity is being rendered currently.
	 * @see JumboRenderer
	 * @see JumboLayer
	 */
	public boolean isRenderable() {
		return renderable;
	}

	/**
	 * Set whether the entity should be rendered by the {@link JumboRenderer}
	 * class during runtime.
	 * 
	 * @param renderable
	 *            Whether the entity should be rendered.
	 * @see JumboRenderer
	 * @see JumboLayer
	 */
	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}

	public JumboTexture getTexture() {
		return tex;
	}

	public void setTexture(JumboTexture tex) {
		this.tex = tex;
	}
}
