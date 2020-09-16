package com.github.dgwatts.pidigits.core;

public class PiDigitResponse {

	private PiDigit[] digits;

	private boolean truncated = false;

	private boolean outOfBounds = false;

	public PiDigitResponse() {
		this(new PiDigit[0]);
	}

	public PiDigitResponse(PiDigit[] digits) {
		this(digits, false, false);
	}

	public PiDigitResponse(PiDigit[] digits, boolean truncated, boolean outOfBounds) {
		this.digits = digits;
		this.truncated = truncated;
		this.outOfBounds = outOfBounds;
	}

	public PiDigit[] getDigits() {
		return digits;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public boolean isOutOfBounds() {
		return outOfBounds;
	}
}
