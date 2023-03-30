package io.kristofferfj.github.backend.stockfish

import io.kristofferfj.github.backend.chess.Move
import io.kristofferfj.github.backend.chess.Position
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.System.getProperty


const val executablePath = "backend\\src\\main\\resources\\stockfish-windows-2022-x86-64-avx2.exe"
val workingDir: String = getProperty("user.dir")
val fullPath = "$workingDir/$executablePath"

@Service
class Stockfish {

    private final val process: Process = ProcessBuilder(fullPath)
        .redirectErrorStream(true)
        .start()

    private val outStream = PrintWriter(OutputStreamWriter(process.outputStream))
    private val inStream = BufferedReader(InputStreamReader(process.inputStream))
    private var line: String? = null

    fun getBestMove(position: Position): Move {
        return getBestMove(position.toFen())
    }

    fun getBestMove(fen: String): Move {
        sendCommand("uci")
        setPosition(fen)
        sendCommand("go depth 10")

        while (inStream.readLine().also { line = it } != null) {
            println(line)
            if (line!!.startsWith("bestmove")) {
                return Move(line!!.split(" ")[1])
            }
        }

        throw RuntimeException("Stockfish was unable to find move")
    }

    private fun sendCommand(command: String) {
        outStream.println(command)
        outStream.flush()
    }

    private fun setPosition(fen: String) {
        sendCommand("position fen $fen")
    }
}
