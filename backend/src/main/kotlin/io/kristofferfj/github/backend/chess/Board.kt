package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Piece

class Board(val rows: MutableList<MutableList<Piece>>) {

    fun set(square: Square, value: Piece) {
        this.rows[square.row - 1][square.column.ordinal] = value
    }

    fun at(square: Square): Piece {
        return this.rows[square.row - 1][square.column.ordinal]
    }

    fun at(column: Column, row: Int): Piece {
        return at(Square(column, row))
    }

    override fun toString(): String {
        return this.rows.reversed().joinToString("") {
            "${
                it.joinToString("") { square ->
                    when (square) {
                        Piece.E -> " "
                        else -> square.name
                    }
                }
            }\n"
        }
    }
}
