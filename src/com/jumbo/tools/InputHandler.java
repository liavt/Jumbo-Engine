package com.jumbo.tools;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * A class meant to replace the {@link Keyboard} class from LWJGL.
 *
 * @author Liav
 */
public final class InputHandler {
	private InputHandler() {
	}

	public static int mousex, mousey, wheel;
	public static boolean clicked, right, leftreleased, rightreleased;
	public static final ArrayList<JumboInputListener> listeners = new ArrayList<>();
	private static final ArrayList<Character> released = new ArrayList<>(), typed = new ArrayList<>();

	public enum Key {
		NONE, ESCAPE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO, MINUS, EQUALS, BACK, TAB, Q, W, E, R, T, Y, U, I, O, P, LBRACKET, RBRACKET, RETURN, LCONTROL, A, S, D, F, G, H, J, K, L, SEMICOLON, APOSTROPHE, GRAVE, LSHIFT, BACKSLASH, Z, X, C, V, B, N, M, COMMA, PERIOD, SLASH, RSHIFT, MULTIPLY, LMENU, SPACE, CAPSLOCK, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, NUMLOCK, SCROLL, NUMPAD7, NUMPAD8, NUMPAD9, SUBTRACT, NUMPAD4, NUMPAD5, NUMPAD6, ADD, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD0, DECIMAL, F11, F12, F13, F14, F15, F16, F17, F18, KANA, F19, CONVERT, NOCONVERT, YEN, NUMPADEQUALS, CIRCUMFLEX, AT, COLON, UNDERLINE, KANJI, STOP, AX, UNLABELED, NUMPADENTER, RCONTROL, SECTION, NUMPADCOMMA, DIVIDE, SYSRQ, RMENU, FUNCTION, PAUSE, HOME, UP, PAGEUP, LEFT, RIGHT, END, DOWN, PAGEDOWN, INSERT, LMETA, RMETA, APPS, POWER, SLEEP;

		boolean down = false;
	}

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
		for (Key k : Key.values()) {
			k.down = Keyboard.isKeyDown(k.ordinal());
		}
	}

	public static boolean isKeyDown(int key) {
		return Key.values()[key].down;
	}

	public static boolean isKeyDown(Key k) {
		return k.down;
	}

	public static boolean isKeyReleased(Key k) {
		return isKeyReleased(k.ordinal());
	}

	@SuppressWarnings("boxing")
	public static boolean isKeyReleased(int key) {
		return released.contains((char) key);
	}

	public static boolean isKeyTyped(Key k) {
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
