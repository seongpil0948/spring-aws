package com.sixplus.server.api.reflect;

import com.sixplus.server.api.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class ReflectController {
    @Autowired
    private ReflectService reflectService;

    @GetMapping("/reflect")
    public Mono<Response<?>> alive() {
        try {
            reflectService.trigger(new String[]{"one", "two", "three"});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(Response.ok("alive"));
    }

}
