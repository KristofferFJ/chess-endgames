package io.kristofferfj.github.backend.stockfish

import io.kristofferfj.github.backend.chess.Move
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.System.getProperty
import java.lang.System.out


const val executablePath = "backend\\src\\main\\resources\\stockfish-windows-2022-x86-64-avx2.exe"
val workingDir: String = getProperty("user.dir")
val fullPath = "$workingDir/$executablePath"

@Service
class Stockfish {

    private var line: String? = null

    fun getBestMove(fen: String): Move {
        val process: Process = ProcessBuilder(fullPath)
            .redirectErrorStream(true)
            .start()

        val outStream = PrintWriter(OutputStreamWriter(process.outputStream))
        val inStream = BufferedReader(InputStreamReader(process.inputStream))

        sendCommand("uci", outStream)
        setPosition(fen, outStream)
        sendCommand("go depth 10", outStream)

        while (inStream.readLine().also { line = it } != null) {
            println(line)
            if (line!!.startsWith("bestmove")) {
                return Move(line!!.split(" ")[1])
            }
        }

        throw RuntimeException("Stockfish was unable to find move")
    }

    private fun sendCommand(command: String, outStream: PrintWriter) {
        outStream.println(command)
        outStream.flush()
    }

    private fun setPosition(fen: String, outStream: PrintWriter) {
        sendCommand("position fen $fen", outStream)
    }
}
