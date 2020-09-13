package com.github.dgwatts.pidigits;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPiDigitsServiceImpl {

	// 1415926535
	private static final char[] FIRST_TEN = new char[]{'1', '4', '1', '5', '9', '2', '6', '5', '3', '5'};

	// 5779458151
	private static final char[] LAST_TEN = new char[]{'5', '7', '7', '9', '4', '5', '8', '1', '5', '1'};

	private PiDigitsServiceImpl instanceUnderTest;

	@BeforeEach
	public void setup() {
		instanceUnderTest = new PiDigitsServiceImpl();
	}

	@Test
	public void testGetDigitFirstTenDigitsIndividually() throws IOException {


		for (int i = 0; i < FIRST_TEN.length; i++) {
			assertEquals("digit " + (i), FIRST_TEN[i], instanceUnderTest.get(i).getValue());
		}
	}

	@Test
	public void testGetDigitLastTenDigitsIndividually() throws IOException {
		for (int i = 0; i < LAST_TEN.length; i++) {
			assertEquals("digit " + (i+999990), LAST_TEN[i], instanceUnderTest.get(i+999990).getValue());
		}
	}

	@Test
	public void testGetRangeFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(0, 10);
		assertEquals("10 elements", FIRST_TEN.length, range.length);

		for (int i = 0; i < range.length; i++) {
			assertEquals("index " + i, i, range[i].getIndex());
			assertEquals("digit " + i, FIRST_TEN[i], range[i].getValue());
		}
	}

	@Test
	public void testGetRangeLastTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(999990, 1000000);
		assertEquals("10 elements", LAST_TEN.length, range.length);

		for (int i = 0; i < range.length; i++) {
			assertEquals("index " + (i+999990), i+999990, range[i].getIndex());
			assertEquals("digit " + i, LAST_TEN[i], range[i].getValue());
		}
	}

	@Test
	public void testGetRangeSingleArgFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(10);

		assertEquals("10 elements", FIRST_TEN.length, range.length);

		for (int i = 0; i < range.length; i++) {
			assertEquals("index " + i, i, range[i].getIndex());
			assertEquals("digit " + i, FIRST_TEN[i], range[i].getValue());
		}
	}

	@Test
	public void testGetDigitsEveryOtherDigitOfFirstTen() throws IOException {
		final PiDigit[] digits = instanceUnderTest.getDigits(new int[]{0, 2, 4, 6, 8});

		assertEquals("5 elements", 5, digits.length);

		for (int i = 0; i < digits.length; i++) {
			assertEquals("index " + (i*2), i*2, digits[i].getIndex());
			assertEquals("digit " + i*2, FIRST_TEN[i*2], digits[i].getValue());
		}
	}
}
