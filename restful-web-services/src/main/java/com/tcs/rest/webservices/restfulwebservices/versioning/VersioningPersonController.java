package com.tcs.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	//URI Versioning (Twitter)	
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//Request Parameter Versioning (Amazon)
	@GetMapping(value="/person", params = "version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		return new PersonV1("Bob Charlie");
	}
	@GetMapping(value="/person", params = "version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//Header Versioning (Microsoft)
	@GetMapping(value="/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonHeader() {
		return new PersonV1("Bob Charlie");
	}
	@GetMapping(value="/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//Media type versioning (Github)
	@GetMapping(value="/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPersonAccept() {
		return new PersonV1("Bob Charlie");
	}
	@GetMapping(value="/person/header", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonAccept() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
}
