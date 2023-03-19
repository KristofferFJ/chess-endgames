package io.kristofferfj.github.backend.chess

class Move(val from: Point, val to: Point) {
    constructor(algebraic: String) : this(
        Point(algebraic.substring(0, 2)),
        Point(algebraic.substring(2, 4))
    )
}
