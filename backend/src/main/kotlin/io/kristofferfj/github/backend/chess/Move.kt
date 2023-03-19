package io.kristofferfj.github.backend.chess

class Move(val from: Square, val to: Square) {
    constructor(algebraic: String) : this(
        Square(algebraic.substring(0, 2)),
        Square(algebraic.substring(2, 4))
    )
}
