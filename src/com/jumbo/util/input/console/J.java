package com.jumbo.util.input.console;

import com.jumbo.util.JumboErrorHandler;

/**
 * Class for shorthand printing via JumboConsole. Contains the l() method, so
 * instead of typing out JumboConsole.log, you just do J.l(). Much faster for
 * debugging purposes.
 * <P>
 * To chain prints together, you do J.l().l().l(). It returns itself basically,
 * so you can do that chain.
 * <p>
 * It also has shorthand for JumboErrorHandler.handle. It is J.h(). It can be
 * chained together like before, but that is likely unneccessary, as errors
 * close the program. You can still do J.l().h()
 * <p>
 * <table>
 * <col width="25%"/> <col width="75%"/> <thead>
 * <tr>
 * <th>Shorthand</th>
 * <th>Equivalent</th>
 * </tr>
 * <thead> <tbody>
 * <tr>
 * <td>J.l(String message, int type)</td>
 * <td>JumboConsole.log(String message, int type)</td>
 * </tr>
 * <tr>
 * <td>J.w(String message)</td>
 * <td>JumboConsole.log(String message, JumboMessageType.WARNING)</td>
 * </tr>
 * <tr>
 * <td>J.e(String message)</td>
 * <td>JumboConsole.log(String message, JumboMessageType.ERROR)</td>
 * </tr>
 * <tr>
 * <td>J.d(String message)</td>
 * <td>JumboConsole.log(String message, JumboMessageType.DEBUG)</td>
 * </tr>
 * <tr>
 * <td>J.h(Throwable t,String message)</td>
 * <td>JumboErrorHandler.handle(Throwable t,String message)</td>
 * </tr>
 * <tr>
 * <td>J.f()</td>
 * <td>JumboConsole.flush()</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author Liav
 * @see JumboConsole
 */
public final class J {
	private J() {

	}

	public final static class JChainer {
		JChainer() {
		}

		public JChainer l(Object message, int type) {
			JumboConsole.log(message, type);
			return this;
		}

		public JChainer l(int type, Object message) {
			return l(message, type);

		}

		public JChainer l(Object message) {
			return l(message, JumboMessageType.INFO);

		}

		public JChainer l(int type, Object... messages) {
			final StringBuilder b = new StringBuilder();
			for (Object s : messages) {
				b.append(s);
			}
			return l(b.toString(), type);

		}

		public JChainer l(Object... messages) {
			return l(JumboMessageType.INFO, messages);// it returns itself so i
														// can do this
		}

		public JChainer e(Object... messages) {
			return l(JumboMessageType.ERROR, messages);
		}

		public JChainer e(Object message) {
			return l(JumboMessageType.ERROR, message);
		}

		public JChainer w(Object... messages) {
			return l(JumboMessageType.WARNING, messages);
		}

		public JChainer w(Object message) {
			return l(JumboMessageType.WARNING, message);
		}

		public JChainer d(Object message) {
			return l(JumboMessageType.DEBUG, message);
		}

		public JChainer d(Object... messages) {
			return l(JumboMessageType.DEBUG, messages);
		}

		public JChainer h(Throwable t, String errmessage) {
			JumboErrorHandler.handle(t, errmessage);
			return this;
		}

		public JChainer h(String errmessage, Throwable t) {
			return h(t, errmessage);
		}

		public JChainer h(Throwable t) {
			return h(t, "");
		}

		public JChainer f() {
			JumboConsole.flush();
			return this;
		}
	}

	static {
		// only allocated on first visit
		jc = new JChainer();
	}

	public static final JChainer jc;

	public static JChainer l(Object message, int type) {
		return jc.l(message, type);
	}

	public static JChainer l(int type, Object message) {
		return jc.l(type, message);

	}

	public static JChainer l(Object message) {
		return jc.l(message);

	}

	public static JChainer l(int type, Object... messages) {
		return jc.l(type, messages);

	}

	public static JChainer l(Object... messages) {
		return jc.l(JumboMessageType.INFO, messages);
	}

	public static JChainer h(Throwable t, String errmessage) {
		return jc.h(t, errmessage);
	}

	public static JChainer h(String errmessage, Throwable t) {
		return jc.h(errmessage, t);
	}

	public static JChainer h(Throwable t) {
		return jc.h(t);
	}

	public static JChainer e(Object... messages) {
		return jc.l(JumboMessageType.ERROR, messages);
	}

	public static JChainer e(Object message) {
		return jc.l(JumboMessageType.ERROR, message);
	}

	public static JChainer w(Object... messages) {
		return jc.l(JumboMessageType.WARNING, messages);
	}

	public static JChainer w(Object message) {
		return jc.l(JumboMessageType.WARNING, message);
	}

	public static JChainer f() {
		return jc.f();
	}

	public static JChainer d(Object message) {
		return jc.l(JumboMessageType.DEBUG, message);
	}

	public static JChainer d(Object... messages) {
		return jc.l(JumboMessageType.DEBUG, messages);
	}

}
