package io.kristofferfj.github.backend.utils

import io.kristofferfj.github.backend.chess.Point
import io.kristofferfj.github.backend.chess.Position
import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.rules.Piece

class FenUtils {

    companion object {

        fun readFen(fen: String): Position {
            val (fenPositionInput, toMoveInput, castlingInput,
                enPassantInput, halfMovesInput, fullMovesInput) = fen.split(" ")
            return Position(
                readFenPosition(fenPositionInput),
                if (toMoveInput == "w") Color.W else Color.B,
                castlingInput.contains("K"),
                castlingInput.contains("Q"),
                castlingInput.contains("k"),
                castlingInput.contains("q"),
                if (enPassantInput == "-") null else Point(enPassantInput),
                halfMovesInput.toInt(),
                fullMovesInput.toInt(),
            )
        }

        private fun readFenPosition(fen: String): MutableList<MutableList<Piece>> {
            val game = mutableListOf(mutableListOf<Piece>())
            fen.toCharArray().forEach {
                when (it) {
                    'K' -> game.first().add(Piece.K)
                    'Q' -> game.first().add(Piece.Q)
                    'R' -> game.first().add(Piece.R)
                    'B' -> game.first().add(Piece.B)
                    'N' -> game.first().add(Piece.N)
                    'P' -> game.first().add(Piece.P)
                    'k' -> game.first().add(Piece.k)
                    'q' -> game.first().add(Piece.q)
                    'r' -> game.first().add(Piece.r)
                    'b' -> game.first().add(Piece.b)
                    'n' -> game.first().add(Piece.n)
                    'p' -> game.first().add(Piece.p)
                    '/' -> game.add(0, mutableListOf())
                    else -> {
                        val spaces = it.toString().toInt()
                        repeat(spaces) { game.first().add(Piece.E) }
                    }
                }
            }

            return game
        }

        fun toFen(position: Position): String {
            return listOf(
                boardFen(position.board),
                position.toMove.name.lowercase(),
                castleFen(position),
                position.enPassantSquare?.toAlgebraic() ?: "-",
                position.halfMoveClock,
                position.fullMoveClock,
            ).joinToString(" ")
        }

        fun castleFen(position: Position): String {
            val legalCastles = mutableListOf<String>()
            if (position.whiteKingSideCastle) legalCastles.add("K")
            if (position.whiteQueenSideCastle) legalCastles.add("Q")
            if (position.blackKingSideCastle) legalCastles.add("k")
            if (position.blackQueenSideCastle) legalCastles.add("q")

            return if (legalCastles.isEmpty()) "-" else legalCastles.joinToString("")
        }

        private fun boardFen(board: List<List<Piece>>): String {
            return board.reversed().joinToString("/") { row ->
                val newRow = mutableListOf<String>()
                row.forEach { char ->
                    when (char) {
                        Piece.E -> {
                            if (newRow.isNotEmpty() && newRow.last().isNumber()) {
                                newRow[newRow.size - 1] = (newRow.last().toInt() + 1).toString()
                            } else {
                                newRow.add("1")
                            }
                        }

                        else -> newRow.add(char.toString())
                    }
                }
                newRow.joinToString("")
            }
        }

        private fun String.isNumber(): Boolean {
            return listOf("1", "2", "3", "4", "5", "6", "7").contains(this)
        }
    }
}

private operator fun <E> List<E>.component6(): E {
    return this[5]
}
