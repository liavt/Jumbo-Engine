package com.jumbo.rendering.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

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
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		GL20.glShaderSource(shader, shaderSource);
		GL20.glCompileShader(shader);
		if (GL11.glGetError() != 0)
			System.err.println("ERROR COMPILING SHADERS, ERROR CODE: " + GL11.glGetError());

		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Shader of type " + type + " wasn't able to be compiled correctly. Error log:");
			System.err.println(GL20.glGetShaderInfoLog(shader, 1024));
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
