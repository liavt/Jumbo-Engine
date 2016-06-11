package com.jumbo.core;

import java.util.ArrayList;

/**
 * Required by a {@link JumboPaintClass} to render.
 */
public class JumboLayer implements Cloneable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public JumboLayer clone() {
		final JumboLayer out = new JumboLayer();
		out.setEntities(entities);
		return out;
	}

	// holds entities similar to a graphics group
	// the game displays one view at a time, and can change at any time
	protected ArrayList<JumboEntity> entities = new ArrayList<>();

	public JumboLayer(JumboEntity... e) {
		for (JumboEntity l : e) {
			entities.add(l);
		}
	}

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

	public final void render() {
		for (int i = 0; i < entities.size(); i++) {
			final JumboEntity e = entities.get(i);
			if (e != null) {
				e.render();
			}
		}
		customRender();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entities == null) ? 0 : entities.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JumboLayer other = (JumboLayer) obj;
		if (entities == null) {
			if (other.entities != null) {
				return false;
			}
		} else if (!entities.equals(other.entities)) {
			return false;
		}
		return true;
	}

	public final void tick() {
		checkForDead();
		for (JumboEntity e : entities) {
			// i still have to check to see if they are null and dead, because
			// an entity's tick() can add some null entities to the layer.
			if (e != null && !e.isDead()) {
				if (e.active) {
					e.tick();
				}
			}
		}
		customTick();
	}

	private void checkForDead() {
		for (int i = 0; i < entities.size(); i++) {
			final JumboEntity e = entities.get(i);
			if (e == null || e.isDead()) {
				entities.remove(i);
				i--;
			}
		}
	}

	protected void customTick() {

	}

	protected void customRender() {
	}

	public void onWindowUpdate() {
		for (JumboEntity e : entities) {
			if (e != null && !e.isDead()) {
				e.setUpdaterequired(true);
				e.calculatePosition();
			}
		}
	}
}
