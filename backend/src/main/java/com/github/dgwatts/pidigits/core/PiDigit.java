package com.github.dgwatts.pidigits.core;

public class PiDigit {
	private final int index;
	private final char value;

	public PiDigit(int index, char value) {
		this.index = index;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public char getValue() {
		return value;
	}
}
