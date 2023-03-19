package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Column

data class Square(val column: Column, val row: Int) {
    constructor(algebraic: String) : this(algebraic[0].toColumn(), algebraic[1].row())

    companion object {
        private fun Char.row(): Int {
            return this.code - 48
        }

        private fun Char.toColumn(): Column {
            return when (this) {
                'a' -> Column.a
                'b' -> Column.b
                'c' -> Column.c
                'd' -> Column.d
                'e' -> Column.e
                'f' -> Column.f
                'g' -> Column.g
                'h' -> Column.h
                else -> throw RuntimeException("what")
            }
        }
    }

    fun toAlgebraic(): String {
        return column.name + row
    }

    fun above(): Square? {
        if(row == 8) return null
        return Square(column, row + 1)
    }

    fun down(): Square? {
        if(row == 1) return null
        return Square(column, row - 1)
    }

    fun right(): Square? {
        if(column == Column.h) return null
        return Square(Column.fromIndex(column.ordinal + 1), row)
    }

    fun left(): Square? {
        if(column == Column.a) return null
        return Square(Column.fromIndex(column.ordinal - 1), row)
    }
}
