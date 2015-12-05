package com.jumbo.components.entities.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboInputHandler;

public class JumboInputBox extends JumboButton {
	protected StringBuffer text = new StringBuffer();

	public JumboInputBox(BufferedImage icon, BufferedImage hovericon, int x, int y, int w, int h) {
		super(icon, hovericon, x, y, w, h);
	}

	public JumboInputBox(BufferedImage icon, BufferedImage hovericon, Rectangle rectangle) {
		super(icon, hovericon, rectangle);
	}

	public JumboInputBox(BufferedImage icon, int x, int y, int w, int h) {
		super(icon, x, y, w, h);
	}

	public JumboInputBox(int x, int y, int w, int h, JumboTexture tex) {
		super(x, y, w, h, tex);
	}

	public JumboInputBox(JumboImage img, Rectangle rect) {
		super(img, rect);
	}

	public JumboInputBox(JumboTexture tex, JumboTexture hover, Rectangle rectangle) {
		super(tex, hover, rectangle);
	}

	public JumboInputBox(JumboTexture tex, Rectangle rectangle) {
		super(tex, rectangle);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected boolean boxactive = false;
	protected int max = -1;

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * When a character is inputted, this method is called. By default, it
	 * always returns 'true.' If you want certain characters not to be inputed,
	 * override this method.
	 * 
	 * @param c
	 *            Character to be filtered.
	 */
	@SuppressWarnings("static-method")
	protected boolean filter(char c) {
		return true;
	}

	protected TriggeredAction click, confirm;

	/**
	 * @return the confirm
	 */
	public TriggeredAction getConfirm() {
		return confirm;
	}

	/**
	 * @param confirm
	 *            the confirm to set
	 */
	public void setConfirm(TriggeredAction confirm) {
		this.confirm = confirm;
	}

	private final TriggeredAction action = () -> {
		boxactive = true;
		if (click != null) {
			click.action();
		}
	};

	@Override
	public void setClickAction(TriggeredAction a) {
		click = a;
	}

	@Override
	public TriggeredAction getClickAction() {
		return click;
	}

	@Override
	public void tick() {
		if (clickaction != action) {
			clickaction = action;
		}
		if (boxactive) {
			final ArrayList<Character> keys = JumboInputHandler.getTyped();
			for (Character s : keys) {
				final int id = s;
				if (id == 8) {
					final int size = text.length() - 1;
					if (size >= 0) {
						text.deleteCharAt(size);
					}
				} else if (id > 20) {
					if (filter(s)) {
						if (text.length() < max || max < 0) {
							text.append(s);
						}
					}
				}
			}
			if (k.clicked || JumboInputHandler.isKeyDown(JumboInputHandler.Key.RETURN)) {
				setDescriptor(new JumboText(text.toString()));
				boxactive = false;
				final TriggeredAction con = confirm;
				if (con != null) {
					con.action();
				}
			} else {
				setDescriptor(new JumboText(text.toString() + "|"));
			}
		}
		super.tick();
	}

	/**
	 * @return the text
	 */
	public StringBuffer getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(StringBuffer text) {
		this.text = text;
	}

	/**
	 * @return the active
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

}
