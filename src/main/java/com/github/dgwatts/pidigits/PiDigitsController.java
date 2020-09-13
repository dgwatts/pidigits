package com.github.dgwatts.pidigits;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PiDigitsController {

	private static final int DEFAULT_RANGE = 30;

	private final Pattern rangePattern = Pattern.compile("^([0-9]+)-([0-9]+)$");

	@Autowired
	private PiDigitsServiceImpl piDigitsService;

	@GetMapping("/pidigits")
	public PiDigit[] getDigits() throws IOException {
		return getDigits(null);
	}

	@GetMapping("/pidigits/{criteria}")
	public PiDigit[] getDigits(@PathVariable String criteria) throws IOException {

		if(criteria == null || criteria.trim().isEmpty()) {
			return piDigitsService.getRange(DEFAULT_RANGE);
		}

		final Matcher matcher = rangePattern.matcher(criteria);
		if(matcher.matches()) {
			String startStr = matcher.group(1);
			String endStr = matcher.group(2);

			int start = Integer.parseInt(startStr);
			int end = Integer.parseInt(endStr);

			return piDigitsService.getRange(start, end);
		}

		if(criteria.matches("[0-9]+(,[0-9]+)*")) {
			final int[] indices = Arrays.stream(
				criteria.split(","))
				.mapToInt(Integer::parseInt)
				.toArray();

			return piDigitsService.getDigits(indices);
		}

		return new PiDigit[0];
	}
}
