package io.kristofferfj.github.backend.utils

import io.kristofferfj.github.backend.chess.Square
import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Piece

class BoardUtils {
    companion object {
        fun List<List<Piece>>.at(square: Square): Piece {
            return this[square.row - 1][square.column.ordinal]
        }

        fun List<List<Piece>>.at(column: Column, row: Int): Piece {
            return at(Square(column, row))
        }
    }
}