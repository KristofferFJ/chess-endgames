package io.kristofferfj.github.backend.rules

import io.kristofferfj.github.backend.rules.Column.Companion.previous
import java.lang.RuntimeException

enum class Column {
    a, b, c, d, e, f, g, h;

    companion object {
        fun fromIndex(index: Int): Column {
            return when (index) {
                0 -> a
                1 -> b
                2 -> c
                3 -> d
                4 -> e
                5 -> f
                6 -> g
                7 -> h
                else -> throw RuntimeException("what")
            }
        }

        fun Column.previous(): Column? {
            if(this == a) return null
            return fromIndex(this.ordinal - 1)
        }

        fun Column.next(): Column? {
            if(this == h) return null
            return fromIndex(this.ordinal + 1)
        }
    }
}
