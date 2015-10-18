package com.jumbo.components.entities;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.jumbo.components.interfaces.ArrayIteratorAction;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboEntity;
import com.jumbo.rendering.JumboGraphicsObject;

public class JumboGraphicsGroup extends JumboEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<JumboEntity> array = new ArrayList<>();
	private ArrayIteratorAction action;

	@Override
	public void setActive(boolean b) {
		for (JumboEntity e : array) {
			e.setActive(b);
		}
	}

	public ArrayIteratorAction getAction() {
		return action;
	}

	public void setAction(ArrayIteratorAction action) {
		this.action = action;
	}

	public JumboGraphicsGroup(Rectangle bounds) {
		super(bounds);
	}

	public JumboGraphicsGroup(JumboEntity[] array) {
		super(new Rectangle(0, 0, 0, 0));
		this.array = new ArrayList<>(array.length);
		for (JumboEntity e : array) {
			this.array.add(e);
		}
	}

	public JumboGraphicsGroup(ArrayList<JumboEntity> array) {
		super(new Rectangle(0, 0, 0, 0));
		this.array = array;
	}

	public JumboGraphicsGroup() {
		super(new Rectangle(0, 0, 0, 0));
	}

	@Override
	public void tick() {
		final Rectangle bounds = getOutbounds();
		final int w = bounds.width, h = bounds.height;
		final boolean blockview = w > 0 && h > 0;
		if (blockview) {
			GL11.glLoadIdentity();
			int x = bounds.x, y = bounds.y;
			GL11.glOrtho(x, w + x, y, h + y, 0.0f, 1.0f);
			GL11.glViewport(x, y, w, h);
		}
		for (int i = 0; i < array.size(); i++) {
			final JumboEntity e = array.get(i);
			if (e != null && !e.isDead()) {
				final ArrayList<JumboEntity> parents = e.getParents();
				if (!parents.contains(this)) {
					e.addParent(this);
				}
				e.calculatePosition();
				e.tick();
			} else {
				array.remove(e);
			}
			if (action != null) {
				action.action(e);
			}
		}
		if (blockview) {
			final int displaywidth = Jumbo.getFrameWidth(), displayheight = Jumbo.getFrameHeight();
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0f, displaywidth, 0, displayheight, 0.0f, 1.0f);
			GL11.glViewport(0, 0, displaywidth, displayheight);
		}
		final TriggeredAction action = getCustomaction();
		if (action != null) {
			action.action();
		}
	}

	public void setRenderable(boolean renderable) {
		for (JumboEntity e : array) {
			if (e instanceof JumboGraphicsObject) {
				((JumboGraphicsObject) e).setRenderable(renderable);
			}
		}
	}

	@Override
	public void calculatePosition() {
		super.calculatePosition();
		for (JumboEntity e : array) {
			if (e != null) {
				e.calculatePosition();
			}
		}
	}

}
