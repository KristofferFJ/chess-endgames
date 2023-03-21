package io.kristofferfj.github.backend.stockfish

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@Service
class StockfishService {
    val path = "hejsa"

    private final val process = ProcessBuilder().command("src/main/resources/stockfish-windows-2022-x86-64-avx2.exe").start()
    private val output = StringBuilder()
    private val input = BufferedReader(InputStreamReader(process.inputStream))
    private val writer = BufferedWriter(OutputStreamWriter(process.outputStream))

    fun stuff() {
        writer.write("position fen rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        writer.write("go movetime 1000")
        writer.flush()
    }
}