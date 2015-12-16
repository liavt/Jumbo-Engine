package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboRenderer;
import com.jumbo.core.JumboTexture;

public class JumboProgressBar extends JumboGraphicsObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected int progress = 0;
	protected boolean vertical = false;
	protected JumboGraphicsObject descriptor;

	/**
	 * @return the vertical
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**
	 * @param vertical
	 *            the vertical to set
	 */
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
		setProgress(progress);
	}

	protected int max = 100;
	protected int min = 0;

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	protected JumboImage overlayarea = new JumboImage(new Rectangle(),
			new JumboTexture(JumboTexture.solidcolor, JumboColor.RED));
	protected TriggeredAction progresschangeaction;

	public JumboProgressBar(Rectangle bounds) {
		super(bounds, new JumboTexture(JumboTexture.solidcolor, JumboColor.DARK_GREY));
		overlayarea.setBounds(new Rectangle(10, 10, bounds.width - 20, bounds.height - 20));
		setProgress(progress);
		overlayarea.setMaintainingPosition(true);
	}

	public int getProgress() {
		return progress;
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
		result = prime * result + progress + min;
		result = prime * result + ((max));
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
		JumboProgressBar other = (JumboProgressBar) obj;
		return progress == other.progress;
	}

	@Override
	public String toString() {
		return hashCode() + "[ " + progress + " / " + max + " ]";
	}

	public void setProgress(int prog) {
		int progress = prog;
		Rectangle bounds = getOutbounds();
		if (progress > max) {
			progress = max;
		}
		if (progress < min) {
			progress = min;
		}
		this.progress = progress;
		float mod = (float) (this.progress) / (float) (Math.abs(max));
		Rectangle overlaybounds = overlayarea.getBounds();
		if (!vertical) {
			overlaybounds.x = 10;
			overlaybounds.height = outbounds.height - 20;
			overlaybounds.width = (int) Math.abs((((bounds.width - 20) * mod)));
			overlayarea.getTexture().getTextureCoords().width = (((bounds.width - 20) * mod)
					/ ((float) bounds.width - 20));
		} else {
			overlaybounds.y = 10;
			overlaybounds.width = outbounds.width - 20;
			overlaybounds.height = (int) Math.abs((((bounds.height - 20) * mod)));
			overlayarea.getTexture().getTextureCoords().height = (((bounds.height - 20) * mod)
					/ ((float) bounds.height - 20));
		}
		if (progresschangeaction != null) {
			progresschangeaction.action();
		}
		setUpdaterequired(false);
	}

	public TriggeredAction getProgresschangeaction() {
		return progresschangeaction;
	}

	public void setProgresschangeaction(TriggeredAction progresschangeaction) {
		this.progresschangeaction = progresschangeaction;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max = max;
		setProgress(progress);
	}

	public JumboTexture getOverlayarea() {
		return overlayarea.getTexture();
	}

	public void setOverlayarea(JumboTexture t) {
		overlayarea.setTexture(new JumboTexture(t, t.getColor()));
		setProgress(progress);
		overlayarea.setMaintainingPosition(true);
	}

	/**
	 * @return the descriptor
	 */
	public JumboGraphicsObject getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor
	 *            the descriptor to set
	 */
	public void setDescriptor(JumboGraphicsObject descriptor) {
		if (descriptor == null) {
			throw new IllegalArgumentException("Input is null!");
		}
		Rectangle bounds = getBounds(), dbounds = descriptor.getBounds();
		this.descriptor = descriptor;
		this.descriptor.increasePosition((int) ((((bounds.width / 2.0f) - ((float) dbounds.getWidth() / 2.0f)))),
				(int) ((((bounds.height / 2.0f) - ((float) dbounds.getHeight() / 2.0f)))));
		this.descriptor.setMaintainingPosition(true);
	}

	@Override
	public Rectangle additionalCalculations(Rectangle bounds) {
		if (descriptor != null) {
			descriptor.setMaintainingDimensions(true);
			descriptor.setMaintainingPosition(true);
		}
		return bounds;
	}

	@Override
	public void setRenderable(boolean renderable) {
		super.setRenderable(renderable);
		if (descriptor != null) {
			descriptor.setRenderable(renderable);
		}
		if (overlayarea != null) {
			overlayarea.setRenderable(renderable);
		}
	};

	@Override
	public void calculatePosition() {
		super.calculatePosition();
		setProgress(progress);
	}

	public void addProgress(int i) {
		this.progress += i;
		setProgress(progress);
	}

	@Override
	public void setMaintainx(boolean b) {
		super.setMaintainx(b);
		if (this.descriptor != null) {
			this.descriptor.setMaintainx(b);
		}
	}

	@Override
	public void setMaintainy(boolean b) {
		super.setMaintainy(b);
		if (this.descriptor != null) {
			this.descriptor.setMaintainy(b);
		}
	}

	@Override
	public void setMaintainwidth(boolean b) {
		super.setMaintainwidth(b);
		if (this.descriptor != null) {
			this.descriptor.setMaintainwidth(b);
		}
	}

	@Override
	public void setMaintainheight(boolean b) {
		super.setMaintainheight(b);
		if (this.descriptor != null) {
			this.descriptor.setMaintainheight(b);
		}
	}

	@Override
	public void customRender() {
		if (overlayarea != null) {
			final ArrayList<JumboEntity> parents = overlayarea.getParents();
			if (!parents.contains(this)) {
				parents.add(this);
			}
		}
		if (descriptor != null) {
			final ArrayList<JumboEntity> parents = descriptor.getParents();
			if (!parents.contains(this)) {
				parents.add(this);
			}
		}
		JumboRenderer.render(this);
		if (overlayarea != null) {
			overlayarea.render();
		}
		if (descriptor != null) {
			descriptor.render();
		}
	}

	@Override
	public void customTick() {
		if (overlayarea != null) {
			overlayarea.tick();
		}
		if (descriptor != null) {
			descriptor.tick();
		}
	}

}
