package com.github.dgwatts.pidigits.meta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api("Metafunctions for the Pi Digits API")
public class MetaController {

	@Autowired
	private MetaService metaService;

	@Autowired
	private HealthService healthService;

	@GetMapping("/meta/health")
	@ApiOperation(value = "Get the server load, in terms of requests over the last minute", response = int.class)
	public int health() {
		return healthService.getLoad();
	}

	@GetMapping("/meta/version")
	@ApiOperation(value = "Get the current deployment's version information", response = VersionInfo.class)
	public VersionInfo version() {
		return metaService.getVersion();
	}

	@GetMapping("/meta/privacy")
	@ApiOperation(value = "Get the privacy policy", response = String.class)
	public String privacy() {
		return "Only the information necessry to provide the service is collected. All information recieved is destroyed at server restart.";
	}

	@PostMapping("/meta/tos")
	@ApiOperation(value = "Get the terms of service", response = String.class)
	public String tos() {
		return "You may use this system for any purpose. No explicit or implicit guarantees provided or liability assumed";
	}


}
