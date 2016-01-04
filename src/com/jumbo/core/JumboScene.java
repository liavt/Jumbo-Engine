package com.jumbo.core;

import java.util.ArrayList;

public class JumboScene {
	private final ArrayList<JumboLayer> layers = new ArrayList<>(4);

	public JumboScene(JumboLayer... arr) {
		for (JumboLayer l : arr) {
			layers.add(l);
		}
	}

	public JumboScene() {
	}

	public void clearLayers() {
		layers.clear();
	}

	protected void customRender() {
	}

	protected void customTick() {
	}

	public ArrayList<JumboLayer> getLayers() {
		return layers;
	}

	public void addLayer(JumboLayer l) {
		layers.add(l);
	}

	public void removeLayer(JumboLayer l) {
		layers.remove(l);
	}

	public void setLayer(int index, JumboLayer l) {
		layers.set(index, l);
	}

	public void render() {
		customRender();
		for (JumboLayer l : layers) {
			l.render();
		}
	}

	public void onWindowUpdate() {
		for (JumboLayer l : layers) {
			l.onWindowUpdate();
		}
	}

	public void tick() {
		customTick();
		for (JumboLayer l : layers) {
			l.tick();
		}
	}
}
