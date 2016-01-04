package com.jumbo.tools.input.console;

import java.util.ArrayList;

public class JumboMessageType {

	private static final ArrayList<JumboMessageType> types = new ArrayList<>();

	public static final int INFO = addType(new JumboMessageType("INFO")),
			WARNING = addType(new JumboMessageType("WARNING", true)),
			ERROR = addType(new JumboMessageType("ERROR", true));

	public static int addType(JumboMessageType t) {
		types.add(t);
		return types.size() - 1;
	}

	public static JumboMessageType getType(int id) {
		return types.get(id);
	}

	/**
	 * @return the types
	 */
	public static ArrayList<JumboMessageType> getTypes() {
		return types;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	private final boolean error;
	private final String name;

	public JumboMessageType(String name) {
		this(name, false);
	}

	public JumboMessageType(String name, boolean error) {
		this.error = error;
		this.name = name;
	}
}
