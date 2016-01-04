package com.jumbo.entities.audio;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.input.console.JumboConsole;

public final class JumboAudioHandler {
	private JumboAudioHandler() {
	}

	private volatile static boolean init = false;

	public static int SOUND_NUM = 256;

	static IntBuffer buffer;
	static final IntBuffer source = BufferUtils.createIntBuffer(16);

	private static int currentsounds = 0;

	/** Velocity of the source sound. */
	public static FloatBuffer sourceVel = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 0.0f, 0.0f });
	/** Position of the listener. */
	public static FloatBuffer listenerPos = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 0.0f, 0.0f });
	/** Velocity of the listener. */
	public static FloatBuffer listenerVel = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 0.0f, 0.0f });
	/**
	 * Orientation of the listener. (first 3 elements are "at", second 3 are
	 * "up")
	 */
	public static FloatBuffer listenerOri = BufferUtils.createFloatBuffer(7)
			.put(new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f });

	public static final void init() throws InstantiationException {
		if (init) {
			throw new InstantiationException("[WARNING] Jumbo Audio Player was already initialized!"); //$NON-NLS-1$
		}
		try {
			AL.create();
		} catch (LWJGLException le) {
			le.printStackTrace();
			return;
		}
		buffer = BufferUtils.createIntBuffer(SOUND_NUM);
		AL10.alGenSources(source);
		AL10.alGenBuffers(buffer);
		buffer.limit(1);
		AL10.alDopplerFactor(1.0f);
		AL10.alDopplerVelocity(1.0f);
		check();
		init = true;
	}

	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}

	private static void check() {
		int err = AL10.alGetError();
		if (AL10.alGetError() != AL10.AL_NO_ERROR) {
			JumboConsole.log("An OpenAL error has occured with an ID code of: " + err, 1);
		}
	}

	private static void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
		check();
	}

	static int addSound(JumboSoundObject s) {
		if (isInit()) {
			int size = buffer.limit() - 1;
			AL10.alBufferData(buffer.get(size), s.getData().format, s.getData().data, s.getData().samplerate);
			// buffer.flip();
			buffer.limit(size + 2);
			// buffer.rewind();
			check();
			return size;
		}
		throw new ExceptionInInitializerError("[WARNING] The Jumbo Audio Player has not been initialized yet!"); //$NON-NLS-1$
	}

	static int playSound(JumboSoundObject s) {
		setListenerValues();
		int id = s.getSoundID();

		source.rewind();
		AL10.alSourceQueueBuffers(source.get(currentsounds), buffer.get(id));
		AL10.alSourcef(source.get(currentsounds), AL10.AL_PITCH, s.getPitch());
		AL10.alSourcef(source.get(currentsounds), AL10.AL_GAIN, s.getGain());
		FloatBuffer pos = BufferUtils.createFloatBuffer(5)
				.put(new float[] { (float) s.getBounds().x / (float) JumboSettings.launchConfig.width(),
						(float) s.getBounds().y / (float) JumboSettings.launchConfig.height(),
						(float) s.getBounds().width / (float) JumboSettings.launchConfig.width(),
						(float) s.getBounds().height / (float) JumboSettings.launchConfig.height() });
		AL10.alSource(source.get(currentsounds), AL10.AL_POSITION, pos);
		AL10.alSource(source.get(currentsounds), AL10.AL_VELOCITY, sourceVel);
		AL10.alSourcei(source.get(currentsounds), AL10.AL_LOOPING, s.isLooping() ? AL10.AL_TRUE : AL10.AL_FALSE);

		AL10.alSourcePlay(source.get(currentsounds));
		currentsounds++;
		AL10.alGenSources();
		check();
		return currentsounds - 1;
	}

	public static void tick() {
		for (int i = 0; i < currentsounds; i++) {
			int state = AL10.alGetSourcei(source.get(i), AL10.AL_SOURCE_STATE);
			if (state != AL10.AL_PLAYING && state != AL10.AL_PAUSED) {
				AL10.alSourceUnqueueBuffers(source.get(i));
				AL10.alSourceStop(source.get(i));
				currentsounds--;
				i = currentsounds;
				break;
			}
		}
		setListenerValues();
	}

	public static void destroy() {
		JumboAudioHandler.stopAllSound();
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		AL.destroy();
	}

	public static void stopAllSound() {
		for (int i = 0; i < currentsounds; i++) {
			AL10.alSourceUnqueueBuffers(source.get(i));
			AL10.alSourceStop(source.get(i));
		}
		currentsounds = 0;
	}
}
