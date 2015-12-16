package com.jumbo.core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Required by a {@link JumboPaintClass} to render.
 */
public class JumboScene {
	// holds entities similar to a graphics group
	// the game displays one view at a time, and can change at any time
	protected ArrayList<JumboEntity> entities = new ArrayList<>();

	public void setActive(boolean bool) {
		for (JumboEntity e : entities) {
			e.setActive(bool);
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

	public void render() {
		for (Iterator<JumboEntity> iterator = entities.iterator(); iterator.hasNext();) {
			final JumboEntity e = iterator.next();
			if (e != null) {
				e.render();
			} else {
				deleteEntity(e);
			}
		}
		customRender();
	}

	public void tick() {
		for (JumboEntity e : entities) {
			if (e != null && !e.isDead()) {
				if (e.active) {
					e.tick();
				}
			} else {
				deleteEntity(e);
			}
		}
		customTick();
	}

	protected void customTick() {

	}

	protected void customRender() {
	}

	public void update() {
		for (JumboEntity e : entities) {
			e.setUpdaterequired(true);
			e.calculatePosition();
		}
	}
}
