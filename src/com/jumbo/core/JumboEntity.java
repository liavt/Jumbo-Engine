package com.jumbo.core;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.jumbo.components.Position;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.entities.JumboGraphicsGroup;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.calculations.JumboMathHandler;

/**
 * The superclass for anything related to the Jumbo engine
 */
public abstract class JumboEntity implements java.io.Serializable, java.lang.Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructer
	 *
	 * @param bounds
	 *            for rendering, measured in pixels
	 */
	public JumboEntity(Rectangle bounds) {
		this.bounds = bounds;
		this.outbounds = bounds;
	}

	@Override
	public JumboEntity clone() {
		try {
			// Serialization of object
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(this);

			// De-serialization of object
			final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			final ObjectInputStream in = new ObjectInputStream(bis);
			return (JumboEntity) in.readObject();
		} catch (Exception e) {
			JumboErrorHandler.handle(e);
		}
		throw new AssertionError("[WARNING] Failed to serialize " + this);
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		bounds.x = x;
		bounds.y = y;
		updaterequired = true;
	}

	/**
	 * @return The final position of this entity. Does not contain inherited
	 *         positions.
	 */
	public Rectangle getOutbounds() {
		return outbounds;
	}

	/**
	 * @param outbounds
	 */
	public void setOutbounds(Rectangle outbounds) {
		this.outbounds = outbounds;
	}

	private Rectangle bounds = new Rectangle();// position and
	// dimensions of the
	// entity
	private boolean updaterequired = true;
	protected TriggeredAction customtickaction, customrenderaction;
	private Dimension optimizedbounds = new Dimension(1, 1);

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	protected boolean active = true;

	public Dimension getOptimizedbounds() {
		return optimizedbounds;
	}

	public void setOptimizedbounds(Dimension optimizedbounds) {
		this.optimizedbounds = optimizedbounds;
	}

	/**
	 * @return The custom tick action.
	 */
	public TriggeredAction getCustomTickAction() {
		return customtickaction;
	}

	/**
	 * @param customaction
	 */
	public void setCustomTickAction(TriggeredAction customaction) {
		this.customtickaction = customaction;
	}

	/**
	 * @return Whether the entity will undergo position and dimension
	 *         recalculation next frame.
	 * @see #calculatePosition()
	 * @see #getOutbounds()
	 */
	public boolean isUpdaterequired() {
		return updaterequired;
	}

	/**
	 * @return the customrenderaction
	 */
	public TriggeredAction getCustomRenderAction() {
		return customrenderaction;
	}

	/**
	 * @param customrenderaction
	 *            the customrenderaction to set
	 */
	public void setCustomRenderAction(TriggeredAction customrenderaction) {
		this.customrenderaction = customrenderaction;
	}

	/**
	 * @param updaterequired
	 *            Whether the entity will undergo position recalculation next
	 *            frame.
	 */
	public void setUpdaterequired(boolean updaterequired) {
		this.updaterequired = updaterequired;
	}

	/**
	 * Calculates the entity position for rendering, and saves the new position
	 * in the entity's outbounds.
	 * <p>
	 * Called automatically each frame if {@link #isUpdaterequired()} is true.
	 * <p>
	 * After all calculations are done, sets {@link #isUpdaterequired()} to be
	 * false.
	 *
	 * @see #getOutbounds
	 * @see #getInheritedOutbounds()
	 */
	public void calculatePosition() {
		this.outbounds = JumboMathHandler.calculateEntityPosition(this);
	}

	@SuppressWarnings("static-method")
	public Rectangle additionalCalculations(Rectangle bounds) {
		return bounds;
	}

	protected Rectangle outbounds = bounds;

	protected boolean maintainx = false, maintainy = false, maintainwidth = true, maintainheight = true;
	protected boolean dead = false;// if true, the entity will be removed from

	/**
	 * Set whether the entity's position should be changed when the screen
	 * resolution is changed.
	 *
	 * @param maintainposition
	 *            Whether the entity's position should be adjusted when the
	 *            window is resized
	 */
	public void setMaintainingPosition(boolean maintainposition) {
		updaterequired = true;
		setMaintainx(maintainposition);
		setMaintainy(maintainposition);
	}

	/**
	 * @param id
	 *            The entity's current parent entity in index id
	 * @return The entity's current parent entity in index id
	 * @see JumboEntity#addParent(JumboEntity)
	 */
	public JumboEntity getParent(int id) {
		return parent.get(id);
	}

	/**
	 * @return Returns an {@link ArrayList} of all the entity's parents
	 */
	public ArrayList<JumboEntity> getParents() {
		return parent;
	}

	/**
	 * @param parent
	 *            The parent to be added
	 */
	public void addParent(JumboEntity parent) {
		this.parent.add(parent);
	}

	protected ArrayList<JumboEntity> parent = new ArrayList<>();

	/**
	 * Use of this method makes {@link #isUpdaterequired()} return true, and
	 * thus recalculates the position of this entity when
	 * {@link #calculatePosition()} is called.
	 *
	 * @return The current bounds, free of calculations
	 */
	public Rectangle getBounds() {
		updaterequired = true;
		return bounds;
	}

	/**
	 * Destroys this entity, makes {@link #isDead()} true and calls
	 * {@link #finalize()}.
	 * <p>
	 * Next frame, the entity will be removed from the current
	 * {@link JumboLayer} or {@link JumboGraphicsGroup}.
	 */
	public void destroy() {
		dead = true;
		try {
			this.finalize();
		} catch (Throwable e) {
			JumboErrorHandler.handle(e);
		}
	}

	/**
	 * Called when rendering for each entity.
	 * <p>
	 * Calls {@link #getInheritedOutbounds} on each of it's parents.
	 *
	 * @return The sum of the outbounds of this entity and all of it's parents.
	 * @see #calculatePosition()
	 * @see #getOutbounds()
	 */
	public Rectangle getInheritedOutbounds() {
		final int size = parent.size();
		if (size == 0) {
			return outbounds;
		}
		int parentx = 0, parenty = 0;
		for (int i = size - 1; i >= 0; i--) {
			final Rectangle parentpos = parent.get(i).getInheritedOutbounds();
			parentx += parentpos.x;
			parenty += parentpos.y;
		}
		return new Rectangle(outbounds.x + parentx, outbounds.y + parenty, (outbounds.width), outbounds.height);
	}

	/**
	 * Calculations with the new bounds will be done next frame.
	 * <p>
	 * Makes {@link #isUpdaterequired()} return true.
	 *
	 * @param bounds
	 *            What the entity's bounds should be changed to.
	 * @see #getBounds()
	 * @see #getOutbounds()
	 */
	public void setBounds(Rectangle bounds) {
		updaterequired = true;
		this.bounds = bounds;
	}

	/**
	 * @param maintainratio
	 *            Whether the entity's dimensions should be changed during frame
	 *            calculations.
	 */
	public void setMaintainingDimensions(boolean maintainratio) {
		updaterequired = true;
		setMaintainwidth(maintainratio);
		setMaintainheight(maintainratio);
	}

	/**
	 * @return the maintainx
	 */
	public boolean isMaintainingX() {
		return maintainx;
	}

	/**
	 * @param maintainx
	 *            the maintainx to set
	 */
	public void setMaintainx(boolean maintainx) {
		updaterequired = true;
		this.maintainx = maintainx;
	}

	/**
	 * @return the maintainy
	 */
	public boolean isMaintainingY() {

		return maintainy;
	}

	/**
	 * @param maintainy
	 *            the maintainy to set
	 */
	public void setMaintainy(boolean maintainy) {
		updaterequired = true;
		this.maintainy = maintainy;
	}

	/**
	 * @return the maintainwidth
	 */
	public boolean isMaintainingWidth() {
		return maintainwidth;
	}

	/**
	 * @param maintainwidth
	 *            the maintainwidth to set
	 */
	public void setMaintainwidth(boolean maintainwidth) {
		updaterequired = true;
		this.maintainwidth = maintainwidth;
	}

	/**
	 * @return the maintainheight
	 */
	public boolean isMaintainingHeight() {
		return maintainheight;
	}

	/**
	 * @param maintainheight
	 *            the maintainheight to set
	 */
	public void setMaintainheight(boolean maintainheight) {
		updaterequired = true;
		this.maintainheight = maintainheight;
	}

	/**
	 * An entity that is dead is considered useless, and will be deleted.
	 *
	 * @return Whether the entity is dead and will be removed next frame
	 * @see #destroy()
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * @param dead
	 *            Whether the entity should be deleted
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Increases the entity's position by single integers
	 * <p>
	 * Makes {@link #isUpdaterequired()} return true.
	 *
	 * @param x
	 *            Value to increase horizontal position by
	 * @param y
	 *            Value to increase vertical position by
	 * @see #getPosition()
	 */
	public void increasePosition(int x, int y) {
		updaterequired = true;
		bounds = new Rectangle(bounds.x + x, bounds.y + y, bounds.width, bounds.height);
	}

	public void increasePosition(Position p) {
		increasePosition(p.x, p.y);
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
		result = prime * result + (dead ? 1231 : 1237);
		result = prime * result + (maintainheight ? 1231 : 1237);
		result = prime * result + (maintainwidth ? 1231 : 1237);
		result = prime * result + (maintainx ? 1231 : 1237);
		result = prime * result + (maintainy ? 1231 : 1237);
		result = prime * result + ((outbounds == null) ? 0 : outbounds.hashCode());
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
		final JumboEntity other = (JumboEntity) obj;
		if (dead != other.dead) {
			return false;
		}
		if (maintainheight != other.maintainheight) {
			return false;
		}
		if (maintainwidth != other.maintainwidth) {
			return false;
		}
		if (maintainx != other.maintainx) {
			return false;
		}
		if (maintainy != other.maintainy) {
			return false;
		}
		if (outbounds == null) {
			if (other.outbounds != null) {
				return false;
			}
		} else if (!outbounds.equals(other.outbounds)) {
			return false;
		}
		return true;
	}

	public void setPosition(Position p) {
		this.setPosition(p.x, p.y);
	}

	/**
	 * The method in which the overriding {@link JumboEntity} will be updated.
	 * <p>
	 * Called once every frame by the parent {@link JumboLayer} or
	 * {@link JumboGraphicsGroup}.
	 * <p>
	 * If {@link #isDead()}, this will not be called , and instead, the
	 * overriding {@link JumboEntity} will be deleted.
	 */
	protected abstract void customRender();// for rendering

	public final void render() {
		calculatePosition();
		customRender();
		if (customrenderaction != null) {
			customrenderaction.action();
		}
	}

	public abstract void customTick();// for calculations

	public final void tick() {
		if (active) {
			customTick();
			if (customtickaction != null) {
				customtickaction.action();
			}
		}
	}

	public void setParents(ArrayList<JumboEntity> thisparents) {
		parent = thisparents;
	}

	public Position getPosition() {
		updaterequired = true;
		return new Position(bounds.x, bounds.y);
	}
}
