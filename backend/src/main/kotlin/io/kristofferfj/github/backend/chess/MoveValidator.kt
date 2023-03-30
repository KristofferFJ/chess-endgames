package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Column.Companion.left
import io.kristofferfj.github.backend.rules.Column.Companion.right
import io.kristofferfj.github.backend.rules.Piece
import java.lang.RuntimeException

class MoveValidator(private val move: Move, private val position: Position) {
    fun validatePreMove() {
        val pieceToMove = position.board.at(move.from)
        check(move.from != move.to)
        if (position.toMove == Color.W) {
            check(Constants.WHITE_PIECES.contains(pieceToMove))
        } else {
            check(Constants.BLACK_PIECES.contains(pieceToMove))
        }

        check(getSquaresMoveableToByPiece(move.from, pieceToMove).contains(move.to))
    }

    fun validatePostMove() {
        val activeKingSquare = getActiveKingSquare()
        check(!getThreatenedSquares().contains(activeKingSquare))
    }

    private fun getActiveKingSquare(): Square {
        val activeKingPiece = if (position.toMove == Color.W) Piece.K else Piece.k
        position.board.rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, piece ->
                if (piece == activeKingPiece) return Square(Column.fromIndex(columnIndex), rowIndex + 1)
            }
        }
        throw RuntimeException("Piece: $activeKingPiece missing from board=\n${position.board}}")
    }

    private fun getThreatenedSquares(): List<Square> {
        if (position.toMove == Color.W) {
            return getSquaresThreatenedByBlack()
        }
        return getSquaresThreatenedByWhite()
    }

    private fun getSquaresThreatenedByWhite(): List<Square> {
        return getSquaresThreatenedByPieces(Constants.WHITE_PIECES)
    }

    private fun getSquaresThreatenedByBlack(): List<Square> {
        return getSquaresThreatenedByPieces(Constants.BLACK_PIECES)
    }

    private fun getSquaresThreatenedByPieces(pieces: List<Piece>): List<Square> {
        val squares = mutableListOf<Square>()
        position.board.rows.forEachIndexed { rowIndex, line ->
            val row = rowIndex + 1
            line.forEachIndexed { columnIndex, piece ->
                val column = Column.fromIndex(columnIndex)
                if (pieces.contains(piece)) {
                    squares.addAll(getSquaresThreatenedByPiece(Square(column, row), piece))
                }
            }
        }
        return squares
    }

    private fun getSquaresMoveableToByPiece(square: Square, piece: Piece): List<Square> {
        return when (piece) {
            Piece.K -> getSquaresMoveableToByKing(square)
            Piece.k -> getSquaresMoveableToByKing(square)
            Piece.Q -> getSquaresMoveableToByBishop(square) + getSquaresMoveableToByRook(square)
            Piece.q -> getSquaresMoveableToByBishop(square) + getSquaresMoveableToByRook(square)
            Piece.R -> getSquaresMoveableToByRook(square)
            Piece.r -> getSquaresMoveableToByRook(square)
            Piece.B -> getSquaresMoveableToByBishop(square)
            Piece.b -> getSquaresMoveableToByBishop(square)
            Piece.N -> getSquaresMoveableToByKnight(square)
            Piece.n -> getSquaresMoveableToByKnight(square)
            Piece.P -> getSquaresMoveableToByPawn(square)
            Piece.p -> getSquaresMoveableToByPawn(square)
            Piece.E -> emptyList()
        }
    }

    private fun getSquaresMoveableToByPawn(square: Square): List<Square> {
        return if (position.toMove == Color.W) {
            getSquaresMoveableToByWhitePawn(square)
        } else getSquaresMoveableToByBlackPawn(square)
    }

    private fun getSquaresMoveableToByWhitePawn(square: Square): List<Square> {
        val squares = mutableListOf<Square>()
        val up = square.up()
        if (up != null && position.board.at(up) == Piece.E) {
            squares.add(up)
        }
        val upUp = up?.up()
        if (upUp != null && square.row == 2 && position.board.at(upUp) == Piece.E) {
            squares.add(upUp)
        }
        val upLeft = square.upLeft()
        if (upLeft != null &&
            (position.board.at(upLeft).getColor() == Color.B
                    || position.enPassantSquare?.equals(upLeft) == true)
        ) {
            squares.add(upLeft)
        }
        val upRight = square.upRight()
        if (upRight != null &&
            (position.board.at(upRight).getColor() == Color.B
                    || position.enPassantSquare?.equals(upRight) == true)
        ) {
            squares.add(upRight)
        }

        return squares
    }

    private fun getSquaresMoveableToByBlackPawn(square: Square): MutableList<Square> {
        val squares = mutableListOf<Square>()
        val down = square.down()
        if (down != null && position.board.at(down) == Piece.E) {
            squares.add(down)
        }
        val downDown = down?.down()
        if (downDown != null && square.row == 7 && position.board.at(downDown) == Piece.E) {
            squares.add(downDown)
        }
        val downLeft = square.downLeft()
        if (downLeft != null &&
            (position.board.at(downLeft).getColor() == Color.W
                    || position.enPassantSquare?.equals(downLeft) == true)
        ) {
            squares.add(downLeft)
        }
        val downRight = square.downRight()
        if (downRight != null &&
            (position.board.at(downRight).getColor() == Color.W
                    || position.enPassantSquare?.equals(downRight) == true)
        ) {
            squares.add(downRight)
        }

        return squares
    }

    private fun getSquaresMoveableToByKnight(square: Square): List<Square> {
        return getSquaresThreatenedByKnight(square).filter {
            position.board.at(it).getColor() != position.toMove
        }
    }

    private fun getSquaresMoveableToByBishop(square: Square): List<Square> {
        return getSquaresThreatenedByBishop(square).filter {
            position.board.at(it).getColor() != position.toMove
        }
    }

    private fun getSquaresMoveableToByRook(square: Square): List<Square> {
        return getSquaresThreatenedByRook(square).filter {
            position.board.at(it).getColor() != position.toMove
        }
    }

    private fun getSquaresMoveableToByKing(square: Square): List<Square> {
        return getSquaresThreatenedByKing(square).filter {
            position.board.at(it).getColor() != position.toMove
        } + getCastlingSquares()
    }

    private fun getCastlingSquares(): List<Square> {
        return if (position.toMove == Color.W) getWhiteCastlingSquares() else getBlackCastlingSquares()
    }

    private fun getBlackCastlingSquares(): List<Square> {
        val squares = mutableListOf<Square>()
        if (position.blackKingSideCastle
            && position.board.at(Column.f, 8) == Piece.E
            && position.board.at(Column.g, 8) == Piece.E
        ) squares.add(Square(Column.g, 8))
        if (position.blackQueenSideCastle
            && position.board.at(Column.d, 8) == Piece.E
            && position.board.at(Column.c, 8) == Piece.E
            && position.board.at(Column.b, 8) == Piece.E
        ) squares.add(Square(Column.c, 8))

        return squares
    }

    private fun getWhiteCastlingSquares(): List<Square> {
        val squares = mutableListOf<Square>()
        if (position.whiteKingSideCastle
            && position.board.at(Column.f, 1) == Piece.E
            && position.board.at(Column.g, 1) == Piece.E
        ) squares.add(Square(Column.g, 1))
        if (position.whiteQueenSideCastle
            && position.board.at(Column.d, 1) == Piece.E
            && position.board.at(Column.c, 1) == Piece.E
            && position.board.at(Column.b, 1) == Piece.E
        ) squares.add(Square(Column.c, 1))

        return squares
    }

    private fun getSquaresThreatenedByPiece(square: Square, piece: Piece): List<Square> {
        return when (piece) {
            Piece.K -> getSquaresThreatenedByKing(square)
            Piece.k -> getSquaresThreatenedByKing(square)
            Piece.Q -> getSquaresThreatenedByBishop(square) + getSquaresThreatenedByRook(square)
            Piece.q -> getSquaresThreatenedByBishop(square) + getSquaresThreatenedByRook(square)
            Piece.R -> getSquaresThreatenedByRook(square)
            Piece.r -> getSquaresThreatenedByRook(square)
            Piece.B -> getSquaresThreatenedByBishop(square)
            Piece.b -> getSquaresThreatenedByBishop(square)
            Piece.N -> getSquaresThreatenedByKnight(square)
            Piece.n -> getSquaresThreatenedByKnight(square)
            Piece.P -> getSquaresThreatenedByPawn(square)
            Piece.p -> getSquaresThreatenedByPawn(square)
            Piece.E -> emptyList()
        }
    }

    private fun getSquaresThreatenedByPawn(square: Square): List<Square> {
        return if (position.board.at(square) == Piece.P) {
            listOfNotNull(square.upLeft(), square.upRight())
        } else {
            listOfNotNull(square.downLeft(), square.downRight())
        }
    }

    private fun getSquaresThreatenedByKnight(square: Square): List<Square> {
        return listOfNotNull(
            square.up()?.upLeft(), square.up()?.upRight(),
            square.right()?.upRight(), square.right()?.downRight(),
            square.down()?.downRight(), square.down()?.downLeft(),
            square.left()?.downLeft(), square.left()?.upLeft()
        )
    }

    private fun getSquaresThreatenedByBishop(square: Square): List<Square> {
        val squares = mutableListOf<Square?>()

        var upLeft = square.upLeft()
        while (upLeft != null && position.board.at(upLeft) == Piece.E) {
            squares.add(upLeft)
            upLeft = upLeft.upLeft()
        }
        squares.add(upLeft)

        var downLeft = square.downLeft()
        while (downLeft != null && position.board.at(downLeft) == Piece.E) {
            squares.add(downLeft)
            downLeft = downLeft.downLeft()
        }
        squares.add(downLeft)

        var upRight = square.upRight()
        while (upRight != null && position.board.at(upRight) == Piece.E) {
            squares.add(upRight)
            upRight = upRight.upRight()
        }
        squares.add(upRight)

        var downRight = square.downRight()
        while (downRight != null && position.board.at(downRight) == Piece.E) {
            squares.add(downRight)
            downRight = downRight.downRight()
        }
        squares.add(downRight)

        return squares.filterNotNull()
    }

    private fun getSquaresThreatenedByRook(square: Square): List<Square> {
        val squares = mutableListOf<Square?>()

        var above = square.up()
        while (above != null && position.board.at(above) == Piece.E) {
            squares.add(above)
            above = above.up()
        }
        squares.add(above)

        var down = square.down()
        while (down != null && position.board.at(down) == Piece.E) {
            squares.add(down)
            down = down.down()
        }
        squares.add(down)

        var right = square.right()
        while (right != null && position.board.at(right) == Piece.E) {
            squares.add(right)
            right = right.right()
        }
        squares.add(right)

        var left = square.left()
        while (left != null && position.board.at(left) == Piece.E) {
            squares.add(left)
            left = left.left()
        }
        squares.add(left)

        return squares.filterNotNull()
    }

    private fun getSquaresThreatenedByKing(square: Square): List<Square> {
        val columns = listOfNotNull(square.column.left(), square.column, square.column.right())
        val rows = listOfNotNull(
            if (square.row == 1) null else square.row - 1,
            square.row,
            if (square.row == 8) null else square.row + 1
        )

        val squares = mutableListOf<Square>()
        columns.forEach { column ->
            rows.forEach { row ->
                if (Square(column, row) != square) squares.add(Square(column, row))
            }
        }
        return squares
    }
}
