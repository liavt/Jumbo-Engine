package test.jumbo.components;

import org.junit.Test;

import com.jumbo.components.IntCapsule;
import com.jumbo.components.Capsule;

import junit.framework.TestCase;

public class WrapperTest extends TestCase {

	@Test
	public static void testWrapper() {
		final Capsule<Boolean> w = new Capsule<>(false);
		assertFalse(w.get());
		w.set(true);
		assertTrue(w.get());
	}

	@Test
	public static void testIntWrapper() {
		final IntCapsule i = new IntCapsule();
		assertEquals(0, i.getNum());
		i.setNum(1);
		assertEquals(1, i.getNum());
		i.add(5);
		assertEquals(6, i.getNum());
		i.increment();
		assertEquals(7, i.getNum());
		i.add(-8);
		assertEquals(-1, i.getNum());
	}
}
