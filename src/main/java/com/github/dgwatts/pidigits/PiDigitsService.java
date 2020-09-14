package com.github.dgwatts.pidigits;

import java.io.IOException;

public interface PiDigitsService {
	/**
	 * Get a contiguous range of digits from the decimal portion of pi. Uses the semantics of String.substring(int, int),
	 * eg  the range begins at the specified {@code start} digit and extends to the digit at index {@code end - 1}, meaning
	 * the length of the range is {@code end - start}.
	 * @param start The start index, inclusive.
	 * @param end The end index, exclusive.
	 * @return Response with the specified range.
	 */
	PiDigitResponse getRange(int start, int end) throws IOException;

	/**
	 * Get a contiguous range of digits from the decimal portion of pi, starting from the front. Equivalent to calling
	 * getRange(0, end)
	 * @param end The end index, exclusive.
	 * @return Response with the specified range.
	 */
	PiDigitResponse getRange(int end) throws IOException;

	/**
	 * Get a single digit from the decimal portion of pi.
	 * @param index The index of the digut to return
	 * @return Response with the specified digit.
	 */
	PiDigitResponse getDigit(int index) throws IOException;

	/**
	 * Get a selection of digits from the decimal portion of pi. The specified indices do not have to be contiguous or ordered
	 * @param indices The indices of the digits to return
	 * @return The specified digits.
	 */
	PiDigitResponse getDigits(int[] indices) throws IOException;
}
