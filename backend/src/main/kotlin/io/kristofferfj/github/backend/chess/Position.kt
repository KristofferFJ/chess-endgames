package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.chess.Constants.Companion.BLACK_KING_ROOK_SQUARE
import io.kristofferfj.github.backend.chess.Constants.Companion.BLACK_QUEEN_ROOK_SQUARE
import io.kristofferfj.github.backend.chess.Constants.Companion.KINGS
import io.kristofferfj.github.backend.chess.Constants.Companion.KINGS_AND_ROOKS
import io.kristofferfj.github.backend.chess.Constants.Companion.PAWNS
import io.kristofferfj.github.backend.chess.Constants.Companion.WHITE_KING_ROOK_SQUARE
import io.kristofferfj.github.backend.chess.Constants.Companion.WHITE_QUEEN_ROOK_SQUARE
import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Piece
import io.kristofferfj.github.backend.utils.FenUtils

class Position(
    val board: Board,
    var toMove: Color,
    var whiteKingSideCastle: Boolean,
    var whiteQueenSideCastle: Boolean,
    var blackKingSideCastle: Boolean,
    var blackQueenSideCastle: Boolean,
    var enPassantSquare: Square?,
    var halfMoveClock: Int,
    var fullMoveClock: Int,
) {

    fun move(algebraic: String) {
        return move(Move(algebraic))
    }

    fun move(move: Move) {
        var didCapture = false

        val pieceToMove = board.at(move.from)

        MoveValidator(move, this).validatePreMove()
        if (isCastling(pieceToMove, move)) {
            castle(move, pieceToMove)
        } else {
            didCapture = updateSquaresReturnDidCapture(move, pieceToMove)
        }
        MoveValidator(move, this).validatePostMove()

        this.toMove = if (this.toMove == Color.B) Color.W else Color.B
        updateCastlingPrivileges(pieceToMove, move.from)
        updateEnPassantSquare(move, pieceToMove)
        updateHalfMoveClock(didCapture, pieceToMove)
        updateFullMoveClock()
    }

    private fun isCastling(pieceToMove: Piece, move: Move): Boolean {
        if (!KINGS.contains(pieceToMove)) return false
        if (move.from.column == Column.e && (move.to.column == Column.c || move.to.column == Column.g)) return true
        return false
    }

    private fun updateFullMoveClock() {
        if (this.toMove == Color.W) this.fullMoveClock++
    }

    private fun updateHalfMoveClock(didCapture: Boolean, pieceToMove: Piece) {
        if (didCapture || PAWNS.contains(pieceToMove)) {
            this.halfMoveClock = 0
        } else this.halfMoveClock++
    }


    private fun updateCastlingPrivileges(pieceToMove: Piece, from: Square) {
        if (!KINGS_AND_ROOKS.contains(pieceToMove)) return

        if (pieceToMove == Piece.K) { this.whiteKingSideCastle = false; this.whiteQueenSideCastle = false }
        if (pieceToMove == Piece.k) { this.blackKingSideCastle = false; this.blackQueenSideCastle = false }

        if (from == WHITE_KING_ROOK_SQUARE) this.whiteKingSideCastle = false
        if (from == WHITE_QUEEN_ROOK_SQUARE) this.whiteQueenSideCastle = false
        if (from == BLACK_KING_ROOK_SQUARE) this.blackKingSideCastle = false
        if (from == BLACK_QUEEN_ROOK_SQUARE) this.blackQueenSideCastle = false
    }

    private fun updateEnPassantSquare(move: Move, pieceToMove: Piece) {
        if (!PAWNS.contains(pieceToMove)) {
            this.enPassantSquare = null
            return
        }
        if (move.from.row == 2 && move.to.row == 4) {
            this.enPassantSquare = Square(move.from.column, 3)
        }
        if (move.from.row == 7 && move.to.row == 5) {
            this.enPassantSquare = Square(move.from.column, 6)
        }
    }

    private fun castle(move: Move, pieceToMove: Piece) {
        board.set(move.from, Piece.E)
        board.set(move.to, pieceToMove)
        if (move.to.column == Column.c && move.to.row == 8) {
            board.set(Square(Column.d, 8), Piece.r)
            board.set(Square(Column.a, 8), Piece.E)
        } else if (move.to.column == Column.g && move.to.row == 8) {
            board.set(Square(Column.f, 8), Piece.r)
            board.set(Square(Column.h, 8), Piece.E)
        } else if (move.to.column == Column.c && move.to.row == 1) {
            board.set(Square(Column.d, 1), Piece.R)
            board.set(Square(Column.a, 1), Piece.E)
        } else {
            board.set(Square(Column.f, 1), Piece.R)
            board.set(Square(Column.h, 1), Piece.E)
        }
    }

    private fun updateSquaresReturnDidCapture(move: Move, pieceToMove: Piece): Boolean {
        board.set(move.from, Piece.E)
        val replacedPiece = board.at(move.to)
        board.set(move.to, pieceToMove)

        return replacedPiece != Piece.E
    }

    fun toFen(): String {
        return FenUtils.toFen(this)
    }
}
