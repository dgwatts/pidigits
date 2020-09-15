package com.github.dgwatts.pidigits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.annotations.SerializedName;

@Service
public class MetaService {

	@Autowired
	private ConfigurationService configurationService;

	public VersionInfo getVersion() {
return new VersionInfo(configurationService.getVersion(), configurationService.getHash(), configurationService.getBranch());
	}

}
