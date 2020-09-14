package com.github.dgwatts.pidigits;

import static com.github.dgwatts.pidigits.TestUtils.assertRangeEquals;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PiDigitsServiceImplTest {

	private PiDigitsServiceImpl instanceUnderTest;

	@BeforeEach
	public void setup() {
		instanceUnderTest = new PiDigitsServiceImpl();
	}

	@Test
	public void testGetDigitFirstTenDigitsIndividually() throws IOException {
		for (int i = 0; i < TestUtils.FIRST_TEN.length; i++) {
			assertEquals("digit " + (i), TestUtils.FIRST_TEN[i], instanceUnderTest.getDigit(i).getValue());
		}
	}

	@Test
	public void testGetDigitLastTenDigitsIndividually() throws IOException {
		for (int i = 0; i < TestUtils.LAST_TEN.length; i++) {
			assertEquals("digit " + (i+999990), TestUtils.LAST_TEN[i], instanceUnderTest.getDigit(i+999990).getValue());
		}
	}

	@Test
	public void testGetRangeFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(0, 10);
		assertEquals("10 elements", TestUtils.FIRST_TEN.length, range.length);
		assertRangeEquals(range, TestUtils.FIRST_TEN);
	}



	@Test
	public void testGetRangeLastTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(999990, 1000000);
		assertEquals("10 elements", TestUtils.LAST_TEN.length, range.length);

		assertRangeEquals(range, TestUtils.LAST_TEN, 999990);
	}

	@Test
	public void testGetRangeSingleArgFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(10);

		assertEquals("10 elements", TestUtils.FIRST_TEN.length, range.length);

		assertRangeEquals(range, TestUtils.FIRST_TEN);
	}

	@Test
	public void testGetDigitsEveryOtherDigitOfFirstTen() throws IOException {
		final PiDigit[] digits = instanceUnderTest.getDigits(new int[]{0, 2, 4, 6, 8});

		assertEquals("5 elements", 5, digits.length);

		for (int i = 0; i < digits.length; i++) {
			assertEquals("index " + (i*2), i*2, digits[i].getIndex());
			assertEquals("digit " + i*2, TestUtils.FIRST_TEN[i*2], digits[i].getValue());
		}
	}
}
