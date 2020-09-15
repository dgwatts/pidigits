package com.github.dgwatts.pidigits;

public class VersionInfo {

	private final String version;
	private final String hash;
	private final String branch;

	public VersionInfo(String version, String hash, String branch) {
		this.version = version;
		this.hash = hash;
		this.branch = branch;
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
}
