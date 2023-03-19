package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.rules.Piece
import io.kristofferfj.github.backend.utils.Constants.Companion.BLACK_KING_ROOK_POINT
import io.kristofferfj.github.backend.utils.Constants.Companion.BLACK_QUEEN_ROOK_POINT
import io.kristofferfj.github.backend.utils.Constants.Companion.KINGS
import io.kristofferfj.github.backend.utils.Constants.Companion.KINGS_AND_ROOKS
import io.kristofferfj.github.backend.utils.Constants.Companion.PAWNS
import io.kristofferfj.github.backend.utils.Constants.Companion.WHITE_KING_ROOK_POINT
import io.kristofferfj.github.backend.utils.Constants.Companion.WHITE_QUEEN_ROOK_POINT
import io.kristofferfj.github.backend.utils.FenUtils

class Position(
    val board: MutableList<MutableList<Piece>>,
    var toMove: Color,
    var whiteKingSideCastle: Boolean,
    var whiteQueenSideCastle: Boolean,
    var blackKingSideCastle: Boolean,
    var blackQueenSideCastle: Boolean,
    var enPassantSquare: Point?,
    var halfMoveClock: Int,
    var fullMoveClock: Int,
) {

    fun move(algebraic: String) {
        var didCapture = false
        val move = Move(algebraic)
        validateLegality()

        val pieceToMove = board.at(move.from)

        if (isCastling(pieceToMove, move)) {
            castle(move, pieceToMove)
        } else {
            didCapture = updateSquaresReturnDidCapture(move, pieceToMove)
        }

        updateCastlingPrivileges(pieceToMove, move.from)
        updateEnPassantSquare(move, pieceToMove)
        updateHalfMoveClock(didCapture, pieceToMove)
        updateFullMoveClock()
        this.toMove = if (this.toMove == Color.B) Color.W else Color.B
    }

    private fun isCastling(pieceToMove: Piece, move: Move): Boolean {
        if (!KINGS.contains(pieceToMove)) return false
        if(move.from.column == 5 && (move.to.column == 3 || move.to.column == 7)) return true
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


    private fun updateCastlingPrivileges(pieceToMove: Piece, from: Point) {
        if (!KINGS_AND_ROOKS.contains(pieceToMove)) return

        if (pieceToMove == Piece.K) this.whiteKingSideCastle = false; this.whiteQueenSideCastle = false
        if (pieceToMove == Piece.k) this.blackKingSideCastle = false; this.blackQueenSideCastle = false

        if (from == WHITE_KING_ROOK_POINT) this.whiteKingSideCastle = false
        if (from == WHITE_QUEEN_ROOK_POINT) this.whiteQueenSideCastle = false
        if (from == BLACK_KING_ROOK_POINT) this.blackKingSideCastle = false
        if (from == BLACK_QUEEN_ROOK_POINT) this.blackQueenSideCastle = false
    }

    private fun updateEnPassantSquare(move: Move, pieceToMove: Piece) {
        if (!PAWNS.contains(pieceToMove)) {
            this.enPassantSquare = null
            return
        }
        if (move.from.row == 2 && move.to.row == 4) {
            this.enPassantSquare = Point(3, move.from.column)
        }
        if (move.from.row == 7 && move.to.row == 5) {
            this.enPassantSquare = Point(6, move.from.column)
        }
    }

    private fun castle(move: Move, pieceToMove: Piece) {
        board.set(move.from, Piece.E)
        board.set(move.to, pieceToMove)
        if(move.to.column == 3) {
            board.set(Point())
        }

    }

    private fun updateSquaresReturnDidCapture(move: Move, pieceToMove: Piece): Boolean {
        board.set(move.from, Piece.E)
        val replacedPiece = board.at(move.to)
        board.set(move.to, pieceToMove)

        return replacedPiece != Piece.E
    }

    private fun validateLegality() {
        //TODO
    }

    fun print(): String {
        return this.board.reversed().joinToString("") {
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

    fun toFen(): String {
        return FenUtils.toFen(this)
    }

    fun List<List<Piece>>.at(point: Point): Piece {
        return this[point.row - 1][point.column - 1]
    }

    fun List<MutableList<Piece>>.set(point: Point, value: Piece) {
        this[point.row - 1][point.column - 1] = value
    }
}
