package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.jumbo.components.JumboColor;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboTexture;
import com.jumbo.tools.calculations.JumboMathHandler;
import com.jumbo.tools.input.JumboInputListener;

public class JumboSlider extends JumboProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean active = false, enabled = true;
	private JumboImage valuebox;
	protected JumboImage disabledicon;

	public JumboSlider(Rectangle bounds, boolean vert) {
		super(bounds);
		vertical = vert;
		if (!vertical) {
			valuebox = new JumboImage(new Rectangle(0, 0, 10, overlayarea.getBounds().height),
					new JumboTexture(JumboTexture.solidcolor, JumboColor.LIGHT_GREY));
		} else {
			valuebox = new JumboImage(new Rectangle(0, 0, overlayarea.getBounds().width, 10),
					new JumboTexture(JumboTexture.solidcolor, JumboColor.LIGHT_GREY));
		}
		valuebox.addParent(overlayarea);

		this.disabledicon = new JumboImage(new JumboTexture(new JumboColor(100, 100, 100, 125)),
				new Rectangle(0, 0, getBounds().width, getBounds().height));
		this.disabledicon.addParent(this);
	}

	public JumboSlider(Rectangle bounds) {
		this(bounds, false);
	}

	@Override
	public void setProgress(int progress) {
		super.setProgress(progress);
		if (valuebox != null && this.progress > min) {
			Rectangle bounds = valuebox.getOutbounds();
			if (!vertical) {
				bounds.x = (int) (((((float) (this.progress - min) / (float) (max - min)))
						* (getOutbounds().width - 20)));
				bounds.x -= 5;
				bounds.y = 0;
				bounds.width = 10;
				bounds.height = overlayarea.getOutbounds().height;
			} else {
				bounds.y = (int) (((((float) (this.progress - min) / (float) (max - min)))
						* (getOutbounds().height - 20)));
				bounds.y -= 5;
				bounds.x = 0;
				bounds.height = 10;
				bounds.width = overlayarea.getOutbounds().width;
			}
		}
	}

	public JumboTexture getValuebox() {
		return valuebox.getTexture();
	}

	public void setValuebox(JumboTexture t) {
		this.valuebox.setTexture(t);
	}

	protected static JumboInputListener k = new JumboInputListener();

	@Override
	public void customRender() {
		super.customRender();
		if (enabled) {
			int x = k.mousex, y = k.mousey, rx = renderpos.x, ry = renderpos.y;
			boolean clicked = Mouse.isButtonDown(0), colliding = JumboMathHandler.collides(x, y,
					new Rectangle(rx, ry, outbounds.width, outbounds.height));
			if (colliding) {
				// JumboDisplayManager.getFrame().setCursor(new
				// Cursor(Cursor.HAND_CURSOR));
				active = true;
			}
			if (!clicked) {
				active = false;
			}
			if (active) {
				if (!vertical) {
					x -= rx;
					x = (int) (((((float) x / (float) outbounds.width) * (max - min))));
					if (x != progress) {
						setProgress(x + min);
					}
				} else {
					y -= ry;
					y = (int) (((((float) y / (float) outbounds.height) * (max - min))));
					if (y != progress) {
						setProgress(y + min);
					}
				}
			}
		} else {
			if (disabledicon != null) {
				ArrayList<JumboEntity> parents = disabledicon.getParents();
				if (!parents.contains(this)) {
					parents.add(this);
				}
				disabledicon.setOutbounds(new Rectangle(0, 0, outbounds.width, outbounds.height));
				disabledicon.render();
			}
		}
		valuebox.render();
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void setRenderable(boolean b) {
		super.setRenderable(b);
		if (valuebox != null) {
			valuebox.setRenderable(b);
		}
		if (disabledicon != null) {
			disabledicon.setRenderable(b);
		}
	}

}
