package com.jumbo.core.texture;

//it is intented to be used statically, and you can't override static methods. making a bunch of lambdas would be a massive mess
public abstract class JumboTextureBinder {
	public abstract void bind(int id);

	public abstract void unbind();

	public abstract void unload(int id);

	public abstract int load(int width, int height, int... pixels);

	public abstract int[] getData(int width, int height, int id);
}
