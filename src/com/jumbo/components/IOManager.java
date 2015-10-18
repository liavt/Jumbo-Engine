package com.jumbo.components;

import com.jumbo.tools.loaders.StringHandler;

/**
 * Abstract class that provides the ability for statically defined classes (like
 * enums) to have custom IO actions, like saving and loading. Currently, there
 * is only an implementation for Strings.
 * <p>
 * In order to use it in an enum, just define a variable for it, and ask for it
 * in a constructor. Now, your enums can have custom IO actions! This method
 * saves space, and looks neater.
 * 
 * @author Liav
 * @see StringHandler
 */
public abstract class IOManager {
	public abstract String save();

	public abstract void load(String s);
}
