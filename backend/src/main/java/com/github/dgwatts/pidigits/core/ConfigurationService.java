package com.github.dgwatts.pidigits.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Application configuration
 */
@Service
@PropertySource(value = { "classpath:application.properties" })
@PropertySource(value = { "file:${user.home}/.pidigits/application.properties" }, ignoreResourceNotFound = true)
@PropertySource(value = { "classpath:version.properties" }, ignoreResourceNotFound = true)
public class ConfigurationService {
	@Value("${pidigits.maxdigits:500}")
	private int maxDigits;

	@Value("${pidigits.version:unknown}")
	private String version;

	@Value("${pidigits.hash:unknown}")
	private String hash;

	@Value("${pidigits.branch:unknown}")
	private String branch;

	@Value("${pidigits.buildTime:-1}")
	private long buildTime;

	public int getMaxDigits() {
		return maxDigits;
	}

	public String getVersion() {
		return version;
	}

	public String getHash() {
		return hash;
	}

	public String getBranch() {
		return branch;
	}

	public long getBuildTime() {
		return buildTime;
	}
}