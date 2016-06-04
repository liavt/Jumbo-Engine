package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.Position;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.components.interfaces.TriggeredEvent;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.text.JumboText;
import com.jumbo.entities.graphics.text.JumboTextBox;
import com.jumbo.tools.input.JumboInputListener;

public class JumboButton extends JumboGraphicsObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected static final JumboTexture hovertex = new JumboTexture(new JumboColor(0, 0, 255, 125));
	protected static final JumboTexture disabledtex = new JumboTexture(new JumboColor(100, 100, 100, 125));

	protected boolean triggered = false;

	protected JumboGraphicsObject descriptor;

	protected JumboImage icon;
	protected JumboImage hovericon = new JumboImage(hovertex, new Rectangle(0, 0, 1, 1));
	protected JumboImage disabledicon;

	protected TriggeredEvent hoverevent;
	protected TriggeredEvent trigger;
	protected TriggeredAction clickaction;

	public JumboImage getHoverIcon() {
		return this.hovericon;
	}

	public void setHoverIcon(BufferedImage hovericon) {
		this.hovericon = new JumboImage(hovericon, getBounds());
	}

	public TriggeredEvent getHoverevent() {
		return this.hoverevent;
	}

	public void setHoverevent(TriggeredEvent hoverevent) {
		this.hoverevent = hoverevent;
	}

	public TriggeredEvent getTrigger() {
		return this.trigger;
	}

	public void setTrigger(TriggeredEvent trigger) {
		this.trigger = trigger;
	}

	public TriggeredAction getClickAction() {
		return this.clickaction;
	}

	public void setClickAction(TriggeredAction triggeraction) {
		this.clickaction = triggeraction;
	}

	public TriggeredAction getHoveraction() {
		return this.hovericon.getCustomTickAction();
	}

	public void setHoveraction(TriggeredAction hoveraction) {
		this.hovericon.setCustomTickAction(hoveraction);
	}

	public JumboButton(BufferedImage icon, BufferedImage hovericon, Rectangle rectangle) {
		super(rectangle, null);
		if (icon != null) {
			this.icon = new JumboImage(icon, new Rectangle(0, 0, rectangle.width, rectangle.height));
			this.icon.addParent(this);
			this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
					new Rectangle(0, 0, getBounds().width, getBounds().height));
			this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
			this.disabledicon.addParent(this);
		}
		if (hovericon != null) {
			this.hovericon = new JumboImage(hovericon, new Rectangle(0, 0, rectangle.width, rectangle.height));
			this.hovericon.addParent(this);
		}
	}

	public JumboButton(BufferedImage icon, int x, int y, int w, int h) {
		super(new Rectangle(x, y, w, h), null);
		if (icon != null) {
			this.icon = new JumboImage(icon, new Rectangle(0, 0, w, h));
			this.icon.addParent(this);
			this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
					new Rectangle(0, 0, getBounds().width, getBounds().height));
			this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
			this.disabledicon.addParent(this);
		}
		this.hovericon.addParent(this);
	}

	public JumboButton(BufferedImage icon, BufferedImage hovericon, int x, int y, int w, int h) {
		super(new Rectangle(x, y, w, h), null);
		if (icon != null) {
			this.icon = new JumboImage(icon, new Rectangle(0, 0, w, h));
			this.icon.addParent(this);
			this.hovericon = new JumboImage(hovericon, new Rectangle(0, 0, w, h));
			this.hovericon.addParent(this);
			this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
					new Rectangle(0, 0, getBounds().width, getBounds().height));
			this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
			this.disabledicon.addParent(this);
		}
	}

	public JumboButton(int x, int y, int w, int h, JumboTexture tex) {
		super(new Rectangle(x, y, w, h), null);
		this.icon = new JumboImage(new Rectangle(0, 0, w, h), new JumboTexture(tex));
		this.icon.addParent(this);
		this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
				new Rectangle(0, 0, getBounds().width, getBounds().height));
		this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
		this.disabledicon.addParent(this);
		this.hovericon.addParent(this);
	}

	public JumboButton(JumboTexture tex, Rectangle rectangle) {
		super(rectangle, tex);
		if (tex != null) {
			JumboTexture tex2 = new JumboTexture(tex);
			this.icon = new JumboImage(new Rectangle(0, 0, rectangle.width, rectangle.height), new JumboTexture(tex2));
			this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
					new Rectangle(0, 0, getBounds().width, getBounds().height));
			this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
			this.disabledicon.addParent(this);
		}
		this.hovericon.addParent(this);
		this.hovericon.setBounds(new Rectangle(0, 0, rectangle.width, rectangle.height));
		setBounds(rectangle);
	}

	public JumboButton(JumboTexture tex, JumboTexture hover, Rectangle rectangle) {
		super(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height), null);
		if (tex != null) {
			JumboTexture tex2 = new JumboTexture(tex);
			this.icon = new JumboImage(new Rectangle(0, 0, rectangle.width, rectangle.height), new JumboTexture(tex2));
			this.icon.addParent(this);
			this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
					new Rectangle(0, 0, getBounds().width, getBounds().height));
			this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
			this.disabledicon.addParent(this);
		}
		if (hover != null) {
			this.hovericon = new JumboImage(new Rectangle(0, 0, rectangle.width, rectangle.height), hover);
		}
	}

	public JumboButton(JumboImage img, Rectangle rect) {
		super(rect, null);
		img.setBounds(new Rectangle(0, 0, rect.width, rect.height));
		this.icon = img;
		this.disabledicon = new JumboImage(new JumboTexture(this.icon.getTexture()),
				new Rectangle(0, 0, getBounds().width, getBounds().height));
		this.disabledicon.getTexture().setColor(new JumboColor(100, 100, 100, 125));
		this.disabledicon.addParent(this);
		this.hovericon.addParent(this);
		this.hovericon.setBounds(new Rectangle(0, 0, rect.width, rect.height));
		setBounds(rect);
	}

	@Override
	public void setBounds(Rectangle bounds) {
		super.setBounds(bounds);
		if (this.icon != null) {
			this.icon.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));
		}
		if (this.disabledicon != null) {
			this.disabledicon.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));
		}
		if (this.disabledicon != null) {
			this.hovericon.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));
		}
	}

	protected static JumboInputListener k = new JumboInputListener();

	public JumboGraphicsObject getDescriptor() {
		return this.descriptor;
	}

	@Override
	public void setMaintainx(boolean b) {
		super.setMaintainx(b);
		;
		if (this.icon != null) {
			this.icon.setMaintainx(b);
		}
		if (this.hovericon != null) {
			this.hovericon.setMaintainx(b);
		}
		if (this.disabledicon != null) {
			this.disabledicon.setMaintainx(b);
		}
		if (this.descriptor != null) {
			this.descriptor.setMaintainx(b);
		}
	}

	@Override
	public void setMaintainy(boolean b) {
		super.setMaintainy(b);
		;
		if (this.icon != null) {
			this.icon.setMaintainy(b);
		}
		if (this.hovericon != null) {
			this.hovericon.setMaintainy(b);
		}
		if (this.disabledicon != null) {
			this.disabledicon.setMaintainy(b);
		}
		if (this.descriptor != null) {
			this.descriptor.setMaintainy(b);
		}
	}

	@Override
	public void setMaintainwidth(boolean b) {
		super.setMaintainwidth(b);
		;
		if (this.icon != null) {
			this.icon.setMaintainwidth(b);
		}
		if (this.hovericon != null) {
			this.hovericon.setMaintainwidth(b);
		}
		if (this.disabledicon != null) {
			this.disabledicon.setMaintainwidth(b);
		}
		if (this.descriptor != null) {
			this.descriptor.setMaintainwidth(b);
		}
	}

	@Override
	public void setMaintainheight(boolean b) {
		super.setMaintainheight(b);
		;
		if (this.icon != null) {
			this.icon.setMaintainheight(b);
		}
		if (this.hovericon != null) {
			this.hovericon.setMaintainheight(b);
		}
		if (this.disabledicon != null) {
			this.disabledicon.setMaintainheight(b);
		}
		if (this.descriptor != null) {
			this.descriptor.setMaintainheight(b);
		}
	}

	public void setDescriptor(JumboGraphicsObject descriptor) {
		Rectangle bounds = getBounds(), dbounds = descriptor.getBounds();
		this.descriptor = descriptor;
		if (!this.descriptor.getParents().contains(this)) {
			this.descriptor.addParent(this);
		}
		this.descriptor.increasePosition((int) ((((bounds.width / 2.0f) - ((float) dbounds.getWidth() / 2.0f)))),
				(int) ((((bounds.height / 2.0f) - ((float) dbounds.getHeight() / 2.0f)))));
		this.descriptor.setMaintainingPosition(true);
	}

	public void setDescriptor(JumboText t) {
		Rectangle bounds = getBounds();
		this.descriptor = new JumboTextBox(new Rectangle(0, 0, bounds.width, bounds.height), t);
		this.descriptor.addParent(this);
		this.descriptor.setMaintainingPosition(true);
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @return the triggered
	 */
	public boolean isTriggered() {
		return this.triggered;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public JumboGraphicsObject getIcon() {
		return this.icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = new JumboImage(icon, getBounds());
	}

	public void trigger() {
		if (this.clickaction != null) {
			this.clickaction.action();
		}
	}

	@Override
	public void setRenderable(boolean render) {
		if (this.icon != null) {
			this.icon.setRenderable(render);
		}
		if (this.hovericon != null) {
			this.hovericon.setRenderable(render);
		}
		if (this.disabledicon != null) {
			this.disabledicon.setRenderable(render);
		}
		if (this.descriptor != null) {
			this.descriptor.setRenderable(render);
		}
	}

	@Override
	public JumboTexture getTexture() {
		return this.icon.getTexture();
	}

	@Override
	public void setTexture(JumboTexture t) {
		if (this.icon != null) {
			this.icon.setTexture(t);
		} else {
			this.icon = new JumboImage(t, new Rectangle(0, 0, 0, 0));
			this.icon.addParent(this);
			setBounds(getBounds());
		}
	}

	@Override
	public void customRender() {
		ArrayList<JumboEntity> parents = new ArrayList<>();
		final Rectangle outpos = getOutbounds();
		final Rectangle iconbounds = new Rectangle(0, 0, outpos.width, outpos.height);
		final Position renderpos;
		if (this.icon != null) {
			parents = this.icon.getParents();
			if (!parents.contains(this)) {
				parents.add(this);
			}
			this.icon.setOutbounds(iconbounds);
			this.icon.render();
			renderpos = this.icon.getRenderposition();
		} else {
			final Rectangle bounds = getInheritedOutbounds();
			renderpos = new Position(bounds.x, bounds.y);
		}
		int x = k.mousex;
		int y = k.mousey;
		final Rectangle outbounds = new Rectangle(renderpos.x, renderpos.y, outpos.width, outpos.height);
		final boolean clicked = k.leftreleased;
		final boolean hovering = x >= outbounds.x && x <= outbounds.width + outbounds.x && y >= outbounds.y
				&& y <= outbounds.height + outbounds.y,
				triggertriggered = this.trigger != null && this.trigger.triggered();
		final boolean triggeraction = (clicked && hovering) || triggertriggered, triggerhover = hovering
				|| (this.triggered && triggertriggered) || (this.hoverevent != null && this.hoverevent.triggered());
		final boolean buttonactive = !this.outofbounds && this.active && k.inputenabled;
		if (buttonactive) {
			if (triggeraction) {
				if (!this.triggered) {
					if (this.clickaction != null) {
						this.clickaction.action();
					}
					this.triggered = true;
				}
			} else if (triggerhover) {
				// JumboDisplayManager.getFrame().setCursor(new Cursor(12));
				this.triggered = false;
				if (this.hovericon != null) {
					parents = this.hovericon.getParents();
					if (!parents.contains(this)) {
						parents.add(this);
					}
					this.hovericon.setOutbounds(iconbounds);
					this.hovericon.render();
				}
			} else {
				this.triggered = false;
			}
		} else {
			if (this.triggered && !triggeraction && !clicked) {
				this.triggered = false;
			}
			if (this.disabledicon != null) {
				parents = this.disabledicon.getParents();
				if (!parents.contains(this)) {
					parents.add(this);
				}
				this.disabledicon.setOutbounds(iconbounds);
				this.disabledicon.render();
			}
		}
		if (this.descriptor != null) {
			parents = this.descriptor.getParents();
			if (!parents.contains(this)) {
				parents.add(this);
			}
			this.descriptor.render();
		}
	}

	/**
	 * @param hovericon
	 *            the hovericon to set
	 */
	public void setHoverIcon(JumboImage hovericon) {
		this.hovericon = hovericon;
	}

	/**
	 * @param hovericon
	 *            the hovericon to set
	 */
	public void setHoverIcon(JumboTexture hovericon) {
		this.hovericon.setTexture(hovericon);
	}

	@Override
	public void customTick() {
		if (this.icon != null) {
			this.icon.tick();
		}
		if (this.hovericon != null) {
			this.hovericon.tick();
		}
		if (this.descriptor != null) {
			this.descriptor.tick();
		}
		if (this.disabledicon != null) {
			this.disabledicon.tick();
		}
		final TriggeredAction action = getCustomTickAction();
		if (action != null) {
			action.action();
		}
	}

}
