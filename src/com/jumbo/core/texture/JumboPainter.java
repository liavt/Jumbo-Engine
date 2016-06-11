package com.jumbo.core.texture;

public abstract class JumboPainter implements AutoCloseable {
	protected int[] data;
	protected int width, height, ID;

	public static JumboPainter loadPainter(final JumboTexture tex, JumboPainter p) {
		p.data = tex.getData();
		p.ID = tex.getID();
		p.height = tex.getHeight();
		p.width = tex.getWidth();
		return p;
	}

	@Override
	public void close() {
		final JumboTexture t = new JumboTexture(ID);
		t.setData(data, width, height);// handles all the stuff for you
		data = null;// 'deallocate' the array. the garbage collector will delete
					// it's space later
	}
}
