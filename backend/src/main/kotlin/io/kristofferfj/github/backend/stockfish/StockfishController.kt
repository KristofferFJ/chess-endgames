package io.kristofferfj.github.backend.stockfish

import io.kristofferfj.github.backend.chess.Move
import io.kristofferfj.github.backend.utils.FenUtils
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

    class PlayerMove(
        val algebraicMove: String,
        val fen: String,
    )

    @PostMapping
    fun move(@RequestBody playerMove: PlayerMove): ResponseEntity<String> {
        val position = FenUtils.readFen(playerMove.fen)
        position.move(playerMove.algebraicMove)
        position.move(stockfish.getBestMove(position))
        return ResponseEntity.ok(position.toFen())
    }
}
