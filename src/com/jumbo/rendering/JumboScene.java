package com.jumbo.rendering;

import java.util.ArrayList;

import com.jumbo.components.entities.ui.JumboButton;

/**
 * Required by a {@link JumboPaintClass} to render.
 */
public class JumboScene {
	// holds entities similar to a graphics group
	// the game displays one view at a time, and can change at any time
	protected ArrayList<JumboEntity> entities = new ArrayList<>();

	public void buttonsActive(boolean bool) {
		for (JumboEntity e : entities) {
			if (e instanceof JumboButton) {
				((JumboButton) e).setActive(bool);
			}
		}
	}

	public void addEntity(JumboEntity e) {
		entities.add(e);
	}

	public void clear() {
		entities.clear();
	}

	public void deleteEntity(JumboEntity e) {
		entities.remove(e);
	}

	public ArrayList<JumboEntity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<JumboEntity> e) {
		entities = e;
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			final JumboEntity e = entities.get(i);
			e.calculatePosition();
			e.tick();
			if (e.isDead()) {
				entities.remove(e);
			}
		}
		customTick();
	}

	protected void customTick() {
	}

	public void update() {
		for (JumboEntity e : entities) {
			e.setUpdaterequired(true);
			e.calculatePosition();
		}
	}
}
