package io.kristofferfj.github.backend

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendApplication() : ApplicationRunner {
    override fun run(p0: ApplicationArguments) {
    }
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
