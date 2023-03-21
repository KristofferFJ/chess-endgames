package io.kristofferfj.github.backend

import io.kristofferfj.github.backend.stockfish.StockfishService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendApplication(val stockfishService: StockfishService) : ApplicationRunner {
    override fun run(p0: ApplicationArguments) {
        stockfishService.stuff()
    }
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
