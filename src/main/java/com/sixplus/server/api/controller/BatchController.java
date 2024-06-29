package com.sixplus.server.api.controller;

import com.sixplus.server.api.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RequestMapping(value = "/api/base")
@RestController
public class BatchController {

	@GetMapping("/alive")
	public Mono<Response<?>> alive() {
		return Mono.just(Response.ok("alive"));
	}

}
