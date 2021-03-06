package com.github.dgwatts.pidigits.meta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dgwatts.pidigits.core.ConfigurationService;

@Service
public class MetaService {

	@Autowired
	private ConfigurationService configurationService;

	public VersionInfo getVersion() {
return new VersionInfo(configurationService.getVersion(), configurationService.getHash(), configurationService.getBranch());
	}

}
