package com.jumbo.tools.loaders;

public class JumboFont {
	private int size;
	private final String name;

	public JumboFont(String n) {
		name = n;
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
