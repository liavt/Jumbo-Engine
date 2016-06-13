package test.jumbo.core;

import java.awt.Dimension;

import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboLaunchConfig;
import com.jumbo.util.input.console.J;

import junit.framework.TestCase;

public class JumboTest extends TestCase {

	@Test
	public static void testDisplay() {

		J.l("Testing display settings");
		assertTrue(Jumbo.isRunning());
		assertTrue(Display.isCreated());
		assertEquals(500, Jumbo.getFrameWidth());
		assertEquals(500, Jumbo.getFrameHeight());
	}

	@Test
	public static void testWindowRecreation() {
		J.l("Testing window restart");
		Jumbo.restartWindow();

		assertTrue(Jumbo.isRunning());
		assertTrue(Display.isCreated());
	}

	@Test
	public static void testNewLaunchConfig() throws LWJGLException {
		J.l("Testing new JumboLaunchConfig");

		final JumboLaunchConfig c = new JumboLaunchConfig("Testing...", new Dimension(240, 340));
		Jumbo.setNewLaunchConfig(c);

		Jumbo.update();

		assertTrue(Jumbo.isRunning());
		assertTrue(Display.isCreated());

		assertEquals(240, Jumbo.getFrameWidth());
		assertEquals(340, Jumbo.getFrameHeight());
	}
}
