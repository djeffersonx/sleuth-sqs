package br.com.idws.sleuthsqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Consumer2Application

fun main(args: Array<String>) {
    runApplication<Consumer2Application>(*args)
}

@Bean
fun objectMapper(): ObjectMapper {
    return ObjectMapper().apply {
        registerModule(KotlinModule())
        registerModule(JavaTimeModule())
    }
}