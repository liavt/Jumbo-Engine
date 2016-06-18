package test.jumbo.components;

import org.junit.Before;
import org.junit.Test;

import com.jumbo.components.JumboColor;

import junit.framework.TestCase;

public class JumboColorTest extends TestCase {
	private JumboColor c;

	@Override
	@Before
	public void setUp() {
		c = JumboColor.BLACK;
	}

	@Test
	public void testHashcode() {
		assertEquals("#000000", c.getHexcode());
		assertEquals(JumboColor.BLACK, JumboColor.fromHex(c.getHexcode()));
	}
}
