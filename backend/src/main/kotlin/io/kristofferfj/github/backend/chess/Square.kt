package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Column
import io.kristofferfj.github.backend.rules.Column.Companion.right
import io.kristofferfj.github.backend.rules.Column.Companion.left

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

    fun upLeft(): Square? {
        if(row == 8 || column == Column.a) return null
        return Square(column.left()!!, row + 1)
    }

    fun downLeft(): Square? {
        if(row == 1 || column == Column.a) return null
        return Square(column.left()!!, row - 1)
    }

    fun upRight(): Square? {
        if(row == 8 || column == Column.h) return null
        return Square(column.right()!!, row + 1)
    }

    fun downRight(): Square? {
        if(row == 1 || column == Column.h) return null
        return Square(column.right()!!, row - 1)
    }

    fun up(): Square? {
        if(row == 8) return null
        return Square(column, row + 1)
    }

    fun down(): Square? {
        if(row == 1) return null
        return Square(column, row - 1)
    }

    fun right(): Square? {
        if(column == Column.h) return null
        return Square(column.right()!!, row)
    }

    fun left(): Square? {
        if(column == Column.a) return null
        return Square(column.left()!!, row)
    }
}
