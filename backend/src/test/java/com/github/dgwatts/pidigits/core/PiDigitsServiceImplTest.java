package com.github.dgwatts.pidigits;

import static com.github.dgwatts.pidigits.TestUtils.assertRangeEquals;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.io.IOException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes={PiDigitsServiceImpl.class, ConfigurationService.class})
public class PiDigitsServiceImplTest {

	@Autowired
	private PiDigitsServiceImpl instanceUnderTest;

	@Test
	public void testGetDigitFirstTenDigitsIndividually() throws IOException {
		for (int i = 0; i < TestUtils.FIRST_TEN.length; i++) {
			assertEquals("digit " + (i), TestUtils.FIRST_TEN[i], instanceUnderTest.getDigit(i).getDigits()[0].getValue());
		}
	}

	@Test
	public void testGetDigitLastTenDigitsIndividually() throws IOException {
		for (int i = 0; i < TestUtils.LAST_TEN.length; i++) {
			assertEquals("digit " + (i+999990), TestUtils.LAST_TEN[i], instanceUnderTest.getDigit(i+999990).getDigits()[0].getValue());
		}
	}

	@Test
	public void testGetRangeFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(0, 10).getDigits();
		assertEquals("10 elements", TestUtils.FIRST_TEN.length, range.length);
		assertRangeEquals(range, TestUtils.FIRST_TEN);
	}



	@Test
	public void testGetRangeLastTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(999990, 1000000).getDigits();
		assertEquals("10 elements", TestUtils.LAST_TEN.length, range.length);

		assertRangeEquals(range, TestUtils.LAST_TEN, 999990);
	}

	@Test
	public void testGetRangeSingleArgFirstTenDigits() throws IOException {
		final PiDigit[] range = instanceUnderTest.getRange(10).getDigits();

		assertEquals("10 elements", TestUtils.FIRST_TEN.length, range.length);

		assertRangeEquals(range, TestUtils.FIRST_TEN);
	}

	@Test
	public void testGetRangeSingleArgFirstSixHundredDigitsIsTruncated() throws IOException {
		final PiDigitResponse response = instanceUnderTest.getRange(600);
		final PiDigit[] range = response.getDigits();

		assertEquals("500 elements", 500, range.length);
		assertTrue("truncated", response.isTruncated());
	}

	@Test
	public void testGetRangeTwoArgFirstSixHundredDigitsIsTruncated() throws IOException {
		final PiDigitResponse response = instanceUnderTest.getRange(0, 600);
		final PiDigit[] range = response.getDigits();

		assertEquals("500 elements", 500, range.length);
		assertTrue("truncated", response.isTruncated());
	}

	@Test
	public void testGetDigitsFirstSixHundredDigitsIsTruncated() throws IOException {
		final PiDigitResponse response = instanceUnderTest.getDigits(IntStream.range(0, 600).toArray());

		final PiDigit[] range = response.getDigits();

		assertEquals("500 elements", 500, range.length);
		assertTrue("truncated", response.isTruncated());
	}

	@Test
	public void testGetDigitsEveryOtherDigitOfFirstTen() throws IOException {
		final PiDigitResponse response = instanceUnderTest.getDigits(new int[]{0, 2, 4, 6, 8});
		final PiDigit[] digits = response.getDigits();

		assertEquals("5 elements", 5, digits.length);

		for (int i = 0; i < digits.length; i++) {
			assertEquals("index " + (i*2), i*2, digits[i].getIndex());
			assertEquals("digit " + i*2, TestUtils.FIRST_TEN[i*2], digits[i].getValue());
		}
	}

	@Test
	public void testGetDigitOutOfBounds() throws IOException {
		PiDigitResponse response;

		response = instanceUnderTest.getDigit(-1);
		assertTrue("Should be out of bounds", response.isOutOfBounds());

		response = instanceUnderTest.getDigit(10000000);
		assertTrue("Should be out of bounds", response.isOutOfBounds());
	}

	@Test
	public void testGetRangeOutOfBounds() throws IOException {
		PiDigitResponse response;

		response = instanceUnderTest.getRange(1000000 - 10, 1000000 + 400);
		assertTrue("Should be out of bounds", response.isOutOfBounds());
		assertFalse("Should not be truncated", response.isTruncated());
		assertEquals("10 elements", 10, response.getDigits().length);
	}

	@Test
	public void testGetRangeOutOfBoundsAndTruncated() throws IOException {
		PiDigitResponse response;

		response = instanceUnderTest.getRange(999990, 1000000 + 600);
		assertTrue("Should be out of bounds", response.isOutOfBounds());
		assertTrue("Should be truncated", response.isTruncated());

		assertEquals("10 elements", 10, response.getDigits().length);
	}

	@Test
	public void testGetRangeTruncatedSoNoLongerOutOfBounds() throws IOException {
		PiDigitResponse response;

		response = instanceUnderTest.getRange(1000000 - 600, 1000000 + 200);
		assertFalse("Should not be out of bounds", response.isOutOfBounds());
		assertTrue("Should be truncated", response.isTruncated());

		assertEquals("500 elements", 500, response.getDigits().length);
	}
}
