package com.github.dgwatts.pidigits;

public class PiDigitResponse {

	private PiDigit[] digits;

	private boolean truncated = false;

	public PiDigitResponse() {
		this(new PiDigit[0]);
	}

	public PiDigitResponse(PiDigit[] digits) {
		this(digits, false);
	}

	public PiDigitResponse(PiDigit[] digits, boolean truncated) {
		this.digits = digits;
		this.truncated = truncated;
	}

	public PiDigit[] getDigits() {
		return digits;
	}

	public boolean isTruncated() {
		return truncated;
	}
}
