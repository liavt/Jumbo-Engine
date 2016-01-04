package com.jumbo.entities.graphics;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.jumbo.components.JumboColor;
import com.jumbo.components.Position;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.JumboTexture;
import com.jumbo.tools.input.console.JumboConsole;

@Deprecated
public class JumboLine extends JumboGraphicsObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int width = 1;

	public int getWidth() {
		setUpdaterequired(true);
		return width;
	}

	public void setWidth(int width) {
		setUpdaterequired(true);
		this.width = width;
	}

	public Position getPos1() {
		setUpdaterequired(true);
		return pos1;
	}

	public void setPos1(Position pos1) {
		setUpdaterequired(true);
		this.pos1 = pos1;
	}

	public Position getPos2() {
		setUpdaterequired(true);
		return pos2;
	}

	public void setPos2(Position pos2) {
		setUpdaterequired(true);
		this.pos2 = pos2;
	}

	public static final int MAX_WIDTH = 101;

	public void refreshPosition() {
		int width = this.width, max = MAX_WIDTH;
		if (this.width >= max) {
			throw new IndexOutOfBoundsException("LINE WIDTH IS GREATER THAN MAX_WIDTH, WHICH IS " + max);
		}
		int ydifference = Math.abs(pos1.y - pos2.y), xdifference = Math.abs(pos1.x - pos2.x),
				height = ydifference / (max - width);
		setBounds(new Rectangle(pos1.x, pos1.y, width, ydifference));
		setVectorBotRight(new Dimension(xdifference, height / 2));
		setVectorBotLeft(new Dimension(xdifference, height));
		setVectorTopRight(new Dimension(0, height));
		setVectorTopRight(new Dimension(0, height / 2));
		JumboConsole.log(getBounds());
	}

	private Position pos1, pos2;

	// @Override
	// public void setRendertype(int type) {
	// super.setRendertype(3);
	// }

	public JumboLine(Position pos1, Position pos2, int width) {
		super(new Rectangle(pos1.x, pos1.y, width, pos1.y - pos2.y),
				new JumboTexture(JumboTexture.solidcolor, JumboColor.WHITE));
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.width = width;
		refreshPosition();
		// setRendertype(3);
	}

	@Override
	public void customRender() {
		if (isUpdaterequired()) {
			refreshPosition();
		}
		JumboRenderer.render(this);
	}

	@Override
	public void customTick() {

	}

}
