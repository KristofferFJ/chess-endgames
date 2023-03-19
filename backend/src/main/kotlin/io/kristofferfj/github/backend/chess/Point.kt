package io.kristofferfj.github.backend.chess

class Point(val row: Int, val column: Int) {

    companion object {
        private fun Char.column(): Int {
            return this.code - 96
        }

        private fun Char.row(): Int {
            return this.code - 48
        }
    }

    constructor(algebraic: String) : this(algebraic[1].row(), algebraic[0].column())

    fun toAlgebraic(): String {
        return (this.column + 96).toChar().toString() + this.row.toString()
    }

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other !is Point) return false

        return other.row == this.row && other.column == this.column
    }

    enum class Columns { A, B, C, D, E, F, G, H}
    enum class Rows
}
