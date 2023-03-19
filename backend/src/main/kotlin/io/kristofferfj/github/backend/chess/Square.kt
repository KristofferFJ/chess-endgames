package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.rules.Column
import java.lang.RuntimeException

class Square(val column: Column, val row: Int) {

    companion object {
        private fun Char.column(): Int {
            return this.code - 96
        }

        private fun Char.row(): Int {
            return this.code - 48
        }

        private fun Char.toColumn(): Column {
            return when(this) {
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

    constructor(algebraic: String) : this(algebraic[0].toColumn(), algebraic[1].row())

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Square) return false

        return other.row == this.row && other.column == this.column
    }

    fun toAlgebraic(): String {
        return column.name + row
    }
}
