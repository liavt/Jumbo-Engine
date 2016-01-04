package com.jumbo.tools.input;

public class JumboKeyBind {
	public final String name;
	public final JumboKey primary, alt;
	public boolean primarydown = false, altdown = false;

	JumboKeyBind(String name, JumboKey primary, JumboKey alt) {
		this.name = name;
		this.primary = primary;
		this.alt = alt;
	}

	JumboKeyBind(String name, JumboKey primary) {
		this(name, primary, JumboKey.NONE);
	}
}
