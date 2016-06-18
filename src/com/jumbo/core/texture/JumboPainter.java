package com.jumbo.core.texture;

import com.jumbo.components.JumboColor;

public abstract class JumboPainter implements AutoCloseable {
	protected int[] data;
	protected int width, height, ID;

	private JumboColor color;
	private int size;

	/**
	 * @return the color
	 */
	public JumboColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(JumboColor color) {
		this.color = color;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public JumboPainter(JumboTexture tex) {
		data = tex.getData();
		ID = tex.getID();
		height = tex.getHeight();
		width = tex.getWidth();
	}

	@Override
	public void close() {
		final JumboTexture t = new JumboTexture(ID);
		t.setData(data, width, height);// handles all the stuff for you
		data = null;// 'deallocate' the array. the garbage collector will delete
					// it's space later
	}

	public abstract JumboPainter drawQuad();
}
