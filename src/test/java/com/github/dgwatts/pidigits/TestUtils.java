package com.github.dgwatts.pidigits;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class TestUtils {

	// 1415926535
	public static final char[] FIRST_TEN = new char[]{'1', '4', '1', '5', '9', '2', '6', '5', '3', '5'};

	// 5779458151
	public static final char[] LAST_TEN = new char[]{'5', '7', '7', '9', '4', '5', '8', '1', '5', '1'};

	private TestUtils() { }

	public static void assertRangeEquals(PiDigit[] range, char[] expected) {
		assertRangeEquals(range, expected, 0);
	}

	public static void assertRangeEquals(PiDigit[] range, char[] expected, int shunt) {
		for (int i = 0; i < range.length; i++) {
			assertEquals("index " + (i + shunt), i + shunt, range[i].getIndex());
			assertEquals("digit " + i, expected[i], range[i].getValue());
		}
	}

}
