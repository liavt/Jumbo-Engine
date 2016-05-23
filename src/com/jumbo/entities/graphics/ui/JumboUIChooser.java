package com.jumbo.entities.graphics.ui;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.jumbo.entities.graphics.JumboChooser;

public class JumboUIChooser extends JumboChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboUIChooser(Rectangle b, ArrayList<String> options) {
		this(b, new String[] { "" });
		final int size = options.size();
		final String[] newoptions = new String[size];
		for (int i = 0; i < size; i++) {
			newoptions[i] = options.get(i);
		}
		setOptions(newoptions);
	}

	public JumboUIChooser(Rectangle b, String[] options) {
		super(b, JumboUIHandler.create(50, 50), JumboUIHandler.create(50, 50, JumboUIHandler.getHovercolor()), options);
	}

	public <T extends Enum<?>> JumboUIChooser(Rectangle b, T[] enums) {
		super(b, enums, JumboUIHandler.create(50, 50), JumboUIHandler.create(50, 50, JumboUIHandler.getHovercolor()));
	}

}
