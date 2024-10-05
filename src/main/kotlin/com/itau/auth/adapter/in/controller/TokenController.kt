package com.itau.auth.adapter.`in`.controller

import com.itau.auth.application.port.TokenHandlerInputPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("v1/token-validate")
class TokenController(private val tokenHandlerInputPort: TokenHandlerInputPort) {
    @GetMapping
    fun handle(@RequestParam("token") token: String) =
        tokenHandlerInputPort.process(Mono.just(token))
            .onErrorResume {
            Mono.just(false)
        }

    //TODO: improve response and error handler
}