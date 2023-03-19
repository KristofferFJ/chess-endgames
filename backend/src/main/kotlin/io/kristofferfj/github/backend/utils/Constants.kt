package io.kristofferfj.github.backend.utils

import io.kristofferfj.github.backend.chess.Square
import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Piece

class Constants {
    companion object {
        const val RUY_LOPEZ = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3"
        const val STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        val PAWNS = listOf(Piece.P, Piece.p)
        val KINGS = listOf(Piece.K, Piece.k)
        val KINGS_AND_ROOKS = listOf(Piece.K, Piece.k, Piece.R, Piece.r)
        val WHITE_KING_ROOK_Square = Square(Column.h, 1)
        val WHITE_QUEEN_ROOK_Square = Square(Column.a, 1)
        val BLACK_KING_ROOK_Square = Square(Column.h, 8)
        val BLACK_QUEEN_ROOK_Square = Square(Column.a, 8)
    }
}
