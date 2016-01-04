package com.jumbo.tools.input;

import java.util.ArrayList;

public final class JumboKeyBinder {

	public static final ArrayList<JumboKeyBind> keys = new ArrayList<>();

	public static boolean isKeyDown(String key) {
		JumboKeyBind b = getKey(key);
		return b.primarydown || b.altdown;
	}

	public static void refresh() {
		for (JumboKeyBind b : keys) {
			b.primarydown = JumboInputHandler.isKeyDown(b.primary);
			b.altdown = JumboInputHandler.isKeyDown(b.alt);
		}
	}

	public static void addKeyBind(JumboKeyBind b) {
		keys.add(b);
	}

	public static ArrayList<JumboKeyBind> getKeyBinds() {
		return keys;
	}

	public static void setKeyBinds(ArrayList<JumboKeyBind> b) {
		keys.clear();
		keys.addAll(b);
	}

	public static void setKeyBind(int index, JumboKeyBind b) {
		keys.set(index, b);
	}

	public static void removeKeyBind(JumboKeyBind b) {
		keys.remove(b);
	}

	public static void removeKeyBind(int i) {
		keys.remove(i);
	}

	public static JumboKeyBind getKey(String key) {
		for (JumboKeyBind b : keys) {
			if (b.name.equals(key)) {
				return b;
			}
		}
		throw new IllegalArgumentException("NO KEY FOUND BY THE NAME OF " + key);
	}
}
