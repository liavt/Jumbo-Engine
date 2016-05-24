package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.text.JumboText;
import com.jumbo.entities.graphics.text.JumboTextBox;

public class JumboChooser extends JumboGraphicsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JumboButton left, right;
	private final JumboTextBox desc;
	private String[] options;
	private short current = 0;
	private TriggeredAction changeaction;

	public JumboChooser(Rectangle b, JumboTexture t, String[] options) {
		this(b, t, null, options);
	}

	public <T extends Enum<?>> JumboChooser(Rectangle b, T[] enums, JumboTexture tex, JumboTexture h) {
		this(b, tex, h, null);
		final String[] values = new String[enums.length];
		for (int i = 0; i < enums.length; i++) {
			values[i] = enums[i].toString();
		}
		options = values;
		refreshText();
	}

	/**
	 * @return the options
	 */
	public String[] getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(String[] options) {
		setUpdaterequired(true);
		current = 0;
		this.options = options;
		refreshText();
	}

	/**
	 * @return the current
	 */
	public short getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(short current) {
		setUpdaterequired(true);
		this.current = current;
		refreshText();
	}

	/**
	 * @return the changeaction
	 */
	public TriggeredAction getChangeaction() {
		return changeaction;
	}

	/**
	 * @param changeaction
	 *            the changeaction to set
	 */
	public void setChangeaction(TriggeredAction changeaction) {
		this.changeaction = changeaction;
	}

	/**
	 * @return the left
	 */
	public JumboButton getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public JumboButton getRight() {
		return right;
	}

	/**
	 * @return the desc
	 */
	public JumboTextBox getDesc() {
		return desc;
	}

	public JumboChooser(Rectangle bounds, JumboTexture tex, JumboTexture hover, String[] options) {
		super(bounds, null);
		this.options = options;
		left = new JumboButton(tex, hover, new Rectangle(0, 0, 50, 50));
		right = new JumboButton(tex, hover, new Rectangle(bounds.width - 50, 0, 50, 50));
		left.setMaintainingPosition(true);
		left.setDescriptor(new JumboText("$<"));
		left.addParent(this);
		left.setClickAction(() -> {
			final short size = (short) this.options.length;
			short next = (short) (current - 1);
			if (next < 0) {
				next = (short) (size - 1);
			} else if (next >= size) {
				next = 0;
			}
			current = next;
			refreshText();
		});
		right.setMaintainingPosition(true);
		right.setDescriptor(new JumboText(">"));
		right.addParent(this);
		right.setClickAction(() -> {
			final short size = (short) this.options.length;
			short next = (short) (current + 1);
			if (next < 0) {
				next = (short) (size - 1);
			} else if (next >= size) {
				next = 0;
			}
			current = next;
			refreshText();
		});
		desc = new JumboTextBox(new Rectangle(50, 0, bounds.width - 50, 50), new JumboText(""));
		desc.setMaintainingPosition(true);
		desc.addParent(this);
		refreshText();
	}

	private void refreshText() {
		if (options != null) {
			if (current >= options.length) {
				current = (short) (options.length - 1);
			}
			if (current < 0) {
				current = 0;
			}
			desc.setText(options[current]);
		}
		if (changeaction != null) {
			changeaction.action();
		}
	}

	@Override
	public void customRender() {
		if (left != null) {
			final ArrayList<JumboEntity> e = left.getParents();
			if (!e.contains(this)) {
				e.add(this);
			}
			left.render();
		}
		if (right != null) {
			final ArrayList<JumboEntity> e = right.getParents();
			if (!e.contains(this)) {
				e.add(this);
			}
			right.render();
		}
		if (desc != null) {
			final ArrayList<JumboEntity> e = desc.getParents();
			if (!e.contains(this)) {
				e.add(this);
			}
			desc.render();
		}
	}

	@Override
	public Rectangle additionalCalculations(Rectangle bounds) {
		right.getBounds().x = bounds.width - 50;
		desc.getBounds().width = bounds.width - 100;
		refreshText();
		return bounds;
	}

	@Override
	public void setRenderable(boolean b) {
		super.setRenderable(b);
		left.setRenderable(b);
		right.setRenderable(b);
		desc.setRenderable(b);
	}

	public String getSelected() {
		return options[current];
	}

	@Override
	public void customTick() {
		if (left != null) {
			left.tick();
		}
		if (right != null) {
			right.tick();
		}
		if (desc != null) {
			desc.tick();
		}
	}

}
