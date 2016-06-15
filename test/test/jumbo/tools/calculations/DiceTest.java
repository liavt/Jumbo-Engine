package test.jumbo.tools.calculations;

import org.junit.Test;

import com.jumbo.util.calculations.Dice;

import junit.framework.TestCase;

public class DiceTest extends TestCase {
	@Test
	public static void testSigned() {
		final int i = Dice.rollSigned();
		assertTrue(i == -1 || i == 1);
	}

	@Test
	public static void testBoolean() {
		final boolean b = Dice.rollBool();
		assertNotNull(b);
	}
}
