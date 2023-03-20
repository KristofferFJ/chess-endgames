package io.kristofferfj.github.backend.rules

import io.kristofferfj.github.backend.chess.Constants

enum class Piece {
    p, r, n, b, q, k,
    P, R, N, B, Q, K,
    E;

    fun getColor(): Color? {
        if(Constants.WHITE_PIECES.contains(this)) return Color.W
        if(Constants.BLACK_PIECES.contains(this)) return Color.B
        return null
    }
}
