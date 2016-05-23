package com.jumbo.tools.input;

import com.jumbo.tools.JumboErrorHandler;

public class JumboInputListener {
	public int mousex, mousey, wheel;
	public boolean clicked, inputenabled, rightclicked, leftreleased, rightreleased;

	public void destroy() {
		JumboInputHandler.listeners.remove(this);
		try {
			finalize();
		} catch (Throwable e) {
			JumboErrorHandler.handle(e);
		}
	}

	public JumboInputListener() {
		JumboInputHandler.listeners.add(this);
	}

}
