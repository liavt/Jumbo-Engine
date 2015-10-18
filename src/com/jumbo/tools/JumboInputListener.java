package com.jumbo.tools;

public class JumboInputListener {
	public int mousex, mousey, wheel;
	public boolean clicked, inputenabled, rightclicked, leftreleased, rightreleased;

	public void destroy() {
		InputHandler.listeners.remove(this);
		try {
			finalize();
		} catch (Throwable e) {
			ErrorHandler.handle(e);
		}
	}

	public JumboInputListener() {
		InputHandler.listeners.add(this);
	}

}
