package com.itau.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItauAuthApplication

fun main(args: Array<String>) {
    runApplication<ItauAuthApplication>(*args)
}
