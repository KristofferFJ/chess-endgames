package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Column.Companion.next
import io.kristofferfj.github.backend.rules.Column.Companion.previous
import io.kristofferfj.github.backend.rules.Piece

class MoveValidator(val move: Move, val position: Position) {

    fun getThreatenedSquares(): List<Square> {
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

    private fun getSquaresThreatenedByPiece(square: Square, piece: Piece): List<Square> {
        return when (piece) {
            Piece.K -> getSquaresThreatenedByKing(square)
            Piece.k -> getSquaresThreatenedByKing(square)
            Piece.R -> getSquaresThreatenedByRook(square)
            Piece.r -> getSquaresThreatenedByRook(square)
        }
    }

    private fun getSquaresThreatenedByRook(square: Square): List<Square> {
        val squares = mutableListOf<Square?>()

        var above = square.above()
        while (above != null && position.board.at(above) == Piece.E) {
            squares.add(above)
            above = above.above()
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
        if (right != null) squares.add(right)

        var left = square.left()
        while (left != null && position.board.at(left) == Piece.E) {
            squares.add(left)
            left = left.left()
        }
        if (left != null) squares.add(left)

        return squares.filterNotNull()
    }

    private fun getSquaresThreatenedByKing(square: Square): List<Square> {
        val columns = listOfNotNull(square.column.previous(), square.column, square.column.next())
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
