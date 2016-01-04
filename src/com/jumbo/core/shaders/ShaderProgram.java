package com.jumbo.core.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.input.console.JumboConsole;

@Deprecated
public class ShaderProgram {
	private static final String vertexMainPath = "src/com/jumbo/rendering/shaders/vertexShaderMain.txt",
			fragmentMainPath = "src/com/jumbo/rendering/shaders/fragmentShaderMain.txt";
	public static int programID = GL20.glCreateProgram(), position;

	private static int createShader(String path, int type) {
		int shader = GL20.glCreateShader(type);
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			JumboErrorHandler.handle(e, "Problem reading shader file!");
		}
		GL20.glShaderSource(shader, shaderSource);
		GL20.glCompileShader(shader);
		if (GL11.glGetError() != 0) {
			JumboConsole.log("ERROR COMPILING SHADERS, ERROR CODE: " + GL11.glGetError(), 1);
		}

		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			JumboConsole.log("Shader of type " + type + " wasn't able to be compiled correctly. Error log:", 1);
			JumboConsole.log(GL20.glGetShaderInfoLog(shader, 1024), 1);
		}
		return shader;
	}

	public static void init() {
		int vshader = createShader(vertexMainPath, GL20.GL_VERTEX_SHADER);
		int fshader = createShader(fragmentMainPath, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(programID, vshader);
		GL20.glAttachShader(programID, fshader);
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		GL20.glUseProgram(programID);
		GL20.glUseProgram(0);
	}
}
