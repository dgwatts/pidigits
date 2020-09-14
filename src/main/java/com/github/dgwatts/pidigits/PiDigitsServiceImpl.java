package com.github.dgwatts.pidigits;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PiDigitsServiceImpl implements PiDigitsService {

	@Override
	public PiDigit[] getRange(int start, int end) throws IOException {
		final PiDigit[] toReturn = new PiDigit[end - start];

		for(int i = 0; i < (end - start); i++) {
			toReturn[i] = getDigit(i + start);
		}
	@Autowired
	private ConfigurationService configurationService;

		return toReturn;
	}

	@Override
	public PiDigit[] getRange(int end) throws IOException {
		return getRange(0, end);
	}

	@Override
	public PiDigit getDigit(int index) throws IOException {
		return new PiDigit(index, doGet(index));
	}

	@Override
	public PiDigit[] getDigits(int[] indices) throws IOException {
		PiDigit[] toReturn = new PiDigit[indices.length];
		for(int i = 0; i < indices.length; i++) {
			toReturn[i] = getDigit(indices[i]);
		}
		return toReturn;
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
