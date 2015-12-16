package com.jumbo.entities.audio;

import java.awt.Rectangle;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import org.newdawn.slick.util.ResourceLoader;

import com.jumbo.core.JumboEntity;

public class JumboSoundObject extends JumboEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean looping = false;
	private int soundID = -1, sourceID = -1;

	private float pitch = 1, gain = 1;

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
	}

	public int getSoundID() {
		return soundID;
	}

	public void setSoundID(int soundID) {
		this.soundID = soundID;
	}

	public WaveData getData() {
		return data;
	}

	public void setData(WaveData data) {
		this.data = data;
	}

	private WaveData data;

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	public JumboSoundObject(Rectangle bounds, String path) {
		super(bounds);
		data = WaveData.create(ResourceLoader.getResourceAsStream(path));
		soundID = JumboAudioHandler.addSound(this);
		data.dispose();
	}

	public JumboSoundObject(String path) {
		this(new Rectangle(), path);
	}

	public void play() {
		sourceID = JumboAudioHandler.playSound(this);
	}

	@Override
	public void customRender() {
	}

	public void pause() {
		AL10.alSourcePause(JumboAudioHandler.source.get(sourceID));
	}

	public void rewind() {
		AL10.alSourceRewind(JumboAudioHandler.source.get(sourceID));
	}

	public void stop() {
		AL10.alSourceStop(JumboAudioHandler.source.get(sourceID));
	}

	@Override
	public void customTick() {
	}
}
