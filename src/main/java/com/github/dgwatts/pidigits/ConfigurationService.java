package com.github.dgwatts.pidigits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Application configuration
 */
@Service
@PropertySource(value = { "classpath:application.properties" })
@PropertySource(ignoreResourceNotFound = true, value = { "file:${user.home}/.pidigits/application.properties" })
public class ConfigurationService {
	@Value("${pidigits.maxdigits:500}")
	private int maxDigits;

	public int getMaxDigits() {
		return maxDigits;
	}

	@PostConstruct
	public void fred() {
		System.out.println("constrcuted");
	}
}