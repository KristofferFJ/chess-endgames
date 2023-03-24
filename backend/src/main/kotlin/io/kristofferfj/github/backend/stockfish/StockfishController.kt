package io.kristofferfj.github.backend.stockfish

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("stockfish")
class StockfishController(
    private val stockfish: Stockfish,
) {

    class FenDto(val fen: String)

    @PostMapping
    fun getStockfishMove(@RequestBody fenDto: FenDto): ResponseEntity<String> {
        return ResponseEntity.ok(stockfish.getBestMove(fenDto.fen).toString())
    }
}

