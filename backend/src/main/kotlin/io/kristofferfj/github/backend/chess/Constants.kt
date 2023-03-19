package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Piece

class Constants {
    companion object {
        const val RUY_LOPEZ = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3"
        const val STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        val PAWNS = listOf(Piece.P, Piece.p)
        val KINGS = listOf(Piece.K, Piece.k)
        val KINGS_AND_ROOKS = listOf(Piece.K, Piece.k, Piece.R, Piece.r)
        val WHITE_PIECES = listOf(Piece.R, Piece.B, Piece.N, Piece.K, Piece.Q, Piece.P)
        val BLACK_PIECES = listOf(Piece.r, Piece.b, Piece.n, Piece.k, Piece.q, Piece.p)
        val WHITE_KING_ROOK_SQUARE = Square(Column.h, 1)
        val WHITE_QUEEN_ROOK_SQUARE = Square(Column.a, 1)
        val BLACK_KING_ROOK_SQUARE = Square(Column.h, 8)
        val BLACK_QUEEN_ROOK_SQUARE = Square(Column.a, 8)
    }
}
