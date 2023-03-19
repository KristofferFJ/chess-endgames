package io.kristofferfj.github.backend.utils

import io.kristofferfj.github.backend.chess.Constants
import io.kristofferfj.github.backend.rules.Column.*
import io.kristofferfj.github.backend.rules.Column.b
import io.kristofferfj.github.backend.rules.Piece
import io.kristofferfj.github.backend.rules.Piece.*
import org.junit.jupiter.api.Test

class BoardUtilsTest {

    @Test
    fun atTest() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        val board = position.board
        assert(board.at(a, 1) == R)
        assert(board.at(b, 1) == N)
        assert(board.at(c, 1) == B)
        assert(board.at(d, 1) == Q)
        assert(board.at(e, 1) == K)
        assert(board.at(f, 1) == B)
        assert(board.at(g, 1) == N)
        assert(board.at(h, 1) == R)
        assert(board.at(a, 2) == P)
        assert(board.at(b, 2) == P)
        assert(board.at(c, 2) == P)
        assert(board.at(d, 2) == P)
        assert(board.at(e, 2) == P)
        assert(board.at(f, 2) == P)
        assert(board.at(g, 2) == P)
        assert(board.at(h, 2) == P)
        assert(board.at(a, 7) == p)
        assert(board.at(b, 7) == p)
        assert(board.at(c, 7) == p)
        assert(board.at(d, 7) == p)
        assert(board.at(e, 7) == p)
        assert(board.at(f, 7) == p)
        assert(board.at(g, 7) == p)
        assert(board.at(h, 7) == p)
        assert(board.at(a, 8) == r)
        assert(board.at(b, 8) == n)
        assert(board.at(c, 8) == Piece.b)
        assert(board.at(d, 8) == q)
        assert(board.at(e, 8) == k)
        assert(board.at(f, 8) == Piece.b)
        assert(board.at(g, 8) == n)
        assert(board.at(h, 8) == r)

    }
}