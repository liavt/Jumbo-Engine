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
		checkLayer(l);
	}

	public void removeLayer(JumboLayer l) {
		layers.remove(l);
	}

	public void setLayer(int index, JumboLayer l) {
		layers.set(index, l);
		checkLayer(l);
	}

	public void render() {
		customRender();
		for (int i = 0; i < layers.size(); i++) {
			final JumboLayer l = layers.get(i);
			if (l != null) {
				l.render();
			} else {
				layers.remove(i);
			}
		}
	}

	private final void checkLayer(JumboLayer l) {
		if (l == null) {
			throw new NullPointerException("Layer " + layers.indexOf(l) + " is null!");
		}
	}

	public void onWindowUpdate() {
		for (int i = 0; i < layers.size(); i++) {
			final JumboLayer l = layers.get(i);
			if (l != null) {
				l.onWindowUpdate();
			} else {
				layers.remove(i);
			}
		}
	}

	public void tick() {
		customTick();
		for (int i = 0; i < layers.size(); i++) {
			final JumboLayer l = layers.get(i);
			if (l != null) {
				l.tick();
			} else {
				layers.remove(i);
			}
		}
	}
}
