package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.utils.FenUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MoveValidatorTest {

    @Test
    fun castlingValidatorTest() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        Assertions.assertThrows(IllegalStateException::class.java) { position.move("e1g1") }
        position.move("e2e4")
        position.move("e7e5")
        position.move("g1f3")
        position.move("d7d6")
        Assertions.assertThrows(IllegalStateException::class.java) { position.move("e1g1") }
        position.move("f1e2")
        position.move("a7a6")
        position.move("e1g1")
    }
}
