package com.jumbo.tools.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.jumbo.tools.JumboSettings;

/**
 * A class meant to replace the {@link Keyboard} class from LWJGL.
 *
 * @author Liav
 */
public final class JumboInputHandler {
	private JumboInputHandler() {
	}

	public static int mousex, mousey, wheel;
	public static boolean clicked, right, leftreleased, rightreleased;
	public static final ArrayList<JumboInputListener> listeners = new ArrayList<>();
	private static final ArrayList<Character> released = new ArrayList<>(), typed = new ArrayList<>();

	@SuppressWarnings("boxing")
	public static void refresh() {
		if (!Keyboard.areRepeatEventsEnabled()) {
			Keyboard.enableRepeatEvents(true);
		}
		mousex = Mouse.getX();
		mousey = Mouse.getY();
		clicked = Mouse.isButtonDown(0);
		right = Mouse.isButtonDown(1);
		wheel = Mouse.hasWheel() ? Mouse.getDWheel() : 0;
		final boolean lastleftreleased = leftreleased, lastrightreleased = rightreleased;
		leftreleased = false;
		rightreleased = false;
		// there is a strange bug where it would say the mouse was released 2
		// frames in a row. This would cause multiple buttons to fire, which is
		// undesirable
		while (Mouse.next()) {
			if (!Mouse.getEventButtonState()) {
				if (Mouse.getEventButton() == 0 && !lastleftreleased) {
					leftreleased = true;
				} else if (Mouse.getEventButton() == 1 && !lastrightreleased) {
					rightreleased = true;
				}
			}
		}
		final boolean inputenabled = JumboSettings.inputEnabled;
		for (JumboInputListener k : listeners) {
			k.clicked = clicked;
			k.mousex = mousex;
			k.mousey = mousey;
			k.wheel = wheel;
			k.rightclicked = right;
			k.inputenabled = inputenabled;
			k.leftreleased = leftreleased;
			k.rightreleased = rightreleased;
		}
		released.clear();
		typed.clear();
		while (Keyboard.next()) {
			final char key = Keyboard.getEventCharacter();
			if (!Keyboard.getEventKeyState()) {
				released.add(key);
			} else {
				typed.add(key);
			}
		}
		for (JumboKey k : JumboKey.values()) {
			k.down = Keyboard.isKeyDown(k.ordinal());
		}
	}

	public static boolean isKeyDown(int key) {
		return JumboKey.values()[key].down;
	}

	public static boolean isKeyDown(JumboKey k) {
		return k.down;
	}

	public static boolean isKeyReleased(JumboKey k) {
		return isKeyReleased(k.ordinal());
	}

	@SuppressWarnings("boxing")
	public static boolean isKeyReleased(int key) {
		return released.contains((char) key);
	}

	public static boolean isKeyTyped(JumboKey k) {
		return isKeyTyped(k.ordinal());
	}

	@SuppressWarnings("boxing")
	public static boolean isKeyTyped(int key) {
		return typed.contains((char) key);
	}

	public static ArrayList<Character> getTyped() {
		return typed;
	}

	public static int numberOfKeys() {
		return Keyboard.getKeyCount();
	}
}
