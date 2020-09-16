package com.github.dgwatts.pidigits.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="pidigits",  description="Operations for retrieving digits of pi")
public class PiDigitsController {

	private static final int DEFAULT_RANGE = 30;

	private final Pattern rangePattern = Pattern.compile("^([0-9]+)-([0-9]+)$");

	@Autowired
	private PiDigitsServiceImpl piDigitsService;

	@Autowired
	private HttpServletResponse response;

	@GetMapping("/pidigits")
	@ApiOperation(value = "Get the first 30 digits of pi", response = PiDigitResponse.class, produces = "application/json", httpMethod = "GET")
	public PiDigitResponse getDigits() throws IOException {
		return getDigits(null);
	}

	@GetMapping("/pidigits/{criteria}")
	@ApiOperation(value = "Get multiple pi", response = PiDigitResponse.class, produces = "application/json", httpMethod = "GET")
	public PiDigitResponse getDigits(@PathVariable String criteria) throws IOException {

		PiDigitResponse piDigitResponse = new PiDigitResponse();

		if(criteria == null || criteria.trim().isEmpty()) {
			piDigitResponse = piDigitsService.getRange(DEFAULT_RANGE);
		} else {
			final Matcher matcher = rangePattern.matcher(criteria);
			if (matcher.matches()) {
				String startStr = matcher.group(1);
				String endStr = matcher.group(2);

				int start = Integer.parseInt(startStr);
				int end = Integer.parseInt(endStr);

				piDigitResponse = piDigitsService.getRange(start, end);
			} else if (criteria.matches("[0-9]+(,[0-9]+)*")) {
				final int[] indices = Arrays.stream(
						criteria.split(","))
						.mapToInt(Integer::parseInt)
						.toArray();

				piDigitResponse = piDigitsService.getDigits(indices);
			} else {
				piDigitResponse = null;
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}

		if(piDigitResponse != null && (piDigitResponse.isOutOfBounds() || piDigitResponse.isTruncated())) {
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		}

		return piDigitResponse;
	}
}
