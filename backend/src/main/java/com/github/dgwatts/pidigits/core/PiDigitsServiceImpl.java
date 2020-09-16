package com.github.dgwatts.pidigits.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PiDigitsServiceImpl implements PiDigitsService {

	private static final Logger LOG = LoggerFactory.getLogger(PiDigitsServiceImpl.class);

	private static final String RESOURCE = "pi1000000.txt";
	private int resourceSize = 0;

	@Autowired
	private ConfigurationService configurationService;

	@PostConstruct
	public void readStreamLength() throws IOException {
		InputStream in = new ClassPathResource(RESOURCE).getInputStream();
		int read = -1;
		byte[] buf = new byte[1024];
		while ((read = in.read(buf)) != -1) {
			resourceSize += read;
		}

		LOG.info("{} decimal digits available", resourceSize);
	}

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
		return getDigits(new int[]{index});
	}

	@Override
	public PiDigitResponse getDigits(int[] indices) throws IOException {
		boolean isTruncated = false;
		boolean isOutOfBounds = false;
		final int maxDigits = configurationService.getMaxDigits();
		if(indices.length > maxDigits) {
			int[] truncated = new int[maxDigits];
			System.arraycopy(indices, 0, truncated, 0, maxDigits);
			indices = truncated;
			isTruncated = true;
		}

		List<Integer> actualIndices = new ArrayList<>();
		for(int i = 0; i < indices.length; i++) {
			final boolean thisIndexOutOfBounds = isOutOfBounds(indices[i]);
			isOutOfBounds |= thisIndexOutOfBounds;
			if(!thisIndexOutOfBounds) {
				actualIndices.add(indices[i]);
			}
		}

		indices = actualIndices.stream().mapToInt(Integer::intValue).toArray();

		PiDigit[] digits = new PiDigit[indices.length];
		for(int i = 0; i < indices.length; i++) {
			final boolean thisIndexOutOfBounds = isOutOfBounds(indices[i]);
			isOutOfBounds |= thisIndexOutOfBounds;
			if(thisIndexOutOfBounds) {
				continue;
			}
			digits[i] = new PiDigit(indices[i], doGet(indices[i]));
		}
		return new PiDigitResponse(digits, isTruncated, isOutOfBounds);
	}

	private boolean isOutOfBounds(int index) {
		return index < 0 || index >= resourceSize;
	}

	private char doGet(int index) throws IOException {
		char read = 'x';
		InputStream in = new ClassPathResource(RESOURCE).getInputStream();
		for(int i = 0; i <= index; i++) {
			read = (char) in.read();
			if(read == -1) {
				// Gone past the end of the stream
				throw new IOException("Attempted to read past end of stream");
			}
		}
		return read;
	}
}
