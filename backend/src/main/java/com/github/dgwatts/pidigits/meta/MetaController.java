package com.github.dgwatts.pidigits.meta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MetaController {

	@Autowired
	private MetaService metaService;

	@Autowired
	private HealthService healthService;

	@GetMapping("/meta/health")
	public int health() {
		return healthService.getLoad();
	}

	@GetMapping("/meta/version")
	public VersionInfo version() {
		return metaService.getVersion();
	}

	@PostMapping("/meta/authresponse")
	public void auth() {
		// validate the response

		// Add the token / id to the "database"

		// return the token to the user
	}




}
