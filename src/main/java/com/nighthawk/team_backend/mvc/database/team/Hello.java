package com.nighthawk.team_backend.mvc.database.team;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "{\"json\": \"hello world\"}";
	}

	@RequestMapping({ "/json" })
	public String jsonPage() {
		return "{\"json\": \"hello world\"}";
	}

}