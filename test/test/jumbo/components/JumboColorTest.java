package test.jumbo.components;

import org.junit.Test;

import com.jumbo.components.JumboColor;

import junit.framework.TestCase;

public class JumboColorTest extends TestCase {
	private static JumboColor c;

	@Test
	public static void testHashcode() {
		c = JumboColor.BLACK;
		assertEquals("#000000", c.getHexcode());
		assertEquals(JumboColor.BLACK, JumboColor.fromHex(c.getHexcode()));

		c = JumboColor.LIGHT_GREY;
		assertEquals("#afafaf", c.getHexcode());
		assertEquals(JumboColor.LIGHT_GREY, JumboColor.fromHex(c.getHexcode()));
	}

	@Test
	public static void testGettersAndSetters() {
		c = new JumboColor();
		c.setRed(50);
		c.setGreen(100);
		c.setBlue(150);
		c.setAlpha(200);

		assertEquals(50, c.getRed());
		assertEquals(100, c.getGreen());
		assertEquals(150, c.getBlue());
		assertEquals(200, c.getAlpha());

	}

	@Test
	public static void testStatic() {
		assertEquals(175, JumboColor.LIGHT_GREY.getRed());
		assertEquals(255, JumboColor.RED.getRed());
	}

	@Test
	public static void testConstructors() {
		c = new JumboColor(JumboColor.LIGHT_GREY);
		assertEquals(JumboColor.LIGHT_GREY.getRed(), c.getRed());

		c = new JumboColor(50, 100, 150, 200);
		assertEquals(50, c.getRed());
		assertEquals(100, c.getGreen());
		assertEquals(150, c.getBlue());
		assertEquals(200, c.getAlpha());
	}
}
