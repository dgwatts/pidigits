package com.github.dgwatts.pidigits;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PiDigitsServiceImpl implements PiDigitsService {

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public PiDigitResponse getRange(int start, int end) throws IOException {
		return getDigits(IntStream.range(start, end).toArray());
	}

	@Override
	public PiDigitResponse getRange(int end) throws IOException {
		return getRange(0, end);
	}

	@Override
	public PiDigitResponse getDigit(int index) throws IOException {
		return new PiDigitResponse(new PiDigit[]{new PiDigit(index, doGet(index))});
	}

	@Override
	public PiDigitResponse getDigits(int[] indices) throws IOException {
		boolean isTruncated = false;
		final int maxDigits = configurationService.getMaxDigits();
		if(indices.length > maxDigits) {
			int[] truncated = new int[maxDigits];
			System.arraycopy(indices, 0, truncated, 0, maxDigits);
			indices = truncated;
			isTruncated = true;
		}

		PiDigit[] digits = new PiDigit[indices.length];
		for(int i = 0; i < indices.length; i++) {
			digits[i] = new PiDigit(indices[i], doGet(indices[i]));
		}
		return new PiDigitResponse(digits, isTruncated);
	}

	private char doGet(int index) throws IOException {
		ClassPathResource digits = new ClassPathResource("pi1000000.txt");
		char read = 'x';
		InputStream in = digits.getInputStream();
		for(int i = 0; i <= index; i++) {
			read = (char) in.read();
		}
		return read;
	}
}
