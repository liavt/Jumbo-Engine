package com.jumbo.core.modules.presets;

import java.awt.Dimension;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.jumbo.components.Position;
import com.jumbo.components.Quad;
import com.jumbo.components.QuadF;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.modules.JumboRenderModule;
import com.jumbo.core.texture.JumboTexture;
import com.jumbo.util.JumboErrorHandler;
import com.jumbo.util.JumboSettings;
import com.jumbo.util.calculations.JumboMathHandler;

public class JumboRenderModuleGL11 extends JumboRenderModule {
	@Override
	public void resize(int newWidth, int newHeight) {
		GL11.glLoadIdentity();

		JumboMathHandler.currentdim = new Dimension(newWidth, newHeight);
		GL11.glOrtho(0.0f, newWidth, 0, newHeight, 0.0f, 1.0f);
		GL11.glViewport(0, 0, newWidth, newHeight);
		// }
		JumboMathHandler.xmod = (newWidth / ((float) JumboSettings.launchConfig.width()));
		JumboMathHandler.ymod = (newHeight / ((float) JumboSettings.launchConfig.height()));
	}

	@Override
	public void render(JumboGraphicsObject e, int renderwidth, int renderheight) {
		Quad rect = new Quad();
		try {
			rect = e.getInheritedOutbounds();
		} catch (NullPointerException ex)

		{
			JumboErrorHandler.handle(ex, "Entity " + e + " has a null parent!");
		}
		e.setRenderposition(new Position(rect.x, rect.y));

		final boolean outofbounds = (rect.x + rect.width <= 0 || rect.x >= renderwidth || rect.y + rect.height <= 0
				|| rect.y >= renderheight);
		e.setOutofbounds(outofbounds);
		if (e.isRenderable() && !outofbounds)

		{
			// GL11.glLoadIdentity();
			// check to make sure its not offscreen
			// texture binding
			final JumboTexture tex = e.getTexture();
			// to prevent repeat method calls
			tex.bind();
			// JumboColor
			QuadF c = tex.getColor();
			GL11.glColor4f(c.x, c.y, c.width, c.height);
			final QuadF texturecoords = tex.getTextureCoords();
			// float texturex = e.getTexture().getTextureCoords().x,
			// texturey =
			// e
			// .getTexture().getTextureCoords().y, texturew = e
			// .getTexture().getTextureCoords().z, textureh = e
			// .getTexture().getTextureCoords().w;
			// GL20.glUseProgram(ShaderProgram.programID);
			// GL20.glVertexAttrib4f(ShaderProgram.position, rect.x, rect.y,
			// rect.width, rect.height);
			// position transformations
			// rendering is here
			final Dimension topleft = e.getVectorTopLeft(), topright = e.getVectorTopRight(),
					botleft = e.getVectorBotLeft(), botright = e.getVectorBotRight();
			final int rotation = tex.getRotation();
			GL11.glMatrixMode(5890);
			GL11.glLoadIdentity();
			if (rotation > 0) {
				GL11.glRotatef(rotation, 0, 0, 1);
			}
			GL11.glTranslatef(texturecoords.x, texturecoords.y, 0);
			GL11.glMatrixMode(5888);
			GL11.glBegin(e.getRendertype());
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(rect.x + topleft.width, rect.y + rect.height + topleft.height);
			GL11.glTexCoord2f(texturecoords.width, 0);
			GL11.glVertex2f(rect.x + rect.width + topright.width, rect.y + rect.height + topright.height);
			GL11.glTexCoord2f(texturecoords.width, texturecoords.height);
			GL11.glVertex2f(rect.x + rect.width + botright.width, rect.y + botright.height);
			GL11.glTexCoord2f(0, texturecoords.height);
			GL11.glVertex2f(rect.x + botleft.width, rect.y + botleft.height);
			GL11.glEnd();
			// GL20.glUseProgram(0);
			// // e.getTexture().unbind();
		}
	};

	@Override
	public void init() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_FILL);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	};

	@Override
	public void prepare() {
	}
}
