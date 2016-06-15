package com.jumbo.core.modules.presets;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.jumbo.core.modules.JumboTextureModule;

public class JumboTextureModuleGL11 extends JumboTextureModule {
	@Override
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@Override
	public void bind(int id) {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	@Override
	public void unload(int id) {
		glDeleteTextures(id);
	}

	@Override
	public int load(int width, int height, int... pixels) {
		final IntBuffer buffer = ByteBuffer.allocateDirect(pixels.length << 2).order(ByteOrder.nativeOrder())
				.asIntBuffer();
		buffer.put(pixels).flip();
		final int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
	}

	@Override
	public int[] getData(int width, int height, int id) {
		final IntBuffer buf = BufferUtils.createIntBuffer(width * height);
		bind(id);
		// glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
		// GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		final int[] data = new int[buf.capacity()];
		buf.get(data);
		return data;
	}

}
