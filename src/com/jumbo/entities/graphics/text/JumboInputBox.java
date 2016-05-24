package com.jumbo.entities.graphics.text;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.graphics.JumboButton;
import com.jumbo.entities.graphics.JumboImage;
import com.jumbo.tools.input.JumboInputHandler;
import com.jumbo.tools.input.JumboKey;

public class JumboInputBox extends JumboButton {
	protected StringBuffer text = new StringBuffer();
	protected String prefix = "";

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

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
	 * When a character is inputted, this method is called. By default, it does
	 * not allow for backslashes '\' as the user should not be able to input
	 * control characters. If you want other certain characters not to be
	 * inputed, override this method.
	 * 
	 * @param c
	 *            Character to be filtered.
	 */
	@SuppressWarnings("static-method")
	protected boolean filter(char c) {
		return c != '\\';
	}

	public void clear() {
		text.replace(0, text.length(), "");
		((JumboTextBox) descriptor).setText(prefix + text.toString());
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
		setDescriptor(new JumboText(prefix + text.toString() + "|"));
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
	public void customRender() {
		if (clickaction != action) {
			clickaction = action;
		}
		super.customRender();
		if (boxactive) {
			final ArrayList<Character> keys = JumboInputHandler.getTyped();
			for (Character s : keys) {
				final int id = s;
				if (id == 8) {
					final int size = text.length() - 1;
					if (size >= 0) {
						text.deleteCharAt(size);
						((JumboTextBox) descriptor).setText(prefix + text.toString() + "|");
					}
				} else if (id > 20) {
					if (filter(s)) {
						if (text.length() < max || max < 0) {
							text.append(s);
							setDescriptor(new JumboText(prefix + text.toString() + "|"));
						}
					}
				}
			}
			if (k.clicked || JumboInputHandler.isKeyDown(JumboKey.RETURN)) {
				setDescriptor(new JumboText(prefix + text.toString()));
				boxactive = false;
				final TriggeredAction con = confirm;
				if (con != null) {
					con.action();
				}
			} else {
			}
		}
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
